<template>
  <BCard class="border-0 shadow-sm carte-filtres">
    <h5 class="fw-bold mb-4 titre-filtres"><i class="bi bi-list-ul me-2"></i>Sommaire</h5>

    <BListGroup flush class="gap-2">
      <BListGroupItem
        v-for="(chapitre, index) in chapitres"
        :key="chapitre.id"
        class="chapitre-item border-0 d-flex align-items-center"
        :class="{
          'chapitre-actif': index === indexChapitreActif,
          'opacity-50 text-decoration-line-through': index > nbChapitresFinis,
        }"
        @click="$emit('changer-chapitre', index)"
      >
        <div
          class="numero-chapitre me-3 fw-bold d-flex justify-content-center align-items-center"
          :class="{
            'numero-actif': index === indexChapitreActif,
            'bg-success text-white shadow-sm': index < nbChapitresFinis && index !== indexChapitreActif,
          }"
        >
          <i v-if="index < nbChapitresFinis && index !== indexChapitreActif" class="bi bi-check-lg"></i>
          <i v-else-if="index > nbChapitresFinis" class="bi bi-lock-fill small"></i>
          <span v-else>{{ index + 1 }}</span>
        </div>

        <span class="fw-bold text-truncate" style="font-size: 0.95rem">
          {{ chapitre.titre }}
        </span>
      </BListGroupItem>
    </BListGroup>
  </BCard>
</template>

<script setup>
defineProps({
  chapitres: {
    type: Array,
    required: true,
  },
  indexChapitreActif: {
    type: Number,
    required: true,
  },
  nbChapitresFinis: {
    type: Number,
    required: true,
  },
})

defineEmits(['changer-chapitre'])
</script>

<style scoped>
.chapitre-item {
  border-radius: 8px !important;
  cursor: pointer;
  transition: all 0.2s ease;
  background-color: transparent;
  color: #6c757d;
}

.chapitre-item:hover {
  background-color: #f8f9fa;
  transform: translateX(5px);
}

.chapitre-actif {
  background-color: rgba(74, 44, 89, 0.08) !important;
  color: #4a2c59 !important;
}

.numero-chapitre {
  width: 32px;
  height: 32px;
  border-radius: 50%;
  background-color: #e9ecef;
  color: #6c757d;
  font-size: 0.9rem;
  transition: all 0.3s ease;
}

.numero-actif {
  background-color: #4a2c59;
  color: white;
  box-shadow: 0 4px 8px rgba(74, 44, 89, 0.2);
}
</style>
