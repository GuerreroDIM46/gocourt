import { defineStore } from "pinia"
import {
    getPartidos,
    postPartido,
    getPartidoPuntuaciones,
    getPartidosHistoricos,
    getPartidosPorConfirmar,
    getPartidosValidados,
    putPartido,
    getPartido,
    deleteEntidad
} from "@/stores/APIservice.js";

export const usePartidosAPIStore = defineStore("partidosAPI", {
    state: () => ({
        partidos: [],
        partidosHistoricos: [],
        partidosValidados: [],
        partidosPorConfirmar: [],
        partidoCompleto: [],
        partidosCargados: false
    }),

    actions: {
        async cargarPartidosGenerales(tipoDeCarga, propiedadPartidos) {
            const response = await tipoDeCarga()
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
                        )
                        partido.puntuaciones =
                            puntuacionesResponse.data._embedded.puntuaciones
                        return partido;
                    })
                )
                console.log("Partidos con puntuaciones:", this[propiedadPartidos])
                this.partidosCargados = true
                return true
            }
            this[propiedadPartidos] = []
            return false
        },
        obtenerId(entidad) {
            return entidad._links.self.href.split('/').pop()
        },     
        async cargarPartidos() {
            return await this.cargarPartidosGenerales(getPartidos, "partidos")
        },
        async cargarPartido(id) {
            const response = await getPartido(id)
            const partido = response.data;
            const puntuacionesResponse = await getPartidoPuntuaciones(id);
            const puntuaciones = puntuacionesResponse.data._embedded.puntuaciones;
            console.log('el partido es: ', partido)
            console.log('las puntuaciones son: ', puntuaciones)
            partido.puntuaciones = puntuaciones
            this.partidoCompleto = partido           
        },
        async enviarPartido(partido) {
            try {
                const response = await postPartido(partido);
                if (response.status === 200 || response.status === 201) {
                    const { _links, ...partidoCreado } = response.data;
                    this.partidos.push(partidoCreado);
                    console.log("Datos del partido creado devuelto por la api: ", partidoCreado);
                    this.cargarPartidos();
                    const url = _links.self.href;
                    console.log("link del partido devuelto por la api", url);
                    this.debeRecargar = true;
                    return url;
                }
            } catch (error) {
                if (error.response && error.response.status === 409) {
                    console.log("Ya existe un partido programado para ese horario en ese campo.");
                    return "error";
                } else {
                    console.error("Error al enviar el partido: ", error);
                    throw error; 
                }
            }
        },
        async actualizarPartido(partido) {
            const { url, ...partidoSinUrl } = partido
            console.log('partido enviado desde el store la api: ', partidoSinUrl)
            console.log('id de partido (store): ', url)
            try {
                const response = await putPartido(partidoSinUrl, url)
                if (response.status == 200) {
                    return 'OK'
                }
            } catch (error) {
                if (error.response && error.response.status == 409) {
                    console.log("Ya existe un partido programado para ese horario en ese campo.");
                    return "error";
                } else {
                    console.error("Error al enviar el partido: ", error);
                    throw error;
                }
            }
        },
        async cargarPartidosHistoricos() {
            return await this.cargarPartidosGenerales(
                getPartidosHistoricos,
                "partidosHistoricos"
            )
        },
        async cargarPartidosValidados() {
            return await this.cargarPartidosGenerales(
                getPartidosValidados,
                "partidosValidados"
            )
        },
        async cargarPartidosPorConfirmar() {
            return await this.cargarPartidosGenerales(
                getPartidosPorConfirmar,
                "partidosPorConfirmar"
            )
        },
        async eliminarPartido(partidoHref) {
            const response = await deleteEntidad(partidoHref)
            console.log("Respuesta de la api al borrar partido: ", response)
        },        
    },
});
