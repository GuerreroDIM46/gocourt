<script>
import { faSquarePen } from '@fortawesome/free-solid-svg-icons';

export default {
    props: ['principiantesprop'],
    emits: [],
    methods: {}
}

</script>

<template>
    <tr class="jugador containerjugador ">
        <th class="fl">{{ principiantesprop.nombre }}
        {{ principiantesprop.apellido1 }}
        {{ principiantesprop.apellido2 }}</th>
        <th class="crecer"></th>
        <th class="me-2 fl">{{ principiantesprop.puntuacionLargo }}</th>
        <th class="me-2 fl">{{ principiantesprop.puntuacionCorto }}</th>
        <th class="me-2 ">{{ principiantesprop.puntuacionCorto }}</th>
        <th >
        <font-awesome-icon :icon="['fas', 'magnifying-glass']" style="color: #74C0FC;"
            class="icono-fontawesome me-1 ms-2" size="xl" />
        <font-awesome-icon :icon="['fas', 'fa-pen-to-square']" style="color: #FFD43B;" class="icono-fontawesome me-1"
            size="xl" />
        <!-- <font-awesome-icon :icon="['fas', 'circle-xmark']" style="color: #ff0000;" /> -->
        <font-awesome-icon :icon="['fas', 'trash']" style="color: #ff0000;" class="icono-fontawesome" size="xl" />
    </th>
    </tr>


</template>


<style scoped>
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
</style>