import { defineStore } from 'pinia'
import { getCampos } from '@/stores/APIservice.js'

export const useCamposAPIStore = defineStore('camposAPI', {
    state: () => ({
        campos: [],
        camposCargados: false,
    }),

    actions: {
        async cargarCampos() {
            this.camposCargados = false
            await getCampos().then((response) => {
                if (response.data._embedded) {
                    const campos = response.data._embedded.campos
                    this.campos = campos
                }
                this.camposCargados = true                
            })
        },
    }
});