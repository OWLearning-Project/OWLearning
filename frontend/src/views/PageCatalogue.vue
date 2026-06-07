<template>
  <BContainer class="py-5">
    <BandeauTitre titre="Catalogue" sous-titre="Découvrez les différents cours disponibles" />

    <BRow>
      <BCol lg="3" md="4" class="mb-4">
          <FiltresRecherche :categories-possibles="categoriesPossibles" v-model:recherche="recherche" v-model:difficultes="difficultes" v-model:categories="categories" v-model:coursPrives="afficherCoursPrives" afficher-option-prive />
      </BCol>

      <BCol lg="9" md="8">
        <Chargement v-if="chargement" />

        <BAlert v-else-if="erreur !== ''" variant="danger" show>
          <i class="bi bi-exclamation-triangle-fill me-2"></i> {{ erreur }}
        </BAlert>

        <BAlert v-else-if="coursFiltres.length === 0" variant="info" show class="alerte-catalogue-vide border-0 shadow-sm">
          <i class="bi bi-search me-2"></i> Aucun cours ne correspond à votre recherche
        </BAlert>

        <div v-else>
          <BRow class="g-4">
            <BCol cols="12" md="6" lg="4" v-for="cours in coursAffiches" :key="cours.id">
              <CarteCours :cours="cours" @clic-bouton="consulterCours" />
            </BCol>
          </BRow>

          <PaginationCustom v-model="pageCourante" :total-rows="coursFiltres.length" :perPage="coursParPage" />
        </div>
      </BCol>
    </BRow>
  </BContainer>
</template>

<script setup>
import {onMounted} from 'vue';
import {useRouter} from 'vue-router';
import {useCatalogue} from "@/composables/useCatalogue.js";
import FiltresRecherche from "@/components/FiltresRecherche.vue";
import CarteCours from "@/components/CarteCours.vue";
import BandeauTitre from "@/components/BandeauTitre.vue";
import Chargement from "@/components/Chargement.vue";
import PaginationCustom from "@/components/PaginationCustom.vue";

const router = useRouter()

const {
  chargement,
  erreur,
  pageCourante,
  coursParPage,
  recherche,
  difficultes,
  categories,
  afficherCoursPrives,
  categoriesPossibles,
  coursFiltres,
  coursAffiches,
  chargerCours
} = useCatalogue();

onMounted(() => {
  chargerCours();
});

function consulterCours(idCours) {
  router.push({ name: 'apercuCours', params: { idCours } });
}
</script>
