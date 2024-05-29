<script>
import Jugador from "@/components/Jugador.vue"
import Formulario from "@/components/Formulario.vue"
import JugadorSeleccionado from "@/components/JugadorSeleccionado.vue"
import { mapState, mapActions } from 'pinia'
import { useJugadoresAPIStore } from '@/stores/jugadoresAPI'
import { useCamposAPIStore } from '@/stores/camposAPI'
import { usePuntuacionesAPIStore } from '@/stores/puntuacionesAPI'
import { usePartidosAPIStore } from '@/stores/partidosAPI'
import { Modal } from 'bootstrap'


export default {
    components: {
        Jugador,
        Formulario,
        JugadorSeleccionado
    },
    data() {
        return {
            tipoSeleccionado: 'todos',
            campoSeleccionado: 'todos',
            paginaActual: 1,
            tamanoPagina: 10,
            jugadorActual: null,
            jugadorSimilar: null,
            bsModalCrearEditarJugador: null,
            bsModalConfirmacion: null,
            bsModalVerJugador: null,
            bsModalCrearPartido: null,
            busqueda: '',
            estado: '',
            jugador: {},
            similar: {},
            campoSeleccionadoPartido: '',
            fechaSeleccionada: '',
            horaSeleccionada: '',
        }
    },
    computed: {
        ...mapState(useJugadoresAPIStore, ['jugadores', 'jugadoresSimilares']),
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
            if (this.estado == 'viendo') {
                return 'Detalles de Jugador'
            } else if (this.estado == 'editando') {
                return 'Editar Jugador'
            } else if (this.estado == 'creando') {
                return 'Nuevo Jugador'
            } else if (this.estado == 'asignando') {
                return 'Selecciona los detalles del partido'
            }
        },

    },
    methods: {
        ...mapActions(useJugadoresAPIStore, ['cargarFederados', 'cargarPrincipiantes', 'crearJugador', 'actualizarJugador', 'eliminarJugador', 'cargarJugadoresSimilares']),
        ...mapActions(useCamposAPIStore, ['cargarCampos']),
        ...mapActions(usePuntuacionesAPIStore, ['crearAsignacion']),
        ...mapActions(usePartidosAPIStore, ['enviarPartido']),
        cambiarPagina(nuevaPagina) {
            if (nuevaPagina < 1 || nuevaPagina > this.totalPaginas) return
            this.paginaActual = nuevaPagina
        },
        abrirModal(estado, jugador = null, similar = null) {
            this.estado = estado
            this.jugadorActual = jugador
            this.jugadorSimilar = similar
            if (this.estado == 'creando') {
                console.log('estado: ', this.estado)
                this.bsModalCrearEditarJugador.show();
            } else if (estado == 'editando') {
                console.log('estado: ', this.estado)
                console.log('jugador editando: ', jugador)
                this.bsModalCrearEditarJugador.show();
            } else if (this.estado == 'viendo') {
                console.log('estado: ', this.estado)
                console.log('jugador viendo: ', jugador)
                this.bsModalVerJugador.show();
            } else if (this.estado == 'asignando') {
                console.log('estado: ', this.estado)
                console.log('jugador actual: ', jugador)
                console.log('jugador similar: ', this.jugadorSimilar)
                this.bsModalVerJugador.hide();
                this.bsModalCrearPartido.show();
            } 
        },
        ocultarModal() {
            if (this.estado == 'creando' || this.estado == 'editando') {
                this.bsModalCrearEditarJugador.hide()
            } else if (this.estado == 'viendo') {
                this.bsModalVerJugador.hide()
            } else if (this.estado == 'asignando') {
                this.bsModalCrearPartido.hide()
                this.abrirModal('viendo', this.jugadorActual)
            }

        },
        mostrarModalCreado() {
            if (this.estado == 'creando' || this.estado == 'editando') {
                this.ocultarModal()
                this.bsModalConfirmacion.show()
            } else if (this.estado == 'asignando') {
                this.bsModalCrearPartido.hide()
                this.bsModalConfirmacion.show()
            } 
        },
        ocultarModalCreado() {
            if (this.estado == 'creando' || this.estado == 'editando') {
                this.bsModalConfirmacion.hide()
            } else if (this.estado == 'asignando') {
                this.bsModalConfirmacion.hide()
                this.abrirModal('viendo', this.jugadorActual)
            }
        },
        manejarFormulario(jugador) {
            if (this.estado == 'editando') {
                this.actualizarJugador(jugador).then(() => {
                    this.jugadorActual = null
                    this.mostrarModalCreado()
                })
            } else if (this.estado == 'creando') {
                this.crearJugador(jugador).then(() => {
                    this.mostrarModalCreado()
                })
            }
        },
        borrarJugador({ href }) {
            console.log("Href recibido:", href)
            this.eliminarJugador(href)
        },
        async crearPartido() {
            const partido = {
                campo: this.campoSeleccionadoPartido,
                cuando: `${this.fechaSeleccionada}T${this.horaSeleccionada}:00Z`,
            }
            const partidoURL = await this.enviarPartido(partido)
            const asignacion1 = {
                aceptado: false,
                jugador: this.jugadorActual._links.self.href,
                partido: partidoURL
            }
            const asignacion2 = {
                aceptado: false,
                jugador: this.jugadorSimilar._links.self.href,
                partido: partidoURL
            }
            const asignacion1URL = await this.crearAsignacion(asignacion1)
            const asignacion2URL = await this.crearAsignacion(asignacion2)
            console.log('asignaciones devueltas', asignacion1URL)
            console.log('asignaciones devueltas', asignacion2URL)
            this.mostrarModalCreado()
        },


    },
    watch: {
        tipoSeleccionado(newVal, oldVal) {
            if (newVal != oldVal) this.paginaActual = 1
        },
        campoSeleccionado(newVal, oldVal) {
            if (newVal != oldVal) this.paginaActual = 1
        },
        'jugadoresAPIStore.debeRecargar': function (newValue) {
            if (newValue) {
                this.jugadoresAPIStore.cargarFederados() // Ultra Feo
                this.jugadoresAPIStore.cargarPrincipiantes()  // Funciona, es lo que hay
                this.jugadoresAPIStore.cargarJugadores()
                console.log("Recargando jugadores...")
            }
        },
        jugadorActual: {
            immediate: true,
            deep: true,
            handler(nuevoValor) {
                if (this.estado == 'viendo' && nuevoValor) {
                    this.cargarJugadoresSimilares(nuevoValor);
                }
            }
        }
    },
    mounted() {
        this.cargarFederados()
        this.cargarPrincipiantes()
        this.cargarCampos()
        this.bsModalCrearEditarJugador = new Modal(this.$refs.modalCrearEditarJugador)
        this.bsModalVerJugador = new Modal(this.$refs.modalVerJugador)
        this.bsModalConfirmacion = new Modal(this.$refs.modalConfirmacion)
        this.bsModalCrearPartido = new Modal(this.$refs.modalCrearPartido)
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
                    <button type="button" @click="abrirModal('creando')"
                        class="btn btn-outline-success btn-no-wrap mb-2  col-auto ms-auto">
                        <span class="btn-text">Nuevo Jugador</span>
                        <font-awesome-icon :icon="['fas', 'user-plus']" />
                    </button>
                </div>
            </tr>
            <tr v-for="jugador in jugadoresPaginados" :key="jugador._links.self.href">
                <Jugador 
                    :jugador="jugador" 
                    @editar-jugador="abrirModal('editando', jugador)"
                    @ver-jugador="abrirModal('viendo', jugador)" 
                    @borrar-jugador="borrarJugador"
                ></Jugador>
            </tr>
        </table>
        <!-- Modal creacion y edicion de jugadores-->
        <div class="modal fade" id="modalCrearEditarJugador" ref="modalCrearEditarJugador" tabindex="-1"
            aria-labelledby="modalCrearEditarJugadorLabel" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered">
                <div class="modal-content">
                    <div class="modal-header verdeclaro" :class="{ 'verdeoscuro': this.estado == 'viendo' }">
                        <h1 class="modal-title fs-5" id="modalCrearEditarJugadorLabel">{{ tituloModal }}</h1>
                        <button type="button" class="btn btn-danger ms-auto" @click="ocultarModal"
                            aria-label="Close"><font-awesome-icon :icon="['fas', 'xmark']" /></button>
                    </div>
                    <div class="modal-body">
                        <Formulario 
                            :jugador="jugadorActual" 
                            :estado="estado" 
                            @formulario-relleno="manejarFormulario" 
                            @formulario-actualizado="manejarFormulario">
                        </Formulario>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!-- Modal ver jugadores-->
    <div class="modal fade" id="modalVerJugador" ref="modalVerJugador" tabindex="-1"
        aria-labelledby="modalVerJugadorLabel" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered">
            <div v-if="this.jugadorActual != null" class="modal-content">
                <div class="modal-header verdeclaro" :class="{ 'verdeoscuro': this.estado == 'viendo' }">
                    <h1 class="modal-title fs-5" id="modalVerJugadorLabel">{{ tituloModal }}</h1>
                    <button type="button" class="btn btn-danger ms-auto" data-bs-dismiss="modal" @click="ocultarModal"
                        aria-label="Close"><font-awesome-icon :icon="['fas', 'xmark']" /></button>
                </div>
                <div class="modal-body">
                    <div class="casilla">
                        <div>
                            <div class="mb-2">
                                <div class="jugador containerjugador">
                                    <div class="fl">
                                        <div v-if="this.jugadorActual.tipo == 'federado'"
                                            class="badge bg-secondary me-2">
                                            Federado</div>
                                        <div v-if="this.jugadorActual.tipo == 'principiante'"
                                            class="badge bg-success me-2">
                                            Principiante</div>
                                        <div v-if="this.jugadorActual.profesional" class="badge bg-warning me-2">PRO
                                        </div>
                                        <strong>{{ this.jugadorActual.nombre }} {{ this.jugadorActual.apellido1 }} {{
                                            this.jugadorActual.apellido2
                                            }}</strong>
                                    </div>
                                </div>
                                <div class="jugador containerjugador">
                                    <div class="fl"> - DNI: {{ this.jugadorActual.dni }}</div>
                                </div>
                                <div class="jugador containerjugador">
                                    <div class="fl"> - Su telefono es: 
                                        <a :href="'tel:' + jugadorActual.telefono">{{ jugadorActual.telefono }}</a>
                                    </div>
                                </div>
                                <div class="jugador containerjugador">
                                    <div class="fl"> - Su correo electronico es:
                                        <a :href="'mailto:' + jugadorActual.email">{{ jugadorActual.email }}</a>
                                    </div>
                                </div>
                                <div class="jugador containerjugador">
                                    <div class="fl"> - Juega en: {{ this.jugadorActual.nombreCampo }}</div>
                                </div>
                                <div class="jugador containerjugador">
                                    <div class="fl" v-if="this.jugadorActual.tipo == 'federado'"> - Su handicap es: {{
                                        this.jugadorActual.handicap }}
                                    </div>
                                    <div class="fl" v-if="this.jugadorActual.tipo == 'principiante'"> - Su handicap
                                        simulado
                                        es: {{
                                        this.jugadorActual.handicap.toFixed(1) }}
                                    </div>
                                </div>
                            </div>
                            <div class="card">
                                <div class="card-header verdeclaro"> Jugadores de Nivel Similar </div>
                                <ul class="list-group list-group-flush" v-for="similar in this.jugadoresSimilares"
                                    :key="similar._links.self.href">
                                    <JugadorSeleccionado 
                                        :jugadorSimilar="similar" 
                                        :jugadorViendo="this.jugadorActual"
                                        @abrir-modal-jugador-asignar="abrirModal('asignando', similar, this.jugadorActual)">
                                    </JugadorSeleccionado>
                                </ul>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!-- Modal creacion de partidos-->
    <div class="modal fade" id="modalCrearPartido" ref="modalCrearPartido" tabindex="-1"
        aria-labelledby="modalCrearPartidoLabel" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered">
            <div class="modal-content">
                <div class="modal-header verdeclaro" :class="{ 'verdeoscuro': this.estado == 'viendo' }">
                    <h1 class="modal-title fs-5" id="modalCrearPartidoLabel">{{ tituloModal }}</h1>
                    <button type="button" class="btn btn-danger ms-auto" data-bs-dismiss="modal"  @click="ocultarModal"
                        aria-label="Close"><font-awesome-icon :icon="['fas', 'xmark']" /></button>
                </div>
                <div class="modal-body">
                    <div>
                        <div class="row">
                            <div class="col-md-12 form-group mb-3">
                                <label for="campo">Campo</label>
                                <select id="campo" class="form-select" v-model="campoSeleccionadoPartido">
                                    <option v-for="campo in campos" :key="campo._links.self.href"
                                        :value="campo._links.self.href">{{
                            campo.nombre }}
                                    </option>
                                </select>
                            </div>
                            <div class="col-md-6 form-group mb-3">
                                <label for="fecha">Fecha</label>
                                <input type="date" id="fecha" class="form-control" v-model="fechaSeleccionada" required>
                            </div>
                            <div class="col-md-6 form-group mb-3">
                                <label for="hora">Hora</label>
                                <input type="time" id="hora" class="form-control" v-model="horaSeleccionada" required>
                            </div>
                        </div>
                        <div class="d-flex justify-content-between">
                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal"
                            @click="ocultarModal">Cancelar</button>
                            <div class="ml-auto">
                                <button type="button" class="btn btn-primary"
                                    @click="crearPartido">Confirmar</button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
        <!-- Modal Confirmacion -->
    <div class="modal fade" id="modalConfirmacion" ref="modalConfirmacion" tabindex="-1" aria-labelledby="modalConfirmacionLabel"
        aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered">
            <div class="modal-content">
                <div class="modal-header verdeoscuro">
                    <h1 v-if="this.estado == 'creando'" class="modal-title fs-5" id="modalConfirmacionLabel">Creacion de
                        jugador</h1>
                    <h1 v-if="this.estado == 'editando'" class="modal-title fs-5" id="modalConfirmacionLabel">Edicion de
                        jugador</h1>
                    <h1 v-if="this.estado == 'asignando'" class="modal-title fs-5" id="modalConfirmacionLabel">Creacion de
                        partido</h1>
                    <button type="button" class="btn btn-danger ms-auto" data-bs-dismiss="modal"
                        aria-label="Close"><font-awesome-icon :icon="['fas', 'xmark']" /></button>
                </div>
                <div class="modal-body d-flex align-items-center justify-content-center">
                    <h5 v-if="this.estado == 'creando'" style="text-align:justify;">Jugador creado correctamente</h5>
                    <h5 v-if="this.estado == 'editando'" style="text-align:justify;">Jugador editado correctamente</h5>
                    <h5 v-if="this.estado == 'asignando'" style="text-align:justify;">Partido creado correctamente</h5>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-primary" data-bs-dismiss="modal">Pues muy bien</button>
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