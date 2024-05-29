import { defineStore } from "pinia";
import {
    getPartidos,
    postPartido,
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
        async enviarPartido(partido) {
            const response = await postPartido(partido);
            if (response.status == 200 || response.status == 201) {
                const { _links, ...partidoCreado } = response.data;
                this.partidos.push(partidoCreado);
                console.log(
                    "Datos del partido creado devuelto por la api: ",
                    partidoCreado
                );
                this.cargarPartidos();
                const url = _links.self.href;
                console.log("link del partido devuelto por la api", url)
                this.debeRecargar = true
                return url;
            }
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