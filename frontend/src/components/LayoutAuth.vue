<template>
  <BContainer fluid class="min-vh-100 d-flex flex-column p-0 bg-light">
    <header class="entete-auth sticky-top p-2 text-white d-flex align-items-center shadow-sm" style="background-color: #a17c5b;">
      <img src="../assets/OwleEcharpe.svg" alt="Logo" width="70" height="70" class="fs-4 me-2" />
      <h1 class="m-0 fs-4 fw-bold">OWLearning</h1>
    </header>

    <BContainer class="flex-grow-1 flex-column d-flex align-items-center mt-4 mb-5">
      <h1 class="titre-auth my-4 text-center fw-bold" style="color: #4a2c59;">{{ titre }}</h1>

      <BCard class="carte-auth shadow w-100 border-0 p-2" style="max-width: 500px; border-radius: 12px;">
        <slot></slot>
      </BCard>
    </BContainer>
  </BContainer>
</template>

<script setup>
defineProps({
  titre: {
    type: String,
    default: 'Bienvenue sur OWLearning !'
  }
})
</script>
<style scoped>

</style>
