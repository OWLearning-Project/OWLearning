<template>
  <div class="text-center my-5">
    <BSpinner
      class="spinner-custom"
      :label="texte"
    ></BSpinner>
    <p class="mt-3 text-muted fw-bold">{{ texte }}</p>
  </div>
</template>

<script setup>
defineProps({
  texte: {
    type: String,
    default: 'Chargement...'
  }
});
</script>

<style scoped>
.spinner-custom {
  width: 3rem;
  height: 3rem;
  color: #a17c5b;
}
</style>
