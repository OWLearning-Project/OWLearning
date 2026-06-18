<template>
  <BContainer class="py-5">
    <BandeauTitre titre="Mes cours inscrits" sous-titre="Retrouvez les cours que vous suivez" />

    <BRow>
      <BCol lg="3" md="4" class="mb-4">
        <FiltresRecherche v-model:recherche="recherche" v-model:difficultes="difficultes" v-model:categories="categories" :categoriesPossibles="categoriesPossibles" />
      </BCol>

      <BCol lg="9" md="8">
        <Chargement v-if="chargement" />

        <BAlert v-else-if="erreur !== ''" variant="danger" show>
          <i class="bi bi-exclamation-triangle-fill me-2"></i> {{ erreur }}
        </BAlert>

        <BAlert
          v-else-if="coursFiltres.length === 0"
          variant="info"
          show
          class="border-0 shadow-sm"
          style="border-radius: 12px"
        >
          <i class="bi bi-search me-2"></i> Aucun cours ne correspond à votre recherche
        </BAlert>

        <div v-else>
          <BRow class="g-4">
            <BCol cols="12" md="6" lg="4" v-for="cours in coursAffiches" :key="cours.id">
              <CarteCours :cours="cours" :progression="tauxProgression[cours.id] || 0" texte-bouton="Reprendre" @clic-bouton="consulterCours" />
            </BCol>
          </BRow>

          <PaginationCustom v-model="pageCourante" :total-rows="coursFiltres.length" :perPage="coursParPage" />

        </div>
      </BCol>
    </BRow>
  </BContainer>
</template>

<script setup>
  import { onMounted } from 'vue'
  import { useRouter } from 'vue-router'
  import CarteCours from "@/components/CarteCours.vue";
  import FiltresRecherche from "@/components/FiltresRecherche.vue";
  import BandeauTitre from "@/components/BandeauTitre.vue";
  import PaginationCustom from "@/components/PaginationCustom.vue";
  import Chargement from "@/components/Chargement.vue";
  import { useCoursInscrits } from "@/composables/useCoursInscrits.js";

  const router = useRouter()

  const {
    chargement,
    erreur,
    tauxProgression,
    pageCourante,
    coursParPage,
    recherche,
    difficultes,
    categories,
    categoriesPossibles,
    coursFiltres,
    coursAffiches,
    initialiserPage
  } = useCoursInscrits();

onMounted(async () => {
  await initialiserPage();
});

function consulterCours(idCours) {
  router.push({ name: 'cours', params: { id: idCours } });
}
</script>

<style scoped>
</style>
