<script>
import Jugador from "@/components/Jugador.vue"
import Formulario from "@/components/Formulario.vue"
import { mapState, mapActions } from 'pinia'
import { useJugadoresAPIStore } from '@/stores/jugadoresAPI'
import { useCamposAPIStore } from '@/stores/camposAPI'
import { Modal } from 'bootstrap'


export default {
  components: { 
    Jugador, 
    Formulario },
  data() {
    return {
      tipoSeleccionado: 'todos',
      campoSeleccionado: 'todos',
      paginaActual: 1,
      tamanoPagina: 10,
      jugadorActual: null,
      editando: false,
      viendo: false,
      bsModal: null,
      busqueda: ''
    }
  },
  computed: {
    ...mapState(useJugadoresAPIStore, ['jugadores']),
    ...mapState(useCamposAPIStore, ['campos']),
    jugadoresFiltrados() {
      let filtrados = this.jugadores
      if (this.tipoSeleccionado != 'todos') {
        filtrados = filtrados.filter(jugador => jugador.tipo == this.tipoSeleccionado)
      }
      if (this.campoSeleccionado != 'todos') {
        filtrados = filtrados.filter(jugador => jugador.nombreCampo == this.campoSeleccionado)
      }
      if (this.busqueda) {
        const busquedaMinuscula = this.busqueda.toLowerCase()
        filtrados = filtrados.filter(jugador => 
          (`${jugador.nombre} ${jugador.apellido1} ${jugador.apellido2}`.toLowerCase().includes(busquedaMinuscula))
        )
      }
      return filtrados
    },
    jugadoresPaginados() {
      const start = (this.paginaActual - 1) * this.tamanoPagina
      const end = start + this.tamanoPagina
      return this.jugadoresFiltrados.slice(start, end)
    },
    totalPaginas() {
      return Math.ceil(this.jugadoresFiltrados.length / this.tamanoPagina)
    },
    jugadoresAPIStore() {           // para observar cambios en el store (debeRecargar)
      return useJugadoresAPIStore()
    },
    tituloModal() {
      if (this.viendo) {
        return 'Detalles de Jugador'
      } else if (this.editando) {
        return 'Editar Jugador'
      } else {
        return 'Nuevo Jugador'
      }
    }
  },
  methods: {
    ...mapActions(useJugadoresAPIStore, ['cargarFederados', 'cargarPrincipiantes', 'crearJugador', 'actualizarJugador', 'eliminarJugador']),
    ...mapActions(useCamposAPIStore, ['cargarCampos']),
    cambiarPagina(nuevaPagina) {
      if (nuevaPagina < 1 || nuevaPagina > this.totalPaginas) return
      this.paginaActual = nuevaPagina
    },
    abrirModalCrear() {
      this.jugadorActual = null
      this.editando = false
      this.viendo = false
      this.bsModal.show()
    },
    abrirModalEditar(jugador) {
      this.jugadorActual = jugador
      console.log("jugador a editar: ", jugador)
      this.editando = true
      this.viendo = false
      this.bsModal.show()
    },
    abrirModalVer(jugador) {
      this.jugadorActual = jugador
      console.log("jugador a ver: ", jugador)
      this.editando = false
      this.viendo = true
      this.bsModal.show()
    },
    manejarFormulario(jugador) {
      if (this.editando) {
        console.log("jugador actualizado (componente Jugadores.vue): ", jugador)
        this.actualizarJugador(jugador).then(() => {
          this.bsModal.hide()
          this.editando = false
          this.jugadorActual = null
        })
      } else {
        this.crearJugador(jugador).then(() => {
          this.bsModal.hide()
        })
      }
    },
    borrarJugador({ href }) {
        console.log("Href recibido:", href)
        this.eliminarJugador(href)
    },
    salirModal() {
      this.editando = false
      this.viendo = false
      this.jugadorActual = null
      this.bsModal.hide()
    }

  },
  watch: {
    tipoSeleccionado(newVal, oldVal) {
      if (newVal != oldVal) this.paginaActual = 1
    },
    campoSeleccionado(newVal, oldVal) {
      if (newVal != oldVal) this.paginaActual = 1
    },
    'jugadoresAPIStore.debeRecargar': function(newValue) {
    if (newValue) {
      this.jugadoresAPIStore.cargarFederados() // Ultra Feo
      this.jugadoresAPIStore.cargarPrincipiantes()  // Funciona, es lo que hay
      this.jugadoresAPIStore.actualizarTodosJugadores()
      this.jugadoresAPIStore.resetRecarga()
      console.log("Recargando jugadores...")
    }
  }
  },
  mounted() {
    this.cargarFederados()
    this.cargarPrincipiantes()
    this.cargarCampos()
    let modalElement = this.$refs.formularioModal
    this.bsModal = new Modal(modalElement)
  }
}
</script>

<template>
  <div class="container">
    <table class="card">
      <tr class="card-header">
        <div class="row gy-2 gx-3 align-items-center">
          <!-- Paginación -->
          <ul class="pagination me-2 mb-2 col-auto">
            <!-- Primera Página -->
            <li class="page-item" :class="{ disabled: paginaActual == 1 }">
              <a class="page-link" href="#" @click="cambiarPagina(1)"><font-awesome-icon
                  :icon="['fas', 'angle-double-left']" /></a>
            </li>
            <!-- Página Anterior -->
            <li class="page-item" :class="{ disabled: paginaActual == 1 }">
              <a class="page-link" href="#" @click="cambiarPagina(paginaActual - 1)"><font-awesome-icon
                  :icon="['fas', 'angle-left']" /></a>
            </li>
            <!-- Página Actual -->
            <li class="page-item active">
              <a class="page-link" href="#">{{ paginaActual }}</a>
            </li>
            <!-- Página Siguiente -->
            <li class="page-item" :class="{ disabled: paginaActual == totalPaginas }">
              <a class="page-link" href="#" @click="cambiarPagina(paginaActual + 1)"><font-awesome-icon
                  :icon="['fas', 'angle-right']" /></a>
            </li>
            <!-- Última Página -->
            <li class="page-item" :class="{ disabled: paginaActual == totalPaginas }">
              <a class="page-link" href="#" @click="cambiarPagina(totalPaginas)"><font-awesome-icon
                  :icon="['fas', 'angle-double-right']" /></a>
            </li>
          </ul>
          <!-- nombre para filtrar jugadores -->
          <div class="col-auto selectauto3">
            <label class="visually-hidden" for="specificSizeInputGroupUsername">Username</label>
            <div class="input-group mb-2 " style="flex-wrap: nowrap;">
              <div class="input-group-text "><font-awesome-icon :icon="['fas', 'magnifying-glass']"
                  class="icono-fontawesome" /></div>
              <input type="text" class="form-control" placeholder="" v-model="busqueda">
            </div>
          </div>
          <!-- Select para filtrar jugadores -->
          <select v-model="tipoSeleccionado" class="form-select col-auto me-2 mb-2 selectauto">
            <option value="todos">Todos</option>
            <option value="federado">Federado</option>
            <option value="principiante">Principiante</option>
          </select>
          <!-- Select para filtrar campos -->
          <select v-model="campoSeleccionado" class="form-select col-auto me-2 mb-2 selectauto2">
            <option value="todos">Todos los campos</option>
            <option v-for="campo in campos" :value="campo.nombre" :key="campo._links.self.href">
              {{ campo.nombre }}
            </option>
          </select>
          <!-- <input class="form-control mb-2 anchomaximo1" type="search" placeholder="Busqueda" aria-label="Search" v-model="busqueda"> -->
          <!-- Botón Nuevo Jugador -->
          <button type="button" @click="abrirModalCrear"
            class="btn btn-outline-success btn-no-wrap mb-2  col-auto ms-auto">
            <span class="btn-text">Nuevo Jugador</span>
            <font-awesome-icon :icon="['fas', 'user-plus']" />
          </button>
        </div>
      </tr>
      <tr v-for="jugador in jugadoresPaginados" :key="jugador._links.self.href">
        <Jugador :jugador="jugador" @editar-jugador="abrirModalEditar(jugador)" @ver-jugador="abrirModalVer(jugador)"
          @borrar-jugador="borrarJugador"></Jugador>
      </tr>
    </table>
    <!-- Modal -->
    <div class="modal fade" id="staticBackdrop" ref="formularioModal" tabindex="-1"
      aria-labelledby="staticBackdropLabel" aria-hidden="true">
      <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
          <div class="modal-header verdeclaro" :class="{ 'verdeoscuro': viendo }">
            <h1 class="modal-title fs-5" id="staticBackdropLabel">{{ tituloModal }}</h1>
            <button type="button" class="btn btn-danger ms-auto" @click="salirModal"
              aria-label="Close"><font-awesome-icon :icon="['fas', 'xmark']" /></button>
          </div>
          <div class="modal-body">
            <Formulario :jugador="jugadorActual" :editando="editando" :viendo="viendo"
              @formulario-relleno="manejarFormulario" @formulario-actualizado="manejarFormulario"></Formulario>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>


<style scoped>

.selectauto {
  width: auto;
  max-width: 20%;
}

.selectauto2 {
  width: auto;
  max-width: 20%;
}

.selectauto3 {
  width: 150px;
}

.flexmio {
  display: flex;
  flex-direction: row;
  flex-wrap: wrap;
}


.btn-no-wrap {
  display: flex;
  align-items: center;
}

.btn-text {
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
  flex-grow: 1;
}

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

.pagination>li>a:hover {
  background-color: #70AD47;
  /* Color de fondo al pasar el mouse */
  color: white;
  /* Color del texto al pasar el mouse */
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