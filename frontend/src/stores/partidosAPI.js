import { defineStore } from "pinia";
import {
    getPartidos,
    getPartidoPuntuaciones,
    getPartidosHistoricos,
    getPartidosPorConfirmar,
    getPartidosValidados,
} from "@/stores/APIservice.js";

export const usePartidosAPIStore = defineStore("partidosAPI", {
    state: () => ({
        partidos: [],
        partidosHistoricos: [],
        partidosValidados: [],
        partidosPorConfirmar: [],
    }),

    actions: {
        async cargarPartidosGenerales(tipoDeCarga, propiedadPartidos) {
            const response = await tipoDeCarga();
            if (
                response.data &&
                response.data._embedded &&
                response.data._embedded.partidos.length > 0
            ) {
                this[propiedadPartidos] = await Promise.all(
                    response.data._embedded.partidos.map(async (partido) => {
                        const partidoId = this.obtenerId(partido);
                        const puntuacionesResponse = await getPartidoPuntuaciones(
                            partidoId
                        );
                        partido.puntuaciones =
                            puntuacionesResponse.data._embedded.puntuaciones;
                        return partido;
                    })
                );
                console.log("Partidos con puntuaciones:", this[propiedadPartidos]);
                return true;
            }
            this[propiedadPartidos] = [];
            return false;
        },
        obtenerId(entidad) {
            return entidad._links.self.href.split('/').pop();
        },     
        async cargarPartidos() {
            return await this.cargarPartidosGenerales(getPartidos, "partidos");
        },
        
        async cargarPartidosHistoricos() {
            return await this.cargarPartidosGenerales(
                getPartidosHistoricos,
                "partidosHistoricos"
            );
        },
        async cargarPartidosValidados() {
            return await this.cargarPartidosGenerales(
                getPartidosValidados,
                "partidosValidados"
            );
        },
        async cargarPartidosPorConfirmar() {
            return await this.cargarPartidosGenerales(
                getPartidosPorConfirmar,
                "partidosPorConfirmar"
            );
        },        
    },
});
