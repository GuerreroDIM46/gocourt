<script>
export default {
    props: ['jugadoresprop'],
    emits: [],
    methods: {}
}
</script>

<template>
  <div class="jugador">
    <h3>{{ jugadoresprop.nombre }}</h3>
    <!-- Agrega más detalles del jugador según tus necesidades -->
  </div>
</template>



<style scoped>
.jugador {
  border: 1px solid #ccc;
  padding: 10px;
  margin-bottom: 10px;
}
</style>