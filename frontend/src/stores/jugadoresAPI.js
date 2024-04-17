import { defineStore } from 'pinia'
import { getFederados, getPrincipiantes } from '@/stores/APIservice'

export const useJugadoresAPIStore = defineStore('jugadoresAPI', {
    state: () => ({
        federados: [],
        principiantes: [],
        jugadores: [],
        federadosCargados: false,
        principiantesCargados: false,
    }),
    actions: {
        async cargarFederados() {
            this.federadosCargados = false;
            await getFederados().then((response) => {
                if (response.data._embedded) {
                    const federadosConTipo = response.data._embedded.federados.map(federado => ({
                        ...federado,
                        tipo: 'federado'
                    }));
                    this.federados = federadosConTipo;
                }
                this.federadosCargados = true;
                this.actualizarTodosJugadores();
            });
        },
        async cargarPrincipiantes() {
            this.principiantesCargados = false;
            await getPrincipiantes().then((response) => {
                if (response.data._embedded) {
                    const principiantesConTipo = response.data._embedded.principiantes.map(principiante => ({
                        ...principiante,
                        tipo: 'principiante'
                    }));
                    this.principiantes = principiantesConTipo;
                }
                this.principiantesCargados = true;
                this.actualizarTodosJugadores();
            });
        },
        actualizarTodosJugadores() {
            this.jugadores = [...this.federados, ...this.principiantes]
                .sort((a, b) => a.nombre.localeCompare(b.nombre))
                console.log("En el store: ", this.jugadores)
        }
    }
});
