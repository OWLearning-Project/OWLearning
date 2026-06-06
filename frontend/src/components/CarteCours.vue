<template>
  <BCard class="h-100 shadow-sm carte-cours border-0 position-relative" no-body>

    <div v-if="progression !== undefined" class="chip-progression shadow-sm fw-bold small d-flex align-items-center">
      <span>{{ progression }}%</span>
    </div>

    <div v-if="cours.estPrive" class="badge-cours-prive position-absolute top-0 end-0 m-2 bg-white rounded-circle d-flex align-items-center justify-content-center shadow-sm" title="Cours privé">
      <i class="icone-cours-prive bi bi-lock-fill"></i>
    </div>

    <div class="bandeau-deco"></div>

    <BCardBody class="d-flex flex-column">
      <BCardTitle class="fw-bold fs-5 mt-2" style="color: #4a2c59">
        {{ cours.titre }}
      </BCardTitle>

      <BCardText v-if="!modeProfesseur" class="text-muted small mb-4">
        <i class="bi bi-person-circle me-1"></i> {{ cours.createur.prenom }} {{ cours.createur.nom }}
      </BCardText>

      <template v-else>
        <BCardText class="text-muted small mb-3">
          {{ cours.description || 'Aucune description disponible' }}
        </BCardText>

        <div class="mt-auto text-muted small mb-3 fw-bold">
          <i class="bi bi-people-fill me-1"></i>
          {{ nbEleves }} élève{{ nbEleves > 1 ? 's' : '' }} inscrit{{ nbEleves > 1 ? 's' : '' }}
        </div>
      </template>

      <div class="mb-3">
        <BBadge pill :class="couleurDifficulte(cours.difficulte)" class="me-2 mb-1 shadow-sm border-0 fw-bold px-3 py-2">
          <i class="bi bi-bar-chart-fill me-1"></i> {{ cours.difficulte }}
        </BBadge>
        <BBadge pill class="badge-categorie border-0 me-1 mb-1 px-3 py-2" v-for="categorie in cours.categories" :key="categorie">
          {{ categorie }}
        </BBadge>
      </div>

      <BButton class="mt-auto fw-bold bouton-cours w-100 rounded-pill py-2" @click="$emit('clic-bouton', cours.id)">
        {{ texteBouton }}
      </BButton>
    </BCardBody>

    <BCardFooter class="bg-white border-top-0 text-end pb-3">
      <small class="text-muted" style="font-size: 0.75rem">
        <i class="bi bi-calendar3 me-1"></i> {{ formaterDate(cours.dateCreation) }}
      </small>
    </BCardFooter>
  </BCard>
</template>

<script setup>
import {formaterDate, couleurDifficulte} from "@/utils/formatage.js";

defineProps({
  cours: {
    type: Object,
    required: true
  },
  progression: {
    type: Number,
    default: undefined
  },
  texteBouton: {
    type: String,
    default: 'Consulter'
  },
  modeProfesseur: {
    type: Boolean,
    default: false
  },
  nbEleves: {
    type: Number,
    default: 0
  }
});

defineEmits(['clic-bouton']);
</script>

<style scoped>
.carte-cours {
  transition: transform 0.3s ease, box-shadow 0.3s ease;
  border-radius: 12px;
  overflow: hidden;
}
.carte-cours:hover {
  transform: translateY(-5px);
  box-shadow: 0 10px 20px rgba(0, 0, 0, 0.1) !important;
}
.bandeau-deco {
  height: 80px;
  background: linear-gradient(135deg, #a17c5b 0%, #4a2c59 100%);
}
.badge-debutant { background-color: #e2f0e5 !important; color: #2d6a4f !important; }
.badge-intermediaire { background-color: #fff3cd !important; color: #856404 !important; }
.badge-avance { background-color: #fad2e1 !important; color: #842047 !important; }
.badge-defaut { background-color: #f8f9fa !important; color: #6c757d !important; }
.badge-categorie {
  background-color: rgba(74, 44, 89, 0.1) !important;
  color: #4a2c59 !important;
  font-weight: 600;
}
.bouton-cours {
  background-color: #4a2c59 !important;
  border: 2px solid #4a2c59 !important;
  color: white !important;
  transition: all 0.3s ease;
}
.bouton-cours:hover {
  background-color: #b8a854 !important;
  border-color: #b8a854 !important;
  color: #4a2c59 !important;
  transform: scale(1.02);
}
.chip-progression {
  position: absolute;
  top: 12px;
  left: 12px;
  background-color: white;
  color: #4a2c59;
  padding: 4px 12px;
  border-radius: 50rem;
  z-index: 2;
}
.badge-cours-prive {
  width: 32px;
  height: 32px;
  color: #6c757d;
}
</style>
