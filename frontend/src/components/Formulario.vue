<script>
import JugadorSeleccionado from "@/components/JugadorSeleccionado.vue"
import { mapState, mapActions } from 'pinia'
import { useCamposAPIStore } from '@/stores/camposAPI'

export default {
    components: { JugadorSeleccionado },
    props: {
        jugador: {},
        estado: '',
    },
    emits: ['formulario-relleno', 'formulario-actualizado'],
    data() {
        return {
            campoSeleccionado: null,
            tipo: 'principiante',
            nombre: '',
            apellido1: '',
            apellido2: '',
            dni: '',
            telefono: '',
            email: '',
            profesional: false,
            handicap: 0,
            puntuacionLargo: 50,
            puntuacionCorto: 50,
            url: '',
            campoSeleccionado: '',
            fechaSeleccionada: '',
            horaSeleccionada: '',
        }
    },
    computed: {
        ...mapState(useCamposAPIStore, ['campos']),
    },
    watch: {
        jugador: {
            immediate: true,
            deep: true,
            handler(nuevoValor) {
                if (this.estado == 'editando' && nuevoValor) {
                    this.tipo = nuevoValor.tipo
                    this.nombre = nuevoValor.nombre
                    this.apellido1 = nuevoValor.apellido1
                    this.apellido2 = nuevoValor.apellido2
                    this.dni = nuevoValor.dni
                    this.telefono = nuevoValor.telefono
                    this.email = nuevoValor.email
                    this.campoSeleccionado = this.campos.find(campo => campo._links.self.href == nuevoValor.campo) || this.campos[0]
                    this.profesional = nuevoValor.profesional
                    this.handicap = nuevoValor.handicap
                    this.puntuacionLargo = nuevoValor.puntuacionLargo
                    this.puntuacionCorto = nuevoValor.puntuacionCorto
                } else {
                    this.resetearFormulario()
                }
            }
        }
    },
    methods: {
        ...mapActions(useCamposAPIStore, ['cargarCampos']),
        enviarFormulario() {
            const urlCampo = this.campoSeleccionado._links.self.href
            const nuevoJugador = {
                nombre: this.nombre,
                apellido1: this.apellido1,
                apellido2: this.apellido2,
                dni: this.dni,
                telefono: this.telefono,
                email: this.email,
                campo: urlCampo,
                tipo: this.tipo
            }
            if (this.tipo == 'federado') {
                nuevoJugador.profesional = this.profesional
                nuevoJugador.handicap = this.handicap
            } else if (this.tipo == 'principiante') {
                nuevoJugador.puntuacionLargo = this.puntuacionLargo
                nuevoJugador.puntuacionCorto = this.puntuacionCorto
            }
            if (this.estado == 'editando' && this.jugador._links.self.href) {
                nuevoJugador.url = this.jugador._links.self.href
            }
            if (this.estado == 'editando') {
                this.$emit('formulario-actualizado', nuevoJugador)
            } else if (this.estado == 'creando') {
                this.$emit('formulario-relleno', nuevoJugador)
            }
        },
        resetearFormulario() {
            this.tipo = 'principiante'
            this.nombre = ''
            this.apellido1 = ''
            this.apellido2 = ''
            this.dni = ''
            this.email = ''
            this.telefono = ''
            this.profesional = false
            this.handicap = 0
            this.puntuacionLargo = 50
            this.puntuacionCorto = 50
            this.campoSeleccionado = this.campos[0] || null
        },
    },
    mounted() {
        this.cargarCampos()
    }
}
</script>

<template>
    <div>
        <form @submit.prevent="enviarFormulario">
            <div class="row mb-3">
                <div class="col-md-4" v-if="this.estado == 'creando'">
                    <label for="tipo" class="form-label">Tipo de Jugador:</label>
                    <select class="form-select" id="tipo" v-model="tipo">
                        <option value="principiante">Principiante</option>
                        <option value="federado">Federado</option>
                    </select>
                </div>
                <div class="col-md-8">
                    <label for="campo" class="form-label">Campo:</label>
                    <select class="form-select" id="campo" v-model="campoSeleccionado">
                        <option v-for="campo in campos" :key="campo._links.self.href" :value="campo">{{ campo.nombre }}
                        </option>
                    </select>
                </div>
            </div>
            <!-- Inputs de texto para otros campos -->
            <div class="row mb-3">
                <div class="col-md-6">
                    <label for="nombre" class="form-label">Nombre:</label>
                    <input type="text" class="form-control" id="nombre" v-model="nombre" required>
                </div>
                <div class="col-md-6">
                    <label for="apellido1" class="form-label">Primer Apellido:</label>
                    <input type="text" class="form-control" id="apellido1" v-model="apellido1" required>
                </div>
                <div class="col-md-6">
                    <label for="apellido2" class="form-label">Segundo Apellido:</label> <input type="text"
                        class="form-control" id="apellido2" v-model="apellido2" required>
                </div>
                <div class="col-md-6">
                    <label for="dni" class="form-label">DNI:</label>
                    <input type="text" class="form-control" id="dni" v-model="dni"
                        pattern="[0-9]{8}[TRWAGMYFPDXBNJZSQVHLCKE]"
                        title="El DNI debe tener 8 números seguidos de una letra" required>
                </div>
                <div class="col-md-6">
                    <label for="telefono" class="form-label">Telefono:</label> <input type="text"
                        class="form-control" id="telefono" v-model="telefono" 
                        pattern="^\d{9}$"
                        title="El teléfono debe tener exactamente 9 números." required>
                </div>
                <div class="col-md-6">
                    <label for="email" class="form-label">Correo Electronico:</label>
                    <input type="text" class="form-control" id="email" v-model="email"
                        pattern="[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,}$"
                        title="Por favor, ingrese un correo electrónico válido." required>
                </div>
            </div>
            <!-- Controles específicos de tipo de jugador -->
            <div v-if="tipo == 'principiante'" class="row mb-3">
                <div class="col-md-6">
                    <label for="puntuacionLargo" class="form-label">Precisión larga distancia: {{ puntuacionLargo
                        }}%</label>
                    <input type="range" class="form-range" id="puntuacionLargo" v-model="puntuacionLargo" min="1"
                        max="100">
                </div>
                <div class="col-md-6">
                    <label for="puntuacionCorto" class="form-label">Precisión corta distancia: {{ puntuacionCorto
                        }}%</label>
                    <input type="range" class="form-range" id="puntuacionCorto" v-model="puntuacionCorto" min="1"
                        max="100">
                </div>
            </div>
            <div v-if="tipo == 'federado'" class="row mb-3">
                <div class="col-md-6">
                    <label for="handicap" class="form-label">Handicap: {{ handicap }}</label>
                    <input type="range" class="form-range" id="handicap" v-model="handicap" min="-40" max="100">
                </div>
                <div class="col-md-6">
                    <label for="profesional" class="form-label">Profesional:</label>
                    <input type="checkbox" class="form-check-input" id="profesional" v-model="profesional">
                </div>
            </div>
            <div class="d-flex">
                <div>
                    <button type="button" class="btn btn-secondary" @click="resetearFormulario">Reset</button>
                </div>
                <div class="crecer"></div>
                <div>
                    <button type="submit" class="btn btn-primary">Guardar</button>
                </div>
            </div>
        </form>
    </div>
</template>

<style scoped>
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
.btn-secondary {
    background-color: #395623 ;
    border-color: #395623;
    color: white;
}
.btn-secondary:hover {
    background-color: white;
    border-color: #395623;
    color: #395623;
}
.crecer {
    flex-grow: 1;
}
</style>
