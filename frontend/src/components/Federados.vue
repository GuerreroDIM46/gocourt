<script>
import navbar2 from "@/components/navbar2.vue"
import Federado from "@/components/Federado.vue"
import { mapState, mapActions } from 'pinia'
import { useJugadoresAPIStore } from '@/stores/jugadoresAPI'

export default {
    components: { navbar2, Federado },
    data() {
        return {}
    },
    computed: {
        ...mapState(useJugadoresAPIStore, ['federados'])
    },
    methods: { ...mapActions(useJugadoresAPIStore, ['cargarFederados']) },
    mounted() {
        this.cargarFederados();
    }

}
</script>

<template>
    <div class="container">
    <div>
        <navbar2></navbar2>
    </div>
    <div class="card text-center">
        <div class="card-header contenedortitulo">
            <div style="margin-left: 10px;">Nombre</div>
            <div class="crecer"></div>
            <div style="margin-right: 10px;">PC</div>
            <div style="margin-right: 5px;">PL</div>
            <div style="margin-right: 95px;">HC</div>
        </div>
        <div class="card-body">
            <div v-for="federado in federados" :key="federado._links.self.href">
                <Federado :federadosprop="federado"></Federado>
            </div>
        </div>
        <div class="centrar">
            <button type="button" class="btn btn-success" @click="abrirModalNuevoPartido">Añadir Jugador</button>
        </div>
    </div>
</div>

</template>

<style scoped>
.centrar {
    text-align: center;
    /* Alinear horizontalmente */
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
</style>