<script>
import { mapState, mapActions } from 'pinia'
import { useCamposAPIStore } from '@/stores/camposAPI'

export default {
  props: {
    jugador: {
      type: Object,
      default: () => ({})
    },
    editando: {
      type: Boolean,
      default: false
    }
  },
  emits: ['formulario-relleno', 'formulario-actualizado'],
  data() {
    return {
      campoSeleccionado: null, // Almacena el objeto completo del campo
      tipo: 'principiante',  // Valor inicial por defecto
      nombre: '',
      apellido1: '',
      apellido2: '',
      dni: '',
      profesional: false,
      handicap: 0,
      puntuacionLargo: '',
      puntuacionCorto: '',
      url:''
    }
  },
  computed: {
    ...mapState(useCamposAPIStore, ['campos'])
  },
  watch: {
    jugador: {
      immediate: true,
      deep: true,
      handler(nuevoValor) {
        if (this.editando && nuevoValor) {
          this.tipo = nuevoValor.tipo
          this.nombre = nuevoValor.nombre
          this.apellido1 = nuevoValor.apellido1
          this.apellido2 = nuevoValor.apellido2
          this.dni = nuevoValor.dni
          this.campoSeleccionado = this.campos.find(campo => campo._links.self.href === nuevoValor.campo) || this.campos[0]
          this.profesional = nuevoValor.profesional
          this.handicap = nuevoValor.handicap
          this.puntuacionLargo = nuevoValor.puntuacionLargo
          this.puntuacionCorto = nuevoValor.puntuacionCorto
        } else {
          this.resetForm();
        }
      }
    }
  },
  methods: {
    ...mapActions(useCamposAPIStore, ['cargarCampos']),
    enviarFormulario() {
      const urlCampo = this.campoSeleccionado._links.self.href;
      const nuevoJugador = {
        nombre: this.nombre,
        apellido1: this.apellido1,
        apellido2: this.apellido2,
        dni: this.dni,
        campo: urlCampo,  
        tipo: this.tipo
      };
      if (this.tipo === 'federado') {
        nuevoJugador.profesional = this.profesional;
        nuevoJugador.handicap = this.handicap;
      } else if (this.tipo === 'principiante') {
        nuevoJugador.puntuacionLargo = this.puntuacionLargo;
        nuevoJugador.puntuacionCorto = this.puntuacionCorto;
      }
      if(this.editando && this.jugador._links.self.href) {
        nuevoJugador.url = this.jugador._links.self.href
      }
      console.log("Datos del jugador a enviar (Formulario):", nuevoJugador);
      if (this.editando) {
        this.$emit('formulario-actualizado', nuevoJugador);
      } else {
        this.$emit('formulario-relleno', nuevoJugador);
      }
      this.resetForm();
    },
    resetForm() {
      this.tipo = 'principiante'
      this.nombre = ''
      this.apellido1 = ''
      this.apellido2 = ''
      this.dni = ''
      this.profesional = false
      this.handicap = 0
      this.puntuacionLargo = 0
      this.puntuacionCorto = 0
      this.campoSeleccionado = this.campos[0] || null
    }
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
        <div class="col-md-4" v-if="!editando">
          <label for="tipo" class="form-label">Tipo de Jugador:</label>
          <select class="form-select" id="tipo" v-model="tipo">
            <option value="principiante">Principiante</option>
            <option value="federado">Federado</option>
          </select>
        </div>
        <div class="col-md-8">
          <label for="campo" class="form-label">Campo:</label>
          <select class="form-select" id="campo" v-model="campoSeleccionado">
            <option v-for="campo in campos" :key="campo.id" :value="campo">{{ campo.nombre }}</option>
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
          <label for="apellido2" class="form-label">Segundo Apellido:</label> <input type="text" class="form-control"
            id="apellido2" v-model="apellido2" required>
        </div>
        <div class="col-md-6">
          <label for="dni" class="form-label">DNI:</label>
          <input type="text" class="form-control" id="dni" v-model="dni" required>
        </div>
      </div>
      <!-- Controles específicos de tipo de jugador -->
      <div v-if="tipo === 'principiante'" class="row mb-3">
        <div class="col-md-6">
          <label for="puntuacionLargo" class="form-label">Precisión larga distancia: {{ puntuacionLargo }}%</label>
          <input type="range" class="form-range" id="puntuacionLargo" v-model="puntuacionLargo" min="0" max="100">
        </div>
        <div class="col-md-6">
          <label for="puntuacionCorto" class="form-label">Precisión corta distancia: {{ puntuacionCorto }}%</label>
          <input type="range" class="form-range" id="puntuacionCorto" v-model="puntuacionCorto" min="0" max="100">
        </div>
      </div>
      <div v-if="tipo === 'federado'" class="row mb-3">
        <div class="col-md-6">
          <label for="handicap" class="form-label">Handicap: {{ handicap }}</label>
          <input type="range" class="form-range" id="handicap" v-model="handicap" min="-40" max="100">
        </div>
        <div class="col-md-6">
          <label for="profesional" class="form-label">Profesional:</label>
          <input type="checkbox" class="form-check-input" id="profesional" v-model="profesional">
        </div>
      </div>
      <div class="row mb-3">
        <div class="col-md-6">
          <button type="button" class="btn btn-secondary" @click="resetForm">Reset</button>
        </div>
        <div class="col-md-6 d-flex justify-content-end">
          <button type="submit" class="btn btn-primary">Guardar</button>
        </div>
      </div>
    </form>
  </div>
</template>

<style scoped>
.btn-primary {
  background-color: #70AD47 !important;
  border-color: #70AD47;
  color: white;
}

.btn-primary:hover {
  transform: scale(1.1);
  font-weight: 800;
}

.btn-secondary {
  background-color: #395623 !important;
  border-color: #395623;
  color: white;
}

.btn-secondary:hover {
  transform: scale(1.1);
  font-weight: 800;
}
</style>
