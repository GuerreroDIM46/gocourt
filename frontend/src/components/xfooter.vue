<template>
  <footer>
    <p class="fw-bold">
      <a href="www.rfegolf.es" target="_blank" rel="noopener noreferrer" aria-label="Web de Manabo"
        title="Web de RF Golf" class="custom-color">
        Web de RF Golf
      </a>
    </p>
    <a href="https://www.instagram.com/rfegolf/" aria-hidden="true"
    target="_blank" class="custom-color">
    <font-awesome-icon :icon="['fab', 'instagram']" size="2xl" />
    </a>
    <a href="https://www.facebook.com/rfegolf" aria-hidden="true"
    target="_blank" class="custom-color ms-5">
    <font-awesome-icon :icon="['fab', 'facebook']" size="2xl" />
    </a>
  </footer>
</template>

<style scoped>
footer {
  background-color: #D5E8D4;
  padding: 10px 0;
  text-align: center;
  color: #000;
}

p {
  margin: 0;
  font-size: 14px;
}

.custom-color {
  color: #395623;
}
</style>
