import { defineStore } from 'pinia'
import {
    getFederados,
    getPrincipiantes,
    postJugador,
    putJugador,
    deleteJugador,
    getJugadoresSimilares
} from '@/stores/APIservice'

export const useJugadoresAPIStore = defineStore('jugadoresAPI', {
    state: () => ({
        federados: [],
        principiantes: [],
        jugadores: [],
        jugadoresSimilares: [],
        jugadoresSimilaresCargados: false,
        federadosCargados: false,
        principiantesCargados: false,
        debeRecargar: false,   // para el watcher despues de crear o actualizar
    }),
    actions: {
        async cargarFederados() {
            const response = await getFederados();
            if (response.data._embedded) {
                const federadosConTipo = response.data._embedded.federados.map(federado => ({
                    ...federado,
                    tipo: 'federado'
                }));
                this.federados = federadosConTipo
                this.federadosCargados = true
                this.actualizarTodosJugadores()
            }
        },
        async cargarPrincipiantes() {
            const response = await getPrincipiantes();
            if (response.data._embedded) {
                const principiantesConTipo = response.data._embedded.principiantes.map(principiante => ({
                    ...principiante,
                    tipo: 'principiante'
                }));
                this.principiantes = principiantesConTipo;
                this.principiantesCargados = true
                this.actualizarTodosJugadores()
            }
        },
        async cargarJugadoresSimilares(jugador) {
            const jugadorId = jugador._links.self.href.split('/').pop()
            console.log("el jugadorId que le paso a quien sea es ", jugadorId)
            const response = await getJugadoresSimilares(jugadorId)
            console.log(response)
            if (response && response.data && response.data._embedded) {
                const federados = response.data._embedded.federados || [];
                const principiantes = response.data._embedded.principiantes || [];
                // Añade un campo tipo a cada jugador para identificar si es federado o principiante
                const federadosConTipo = federados.map(jugador => ({ ...jugador, tipo: 'federado' }));
                const principiantesConTipo = principiantes.map(jugador => ({ ...jugador, tipo: 'principiante' }));
                // Concatena los dos arrays
                this.jugadoresSimilares = [...federadosConTipo, ...principiantesConTipo];
                this.jugadoresSimilaresCargados = true;
            } else {
                this.jugadoresSimilaresCargados = false;
            }
        }, 
        actualizarTodosJugadores() {
            this.jugadores = [...this.federados, ...this.principiantes].sort((a, b) => a.nombre.localeCompare(b.nombre))
            
        },
        async crearJugador(jugador) {
            console.log("Datos del jugador a enviar (JugadoresAPI): ", jugador)
            const response = await postJugador(jugador)
            const { data } = response
            const { _links, ...jugadorCreado } = data
            console.log("Datos del jugador creado devuelto por la api: ", jugadorCreado)
            if (response.status === 200 || response.status === 201) {
                this.jugadores.push(jugadorCreado)
                this.actualizarTodosJugadores()
                this.debeRecargar = true
            }
        },
        async actualizarJugador(jugador) {
            const { url, ...jugadorSinUrl } = jugador;
            console.log('jugador enviado desde el store la api: ', jugadorSinUrl)
            console.log('id de jugador (store): ', url)
            const response = await putJugador(jugadorSinUrl, url)
            console.log(response)
            const index = this.jugadores.findIndex(j => j._links.self.href === url)
            console.log('Índice del jugador a actualizar en el store: ', index)
            if (index !== -1) {
                this.jugadores[index] = {
                    ...this.jugadores[index], ...jugadorSinUrl
                }
                this.actualizarTodosJugadores();
                this.debeRecargar = true
            }
        },
                
        async eliminarJugador(jugadorId) {
            console.log("En el store, jugador a borrar: ", jugadorId)
            const response = await deleteJugador(jugadorId)
            console.log("Respuesta de la api al borrar jugador: ", response)
            if (response.status === 200) {
                const index = this.jugadores.findIndex(p => p._links.self.href === jugadorId)
            if (index !== -1) {
                this.jugadores.splice(index, 1)
                }
            }
        },
        resetRecarga() {
            this.debeRecargar = false;
            console.log("he accedido a reset recarga")
        }
    }
})
