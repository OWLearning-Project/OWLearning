<template>
  <LayoutAuth titre="Bienvenue sur OWLearning !">
    <BForm @submit.prevent="sInscrire" class="p-3">

      <BFormGroup class="mb-4 fw-bold">
        <template #label>Nom <span class="text-danger">*</span> :</template>
        <BFormInput id="nom" v-model="nom" size="lg" required></BFormInput>
      </BFormGroup>

      <BFormGroup class="mb-4 fw-bold">
        <template #label>Prénom <span class="text-danger">*</span> :</template>
        <BFormInput id="prenom" v-model="prenom" size="lg" required></BFormInput>
      </BFormGroup>

      <BFormGroup class="mb-4 fw-bold">
        <template #label>Email <span class="text-danger">*</span> :</template>
        <BFormInput id="email" type="email" v-model="email" size="lg" required></BFormInput>
      </BFormGroup>

      <InputMotDePasse v-model="motDePasse" id="motdepasse" label="Mot de passe" />
      <InputMotDePasse v-model="confirmationMdp" id="confirmation" label="Confirmer mot de passe" />

      <BFormGroup label="Je suis un(e) :" label-class="fw-bold" class="mb-4">
        <BFormRadioGroup v-model="role" name="role-choix" required>
          <BFormRadio value="CREATEUR" class="fw-normal">Créateur / Créatrice</BFormRadio>
          <BFormRadio value="ELEVE" class="fw-normal">Élève</BFormRadio>
        </BFormRadioGroup>
      </BFormGroup>

      <div class="d-grid gap-3 mt-4">
        <BButton type="submit" size="lg" class="bouton-auth-principal fw-bold">
          S'inscrire
        </BButton>

        <hr>

        <div class="text-center">
          <span class="text-muted small fw-normal">Déjà un compte ?</span>
        </div>
        <BButton @click="connexion" variant="outline-dark" size="lg" class="fw-bold">
          Se connecter
        </BButton>
      </div>

    </BForm>
  </LayoutAuth>
</template>

<script setup>
    import {ref} from 'vue';
    import { useRouter } from 'vue-router';
    import LayoutAuth from "@/components/LayoutAuth.vue";
    import InputMotDePasse from "@/components/InputMotDePasse.vue";

    const router = useRouter();

    const email = ref('');
    const motDePasse = ref('');
    const confirmationMdp = ref('');
    const prenom = ref('');
    const nom = ref('');
    const role = ref('ELEVE');

    function connexion () {
        console.log("Redirection vers la page d'inscription");
        router.push('/connexion');
    }

    function inscription () {
        console.log("Inscription en cours...");
    }
</script>
