<script>
export default {
  props: ['partido', 'editando'],
  emits: ['formulario-relleno', 'formulario-actualizado'],
  data() {
    return {
      idLocal: '',
      idVisitante: '',
      golesLocal: null,
      golesVisitante: null,
      // timestamp: ''
    }
  },

  watch: {
    partido: {
      inmedite: true,
      handler(nuevoValor) {
        if (nuevoValor) {
          this.idLocal = nuevoValor.idLocal
          this.idVisitante = nuevoValor.idVisitante
          this.golesLocal = nuevoValor.golesLocal
          this.golesVisitante = nuevoValor.golesVisitante
          // this.timestamp = nuevoValor.timestamp
        }
      }
    }
  },

  methods: {

    enviarFormulario() {
      const nuevoObjetoPartido = {
        idLocal: this.idLocal,
        idVisitante: this.idVisitante,
        golesLocal: this.golesLocal,
        golesVisitante: this.golesVisitante,
        // timestamp: this.timestamp
      }

      // Solamente añadimos la URL si estamos editando uun partido y la propiedad existe
      if(this.editando && this.partido._links.self.href) {
        nuevoObjetoPartido.url = this.partido._links.self.href
      }

      if (this.editando) {
        this.$emit('formulario-actualizado', nuevoObjetoPartido)
      } else {
        this.$emit('formulario-relleno', nuevoObjetoPartido)
      }
    }
  }
}

</script>

<template>
  <form @submit.prevent="enviarFormulario" class="row g-3 needs-validation" novalidate>
    <div class="col-md-6">
      <label for="idLocal" class="form-label">Equipo Local</label>
      <input type="text" class="form-control" id="idLocal" v-model="idLocal" required>
      <div class="valid-feedback">
        Looks good!
      </div>
    </div>
    <div class="col-md-6">
      <label for="idVisitante" class="form-label">Equipo Visitante</label>
      <input type="text" class="form-control" id="idVisitante" v-model="idVisitante" required>
      <div class="valid-feedback">
        Looks good!
      </div>
    </div>
    <div class="col-md-6">
      <label for="golesLocal" class="form-label">Goles Local</label>
      <input type="number" class="form-control" id="golesLocal" v-model="golesLocal" required>
      <div class="valid-feedback">
        Looks good!
      </div>
    </div>
    <div class="col-md-6">
      <label for="golesVisitante" class="form-label">Goles Visitante</label>
      <input type="number" class="form-control" id="golesVisitante" v-model="golesVisitante" required>
      <div class="valid-feedback">
        Looks good!
      </div>
    </div>
    <!-- <div class="col-md-6">
      <label for="timestamp" class="form-label">Fecha-Hora</label>
      <input type="datetime-local" class="form-control" id="timestamp" v-model="timestamp" required>
      <div class="valid-feedback">
        Looks good!
      </div>
    </div> -->
    <div class="col-12">
      <button class="btn btn-primary" type="submit" data-bs-dismiss="modal">Submit form</button>
    </div>
  </form>
</template>