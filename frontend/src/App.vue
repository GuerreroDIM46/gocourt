<script>
import xheader from "@/components/xheader.vue"
import navbar from "@/components/navbar.vue"
import xfooter from "@/components/xfooter.vue"

export default {
  components: { navbar, xfooter, xheader },
}

</script>

<template>
  <div class="app-container ">
    <xheader></xheader>
    <div class="container">
    <navbar></navbar>
  </div>
    <main>holaquetal
    <!-- <router-view></router-view> -->
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