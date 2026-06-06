<template>
  <BCard class="border-0 shadow-sm" style="border-radius: 12px; position: sticky; top: 20px">
    <h5 class="fw-bold mb-4" style="color: #4a2c59">
      <i class="bi bi-funnel-fill me-2"></i>Filtres
    </h5>

    <div class="mb-3">
      <label class="form-label fw-bold small text-muted">Rechercher un cours</label>
      <BFormInput
        v-model="recherche"
        placeholder="Ex: Java..."
        class="rounded-pill px-3"
      ></BFormInput>
    </div>

    <hr class="text-muted mb-4" />

    <template v-if="afficherOptionPrive">
      <div class="mb-4 d-flex justify-content-between align-items-center">
        <label class="form-label fw-bold small text-muted mb-0">Cours privés</label>
        <BFormCheckbox v-model="coursPrives" switch size="lg"></BFormCheckbox>
      </div>
      <hr class="text-muted mb-4" />
    </template>

    <div class="mb-4">
      <label class="form-label fw-bold small text-muted mb-3">Difficulté</label>
      <BFormCheckboxGroup
        v-model="difficultes"
        :options="difficultesPossibles"
        stacked
        class="checkboxes-custom"
      ></BFormCheckboxGroup>
    </div>

    <div class="mb-2" v-if="categoriesPossibles.length > 0">
      <label class="form-label fw-bold small text-muted mb-3">Catégories</label>
      <BFormCheckboxGroup
        v-model="categories"
        :options="categoriesPossibles"
        stacked
        class="checkboxes-custom"
      ></BFormCheckboxGroup>
    </div>
  </BCard>
</template>

<script setup>
const recherche = defineModel('recherche');
const difficultes = defineModel('difficultes');
const categories = defineModel('categories');
const coursPrives = defineModel('coursPrives');

defineProps({
  categoriesPossibles: {
    type: Array,
    required: true
  },
  afficherOptionPrive: {
    type: Boolean,
    default: false
  }
});

const difficultesPossibles = [
  { text: 'Débutant', value: 'DEBUTANT' },
  { text: 'Intermédiaire', value: 'INTERMEDIAIRE' },
  { text: 'Avancé', value: 'AVANCE' },
];
</script>

<style scoped>
:deep(.checkboxes-custom .form-check-input:checked) {
  background-color: #4a2c59 !important;
  border-color: #4a2c59 !important;
}

:deep(.checkboxes-custom .form-check-input:focus) {
  box-shadow: 0 0 0 0.25rem rgba(74, 44, 89, 0.25) !important;
  border-color: #4a2c59 !important;
}
</style>
