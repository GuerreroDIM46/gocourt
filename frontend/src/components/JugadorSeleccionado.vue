<script>
export default {
    props: {
        jugador: {}
    }
}
</script>

<template>
    <li class="list-group-item">
        <div class="jugador-info-container">
            <div class="jugador-info">
                <div class="jugador-datos">
                    <div class="nombre-apellidos">
                        <div v-if="jugador.tipo == 'federado'" class="badge bg-secondary me-2">Federado</div>
                        <div v-if="jugador.tipo == 'principiante'" class="badge bg-success me-2">Principiante</div>
                        <div v-if="jugador.profesional" class="badge bg-warning me-2">PRO</div>
                        <strong>{{ jugador.nombre }} {{ jugador.apellido1 }} {{ jugador.apellido2 }}</strong>
                    </div>
                    <div v-if="jugador.tipo == 'federado'" class="handicap">
                        Handicap: {{ jugador.handicap.toFixed(1) }}
                    </div>
                    <div v-if="jugador.tipo == 'principiante'" class="handicap">
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
    align-items: center;
    margin-bottom: 10px;
}

.jugador-info {
    flex-grow: 1;
}

.nombre-apellidos {
    display: flex;
    align-items: center;
}

.handicap {
    margin-top: 5px;
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