<template>
  <BCard class="border-0 shadow-sm mt-4 carte-filtres">
    <BButton
      variant="link"
      class="text-decoration-none text-dark w-100 d-flex justify-content-between align-items-center p-0"
      @click="$emit('basculer')"
    >
      <h5 class="fw-bold m-0" style="color: #4a2c59">
        <i class="bi bi-people-fill me-2"></i>Étudiants inscrits
      </h5>
      <i class="bi fw-bold fs-5" :class="ouvert ? 'bi-chevron-up' : 'bi-chevron-down'" style="color: #4a2c59"></i>
    </BButton>
    <BCollapse id="collapse-eleves" v-model="ouvert" class="mt-3">
      <hr class="text-muted opacity-25 mt-0 mb-3" />

      <BListGroup v-if="eleves.length > 0" flush class="gap-2">
        <BListGroupItem
          v-for="eleve in eleves"
          :key="eleve.id"
          class="d-flex align-items-center p-2 rounded-3 border-0 eleve-cliquable"
          style="background-color: #f8f9fa"
          @click="$emit('ouvrir-profil', eleve)"
        >
          <BAvatar
            class="me-3 shadow-sm"
            size="2.5rem"
            style="background-color: #b8a854 !important; color: white; font-weight: bold"
            :text="initiales(eleve)"
          />

          <div class="text-truncate flex-grow-1">
            <h6 class="mb-0 fw-bold text-truncate" style="color: #4a2c59">
              {{ eleve.prenom }} {{ eleve.nom }}
            </h6>
            <small class="text-muted d-block text-truncate">
              {{ eleve.email }}
            </small>
          </div>
        </BListGroupItem>
      </BListGroup>

      <div v-else class="text-center py-3 text-muted small">
        Aucun étudiant n'est encore inscrit à ce cours
      </div>
    </BCollapse>
  </BCard>
</template>

<script setup>
const ouvert = defineModel('ouvert', {
  type: Boolean,
  default: false,
})

defineProps({
  eleves: {
    type: Array,
    required: true,
  },
})

defineEmits(['basculer', 'ouvrir-profil'])

function initiales(eleve) {
  return eleve.prenom.charAt(0).toUpperCase() + eleve.nom.charAt(0).toUpperCase()
}
</script>

<style scoped>
.eleve-cliquable:hover {
  background-color: #e9ecef !important;
  transform: translateX(4px);
}
</style>
