<script>
import { mapState, mapActions } from 'pinia'
import { useCamposAPIStore } from '@/stores/camposAPI'


export default {
    components: {  },
    data() {
        return {
        campoSeleccionado: '',
        tipo: 'principiante',
        nombre: '',
        apellido1: '',
        apellido2: '',
        dni: '',
        profesional: false,
        handicap: 0,
        puntuacionLargo: 0,
        puntuacionCorto: 0,
        campo: ''
        }
    },
    computed: {
        ...mapState(useCamposAPIStore, ['campos'])
    },
    methods: { ...mapActions(useCamposAPIStore, ['cargarCampos']),
    
    },
    mounted(){
        this.cargarCampos();
    }

}
</script>

<template>
  <div>
    <form @submit.prevent="enviarFormulario">
      <div class="row mb-3">
  <!-- Select para el tipo de jugador -->
  <div class="col-md-4">
    <label for="tipo" class="form-label">Tipo de Jugador:</label>
    <select class="form-select" id="tipo" v-model="tipo">
      <option value="principiante">Principiante</option>
      <option value="federado">Federado</option>
    </select>
  </div>

  <!-- Select para el campo -->
  <div class="col-md-8">
    <label for="campo" class="form-label">Campo:</label>
    <select class="form-select" id="campo" v-model="this.campoSeleccionado">
      <option v-for="campo in campos" :key="campo.id" :value="campo.nombre">{{ campo.nombre }}</option>
    </select>
  </div>
</div>
      <div class="row mb-3">
        <!-- Nombre y Primer Apellido en la misma fila -->
        <div class="col-md-6">
          <label for="nombre" class="form-label">Nombre:</label>
          <input type="text" class="form-control" id="nombre" v-model="nombre">
        </div>
        <div class="col-md-6">
          <label for="apellido1" class="form-label">Primer Apellido:</label>
          <input type="text" class="form-control" id="apellido1" v-model="apellido1">
        </div>
      </div>
      <div class="row mb-3">
        <!-- Segundo Apellido y DNI en la misma fila -->
        <div class="col-md-6">
          <label for="apellido2" class="form-label">Segundo Apellido:</label>
          <input type="text" class="form-control" id="apellido2" v-model="apellido2">
        </div>
        <div class="col-md-6">
          <label for="dni" class="form-label">DNI:</label>
          <input type="text" class="form-control" id="dni" v-model="dni">
        </div>
      </div>
      <div v-if="tipo !== 'federado'" class="row mb-3">
        <!-- Puntuacion Largo y corto en la misma fila -->
        <div class="col-md-6 d-flex align-items-center">
          <label for="handicap" class="form-label">Precision larga distancia: {{ puntuacionLargo }}%</label>
          <input type="range" class="form-range ms-2" id="handicap" v-model="puntuacionLargo" min="0" max="100" step="1">
        </div>
        <div class="col-md-6 d-flex align-items-center">
          <label for="handicap" class="form-label">Precision corta distancia: {{ puntuacionCorto }}%</label>
          <input type="range" class="form-range ms-2" id="handicap" v-model="puntuacionCorto" min="0" max="100" step="1">
        </div>
      </div>
      <div v-if="tipo !== 'principiante'" class="row mb-3">
        <!-- Handicap y Profesional en la misma fila -->
        <div class="col-md-6 d-flex align-items-center">
          <label for="handicap" class="form-label">Handicap: {{ handicap }}</label>
          <input type="range" class="form-range ms-2" id="handicap" v-model="handicap" min="-40" max="100" step="1">
        </div>
        <div class="col-md-6 d-flex justify-content-center align-items-center">
          <label for="profesional" class="form-label me-2">Profesional:</label>
          <input type="checkbox" class="form-check-input" id="profesional" v-model="profesional">
        </div>
      </div>
      <div class="row mb-3">
        <div class="col-md-6">
          <button type="button" class="btn btn-secondary" @click="resetForm">Reset</button>
        </div>
        <div class="col-md-6 d-flex justify-content-end">
          <button type="submit" class="btn btn-primary">Enviar</button>
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
