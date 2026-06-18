<template>
  <BContainer class="py-5">
    <BandeauTitre titre="Créer un cours" sous-titre="Renseignez les informations et ajoutez vos chapitres" />

    <div class="d-flex justify-content-start mb-4">
      <BButton
        variant="link"
        class="bouton-retour-navigation p-2 text-decoration-none d-flex align-items-center gap-2"
        title="Retour à la page précédente"
        @click="retourArriere"
      >
        <i class="bi bi-arrow-left fs-4"></i>
        <span class="fw-medium fs-5">Retour</span>
      </BButton>
    </div>

    <BRow class="justify-content-center">
      <BCol lg="10">
        <FormulaireCreationCours @annuler="retourArriere" @cours-cree="ouvrirCours" />
      </BCol>
    </BRow>
  </BContainer>
</template>

<script setup>
import { useRouter } from 'vue-router'
import BandeauTitre from '@/components/BandeauTitre.vue'
import FormulaireCreationCours from '@/components/FormulaireCreationCours.vue'

const router = useRouter()

function retourArriere() {
  router.back()
}

function ouvrirCours(cours) {
  router.push({ name: 'cours', params: { id: cours.id } })
}
</script>
