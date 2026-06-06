<template>
  <BNavbar class="bg-white rounded-4 shadow-sm py-2 mb-5 justify-content-center">
    <BNavbarBrand tag="div" class="mb-0 p-0 w-100 text-center">
      <h1 class="fw-bold fs-2 m-0 mt-2" style="color: #4a2c59">{{ titre }}</h1>
      <p class="text-muted fs-6 mt-1 mb-2">{{ sousTitre }}</p>

      <hr
        class="w-25 mx-auto mb-2"
        style="border-color: #a17c5b; border-width: 3px; opacity: 1"
      />
    </BNavbarBrand>
  </BNavbar>
</template>

<script setup>
defineProps({
  titre: {
    type: String,
    required: true
  },
  sousTitre: {
    type: String,
    required: true
  }
});
</script>

<style scoped>
</style>
