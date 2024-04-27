
<script>
import { mapState, mapActions } from 'pinia'
import { useCamposAPIStore } from '@/stores/camposAPI'
import Partido from "@/components/Partido.vue"

export default {
    components: { Partido },
    data() {
        return {}
    },
    computed: {
        ...mapState(useCamposAPIStore, ['campos'])
    },
    methods: { ...mapActions(useCamposAPIStore, ['cargarCampos']) },
    mounted(){
        this.cargarCampos();
    }

}
</script>

<template>
    <div class="container">
        <h1 class="titulo p-4">Listado de campos</h1>

        <ul>
            <li v-for="campo in campos" :key = "campo._links.self.href">
                <Partido :camposprop = "campo"></Partido>
            </li>
        </ul>
    </div>
</template>

<style scoped>
.read-the-docs {
    color: #888;
}
</style>
