<template>
<BContainer class="mt-5">
<BCard class="shadow border-0 p-4">
  <h2 class="fw-bold mb-4 text-center">Modifier le cours</h2>

  <BAlert v-if="messageErreur" variant="danger" show>{{ messageErreur }}</BAlert>
  <BAlert v-if="messageSucces" variant="success" show>{{ messageSucces }}</BAlert>

  <div v-if="chargementEnCours" class="text-center my-5">
    <BSpinner label="Chargement..."></BSpinner>
  </div>

  <BForm v-else @submit.prevent="sauvegarderModifications">

    <BFormGroup class="mb-4 fw-bold" label="Titre du cours :">
      <BFormInput v-model="titre" required></BFormInput>
    </BFormGroup>

    <BFormGroup class="mb-4 fw-bold" label="Description :">
      <BFormTextarea v-model="description" rows="4" required></BFormTextarea>
    </BFormGroup>

    <BFormGroup class="mb-4 fw-bold" label="Difficulté :">
      <BFormInput v-model="difficulte" required></BFormInput>
    </BFormGroup>

    <div class="d-flex justify-content-between mt-5">
      <BButton @click="retour" variant="outline-secondary">Annuler</BButton>
      <BButton type="submit" variant="primary" class="fw-bold px-5">Enregistrer</BButton>
    </div>

  </BForm>
</BCard>
</BContainer>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import { coursClient } from '@/api/coursClient.js';

const route = useRoute();
const router = useRouter();

const titre = ref('');
const description = ref('');
const difficulte = ref('');

const chargementEnCours = ref(true);
const messageErreur = ref('');
const messageSucces = ref('');

const idCours = route.params.id;

onMounted(async () => {
  try {
    const cours = await coursClient.getCours(idCours);

    titre.value = cours.titre;
    description.value = cours.description;
    difficulte.value = cours.difficulte;

  } catch (erreur) {
    console.error("Erreur lors du chargement :", erreur);
    messageErreur.value = "Impossible de charger les informations du cours.";
  } finally {
    chargementEnCours.value = false;
  }
});

async function sauvegarderModifications() {
  messageErreur.value = '';
  messageSucces.value = '';

  try {
    const donneesModifiees = {
      titre: titre.value,
      description: description.value,
      difficulte: difficulte.value,
    };

    await coursClient.modifierCours(idCours, donneesModifiees);

    messageSucces.value = "Le cours a été modifié avec succès !";

    setTimeout(() => {
      retour();
    }, 2000);

  } catch (erreur) {
    console.error("Erreur lors de la sauvegarde :", erreur);
    messageErreur.value = "Une erreur est survenue lors de la sauvegarde.";
  }
}

function retour() {
  router.go(-1);
}
</script>
