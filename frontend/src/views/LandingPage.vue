<script>
import { mapState, mapActions } from 'pinia'
import { usePartidosAPIStore } from '@/stores/partidosAPI'
import { useCamposAPIStore } from '@/stores/camposAPI'
import { usePuntuacionesAPIStore } from '@/stores/puntuacionesAPI'
import { useEmailAPIStore } from '@/stores/emailAPI'

export default {
    data() {
        return {
            campoSeleccionado: '',
            fecha: '',
            hora: '',
            datosCargados: false,
            partidoEnviado: false
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
                cuando: `${this.fecha}T${this.hora}:00Z`,
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
            console.log('datos enviados: partido', datosPartido)
            console.log('datos enviados: asignacion1', asignacion1)
            console.log('datos enviados: asignacion2', asignacion2)
            await Promise.all([
                this.actualizarPartido(datosPartido),
                this.actualizarPuntuacion(asignacion1),
                this.actualizarPuntuacion(asignacion2)
            ])
            this.partidoEnviado = true
            this.enviarEmailsPartidoAceptado(this.partidoCompleto._links.self.href, this.partidoCompleto.puntuaciones[0]._links.self.href, this.partidoCompleto.puntuaciones[1]._links.self.href)
        },

        cerrarVentana() {
            this.$router.go(-1)
        },
        async cargarDatosPartido() {
            await this.cargarPartido(this.$route.params.id);
            if (this.partidoCompleto) {
                this.campoSeleccionado = this.campos.find(campo => campo.nombre == this.partidoCompleto.nombreCampo)
                this.fecha = this.partidoCompleto.cuando.split('T')[0]
                this.hora = this.partidoCompleto.cuando.split('T')[1].substring(0, 5)
                console.log('el partido completo es:', this.partidoCompleto)
            }
            this.datosCargados = true;
        }
    },
    mounted() {
        this.cargarCampos()
        this.cargarDatosPartido()
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
                <strong>Hora:</strong> {{ hora }}
            </p>
            <p>
                <strong>Campo:</strong> {{ campoSeleccionado.nombre }}
            </p>
            <button type="button" class="btn btn-secondary" @click="cerrarVentana">Cerrar</button>
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
                    <label for="hora" class="form-label">Hora</label>
                    <input type="time" class="form-control" id="hora" v-model="hora" required>
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
