<script>
import navbar2 from "@/components/navbar2.vue"
import Principiante from "@/components/Principiante.vue"
import { mapState, mapActions } from 'pinia'
import { useJugadoresAPIStore } from '@/stores/jugadoresAPI.js'


export default {
  components: { navbar2, Principiante },
  data() {
    return {}
  },
  computed: {
    ...mapState(useJugadoresAPIStore, ['principiantes'])
  },
  methods: { ...mapActions(useJugadoresAPIStore, ['cargarPrincipiantes']) },
  mounted() {
    this.cargarPrincipiantes();
  }
}
</script>

<template>
  <div class="container">
    <div>
      <navbar2></navbar2>
    </div>
    <table class="card" >
      
      <tr class="card-header contenedortitulo verdeclaro">
        <th class="ms-2">Nombre</th>
        <th class="crecer"></th>
        <th>PC</th>
        <th class="ms-2">PL</th>
        <th class="ms-2">HC</th>
        <th class="verdeoscuro" style="width: 80px; display: flex; flex-direction: row-reverse; align-items: center;">
  <font-awesome-icon class="me-2" :icon="['fas', 'user-plus']" />
</th>
      </tr>
              <tr v-for="principiante in principiantes" :key="principiante._links.self.href">
          <Principiante :principiantesprop="principiante"></Principiante>
              </tr>
     
      <!-- <div class="centrar">
        <button type="button" class="btn btn-success" @click="abrirModalNuevoPartido">Añadir Jugador</button>
      </div> -->
    </table>
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
.verdeoscuro {
background-color: #395623;
  color: #CCCCCC;
  font-weight: 500;
  border: 1px;
}
</style>