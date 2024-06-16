import { defineStore } from 'pinia'
import {
    getFederados,
    getPrincipiantes,
    postJugador,
    putJugador,
    deleteEntidad,
    getJugadoresSimilares,
    getFederadosSimilares
} from '@/stores/APIservice'

export const useJugadoresAPIStore = defineStore('jugadoresAPI', {
    state: () => ({
        federados: [],
        principiantes: [],
        jugadores: [],
        jugadoresSimilares: [],
        jugadoresCargados: false,
        debeRecargar: false,         
    }),
    actions: {
        async cargarFederados() {
            const response = await getFederados()
            if (response.data._embedded) {
                this.federados = response.data._embedded.federados
                this.cargarJugadores()
            }
        },
        async cargarPrincipiantes() {
            const response = await getPrincipiantes()
            if (response.data._embedded) {                
                this.principiantes = response.data._embedded.principiantes
                this.cargarJugadores()
            }
        },
        async cargarJugadoresSimilares(jugador) {
            const jugadorId = jugador._links.self.href.split('/').pop()
            let response
            if (jugador.tipo == 'federado') {
                response = await getJugadoresSimilares(jugadorId)
            } else if (jugador.tipo == 'principiante') {
                response = await getFederadosSimilares(jugadorId)
            }
            if (response && response.data && response.data._embedded) {
                const federados = response.data._embedded.federados || []
                const principiantes = response.data._embedded.principiantes || []
                this.jugadoresSimilares = [...federados, ...principiantes]
            }
        },
        cargarJugadores() {
            this.jugadores = [...this.federados, ...this.principiantes].sort((a, b) => a.nombre.localeCompare(b.nombre))
            this.jugadoresCargados = true
        },
        async crearJugador(jugador) {
            const response = await postJugador(jugador)
            const { data } = response
            const { _links, ...jugadorCreado } = data
            if (response.status == 200 || response.status == 201) {
                this.jugadores.push(jugadorCreado)
                this.cargarJugadores()
                this.debeRecargar = !this.debeRecargar
            }
        },
        async actualizarJugador(jugador) {
            const { url, ...jugadorSinUrl } = jugador
            const response = await putJugador(jugadorSinUrl, url)
            const index = this.jugadores.findIndex(j => j._links.self.href == url)
            if (index != -1) {
                this.jugadores[index] = {
                    ...this.jugadores[index], ...jugadorSinUrl
                }
                this.cargarJugadores()
                this.debeRecargar = !this.debeRecargar
            }
        },

        async eliminarJugador(jugadorId) {
            const response = await deleteEntidad(jugadorId)
            if (response.status == 200) {
                const index = this.jugadores.findIndex(p => p._links.self.href == jugadorId)
                if (index != -1) {
                    this.jugadores.splice(index, 1)
                    this.debeRecargar = !this.debeRecargar
                }
            }
        },
    }
})
