<script>
import { usePartidosAPIStore } from '@/stores/partidosAPI'
import { usePuntuacionesAPIStore } from '@/stores/puntuacionesAPI'
import { mapState, mapActions } from 'pinia'
import Partido from '@/components/Partido.vue'

export default {
    components: {
        Partido
    },
    data() {
        return {
            activeTab: 'sinConfirmar',
        }
    },
    computed: {
        ...mapState(usePartidosAPIStore, ['partidos', 'partidosHistoricos', 'partidosValidados', 'partidosPorConfirmar', 'partidosCargados']),
        ...mapState(usePuntuacionesAPIStore, ['debeRecargar']),
    },
    methods: {
        ...mapActions(usePartidosAPIStore, ['cargarPartidos', 'cargarPartidosHistoricos', 'cargarPartidosValidados', 'cargarPartidosPorConfirmar']),
        setActiveTab(tab) {
            this.activeTab = tab;
        },
        setVistaInicial() {
            this.activeTab = 'sinConfirmar'
        }
    },
    mounted() {
        Promise.all([
            this.cargarPartidosPorConfirmar(),
            this.cargarPartidosValidados(),
            this.cargarPartidosHistoricos()
        ]).then(() => {
            this.setVistaInicial();
        })        
    },
}
</script>


<template>
    <div class="container">
        <ul class="nav nav-tabs nav-fill mt-1">
            <li class="nav-item me-1" @click="setActiveTab('sinConfirmar')">
                <div :class="['nav-link', { active: activeTab == 'sinConfirmar' }]">Sin Confirmar</div>
            </li>
            <li class="nav-item me-1" @click="setActiveTab('pendientes')">
                <div :class="['nav-link', { active: activeTab == 'pendientes' }]">Pendientes</div>
            </li>
            <li class="nav-item " @click="setActiveTab('jugados')">
                <div :class="['nav-link', { active: activeTab == 'jugados' }]">Jugados</div>
            </li>
        </ul>
        <div class="container mt-5 mb-3" v-if="partidosCargados == false">
                    <div class="d-flex justify-content-center">
                        <div class="spinner-border" role="status">
                            <span class="visually-hidden">Carganding...</span>
                        </div>
                    </div>
                </div>
        <table class="card">
            <tr v-if="this.activeTab == 'sinConfirmar'" v-for="partido in partidosPorConfirmar" :key="partido._links.self.href">
                <Partido 
                :partido="partido"
                :activeTab="activeTab"
                ></Partido>
            </tr>
            <tr v-if="this.activeTab == 'pendientes'" v-for="partido in partidosValidados" :key="partido._links.self.href">
                <Partido 
                :partido="partido"
                :activeTab="activeTab"
                ></Partido>
            </tr>
            <tr v-if="this.activeTab == 'jugados'" v-for="partido in partidosHistoricos" :key="partido._links.self.href">
                <Partido 
                :partido="partido"
                :activeTab="activeTab"
                ></Partido>
            </tr>
        </table>
    </div>

</template>

<style scoped>
.nav-tabs .nav-link {
    background-color: #395623;
    color: #CCCCCC;
    font-weight: 500;
    border: solid, 2px;
    border-color: #395623;
}

.nav-tabs .nav-link.active {
    background-color: #70AD47;
    color: white;
    font-weight: 500;

}


</style>
