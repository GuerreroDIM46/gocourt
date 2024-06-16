import { defineStore } from 'pinia'
import { getPuntuaciones, postAsignacion, putPuntuacion, deleteEntidad } from '@/stores/APIservice.js'

export const usePuntuacionesAPIStore = defineStore('puntuacionesAPI', {
    state: () => ({
        puntuaciones: [],
        puntuacionesCargados: false,
        debeRecargar: false
    }),

    actions: {
        async cargarPuntuaciones() {
            this.puntuacionesCargados = false
            await getPuntuaciones().then((response) => {
                if (response.data._embedded) {
                    const puntuaciones = response.data._embedded.puntuaciones
                    this.puntuaciones = puntuaciones
                }
                this.puntuacionesCargados = true
            })
        },
        async crearAsignacion(asignacion) {
            try {
                const response = await postAsignacion(asignacion)
                if (response.status == 200 || response.status == 201) {
                    const { _links, ...asignacionCreada } = response.data
                    this.puntuaciones.push(asignacionCreada)
                    this.cargarPuntuaciones()
                    const url = _links.self.href
                    this.debeRecargar = !this.debeRecargar
                    return url
                }
            } catch (error) {
                if (error.response && error.response.status == 409) {
                    return "error"
                } else {
                    throw error
                }
            }
        },
        async actualizarPuntuacion(asignacion) {
            const { url, ...asignacionSinUrl } = asignacion
            try {
                const response = await putPuntuacion(asignacionSinUrl, url)
                if (response.status == 200) {
                    return 'OK'
                }
            } catch (error) {
                if (error.response && error.response.status == 409) {
                    return "error"
                } else {
                    throw error
                }
            }
        },
        async eliminarPuntuacion(puntuacionHref) {
            const response = await deleteEntidad(puntuacionHref)
        },   
    }
})