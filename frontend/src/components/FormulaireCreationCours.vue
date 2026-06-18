<template>
  <BForm @submit.prevent="soumettre">
    <BAlert v-if="erreur !== ''" variant="danger" show class="alerte-catalogue-vide border-0 shadow-sm">
      <i class="bi bi-exclamation-triangle-fill me-2"></i> {{ erreur }}
    </BAlert>

    <BAlert v-if="messageSucces !== ''" variant="success" show class="alerte-catalogue-vide border-0 shadow-sm">
      <i class="bi bi-check-circle-fill me-2"></i> {{ messageSucces }}
    </BAlert>

    <BCard class="carte-creation-cours border-0 shadow-sm mb-4">
      <h2 class="titre-carte-cours fs-4 fw-bold mb-4">Informations du cours</h2>

      <BFormGroup class="mb-4">
        <template #label>Titre <span class="text-danger">*</span></template>
        <BFormInput v-model="formulaire.titre" :state="etatChamp(erreurs.titre)" required></BFormInput>
        <p v-if="erreurs.titre" class="text-danger small mb-0 mt-1">{{ erreurs.titre }}</p>
      </BFormGroup>

      <BFormGroup label="Description" label-class="fw-bold" class="mb-4">
        <BFormTextarea v-model="formulaire.description" rows="4"></BFormTextarea>
      </BFormGroup>

      <BRow>
        <BCol md="6">
          <BFormGroup class="mb-4">
            <template #label>Difficulté <span class="text-danger">*</span></template>
            <BFormSelect
              v-model="formulaire.difficulte"
              :options="optionsDifficultes"
              :state="etatChamp(erreurs.difficulte)"
              required
            ></BFormSelect>
            <p v-if="erreurs.difficulte" class="text-danger small mb-0 mt-1">{{ erreurs.difficulte }}</p>
          </BFormGroup>
        </BCol>

        <BCol md="6">
          <BFormGroup class="mb-4">
            <template #label>Catégorie <span class="text-danger">*</span></template>
            <BFormSelect
              v-model="formulaire.categorie"
              :options="optionsCategories"
              :state="etatChamp(erreurs.categorie)"
              required
            ></BFormSelect>
            <p v-if="erreurs.categorie" class="text-danger small mb-0 mt-1">{{ erreurs.categorie }}</p>
          </BFormGroup>
        </BCol>
      </BRow>
    </BCard>

    <div class="d-flex align-items-center justify-content-between gap-3 mb-3">
      <h2 class="titre-carte-cours fs-4 fw-bold mb-0">Chapitres</h2>
      <BButton type="button" variant="outline-dark" class="rounded-pill fw-bold px-3" @click="ajouterChapitre">
        <i class="bi bi-plus-circle me-2"></i>
        Ajouter
      </BButton>
    </div>

    <BAlert v-if="erreurs.chapitres" variant="danger" show class="alerte-catalogue-vide border-0 shadow-sm">
      {{ erreurs.chapitres }}
    </BAlert>

    <BCard
      v-for="(chapitre, index) in chapitres"
      :key="chapitre.uid"
      class="carte-creation-cours border-0 shadow-sm mb-4"
    >
      <div class="d-flex align-items-start justify-content-between gap-3 mb-4">
        <div>
          <BBadge pill class="badge-categorie border-0 px-3 py-2 mb-2">
            Chapitre {{ index + 1 }}
          </BBadge>
          <h3 class="titre-carte-cours fs-5 fw-bold mb-0">{{ chapitre.titre || 'Nouveau chapitre' }}</h3>
        </div>

        <BButton
          type="button"
          variant="outline-danger"
          class="rounded-pill fw-bold px-3"
          @click="retirerChapitre(index)"
        >
          <i class="bi bi-trash me-2"></i>
          Retirer
        </BButton>
      </div>

      <BFormGroup class="mb-4">
        <template #label>Titre <span class="text-danger">*</span></template>
        <BFormInput v-model="chapitre.titre" :state="etatChamp(erreursChapitres[index]?.titre)" required></BFormInput>
        <p v-if="erreursChapitres[index]?.titre" class="text-danger small mb-0 mt-1">
          {{ erreursChapitres[index].titre }}
        </p>
      </BFormGroup>

      <BFormGroup class="mb-4">
        <template #label>Description <span class="text-danger">*</span></template>
        <BFormTextarea
          v-model="chapitre.description"
          :state="etatChamp(erreursChapitres[index]?.description)"
          rows="3"
          required
        ></BFormTextarea>
        <p v-if="erreursChapitres[index]?.description" class="text-danger small mb-0 mt-1">
          {{ erreursChapitres[index].description }}
        </p>
      </BFormGroup>

      <BFormGroup class="mb-0">
        <template #label>Pièce jointe <span class="text-danger">*</span></template>
        <input
          type="file"
          class="form-control"
          :class="{ 'is-invalid': erreursChapitres[index]?.fichier }"
          accept=".pdf,.zip,image/*,video/*"
          required
          @change="selectionnerFichier(index, $event)"
        >
        <p class="text-muted small mb-0 mt-2">
          <i class="bi bi-paperclip me-1"></i>
          {{ nomFichier(chapitre) }}
        </p>
        <p v-if="erreursChapitres[index]?.fichier" class="text-danger small mb-0 mt-1">
          {{ erreursChapitres[index].fichier }}
        </p>
      </BFormGroup>
    </BCard>

    <div class="d-flex justify-content-end gap-3">
      <BButton
        type="button"
        variant="outline-secondary"
        class="rounded-pill fw-bold px-4"
        :disabled="chargement"
        @click="$emit('annuler')"
      >
        Annuler
      </BButton>

      <BButton
        type="submit"
        class="bouton-cours rounded-pill fw-bold px-4"
        :disabled="!formulaireValide || chargement"
      >
        <template v-if="chargement">
          <BSpinner small class="me-2"></BSpinner>
          Création...
        </template>
        <template v-else>
          Créer le cours
        </template>
      </BButton>
    </div>
  </BForm>
</template>

<script setup>
import { computed } from 'vue'
import { useCreationCours } from '@/composables/useCreationCours.js'

const emit = defineEmits(['annuler', 'cours-cree'])

const {
  formulaire,
  chapitres,
  erreurs,
  erreursChapitres,
  erreur,
  messageSucces,
  chargement,
  difficultesPossibles,
  categoriesPossibles,
  formulaireValide,
  ajouterChapitre,
  retirerChapitre,
  selectionnerFichier,
  creerCoursComplet,
} = useCreationCours()

const optionsDifficultes = computed(() => [
  { value: '', text: 'Choisir une difficulté', disabled: true },
  ...difficultesPossibles,
])

const optionsCategories = computed(() => [
  { value: '', text: 'Choisir une catégorie', disabled: true },
  ...categoriesPossibles,
])

async function soumettre() {
  const cours = await creerCoursComplet()

  if (cours) {
    emit('cours-cree', cours)
  }
}

function etatChamp(messageErreur) {
  return messageErreur ? false : null
}

function nomFichier(chapitre) {
  return chapitre.fichier?.name || 'Aucun fichier sélectionné'
}
</script>

<style scoped>
.carte-creation-cours {
  border-radius: 12px;
}
</style>
