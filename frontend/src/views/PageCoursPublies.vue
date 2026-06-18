<template>
  <BContainer class="py-5">
    <BandeauTitre titre="Mes cours publiés" sous-titre="Retrouvez les cours que vous avez publiés" />

    <div class="d-flex justify-content-between align-items-center gap-3 mb-4">
      <BButton variant="link" class="bouton-retour-navigation p-2 text-decoration-none d-flex align-items-center gap-2"
               @click="retourArriere"
               title="Retour à la page précédente">
        <i class="bi bi-arrow-left fs-4"></i>
        <span class="fw-medium fs-5">Retour</span>
      </BButton>
      <BButton class="bouton-cours rounded-pill fw-bold px-4" @click="creerCours">
        <i class="bi bi-plus-circle me-2"></i>
        Créer un cours
      </BButton>
    </div>

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
          <i class="bi bi-search me-2"></i> Aucun cours publié ne correspond à votre recherche
        </BAlert>

        <div v-else>
          <BRow class="g-4">
            <BCol cols="12" md="6" lg="4" v-for="cours in coursAffiches" :key="cours.id">
              <CarteCours :cours="cours" texte-bouton="Modifier le cours" mode-professeur @clic-bouton="modifierCours" :nb-eleves="nombreEleves(cours)"/>
            </BCol>
          </BRow>

          <PaginationCustom v-model="pageCourante" :total-rows="coursFiltres.length" :per-page="coursParPage" />
        </div>
      </BCol>
    </BRow>
  </BContainer>
</template>

<script setup>
  import { onMounted } from 'vue'
  import { useRouter } from 'vue-router';
  import BandeauTitre from "@/components/BandeauTitre.vue";
  import FiltresRecherche from "@/components/FiltresRecherche.vue";
  import CarteCours from "@/components/CarteCours.vue";
  import PaginationCustom from "@/components/PaginationCustom.vue";
  import Chargement from "@/components/Chargement.vue";
  import { useCoursPublies } from "@/composables/useCoursPublies.js";

  const routeur = useRouter();

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
    initialiserPage,
    nombreEleves
  } = useCoursPublies();

  onMounted(async () =>
  {
    await initialiserPage();
  });

  function retourArriere()
  {
    routeur.back();
  }

  function modifierCours(donneesRecues) {
    let coursId;
    if(typeof donneesRecues === 'object'){
      coursId = donneesRecues.id;
    } else {
      coursId = donneesRecues;
    }
    routeur.push(`/cours/${coursId}/modifier`);
  }

  function creerCours()
  {
    routeur.push({ name: 'creationCours' });
  }
</script>
