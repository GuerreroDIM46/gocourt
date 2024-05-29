<script>
export default {
    data() {
        return {
            nombreCompletoUsuarioActual: ''
        }
    },
    props: ['partido', 'activeTab'],
    computed: {
        maximaPuntuacion() {
            return Math.min(...this.partido.puntuaciones.map(p => p.puntuacion))
        }
    },
}
</script>

<template>
    <div class="casilla">
        <table class="table-container">
            <tr>
                <td>
                    <strong>{{ partido.nombreCampo }}</strong>, {{ $formatDate(partido.cuando, 'PP') }}
                </td>
            </tr>
            <tr v-for="puntuacion in partido.puntuaciones" :key="puntuacion._links.self.href" class="jugador">
                <td>
                    <div class="puntuacion-container">
                        <span v-if="puntuacion.nombreCompleto != this.nombreCompletoUsuarioActual || activeTab == 'jugados'">
                            <div v-if="puntuacion.tipo == 'federado'" class="badge bg-secondary me-2">Federado</div>
                            <div v-if="puntuacion.tipo == 'principiante'" class="badge bg-success me-2">Principiante
                            </div>
                            <span>{{ puntuacion.nombreCompleto }}</span>
                        </span>
                        <div v-if="activeTab == 'jugados'"
                            :class="['badge', puntuacion.puntuacion == maximaPuntuacion ? 'bg-success' : 'bg-danger']">
                            {{ puntuacion.puntuacion }}</div>
                        <div v-if="activeTab != 'jugados' && puntuacion.aceptado == true && puntuacion.nombreCompleto != this.nombreCompletoUsuarioActual" class="badge bg-success me-2">
                            Aceptado</div>
                        <div v-if="activeTab != 'jugados' && puntuacion.aceptado == false && puntuacion.nombreCompleto != this.nombreCompletoUsuarioActual" class="badge bg-danger me-2">
                            No aceptado</div>
                    </div>

                </td>
            </tr>
        </table>
    </div>
</template>

<style scoped>
.table-container {
    width: 100%;
}
.puntuacion-container {
    display: flex;
    justify-content: space-between;
    align-items: center;
}
.casilla {
    border-bottom: 1px solid #ccc;
    padding-bottom: 8px;
    margin-bottom: 10px;
}

.jugador {
    padding: 4px;
}

.badge {
    padding: 0.5em 1em;
    border-radius: 0.25em;
}


</style>