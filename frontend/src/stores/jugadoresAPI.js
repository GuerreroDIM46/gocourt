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
        federadosCargados: false,
        principiantesCargados: false,
        debeRecargar: false,   // para el watcher despues de crear o actualizar        
    }),
    actions: {
        async cargarFederados() {
            const response = await getFederados()
            if (response.data._embedded) {
                this.federados = response.data._embedded.federados
                this.federadosCargados = true
                this.cargarJugadores()
            }
        },
        async cargarPrincipiantes() {
            const response = await getPrincipiantes()
            if (response.data._embedded) {                
                this.principiantes = response.data._embedded.principiantes
                this.principiantesCargados = true
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
            console.log(response)
            if (response && response.data && response.data._embedded) {
                const federados = response.data._embedded.federados || []
                const principiantes = response.data._embedded.principiantes || []
                this.jugadoresSimilares = [...federados, ...principiantes]
            }
        },
        cargarJugadores() {
            this.jugadores = [...this.federados, ...this.principiantes].sort((a, b) => a.nombre.localeCompare(b.nombre))

        },
        async crearJugador(jugador) {
            console.log("Datos del jugador a enviar (JugadoresAPI): ", jugador)
            const response = await postJugador(jugador)
            const { data } = response
            const { _links, ...jugadorCreado } = data
            console.log("Datos del jugador creado devuelto por la api: ", jugadorCreado)
            if (response.status == 200 || response.status == 201) {
                this.jugadores.push(jugadorCreado)
                this.cargarJugadores()
                this.debeRecargar = !this.debeRecargar
            }
        },
        async actualizarJugador(jugador) {
            const { url, ...jugadorSinUrl } = jugador
            console.log('jugador enviado desde el store la api: ', jugadorSinUrl)
            console.log('id de jugador (store): ', url)
            const response = await putJugador(jugadorSinUrl, url)
            console.log(response)
            const index = this.jugadores.findIndex(j => j._links.self.href == url)
            console.log('Índice del jugador a actualizar en el store: ', index)
            if (index != -1) {
                this.jugadores[index] = {
                    ...this.jugadores[index], ...jugadorSinUrl
                }
                this.cargarJugadores()
                this.debeRecargar = !this.debeRecargar
            }
        },

        async eliminarJugador(jugadorId) {
            console.log("En el store, jugador a borrar: ", jugadorId)
            const response = await deleteEntidad(jugadorId)
            console.log("Respuesta de la api al borrar jugador: ", response)
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
