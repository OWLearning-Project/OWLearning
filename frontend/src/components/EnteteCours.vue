<template>
  <div class="mb-5">
    <BButton variant="link" class="text-muted text-decoration-none p-0 mb-3" @click="$emit('retour')">
      <i class="bi bi-arrow-left me-2"></i> Retour aux cours
    </BButton>
    <div class="d-flex align-items-center gap-3">
      <h1 class="fw-bold fs-2 m-0 titre-catalogue">{{ cours.titre }}</h1>
      <BBadge v-if="estLeCreateur" variant="warning" class="text-dark fw-bold rounded-pill shadow-sm">
        <i class="bi bi-eye-fill me-1"></i> Aperçu Créateur
      </BBadge>
    </div>
    <p class="text-muted fs-6 mt-2 mb-0">{{ cours.description }}</p>
  </div>
</template>

<script setup>
defineProps({
  cours: {
    type: Object,
    required: true,
  },
  estLeCreateur: {
    type: Boolean,
    default: false,
  },
})

defineEmits(['retour'])
</script>
