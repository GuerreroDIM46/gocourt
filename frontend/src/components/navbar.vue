<script>
// import { useAuthStore } from '@/stores/auth'
// import { mapState, mapActions } from 'pinia'

// export default {
//   computed: {
//     ...mapState(useAuthStore, ['tipoUsuario', 'esAdmin']),

//     cambioUsuario: {
//       get() {
//         return this.esAdmin
//       },
//       set(value) {
//         this.cambiarNivel()
//       }
//     }
//   },
//   methods: {
//     ...mapActions(useAuthStore, ['cambiarNivel'])
//   }
// }
</script>

<template>
  <ul class="nav nav-tabs nav-fill">
    <li class="nav-item">
      <router-link class="nav-link" :class="{ active: ($route.path === '/jugadores' || $route.path === '/federados'  || $route.path === '/principiantes' || $route.path === '/todos') }" to="/principiantes">Principiantes</router-link>
    </li>
    <li class="nav-item">
      <router-link class="nav-link" :class="{ active: $route.path === '/partidos' }" to="/partidos">Federados</router-link>
    </li>
  </ul>
</template>

<style scoped>

.nav-tabs .nav-link {
  background-color: #395623;
  color: #CCCCCC;
  font-weight: 500;
  border: 1px;
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
