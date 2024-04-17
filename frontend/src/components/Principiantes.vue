
<script>
import navbar2 from "@/components/navbar2.vue";
import Principiante from "@/components/Principiante.vue";
import FormularioPrincipiante from '@/components/FormularioPricipiante.vue'
import { useJugadoresAPIStore } from '@/stores/jugadoresAPI';
import { mapState, mapActions } from 'pinia';

export default {
  components: { navbar2, Principiante, FormularioPrincipiante },
  data() {
    return {
      paginaActual: 1,
      elementosPorPagina: 15
    }
  },
  computed: {
      ...mapState(useJugadoresAPIStore, ['principiantes']),
      principiantesPaginados() {
          const inicio = (this.paginaActual - 1) * this.elementosPorPagina;
          const fin = inicio + this.elementosPorPagina;
          return this.principiantes.slice(inicio, fin);
      },
      totalPaginas() {
          return Math.ceil(this.principiantes.length / this.elementosPorPagina);
      }
  },
  methods: {
    ...mapActions(useJugadoresAPIStore, ['cargarPrincipiantes']),
    cambiarPagina(pagina) {
      this.paginaActual = pagina;
    }
  },
  mounted() {
    this.cargarPrincipiantes();
  }
}
</script>

<template>
  <div class="container">
    <navbar2 />
    <table class="card">
      <tr class="card-header contenedortitulo bordeverdeclaro">
        <td class="w-100">
          <div class="d-flex flex-column flex-md-row align-items-start justify-content-md-between">
            <button type="button" data-bs-toggle="modal" data-bs-target="#staticBackdrop" class="btn btn-outline-success mb-2 mb-md-0 order-1 order-md-2">
              Nuevo Jugador <font-awesome-icon class="me-2" :icon="['fas', 'user-plus']" />
            </button>
            <ul class="pagination mb-0 order-2 order-md-1">
              <li class="page-item" :class="{ disabled: paginaActual === 1 }">
                <a class="page-link" href="#" @click.prevent="cambiarPagina(paginaActual - 1)">Previous</a>
              </li>
              <li class="page-item" v-for="n in totalPaginas" :key="n" :class="{ active: n === paginaActual }">
                <a class="page-link" href="#" @click.prevent="cambiarPagina(n)">{{ n }}</a>
              </li>
              <li class="page-item" :class="{ disabled: paginaActual === totalPaginas }">
                <a class="page-link" href="#" @click.prevent="cambiarPagina(paginaActual + 1)">Next</a>
              </li>
            </ul>
          </div>
        </td>
      </tr>
      <tr v-for="principiante in principiantesPaginados" :key="'principiante-' + principiante.id">
        <Principiante :jugador="principiante" />
      </tr>
    </table>

    <div class="modal fade" id="staticBackdrop" ref="formularioModal" data-bs-backdrop="static" data-bs-keyboard="false"
      tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
      <div class="modal-dialog modal-dialog-centered">
          <div class="modal-content">
              <div class="modal-header verdeclaro">
                  <h1 class="modal-title fs-5" id="staticBackdropLabel">
                    <font-awesome-icon :icon="['fas', 'pen-to-square']" class="icono-fontawesome" size="lg" /> Principiante
                  </h1>
                  <div class="crecer"></div>
                  <button type="button" class="btn btn-danger" data-bs-dismiss="modal" aria-label="Close">
                    <font-awesome-icon :icon="['fas', 'xmark']" size="lg"/>
                  </button>
              </div>
              <div class="modal-body">
                  <FormularioPrincipiante></FormularioPrincipiante>
              </div>
              <div class="modal-footer verdeoscuro">
              </div>
          </div>
      </div>
    </div>
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
    background-color: #395623 !Important;
}

.pagination>li>a {
    background-color: white;
    color: #70AD47;
}

.pagination > li > a:hover {
  background-color: #70AD47; /* Color de fondo al pasar el mouse */
  color: white; /* Color del texto al pasar el mouse */
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
