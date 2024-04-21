<script>

export default {
    props: ['jugador'],
    emits: ['editar-jugador', 'borrar-jugador'],
    methods: {
        editarJugador() {
            this.$emit('editar-jugador', this.jugador)
        },
    }
}


</script>

<template>
    <div class="casilla">
        <tr class="jugador containerjugador ">
            <td class="fl">
                <div class="badge bg-success me-2">Principiante</div>
                <strong>{{ jugador.nombre }}
                    {{ jugador.apellido1 }}
                    {{ jugador.apellido2 }}</strong>
            </td>
            <td class="crecer"></td>
            <td>
                <button type="button" class="btn btn-outline-primary" disabled>
                    PL: {{ jugador.puntuacionLargo }}
                </button>
            </td>
            <!-- Puntuación corto en formato botón -->
            <td>
                <button type="button" class="btn btn-outline-primary" disabled>
                    PC: {{ jugador.puntuacionCorto }}
                </button>
            </td>
            <!-- Handicap en formato botón con un decimal -->
            <td>
                <button type="button" class="btn btn-outline-secondary" disabled>
                    Handicap: {{ jugador.handicap.toFixed(1) }}
                </button>
            </td>
        </tr>

        <!-- Segunda fila con DNI, club y acciones -->
        <tr class="jugador containerjugador">
            <!-- Ajusta las columnas para ser responsivas -->
            <td class="d-flex flex-column flex-md-row align-items-md-center w-100">
                <!-- Agrupa DNI y campo en un contenedor para mejor control -->
                <div class="d-flex flex-column mb-2 mb-md-0 flex-fill">
                    <span class="fl"> - DNI: {{ jugador.dni }}</span>
                    <span class="fl"> - Juega en: {{ jugador.nombreCampo }}</span>
                </div>
                <!-- Contenedor para los botones, siempre en una sola fila -->
                <div class="d-flex">
                    <div class="crecer"></div>
                    <button type="button" class="btn btn-outline-info mr-1">
                        <font-awesome-icon :icon="['fas', 'magnifying-glass']" class="icono-fontawesome" size="lg" />
                    </button>
                    <button type="button" class="btn btn-outline-warning mr-1">
                        <font-awesome-icon :icon="['fas', 'pen-to-square']" class="icono-fontawesome"
                            @click="editarJugador" size="lg" />
                    </button>
                    <button type="button" class="btn btn-outline-danger"
                        @click="$emit('borrar-jugador', { href: jugador._links.self.href })">
                        <font-awesome-icon :icon="['fas', 'trash']" class="icono-fontawesome" size="lg" />
                    </button>
                </div>
            </td>
        </tr>
    </div>
</template>



<style scoped>
.casilla {
    border-bottom: 1px solid #ccc;
    /* Añade una línea gris claro como separador */
    padding-bottom: 8px;
    /* Opcional, añade espacio debajo del contenido antes del borde */
    margin-bottom: 10px;
    /* Opcional, añade espacio después del borde */
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
    transform: scale(1.5);
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