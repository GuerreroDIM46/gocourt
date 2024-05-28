<template>
  <header>
    <div class="d-flex" style="height: 10vh;">
      <div>
        <img src="../assets/logo_gocourt.png" class="logofit ms-2">
      </div>
      <div class="d-flex centrar crecer">
        <h1 style="font-style: italic; color: #395623;">GoCourt</h1>
      </div>
      
    </div>
  </header>
</template>

<style scoped>
header {
  background-color: #D5E8D4;
  padding: 10px 0;
  text-align: center;
  color: #000;

}

p {
  margin: 0;
  font-size: 14px;
}

.logofit{
    height:100%;
}

.centrar{
    justify-content: center;
    align-items: center;
}

.crecer{
    flex-grow: 1;
}

</style>