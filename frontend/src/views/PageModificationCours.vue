<template>
<BContainer class="mt-5">
<BCard class="shadow border-0 p-4">
  <h2 class="fw-bold mb-4 text-center">Modifier le cours</h2>

  <BAlert v-if="messageErreur" variant="danger" show>{{ messageErreur }}</BAlert>
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
      <BFormSelect v-model="difficulte" :options="optionsDifficultes" required></BFormSelect>
    </BFormGroup>

    <div class="d-flex justify-content-between mt-5">
      <BButton type="button" @click="retour" variant="outline-secondary" class="rounded-pill fw-bold px-4">Annuler</BButton>

      <div>
        <BButton v-if="!estPublie" type="button" @click="publierCours" variant="success" class="rounded-pill fw-bold px-4 me-3">
          <i class="bi me-2"></i> Publier
        </BButton>
        <BButton type="submit" class="bouton-cours rounded-pill fw-bold px-4">Enregistrer</BButton>
      </div>
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
const estPublie =ref(false);

const chargementEnCours = ref(true);
const messageErreur = ref('');

const idCours = route.params.id;

const optionsDifficultes = [
  { text: 'Débutant', value: 'DEBUTANT' },
  { text: 'Intermédiaire', value: 'INTERMEDIAIRE' },
  { text: 'Avancé', value: 'AVANCE' },
];

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

  try {
    const donneesModifiees = {
      titre: titre.value,
      description: description.value,
      difficulte: difficulte.value,
      estPrive: false
    };

    await coursClient.modifierCours(idCours, donneesModifiees);

    retour();

  } catch (erreur) {
    console.error("Erreur lors de la sauvegarde :", erreur);
    messageErreur.value = "Une erreur est survenue lors de la sauvegarde.";
  }
}

async function publierCours() {
  messageErreur.value='';
  try {
    await coursClient.publierCours(idCours);
    estPublie.value = true;
    router.push('/mes-cours-publies');
  } catch (erreur) {
    console.error("Erreur lors de la publication du cours :", erreur);
    messageErreur.value = "Une erreur est survenue lors de la publication du cours."
  }
}

function retour() {
  router.go(-1);
}
</script>
