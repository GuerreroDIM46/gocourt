<script>
export default {
    props: {
        jugador: {
            type: Object,
            required: true
        }
    }
}
</script>

<template>
    <li class="list-group-item">
        <div class="jugador-info-container">
            <div class="jugador-info">
                <div class="jugador-datos">
                    <div class="nombre-apellidos">
                        <div v-if="jugador.tipo === 'federado'" class="badge bg-secondary me-2">Federado</div>
                        <div v-if="jugador.tipo === 'principiante'" class="badge bg-success me-2">Principiante</div>
                        <div v-if="jugador.profesional" class="badge bg-warning me-2">PRO</div>
                        <strong>{{ jugador.nombre }} {{ jugador.apellido1 }} {{ jugador.apellido2 }}</strong>
                    </div>
                    <div v-if="jugador.tipo === 'federado'" class="handicap">
                        Handicap: {{ jugador.handicap.toFixed(1) }}
                    </div>
                    <div v-if="jugador.tipo === 'principiante'" class="handicap">
                        Handicap Calculado: {{ jugador.handicap.toFixed(1) }}
                    </div>
                </div>
            </div>
            <div class="boton-asignar">
                <!-- Botón a la derecha -->
                <button type="button" class="btn btn-primary">Asignar</button>
            </div>
        </div>
    </li>
</template>
<style scoped>
.jugador-info-container {
    display: flex;
    justify-content: space-between;
    /* Alinea los hijos al inicio y al final del contenedor */
    align-items: center;
    /* Centra los hijos verticalmente */
    margin-bottom: 10px;
    /* Espacio debajo del contenedor */
}

.jugador-info {
    flex-grow: 1;
    /* Ocupa todo el espacio disponible */
}

.nombre-apellidos {
    display: flex;
    align-items: center;
    /* Alinea los badges y el nombre verticalmente */
}

.handicap {
    margin-top: 5px;
    /* Espacio entre nombre y handicap */
}

.btn-primary {
    background-color: #70AD47 !important;
    border-color: #70AD47;
    color: white;
}

.btn-primary:hover {
    transform: scale(1.1);
    font-weight: 800;
}
</style>