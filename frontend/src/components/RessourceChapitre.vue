<template>
  <div class="mb-4">
    <div v-if="ressource.type === 'VIDEO'">
      <h6 class="fw-bold mb-3">
        <i class="bi bi-play-btn-fill me-2" style="color: #4a2c59"></i>{{ ressource.nom }}
      </h6>
      <div class="ratio ratio-16x9 bg-dark rounded-4 shadow-sm overflow-hidden">
        <iframe :src="ressource.url" allowfullscreen class="border-0"></iframe>
      </div>
    </div>

    <div v-else-if="ressource.type === 'IMAGE'">
      <h6 class="fw-bold mb-3" style="color: #4a2c59">
        <i class="bi bi-image-fill me-2"></i>{{ ressource.nom }}
      </h6>
      <div class="text-center bg-white p-2 rounded-4 shadow-sm border border-light-subtle">
        <img :src="ressource.url" :alt="ressource.nom" class="img-fluid rounded-3" style="max-height: 400px; object-fit: contain" />
      </div>
    </div>

    <div
      v-else-if="ressource.type === 'FICHIER_PDF' || ressource.type === 'DOCUMENT'"
      class="p-3 bg-white rounded-4 border border-light-subtle d-flex align-items-center shadow-sm"
    >
      <i class="bi bi-file-earmark-pdf-fill me-3" style="color: #a17c5b; font-size: 2.5rem"></i>
      <div class="flex-grow-1">
        <h6 class="mb-1 fw-bold" style="color: #4a2c59">{{ ressource.nom }}</h6>
        <a :href="ressource.url" target="_blank" rel="noopener noreferrer" class="btn btn-outline-dark btn-sm rounded-pill mt-1">
          <i class="bi bi-book me-1"></i> Consulter
        </a>
      </div>
    </div>

    <div
      v-else-if="ressource.type === 'FICHIER_ZIP'"
      class="p-3 bg-white rounded-4 border border-light-subtle d-flex align-items-center shadow-sm"
    >
      <i class="bi bi-file-earmark-zip-fill me-3 text-secondary" style="font-size: 2.5rem"></i>
      <div class="flex-grow-1">
        <h6 class="mb-1 fw-bold" style="color: #4a2c59">{{ ressource.nom }}</h6>
        <a :href="ressource.url" download class="btn btn-dark btn-sm rounded-pill mt-1">
          <i class="bi bi-download me-1"></i> Télécharger
        </a>
      </div>
    </div>
  </div>
</template>

<script setup>
defineProps({
  ressource: {
    type: Object,
    required: true,
  },
})
</script>
