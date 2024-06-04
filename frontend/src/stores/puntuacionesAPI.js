import { defineStore } from 'pinia'
import { getPuntuaciones, postAsignacion, putPuntuacion } from '@/stores/APIservice.js'

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
                    console.log(puntuaciones)
                }
                this.puntuacionesCargados = true
            })
        },
        async crearAsignacion(asignacion) {
            const response = await postAsignacion(asignacion)
            if (response.status == 200 || response.status == 201) {
                const { _links, ...asignacionCreada } = response.data
                this.puntuaciones.push(asignacionCreada)
                console.log("Datos de la asignacion creada devuelta por la api: ", asignacionCreada)
                this.cargarPuntuaciones()
                const url = _links.self.href
                console.log("link de la asignacion devuelta por la api", url)
                this.debeRecargar = !this.debeRecargar
                return url
            }
        },
        async actualizarPuntuacion(asignacion) {
            const { url, ...asignacionSinUrl } = asignacion
            const response = await putPuntuacion(asignacionSinUrl, url)
            if (response.status == 200 || response.status == 201) {    
                console.log(response.data)
            }        
        },
    }
})