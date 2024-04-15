import { defineStore } from 'pinia'
import { getFederados, getPrincipiantes } from '@/stores/APIservice'

export const useJugadoresAPIStore = defineStore('jugadoresAPI', {
    state: () => ({
        federados: [],
        principiantes: [],
        paginaFederados: 1,
        paginaPrincipiantes: 1,
        tamanoPagina: 20,
        totalPaginasFederados: 0,
        totalPaginasPrincipiantes: 0,
    }),
    actions: {
        async cargarFederados() {
            this.federadosCargados = false;
            const response = await getFederados(this.paginaFederados - 1, this.tamanoPagina);
            if (response.data._embedded) {
                this.federados = response.data._embedded.federados;
                this.totalPaginasFederados = response.data.page.totalPages; // Asegúrate de actualizar el total de páginas
            }
            this.federadosCargados = true;
            
            
        },
        
        async cargarPrincipiantes() {
            this.principiantesCargados = false;
            const response = await getPrincipiantes(this.paginaPrincipiantes - 1, this.tamanoPagina);
            if (response.data._embedded) {
                this.principiantes = response.data._embedded.principiantes;
                this.totalPaginasPrincipiantes = response.data.page.totalPages;
            }
            this.principiantesCargados = true;
            return response; // Asegúrate de devolver la respuesta
        },
        
        cambiarPaginaFederados(nuevaPagina) {
            this.paginaFederados = nuevaPagina;
            this.cargarFederados();
            console.log("En el store: ", this.principiantes)
        },
        
        cambiarPaginaPrincipiantes(nuevaPagina) {
            this.paginaPrincipiantes = nuevaPagina;
            this.cargarPrincipiantes();
        },
        
        paginaAnteriorFederados() {
            if (this.paginaFederados > 1) {
                this.cambiarPaginaFederados(this.paginaFederados - 1);
                console.log("En el store: ", this.principiantes)
            }
        },
        
        paginaSiguienteFederados() {
            if (this.paginaFederados < this.totalPaginasFederados) {
                this.cambiarPaginaFederados(this.paginaFederados + 1);
                console.log("En el store: ", this.principiantes)
            }
        },
        
        paginaAnteriorPrincipiantes() {
            if (this.paginaPrincipiantes > 1) {
                this.cambiarPaginaPrincipiantes(this.paginaPrincipiantes - 1);
            }
        },
        
        paginaSiguientePrincipiantes() {
            if (this.paginaPrincipiantes < this.totalPaginasPrincipiantes) {
                this.cambiarPaginaPrincipiantes(this.paginaPrincipiantes + 1);
            }
        }
    }
})
