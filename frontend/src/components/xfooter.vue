<template>
  <footer>
    <p class="fw-bold">Manabo2</p>
    <p class="fw-bold">
      <a href="https://nihongo-manabo.com/" target="_blank" rel="noopener noreferrer" aria-label="Web de Manabo"
        title="Web de Manabo">
        Web de Manabo
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