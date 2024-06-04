<script>

</script>

<template>
  <ul v-if="!/^\/landingPage\/\d+$/.test($route.path)" class="nav nav-tabs nav-fill">
    <li class="nav-item me-1">
      <router-link class="nav-link" :class="{ active: $route.path == '/jugadores' }" to="/jugadores">Jugadores</router-link>
    </li>
    <li class="nav-item">
      <router-link class="nav-link" :class="{ active: $route.path == '/partidos' }" to="/partidos">Partidos</router-link>
    </li>
  </ul>
</template>

<style scoped>

.nav-tabs .nav-link {
  background-color: #395623;
  color: #CCCCCC;
  font-weight: 500;
  border: solid, 2px;
  border-color: #395623;
}

.nav-tabs .nav-link.active {
  background-color: #70AD47;
  color: white;
  font-weight: 500;

}

/* .nav-tabs .nav-link:hover {
  background-color: #4e732e; 
  border-color: #70AD47;
} */

</style>
