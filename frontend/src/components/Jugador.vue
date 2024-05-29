<script>
export default {
    props: ['jugador'],
    emits: ['editar-jugador', 'borrar-jugador', 'ver-jugador'],
    methods: {
        editarJugador() {
            this.$emit('editar-jugador', this.jugador)
        },
        verJugador() {
            this.$emit('ver-jugador', this.jugador)
        },
    }
}
</script>

<template>
    <div class="casilla">
        <tr class="jugador containerjugador">
            <td class="fl">
                <div v-if="jugador.tipo == 'federado'" class="badge bg-secondary me-2">Federado</div>
                <div v-if="jugador.tipo == 'principiante'" class="badge bg-success me-2">Principiante</div>
                <div v-if="jugador.profesional" class="badge bg-warning me-2">PRO</div>
                <strong>{{ jugador.nombre }} {{ jugador.apellido1 }} {{ jugador.apellido2 }}</strong>
            </td>
            <td class="crecer"></td>

            <td v-if="jugador.tipo == 'principiante'" class="align-middle">
                <button type="button" class="btn btn-outline-primary" disabled>
                    PL: {{ jugador.puntuacionLargo }}
                </button>
                <button type="button" class="btn btn-outline-primary" disabled>
                    PC: {{ jugador.puntuacionCorto }}
                </button>
            </td>
            <td class="align-middle">
                <button type="button" class="btn btn-outline-secondary" disabled>
                    Handicap: {{ jugador.handicap.toFixed(1) }}
                </button>
            </td>
        </tr>
        <tr class="jugador containerjugador">
            <td class="d-flex flex-column flex-md-row align-items-md-center w-100">
                <div class="d-flex flex-column mb-2 mb-md-0 flex-fill">
                    <span class="fl"> - DNI: {{ jugador.dni }}</span>
                    <span class="fl"> - Juega en: {{ jugador.nombreCampo }}</span>
                </div>
                <div class="d-flex">
                    <div class="crecer"></div>
                    <button type="button" class="btn btn-outline-info mr-1" @click="verJugador">
                        <font-awesome-icon :icon="['fas', 'magnifying-glass']" class="icono-fontawesome" size="lg" />
                    </button>
                    <button type="button" class="btn btn-outline-warning mr-1" @click="editarJugador">
                        <font-awesome-icon :icon="['fas', 'pen-to-square']" class="icono-fontawesome" size="lg" />
                    </button>
                    <button type="button" class="btn btn-outline-danger" data-bs-toggle="modal"
                        data-bs-target="#ModalConfirmacionBorrado">
                        <font-awesome-icon :icon="['fas', 'trash']" class="icono-fontawesome" size="lg" />
                    </button>
                </div>
            </td>
        </tr>
    </div>

    <!-- Modal Confirmacion Borrado-->
    <div class="modal fade" id="ModalConfirmacionBorrado" tabindex="-1" aria-labelledby="exampleModalLabel"
        aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered">
            <div class="modal-content">
                <div class="modal-header verdeoscuro">
                    <h1 class="modal-title fs-5" id="exampleModalLabel">Confirmacion de Borrado</h1>
                    <button type="button" class="btn btn-danger ms-auto" data-bs-dismiss="modal" aria-label="Close"><font-awesome-icon :icon="['fas', 'xmark']" /></button>
                </div>
                <div class="modal-body d-flex align-items-center justify-content-center">
                    <h5 style="text-align:justify;">¿Seguro?</h5>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-success" data-bs-dismiss="modal">Cancelar</button>
                    <div class="crecer"></div>
                    <button type="button" class="btn btn-danger" data-bs-dismiss="modal"
                        @click="$emit('borrar-jugador', { href: jugador._links.self.href })">Borrar Jugador</button>
                </div>
            </div>
        </div>
    </div>

</template>

<style scoped>
.casilla {
    border-bottom: 1px solid #ccc;
    padding-bottom: 8px;
    margin-bottom: 10px;
}

.containerjugador {
    display: flex;
    flex-direction: row;
}

.fl {
    font-weight: 400;
}

.jugador {

    margin: 4px;
}

.crecer {
    flex-grow: 1;
}

.icono-fontawesome {
    cursor: pointer;
    transition: transform 0.2s ease;
}

.icono-fontawesome:hover {
    transform: scale(1.2);
}

.btn-outline-primary {
    color: #395623;
    border-color: #395623;
}

.btn-outline-primary:hover,
.btn-outline-secondary:hover {
    transform: none;
}

.btn-outline-secondary {
    color: #70AD47;
    border-color: #70AD47;
}

.btn-outline-info,
.btn-outline-warning,
.btn-outline-danger,
.btn-outline-secondary,
.btn-outline-primary {
    padding: 0.25rem 0.5rem;
    margin-right: 10px;
}

.btn-outline-info:hover,
.btn-outline-warning:hover,
.btn-outline-danger:hover {
    color: white;
}

</style>