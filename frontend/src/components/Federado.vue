<script>
export default {
    props: ['federadosprop'],
    emits: [],
    methods: {}
}

</script>

<template>
    <div class="jugador containerjugador">
        {{ federadosprop.nombre }}
        {{ federadosprop.apellido1 }}
        {{ federadosprop.apellido2 }}
        <div class="crecer"></div>
        {{ federadosprop.handicap }}
        <span class="pro" v-show="federadosprop.profesional">PRO</span>
        <font-awesome-icon :icon="['fas', 'magnifying-glass']" style="color: #74C0FC;"
            class="icono-fontawesome me-1 ms-2" size="xl" />
        <font-awesome-icon :icon="['fas', 'fa-pen-to-square']" style="color: #FFD43B;" class="icono-fontawesome me-1"
            size="xl" />
        <!-- <font-awesome-icon :icon="['fas', 'circle-xmark']" style="color: #ff0000;" /> -->
        <font-awesome-icon :icon="['fas', 'trash']" style="color: #ff0000;" class="icono-fontawesome" size="xl" />
    </div>

</template>


<style scoped>
.pro {
    font-weight: 500;
    font-style: italic;
}
.containerjugador {
    display: flex;
    flex-direction: row;
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