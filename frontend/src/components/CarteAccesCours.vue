<template>
  <BCard class="carte-action-cours border-0 shadow-sm">
    <h2 class="titre-carte-cours fs-4 fw-bold mb-3">Votre accès</h2>

    <div v-if="estInscrit" class="mb-4">
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

    <p v-else class="text-muted mb-4">Inscrivez-vous pour suivre votre progression sur ce cours.</p>

    <BAlert
      v-if="message !== ''"
      :variant="typeMessage"
      show
      class="alerte-catalogue-vide border-0"
    >
      {{ message }}
    </BAlert>

    <BButton
      class="bouton-cours w-100 rounded-pill fw-bold py-2"
      :disabled="actionEnCours"
      @click="$emit('action')"
    >
      <template v-if="actionEnCours">
        <BSpinner small class="me-2"></BSpinner>
        Traitement...
      </template>
      <template v-else>
        {{ libelleAction }}
      </template>
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
  progression:
    {
    type: Number,
    default: 0,
  },
  message:
    {
    type: String,
    default: '',
  },
  typeMessage:
    {
    type: String,
    default: 'info',
  },
  actionEnCours:
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
