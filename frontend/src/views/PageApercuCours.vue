<template>
  <BContainer class="py-5">
    <Chargement v-if="chargement" />

    <BAlert
      v-else-if="erreur !== ''"
      variant="danger"
      show
      class="alerte-catalogue-vide border-0 shadow-sm"
    >
      <i class="bi bi-exclamation-triangle-fill me-2"></i> {{ erreur }}
    </BAlert>

    <template v-else-if="cours">
      <BandeauTitre titre="Aperçu du cours" :sous-titre="cours.titre" />

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

      <BRow class="g-4">
        <BCol lg="8">
          <CarteApercuCours :cours="cours" :est-inscrit="estInscrit" />
        </BCol>

        <BCol lg="4">
          <CarteAccesCours
            :est-inscrit="estInscrit"
            :est-createur-du-cours="estCreateurDuCours"
            :progression="progressionPourcent"
            :peut-voir-cours="peutVoirCours"
            :libelle-action="libelleAction"
            @action="gererActionPrincipale"
          />
        </BCol>
      </BRow>
    </template>
  </BContainer>
</template>

<script setup>
import { onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import BandeauTitre from '@/components/BandeauTitre.vue'
import Chargement from '@/components/Chargement.vue'
import CarteApercuCours from '@/components/CarteApercuCours.vue'
import CarteAccesCours from '@/components/CarteAccesCours.vue'
import { useApercuCours } from '@/composables/useApercuCours.js'

const route = useRoute()
const router = useRouter()
const idCours = Number(route.params.idCours)

const {
  cours,
  estInscrit,
  estCreateurDuCours,
  chargement,
  erreur,
  progressionPourcent,
  peutVoirCours,
  libelleAction,
  chargerApercuCours,
} = useApercuCours(idCours)

onMounted(() => {
  chargerApercuCours()
})

function retourArriere() {
  router.back()
}

function gererActionPrincipale() {
  if (peutVoirCours.value) {
    router.push({ name: 'cours', params: { id: idCours } })
  }
}
</script>
