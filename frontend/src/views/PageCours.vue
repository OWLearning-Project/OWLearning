<template>
  <BContainer class="py-5 min-vh-100">
    <div v-if="cours">
      <EnteteCours :cours="cours" :est-le-createur="estLeCreateur" @retour="retourCatalogue" />

      <BRow>
        <BCol lg="4" class="mb-4">
          <div class="sticky-top" style="top: 20px; z-index: 1">
            <SommaireCours
              :chapitres="cours.chapitres"
              :index-chapitre-actif="indexChapitreActif"
              :nb-chapitres-finis="nbChapitresFinis"
              @changer-chapitre="changerChapitre"
            />

            <ListeElevesCours
              v-model:ouvert="elevesOuverts"
              :eleves="elevesInscrits"
              @basculer="deroulerListeEleves"
              @ouvrir-profil="ouvrirProfil"
            />
          </div>
        </BCol>

        <BCol lg="8">
          <ContenuChapitreCours
            v-if="chapitreActif"
            :cours="cours"
            :chapitre="chapitreActif"
            :est-le-createur="estLeCreateur"
            :progression="progression"
            :index-chapitre-actif="indexChapitreActif"
            @precedent="chapitrePrecedent"
            @suivant="chapitreSuivant"
          />
        </BCol>
      </BRow>
    </div>
  </BContainer>

  <ModalProfilEleve v-model="modalOuverte" :eleve="eleveSelectionne" />
</template>

<script setup>
import { onMounted } from 'vue'
import { useRoute } from 'vue-router'
import EnteteCours from '@/components/EnteteCours.vue'
import SommaireCours from '@/components/SommaireCours.vue'
import ListeElevesCours from '@/components/ListeElevesCours.vue'
import ContenuChapitreCours from '@/components/ContenuChapitreCours.vue'
import ModalProfilEleve from '@/components/ModalProfilEleve.vue'
import { useCours } from '@/composables/useCours.js'

const route = useRoute()
const idCours = route.params.id

const {
  cours,
  indexChapitreActif,
  progression,
  elevesInscrits,
  elevesOuverts,
  modalOuverte,
  eleveSelectionne,
  estLeCreateur,
  chapitreActif,
  nbChapitresFinis,
  chargerCours,
  ouvrirProfil,
  deroulerListeEleves,
  changerChapitre,
  chapitrePrecedent,
  chapitreSuivant,
  retourCatalogue,
} = useCours(idCours)

onMounted(() => {
  chargerCours()
})
</script>
