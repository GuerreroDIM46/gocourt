<script>
import navbar2 from "@/components/navbar2.vue"
import Federado from "@/components/Federado.vue"
import { mapState, mapActions } from 'pinia'
import { useJugadoresAPIStore } from '@/stores/jugadoresAPI'

export default {
    components: { navbar2, Federado },
    computed: {
        ...mapState(useJugadoresAPIStore, {
            federados: state => state.federados
        }),
        totalPaginas() {
            return this.store.totalPaginasFederados;
        }
    },
    data() {
        return {
            store: useJugadoresAPIStore()
        };
    },
    methods: {
        cambiarPagina(pagina) {
            this.store.cambiarPaginaFederados(pagina);
        }
    },
    mounted() {
        this.store.cargarFederados();
    }
}
</script>

<template>
    <div class="container">
        <navbar2 />
        <table class="card">
            <tr class="card-header contenedortitulo">
                <td class="w-100">
                    <!-- Contenedor principal con flex -->
                    <div class="d-flex flex-column flex-md-row align-items-start justify-content-md-between">
                        <!-- Botón Nuevo Jugador, aparece primero y a la izquierda -->
                        <button type="button" class="btn btn-outline-success mb-2 mb-md-0 order-1 order-md-2">
                            Nuevo Jugador <font-awesome-icon class="me-2" :icon="['fas', 'user-plus']" />
                        </button>
                        <!-- Paginación -->
                        <ul class="pagination mb-0 order-2 order-md-1">
                            <li class="page-item" @click="store.paginaAnteriorFederados">
                                <a class="page-link">Anterior</a>
                            </li>
                            <li v-for="pagina in totalPaginas" :key="pagina"
                                :class="{ 'page-item': true, 'active': pagina === store.paginaFederados }">
                                <a class="page-link" @click="cambiarPagina(pagina)">{{ pagina }}</a>
                            </li>
                            <li class="page-item" @click="store.paginaSiguienteFederados">
                                <a class="page-link">Siguiente</a>
                            </li>
                        </ul>
                    </div>
                </td>
            </tr>
            <tr v-for="federado in federados" :key="'federado-' + federado.id">
                <Federado :federadosprop="federado" />
            </tr>
        </table>
    </div>
</template>


<style scoped>
.centrar {
    text-align: center;
}

.contenedortitulo {
    display: flex;
    flex-direction: row;
}

.crecer {
    flex-grow: 1;
}

.verdeclaro {
    background-color: #70AD47;
    color: white;
    font-weight: 500;
}

.verdeoscuro {
    background-color: #395623;
    color: #CCCCCC;
    font-weight: 500;
    border: 1px;
}

.bordeverdeclaro {
    border-color: #70AD47;
}


.pagination {
    margin-bottom: 0px;
}

.pagination>.active>a {
    color: white;
    background-color: #395623 !Important;
}

.pagination>li>a {
    background-color: white;
    color: #70AD47;
}

.pagination > li > a:hover {
  background-color: #70AD47; /* Color de fondo al pasar el mouse */
  color: white; /* Color del texto al pasar el mouse */
}

.btn-outline-success:hover {
    background-color: #70AD47 !important;
    border-color: #70AD47;
    color: white;
}

.btn-outline-success {
    border-color: #395623;
    color: #395623;
}

</style>