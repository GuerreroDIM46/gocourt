<script>
import Federado from "@/components/Federado.vue"
import Principiante from "@/components/Principiante.vue"
import Formulario from "@/components/Formulario.vue"
import { mapState, mapActions } from 'pinia'
import { useJugadoresAPIStore } from '@/stores/jugadoresAPI'

export default {
    components: {  Federado, Principiante, Formulario },
    data() {
        return {
          paginaActual: 1,
          tamanoPagina: 10
        }
    },
    computed: {
      ...mapState(useJugadoresAPIStore, ['jugadores']),
      jugadoresPaginados() {
        const start = (this.paginaActual - 1) * this.tamanoPagina;
        const end = start + this.tamanoPagina;
        return this.jugadores.slice(start, end);
      },
      totalPaginas() {
        return Math.ceil(this.jugadores.length / this.tamanoPagina);
      }
    },
    methods: {
      ...mapActions(useJugadoresAPIStore, ['cargarFederados', 'cargarPrincipiantes']),
      cambiarPagina(nuevaPagina) {
        if (nuevaPagina < 1 || nuevaPagina > this.totalPaginas) return;
        this.paginaActual = nuevaPagina;
      }
    },
    mounted() {
      this.cargarFederados();
      this.cargarPrincipiantes();
    }
}
</script>


<template>
  <div class="container">
    <table class="card">
      <tr class="card-header contenedortitulo">
        <td class="w-100">
          <!-- Contenedor principal con flex -->
          <div class="d-flex flex-column flex-md-row align-items-start justify-content-md-between">
            <!-- Botón Nuevo Jugador, aparece primero y a la izquierda -->
            <button type="button" data-bs-toggle="modal" data-bs-target="#staticBackdrop"
              class="btn btn-outline-success mb-2 mb-md-0 order-1 order-md-2">
              Nuevo Jugador <font-awesome-icon class="me-2" :icon="['fas', 'user-plus']" />
            </button>
            <!-- Paginación -->
            <ul class="pagination mb-0 order-2 order-md-1">
              <li class="page-item" :class="{ disabled: paginaActual === 1 }">
        <a class="page-link" href="#" @click="cambiarPagina(paginaActual - 1)">Previous</a>
      </li>
      <li class="page-item" v-for="n in totalPaginas" :key="n" :class="{ active: n === paginaActual }">
        <a class="page-link" href="#" @click="cambiarPagina(n)">{{ n }}</a>
      </li>
      <li class="page-item" :class="{ disabled: paginaActual === totalPaginas }">
        <a class="page-link" href="#" @click="cambiarPagina(paginaActual + 1)">Next</a>
      </li>
            </ul>
          </div>
        </td>
      </tr>
      <tr v-for="jugador in jugadoresPaginados" :key="jugador._links.self.href">
        <component :is="jugador.tipo === 'federado' ? 'Federado' : 'Principiante'" :jugador="jugador"></component>
      </tr>
    </table>
  </div>

  <!-- Modal -->
  <!-- <div class="modal fade" id="staticBackdrop" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true"> -->
  <div class="modal fade" id="staticBackdrop" ref="formularioModal" data-bs-backdrop="static" data-bs-keyboard="false"
      tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
      <div class="modal-dialog modal-dialog-centered">
          <div class="modal-content">
              <div class="modal-header verdeclaro">
                  <h1 class="modal-title fs-5" id="staticBackdropLabel"><font-awesome-icon :icon="['fas', 'pen-to-square']" class="icono-fontawesome" size="lg" />  Jugador</h1>
                  <div class="crecer"></div>
                  <button type="button" class="btn btn-danger" data-bs-dismiss="modal" aria-label="Close"><font-awesome-icon :icon="['fas', 'xmark']" size="lg"/></button>
              </div>
              <div class="modal-body">
                  <Formulario></Formulario>
              </div>
              <div class="modal-footer verdeoscuro">

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