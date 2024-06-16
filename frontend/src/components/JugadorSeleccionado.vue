<script>
export default {
    props: {
        jugadorSimilar: {},
        jugadorViendo: {}
    },
    computed: {
        buttonText() {
        return this.$route.path == '/estado' ? 'Retar' : 'Asignar';
        }
    },
    emits: ['abrir-modal-jugador-asignar', 'cambiar-jugador'],
    methods: {
        abrirModalJugadorAsignar() {
            console.log('jugador actual en jugadorseleccionado: ', this.jugadorViendo)
            console.log('jugador similar en jugadorseleccionado: ', this.jugadorSimilar)
            this.$emit('abrir-modal-jugador-asignar', this.jugadorViendo, this.jugadorSimilar)
        },
        cambiarJugador() {
            this.$emit('cambiar-jugador', this.jugadorSimilar)
        }
    }
}
</script>

<template>
    <li class="list-group-item">
        <div class="jugador-info-container">
            <div class="jugador-info">
                <div class="jugador-datos cursor-pointer" @click="cambiarJugador">
                    <div class="nombre-apellidos">
                        <div v-if="jugadorSimilar.tipo == 'federado'" class="badge bg-secondary me-2">Federado</div>
                        <div v-if="jugadorSimilar.tipo == 'principiante'" class="badge bg-success me-2">Principiante
                        </div>
                        <div v-if="jugadorSimilar.profesional" class="badge bg-warning me-2">PRO</div>
                        <strong>{{ jugadorSimilar.nombre }} {{ jugadorSimilar.apellido1 }} {{ jugadorSimilar.apellido2
                            }}</strong>
                    </div>
                    <div v-if="jugadorSimilar.tipo == 'federado'" class="handicap">
                        Handicap: {{ jugadorSimilar.handicap.toFixed(1) }}
                    </div>
                    <div v-if="jugadorSimilar.tipo == 'principiante'" class="handicap">
                        Handicap Calculado: {{ jugadorSimilar.handicap.toFixed(1) }}
                    </div>
                </div>
            </div>
            <div class="boton-asignar">
                <button type="button" class="btn btn-primary" @click="abrirModalJugadorAsignar">{{ buttonText }}</button>
            </div>
        </div>
    </li>
</template>

<style scoped>
.jugador-info-container {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 10px;
}
.jugador-info {
    flex-grow: 1;
}
.nombre-apellidos {
    display: flex;
    align-items: center;
}
.handicap {
    margin-top: 5px;
}
.btn-primary {
    background-color: #70AD47;
    border-color: #70AD47;
    color: white;
}
.btn-primary:hover {
    background-color: white;
    border-color: #70AD47;
    color: #70AD47;
}
.crecer {
    flex-grow: 1;
}
.cursor-pointer {
    cursor: pointer;
}
</style>