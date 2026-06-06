<template>
  <BCard v-if="chapitre" class="border-0 shadow-sm" style="border-radius: 12px">
    <BCardBody class="p-4 p-md-5">
      <div v-if="!estLeCreateur" class="d-flex align-content mb-5 gap-3">
        <span class="small fw-bold text-muted text-nowrap">Progression</span>
        <BProgress :value="progression" class="flex-grow-1 shadow-sm barre-violette" height="10px" />
        <span class="small fw-bold" style="color: #4a2c59"> {{ progression }}% </span>
      </div>

      <h2 class="fw-bold mb-3 titre-catalogue">{{ chapitre.titre }}</h2>
      <p class="text-muted fs-5 mb-5 lh-lg border-bottom pb-4">
        {{ chapitre.description }}
      </p>

      <div v-if="chapitre.ressources && chapitre.ressources.length > 0">
        <RessourceChapitre
          v-for="ressource in chapitre.ressources"
          :key="ressource.id"
          :ressource="ressource"
        />
      </div>

      <hr class="my-5 separateur-catalogue opacity-25" />

      <div class="d-flex justify-content-between align-items-center">
        <BButton
          variant="outline-secondary"
          class="rounded-pill px-4 py-2 fw-bold"
          :disabled="indexChapitreActif === 0"
          @click="$emit('precedent')"
        >
          <i class="bi bi-arrow-left me-2"></i> Précédent
        </BButton>

        <span class="text-muted small fw-bold d-none d-md-block">
          Chapitre {{ indexChapitreActif + 1 }} sur {{ cours.chapitres.length }}
        </span>

        <BButton class="rounded-pill px-4 py-2 fw-bold bouton-suivant" @click="$emit('suivant')">
          <span v-if="indexChapitreActif < cours.chapitres.length - 1">
            {{ estLeCreateur ? 'Chapitre suivant' : 'Terminer le chapitre' }}
            <i class="bi bi-arrow-right ms-2"></i>
          </span>
          <span v-else>
            {{ estLeCreateur ? "Quitter l'aperçu" : 'Terminer le cours' }}
            <i class="bi bi-check-circle-fill ms-2"></i>
          </span>
        </BButton>
      </div>
    </BCardBody>
  </BCard>
</template>

<script setup>
import RessourceChapitre from '@/components/RessourceChapitre.vue'

defineProps({
  cours: {
    type: Object,
    required: true,
  },
  chapitre: {
    type: Object,
    default: null,
  },
  estLeCreateur: {
    type: Boolean,
    default: false,
  },
  progression: {
    type: Number,
    required: true,
  },
  indexChapitreActif: {
    type: Number,
    required: true,
  },
})

defineEmits(['precedent', 'suivant'])
</script>

<style scoped>
.bouton-suivant {
  background-color: #4a2c59 !important;
  border-color: #4a2c59 !important;
  color: white !important;
  transition: all 0.3s ease;
}

.bouton-suivant:hover {
  background-color: #b8a854 !important;
  border-color: #b8a854 !important;
  color: #4a2c59 !important;
  transform: translateX(3px);
}

.barre-violette :deep(.progress-bar) {
  background-color: #4a2c59 !important;
}
</style>
