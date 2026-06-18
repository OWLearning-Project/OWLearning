<template>
  <BCard class="carte-apercu-cours border-0 shadow-sm overflow-hidden" no-body>
    <div class="bandeau-deco"></div>

    <BCardBody>
      <div class="d-flex flex-wrap gap-2 mb-3">
        <BBadge
          pill
          :class="couleurDifficulte(cours.difficulte)"
          class="shadow-sm border-0 fw-bold px-3 py-2"
        >
          <i class="bi bi-bar-chart-fill me-1"></i> {{ cours.difficulte }}
        </BBadge>
        <BBadge v-if="cours.estPrive" pill class="badge-categorie border-0 fw-bold px-3 py-2">
          <i class="bi bi-lock-fill me-1"></i> Privé
        </BBadge>
        <BBadge v-if="estInscrit" pill class="badge-inscrit border-0 fw-bold px-3 py-2">
          <i class="bi bi-check-circle-fill me-1"></i> Inscrit
        </BBadge>
      </div>

      <h2 class="titre-carte-cours fw-bold mb-3">{{ cours.titre }}</h2>

      <p class="text-muted mb-4">
        <i class="bi bi-person-circle me-1"></i>
        {{ nomCreateur }}

        <BButton
          variant="outline-primary"
          size="sm"
          class="ms-3 rounded-pill"
          @click="$emit('contact', cours.createur?.id)"
        >
          <i class="bi bi-chat-dots me-1"></i> Contacter
        </BButton>
      </p>

      <p class="description-apercu-cours mb-4">
        {{ cours.description || 'Aucune description disponible pour ce cours.' }}
      </p>

      <div v-if="categories.length > 0" class="mb-4">
        <h3 class="titre-section-apercu fs-5 fw-bold mb-3">Catégories</h3>
        <BBadge
          v-for="categorie in categories"
          :key="categorie"
          pill
          class="badge-categorie border-0 me-2 mb-2 px-3 py-2"
        >
          {{ categorie }}
        </BBadge>
      </div>

      <div>
        <h3 class="titre-section-apercu fs-5 fw-bold mb-3">Programme</h3>

        <div v-if="chapitres.length === 0" class="text-muted">
          Aucun chapitre n'est encore disponible.
        </div>

        <div v-else class="liste-chapitres-apercu">
          <div
            v-for="(chapitre, index) in chapitres"
            :key="chapitre.id"
            class="chapitre-apercu d-flex gap-3 align-items-start"
          >
            <span class="numero-chapitre d-flex align-items-center justify-content-center fw-bold">
              {{ index + 1 }}
            </span>
            <div>
              <h4 class="fs-6 fw-bold mb-1">{{ chapitre.titre }}</h4>
              <p class="text-muted small mb-0">{{ chapitre.description }}</p>
            </div>
          </div>
        </div>
      </div>
    </BCardBody>
  </BCard>
</template>

<script setup>
import { computed } from 'vue'
import { couleurDifficulte } from '@/utils/formatage.js'

const props = defineProps(
  {
  cours:
    {
    type: Object,
    required: true,
  },
  estInscrit:
    {
    type: Boolean,
    default: false,
  },
})

const categories = computed(() => props.cours.categories || [])
const chapitres = computed(() => props.cours.chapitres || [])

const nomCreateur = computed(() =>
{
  const prenom = props.cours.createur?.prenom || ''
  const nom = props.cours.createur?.nom || ''

  return `${prenom} ${nom}`.trim()
})
</script>
