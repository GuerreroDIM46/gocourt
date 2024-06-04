<script>
import { mapState, mapActions } from 'pinia'
import { usePartidosAPIStore } from '@/stores/partidosAPI'
import { useCamposAPIStore } from '@/stores/camposAPI'


export default {
    data() {
        return {
            campoSeleccionado: '',
            fecha: '',
            hora: '',
        }
    },
    computed: {
        ...mapState(useCamposAPIStore, ['campos']),
        ...mapState(usePartidosAPIStore, ['partidoCompleto']),
        partido() {
            return this.partidoCompleto;
        }
    },
    methods: {
        ...mapActions(usePartidosAPIStore, ['actualizarPartido', 'cargarPartido']),
        ...mapActions(useCamposAPIStore, ['cargarCampos']),
        async submitForm() {
            const datosPartido = {
                campo: this.campoSeleccionado,
                cuando: `${this.fecha}T${this.hora}:00Z`,
                url: this.partido._links.self.href
            }
            console.log('datos enviados', datosPartido)
            await this.actualizarPartido(datosPartido)
        },
        cerrarVentana() {
            this.$router.go(-1)
        },
        async cargarDatosPartido() {
            await this.cargarPartido(this.$route.params.id);
            if (this.partido) {
                this.campoSeleccionado = this.campos.find(campo => campo.nombre == this.partido.nombreCampo)
                this.fecha = this.partido.cuando.split('T')[0];
                this.hora = this.partido.cuando.split('T')[1].substring(0, 5);
                console.log('el partido completo es:', this.partidoCompleto)
            }
        }
    },
    mounted() {
        this.cargarCampos();
        this.cargarDatosPartido();
    }
}
</script>

<template>
    <div class="container mt-5 mb-3">
        <h2>Introducir Detalles del Partido: </h2>
        <strong class="ms-3">
            {{ partidoCompleto.puntuaciones[0].nombreCompleto }}
        </strong> vs.
        <strong> {{ partidoCompleto.puntuaciones[1].nombreCompleto }}
        </strong>
        <form @submit.prevent="submitForm">
            <div class="mb-3 mt-3">
                <label for="campo">Campo</label>
                <select id="campo" class="form-select" v-model="campoSeleccionado">
                    <option v-for="campo in campos" :key="campo._links.self.href" :value="campo._links.self.href">
                        {{ campoSeleccionado.nombre }}
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
