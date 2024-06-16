<script>
import { mapState, mapActions } from 'pinia'
import { usePartidosAPIStore } from '@/stores/partidosAPI'
import { useCamposAPIStore } from '@/stores/camposAPI'
import { usePuntuacionesAPIStore } from '@/stores/puntuacionesAPI'
import { useEmailAPIStore } from '@/stores/emailAPI'
import { Modal } from 'bootstrap'

export default {
    data() {
        return {
            campoSeleccionado: '',
            fecha: '',
            horaSeleccionada: '',
            minutoSeleccionado: '',
            datosCargados: false,
            partidoEnviado: false,
            horas: ['08', '09', '10', '11', '12', '13', '14', '15', '16', '17', '18', '19', '20'],
            minutos: ['00', '20', '40'],
            error: '',
            bsModalError: null,
        }
    },
    computed: {
        ...mapState(useCamposAPIStore, ['campos']),
        ...mapState(usePartidosAPIStore, ['partidoCompleto']),
    },
    methods: {
        ...mapActions(usePartidosAPIStore, ['actualizarPartido', 'cargarPartido']),
        ...mapActions(useCamposAPIStore, ['cargarCampos']),
        ...mapActions(usePuntuacionesAPIStore, ['actualizarPuntuacion']),
        ...mapActions(useEmailAPIStore, ['enviarEmailsPartidoAceptado']),
        async enviarFormularioPartido() {
            const datosPartido = {
                campo: this.campoSeleccionado._links.self.href,
                cuando: `${this.fecha}T${this.horaSeleccionada}:${this.minutoSeleccionado}:00Z`,
                url: this.partidoCompleto._links.self.href
            }
            const asignacion1 = {
                aceptado: true,
                url: this.partidoCompleto.puntuaciones[0]._links.self.href
            }
            const asignacion2 = {
                aceptado: true,
                url: this.partidoCompleto.puntuaciones[1]._links.self.href
            }
            const partidoRetorno = await this.actualizarPartido(datosPartido)
            if (partidoRetorno == 'error') {
                this.mostrarModalError("partido")
            } else {
                const asignacion1Retorno = await this.actualizarPuntuacion(asignacion1)
                if (asignacion1Retorno == 'error') {
                    this.mostrarModalError("asignacion1")
                } else {
                    const asignacion2Retorno = await this.actualizarPuntuacion(asignacion2)
                    if (asignacion2Retorno == 'error') {
                        this.mostrarModalError("asignacion2")
                    } else {
                        this.partidoEnviado = true
                        this.enviarEmailsPartidoAceptado(this.partidoCompleto._links.self.href, this.partidoCompleto.puntuaciones[0]._links.self.href, this.partidoCompleto.puntuaciones[1]._links.self.href)
                    }
                }
            }
        },
        mostrarModalError(error) {
            this.error = error
            this.bsModalError.show()
        },
        cerrarVentana() {
            this.$router.go(-1)
        },
        async cargarDatosPartido() {
            await this.cargarPartido(this.$route.params.id)
            if (this.partidoCompleto) {
                this.campoSeleccionado = this.campos.find(campo => campo.nombre == this.partidoCompleto.nombreCampo)
                this.fecha = this.partidoCompleto.cuando.split('T')[0]
                const hora = this.partidoCompleto.cuando.split('T')[1]
                this.horaSeleccionada = hora.split(':')[0]
                this.minutoSeleccionado = hora.split(':')[1]
            }
            this.datosCargados = true
        }
    },
    mounted() {
        this.cargarCampos()
        this.cargarDatosPartido()
        this.bsModalError = new Modal(this.$refs.modalError)
    }
}
</script>



<template>
    <div class="container mt-5 mb-3" v-if="datosCargados">
        <div v-if="partidoEnviado">
            <h2 class="mb-3">Detalles del Partido Enviado</h2>
            <p>
                <strong>Jugadores:</strong>
                {{ partidoCompleto.puntuaciones[0].nombreCompleto }} vs. {{
        partidoCompleto.puntuaciones[1].nombreCompleto }}
            </p>
            <p>
                <strong>Fecha:</strong> {{ fecha }}
            </p>
            <p>
                <strong>Hora:</strong> {{ horaSeleccionada }}:{{ minutoSeleccionado }}
            </p>
            <p>
                <strong>Campo:</strong> {{ campoSeleccionado.nombre }}
            </p>
        </div>
        <div v-if="!partidoEnviado">
            <h2>Introducir Detalles del Partido: </h2>
            <strong class="ms-3">
                {{ partidoCompleto.puntuaciones[0].nombreCompleto }}
            </strong> vs.
            <strong> {{ partidoCompleto.puntuaciones[1].nombreCompleto }}
            </strong>
            <form @submit.prevent="enviarFormularioPartido">
                <div class="mb-3 mt-3">
                    <label for="campo">Campo</label>
                    <select id="campo" class="form-select" v-model="campoSeleccionado">
                        <option v-for="campo in campos" :key="campo._links.self.href" :value="campo">
                            {{ campo.nombre }}
                        </option>
                    </select>
                </div>
                <div class="mb-3">
                    <label for="fecha" class="form-label">Fecha</label>
                    <input type="date" class="form-control" id="fecha" v-model="fecha" required>
                </div>
                <div class="mb-3">
                    <label for="hora">Hora</label>
                                <div class="input-group">
                                    <select class="form-control text-center" v-model="horaSeleccionada" required>
                                        <option v-for="hora in horas" :key="hora" :value="hora">{{ hora }}</option>
                                    </select>
                                    <select class="form-control text-center" v-model="minutoSeleccionado" required>
                                        <option v-for="minuto in minutos" :key="minuto" :value="minuto">{{ minuto }}
                                        </option>
                                    </select>
                                    <span class="input-group-text"><i class="pi pi-clock"
                                            style="font-size: 1rem"></i></span>
                                </div>
                </div>
                <div class="d-flex justify-content-between mt-5">
                    <button type="button" class="btn btn-secondary" @click="cerrarVentana">Cancelar</button>
                    <div class="ml-auto">
                        <button type="submit" class="btn btn-primary">Confirmar</button>
                    </div>
                </div>
            </form>
        </div>
        <div class="container mt-5 mb-3" v-if="!datosCargados">
            <div class="d-flex justify-content-center">
                <div class="spinner-border" role="status">
                    <span class="visually-hidden">Carganding...</span>
                </div>
            </div>
        </div>
    </div>
    <!-- Modal Error -->
    <div class="modal fade" id="modalError" ref="modalError" tabindex="-1" aria-labelledby="modalErrorLabel"
        aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered">
            <div class="modal-content">
                <div class="modal-header verdeoscuro">
                    <h1 class="modal-title fs-5" id="modalConfirmacionLabel">
                        Error</h1>
                </div>
                <div class="modal-body d-flex align-items-center justify-content-center">
                    <h5 v-if="this.error == 'partido'" style="text-align:justify;">Ya existe un partido en ese horario
                        en ese campo
                    </h5>
                    <h5 v-if="this.error == 'asignacion1'" style="text-align:justify;">{{ partidoCompleto.puntuaciones[0].nombreCompleto }}
                        ya tiene partido asignado ese dia
                    </h5>
                    <h5 v-if="this.error == 'asignacion2'" style="text-align:justify;">{{ partidoCompleto.puntuaciones[1].nombreCompleto }}
                        ya tiene partido asignado ese dia
                    </h5>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-primary" data-bs-dismiss="modal"
                        >Mejor pruebo otra vez</button>
                </div>
            </div>
        </div>
    </div>
</template>


<style scoped>
.container {
    max-width: 600px;
    margin: 0 auto;
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
    background-color: #395623;
    border-color: #395623;
    color: white;
}

.btn-secondary:hover {
    background-color: white;
    border-color: #395623;
    color: #395623;
}
</style>
