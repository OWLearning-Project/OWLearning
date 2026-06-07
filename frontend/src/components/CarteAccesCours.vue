<template>
  <BCard class="carte-action-cours border-0 shadow-sm">
    <h2 class="titre-carte-cours fs-4 fw-bold mb-3">Votre accès</h2>

    <div v-if="estCreateurDuCours" class="mb-4">
      <p class="text-muted mb-0">Vous êtes le créateur de ce cours.</p>
    </div>

    <div v-else-if="estInscrit" class="mb-4">
      <div class="d-flex justify-content-between align-items-center mb-2">
        <span class="fw-bold text-muted small">Progression</span>
        <span class="fw-bold">{{ progression }}%</span>
      </div>
      <BProgress
        :value="progression"
        :max="100"
        height="1rem"
        class="progression-cours"
      ></BProgress>
    </div>

    <p v-else class="text-muted mb-4">Vous pouvez consulter l'aperçu de ce cours.</p>

    <BButton
      v-if="peutVoirCours"
      class="bouton-cours w-100 rounded-pill fw-bold py-2"
      @click="$emit('action')"
    >
      {{ libelleAction }}
    </BButton>
  </BCard>
</template>

<script setup>
defineProps({
  estInscrit:
    {
    type: Boolean,
    default: false,
  },
  estCreateurDuCours:
    {
    type: Boolean,
    default: false,
  },
  progression:
    {
    type: Number,
    default: 0,
  },
  peutVoirCours:
    {
    type: Boolean,
    default: false,
  },
  libelleAction:
    {
    type: String,
    required: true,
  }
})

defineEmits(['action'])
</script>
