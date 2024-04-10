<template>
  <footer>
    <p class="fw-bold">Footer de Prueba</p>
    <p class="fw-bold">
      <a href="https://www.google.com/" target="_blank" rel="noopener noreferrer" aria-label="web de referencia"
        title="Web de Manabo">
        Web de Referencia
      </a>
    </p>
    <font-awesome-icon :icon="['fab', 'instagram']" size="2xl" />
    <font-awesome-icon :icon="['fab', 'x-twitter']" class="ms-5" size="2xl" />
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
</style>