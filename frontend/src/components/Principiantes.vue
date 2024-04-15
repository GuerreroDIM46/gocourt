<script>
import navbar2 from "@/components/navbar2.vue";
import Principiante from "@/components/Principiante.vue";
import { useJugadoresAPIStore } from '@/stores/jugadoresAPI.js';
import { mapState } from 'pinia';

export default {
  components: { navbar2, Principiante },
  computed: {
    ...mapState(useJugadoresAPIStore, {
      principiantes: state => state.principiantes
    }),
    totalPaginas() {
      return this.store.totalPaginasPrincipiantes;
    }
  },
  data() {
    return {
      store: useJugadoresAPIStore()
    };
  },
  methods: {
    cambiarPagina(pagina) {
      this.store.cambiarPaginaPrincipiantes(pagina);
    }
  },
  mounted() {
    this.store.cargarPrincipiantes();
  }
}
</script>


<template>
  <div class="container">
    <navbar2 />
    <table class="card">
      <tr class="card-header contenedortitulo bordeverdeclaro">
        <th>
          <!-- Controles de paginación -->
          <ul class="pagination">
            <li class="page-item" @click="store.paginaAnteriorPrincipiantes">
              <a class="page-link">Anterior</a>
            </li>
            <li v-for="pagina in totalPaginas" :key="pagina"
              :class="{ 'page-item': true, 'active': pagina === store.paginaPrincipiantes }">
              <a class="page-link" @click="cambiarPagina(pagina)">{{ pagina }}</a>
            </li>
            <li class="page-item" @click="store.paginaSiguientePrincipiantes">
              <a class="page-link">Siguiente</a>
            </li>
          </ul>
        </th>
        <div class="crecer"></div>
                <button type="button" class="btn btn-outline-success">
                    Nuevo Jugador <font-awesome-icon class="me-2" :icon="['fas', 'user-plus']" />
                </button>
      </tr>
      <tr v-for="principiante in principiantes" :key="'principiante-' + principiante.id">
        <Principiante :principiantesprop="principiante" />
      </tr>
    </table>
  </div>
</template>




<style scoped>
.centrar {
  text-align: center;
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

.bordeverdeclaro {
  border-color: #70AD47;
}

.pagination {
    margin-bottom: 0px;
}

.pagination>.active>a {
  color: white;
  background-color: #70AD47 !Important;
  border: solid 1px #70AD47 !Important;
}

.pagination>li>a {
  background-color: white;
  color: #70AD47;
}

.btn-outline-success:hover {
  background-color: #70AD47 !important;
  border-color: #70AD47;
  color: white;
}

.btn-outline-success {
  border-color: #395623;
  color: #395623;
}
</style>
