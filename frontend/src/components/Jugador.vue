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
  <div class="jugador">
    <h3>{{ jugador.nombre }}</h3>
    <p>Edad: {{ jugador.edad }}</p>
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