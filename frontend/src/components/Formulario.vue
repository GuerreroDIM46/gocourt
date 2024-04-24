<script>
import JugadorSeleccionado from "@/components/JugadorSeleccionado.vue"
import { mapState, mapActions } from 'pinia'
import { useCamposAPIStore } from '@/stores/camposAPI'
import { useJugadoresAPIStore } from '@/stores/jugadoresAPI'

export default {
  components: { JugadorSeleccionado },
  props: {
    jugador: {
      type: Object,
      default: () => ({})
    },
    editando: {
      type: Boolean,
      default: false
    },
    viendo: {
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
      puntuacionLargo: 50,
      puntuacionCorto: 50,
      url:''
    }
  },
  computed: {
    ...mapState(useCamposAPIStore, ['campos']),
    ...mapState(useJugadoresAPIStore, ['jugadoresSimilares'])
  },
  watch: {
    jugador: {
      immediate: true,
      deep: true,
      handler(nuevoValor, oldValor) {
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
        if (this.viendo && nuevoValor) {
          this.cargarJugadoresSimilares(nuevoValor);
        }        
      }
    }
  },
  methods: {
    ...mapActions(useCamposAPIStore, ['cargarCampos']),
    ...mapActions(useJugadoresAPIStore, ['cargarJugadoresSimilares']),
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
      this.puntuacionLargo = 50
      this.puntuacionCorto = 50
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
      <div class="row mb-3" v-if="!viendo">
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
      <div class="row mb-3" v-if="!viendo">
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
          <input type="text" class="form-control" id="dni" v-model="dni" pattern="[0-9]{8}[TRWAGMYFPDXBNJZSQVHLCKE]" title="El DNI debe tener 8 números seguidos de una letra" required>
        </div>
      </div>
      <!-- Controles específicos de tipo de jugador -->
      <div v-if="!viendo && tipo === 'principiante'" class="row mb-3">
        <div class="col-md-6">
          <label for="puntuacionLargo" class="form-label">Precisión larga distancia: {{ puntuacionLargo }}%</label>
          <input type="range" class="form-range" id="puntuacionLargo" v-model="puntuacionLargo" min="1" max="100">
        </div>
        <div class="col-md-6">
          <label for="puntuacionCorto" class="form-label">Precisión corta distancia: {{ puntuacionCorto }}%</label>
          <input type="range" class="form-range" id="puntuacionCorto" v-model="puntuacionCorto" min="1" max="100">
        </div>
      </div>
      <div v-if="!viendo && tipo === 'federado'" class="row mb-3">
        <div class="col-md-6">
          <label for="handicap" class="form-label">Handicap: {{ handicap }}</label>
          <input type="range" class="form-range" id="handicap" v-model="handicap" min="-40" max="100">
        </div>
        <div class="col-md-6">
          <label for="profesional" class="form-label">Profesional:</label>
          <input type="checkbox" class="form-check-input" id="profesional" v-model="profesional">
        </div>
      </div>
<!-- seccion viendo -->
<div class="casilla" v-if="viendo">
        <div>
          <div class="mb-2">
            <div class="jugador containerjugador">
              <div class="fl">
                <div v-if="jugador.tipo === 'federado'" class="badge bg-secondary me-2">Federado</div>
                <div v-if="jugador.tipo === 'principiante'" class="badge bg-success me-2">Principiante</div>
                <div v-if="jugador.profesional" class="badge bg-warning me-2">PRO</div>
                <strong>{{ jugador.nombre }} {{ jugador.apellido1 }} {{ jugador.apellido2 }}</strong>
              </div>
            </div>
            <div class="jugador containerjugador">
              <div class="fl"> - DNI: {{ jugador.dni }}</div>
            </div>
            <div class="jugador containerjugador">
              <div class="fl"> - Juega en: {{ jugador.nombreCampo }}</div>
            </div>
            <div class="jugador containerjugador">
              <div class="fl" v-if="jugador.tipo === 'federado'"> - Su handicap es: {{ jugador.handicap }}</div>
              <div class="fl" v-if="jugador.tipo === 'principiante'"> - Su handicap simulado es: {{
                jugador.handicap.toFixed(1) }}</div>
            </div>
          </div>
          <div class="card">
            <div class="card-header verdeclaro"> Jugadores de Nivel Similar </div>
            <ul class="list-group list-group-flush" v-for="similares in jugadoresSimilares" :key="similares._links.self.href">
              <JugadorSeleccionado :jugador="similares"></JugadorSeleccionado>
            </ul>
          </div>
        </div>
      </div>
      <!-- fin viendo -->
      <div class="d-flex">
  <div>
    <button type="button" class="btn btn-secondary" @click="resetForm" v-if="!viendo">Reset</button>
  </div>
  <div class="crecer"></div>
  <div>
    <button type="submit" class="btn btn-primary" v-if="!viendo">Guardar</button>
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

.verdeclaro {
  background-color: #70AD47;
  color: white;
  font-weight: 500;
}

.crecer {
  flex-grow: 1;
}

</style>
