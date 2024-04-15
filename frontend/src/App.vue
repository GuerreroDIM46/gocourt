<script>
import xheader from "@/components/xheader.vue"
import navbar from "@/components/navbar.vue"
import xfooter from "@/components/xfooter.vue"
// import { mapState, mapActions } from 'pinia'
// import { usePartidosAPIStore } from '@/stores/partidosAPI'

export default {
  components: { navbar, xfooter, xheader },
  // computed: {
  //   ...mapState(usePartidosAPIStore, ['partidosCargados', 'partidos'])
  // },
  // methods: {
  //   ...mapActions(usePartidosAPIStore, ['cargarPartidos'])
  // },
  // mounted() {
  //   this.cargarPartidos()
  //   // this.partidosCargados = true
  // }
}

</script>

<template>
  <div class="app-container ">
    <xheader></xheader>
    <div class="container">
    <navbar></navbar>
  </div>
    <main>
    <router-view></router-view>
    </main>
    <xfooter></xfooter>

  </div>
</template>

<style scoped>
.app-container {
  display: flex;
  flex-direction: column;
  min-height: 100vh;
}
main {
  flex: 1;
}
</style>