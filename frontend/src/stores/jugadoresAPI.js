import { defineStore } from 'pinia'
import { getFederados, getPrincipiantes } from '@/stores/APIservice'

export const useJugadoresAPIStore = defineStore('jugadoresAPI', {
    state: () => ({
        federados: [],
        principiantes: [],
        jugadores: [],
        federadosCargados: false,
        principiantesCargados: false,
        jugadoresCargados: false
    }),
        actions: {
            async cargarFederados() {
                this.federadosCargados = false
                await getFederados().then((response) => {
                    if (response.data._embedded) {
                        const federados = response.data._embedded.federados
                        this.federados = federados
                    }
                    this.federadosCargados = true
                    console.log("En el store: ", this.federados)
                })
            },
            
            async cargarPrincipiantes() {
                this.principiantesCargados = false
                await getPrincipiantes().then((response) => {
                    if (response.data._embedded) {
                        const principiantes = response.data._embedded.principiantes
                        this.principiantes = principiantes
                    }
                    this.principiantesCargados = true
                    console.log("En el store: ", this.principiantes)
                })
            },

            async cargarJugadores() {
                this.principiantesCargados = false
                await Promise.all([this.cargarFederados(), this.cargarPrincipiantes()])
                this.jugadores = [...this.federados, ...this.principiantes]
                this.jugadoresCargados = true;
                console.log('Jugadores cargados:', this.jugadores)
            }
        }
})

