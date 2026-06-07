<template>
  <LayoutAuth titre="Bienvenue sur OWLearning !">

    <BAlert v-if="messageErreur !== ''" variant="danger" show class="alerte-auth m-3 text-center shadow border-0">
      <i class="bi bi-exclamation-triangle-fill me-2"></i> {{ messageErreur }}
    </BAlert>

    <BForm @submit.prevent="seConnecter" class="p-3">
      <BFormGroup class="mb-4 fw-bold">
        <template #label>Email <span class="text-danger">*</span> :</template>
        <BFormInput id="email" type="email" v-model="email" size="lg" required></BFormInput>
      </BFormGroup>

      <InputMotDePasse v-model="motDePasse" id="motdepasse" label="Mot de passe" />

      <div class="d-grid gap-3 mt-4">
        <BButton type="submit" size="lg" class="bouton-auth-principal fw-bold">
          <template v-if="chargement">
            <BSpinner small class="me-2"></BSpinner>
            Connexion en cours...
          </template>
          <template v-else>
            Connexion
          </template>
        </BButton>

        <hr>

        <div class="text-center">
          <span class="text-muted small fw-normal">Pas encore de compte ?</span>
        </div>

        <BButton @click="inscription" variant="outline-dark" size="lg" class="fw-bold">
          Créer un compte
        </BButton>
      </div>
    </BForm>
  </LayoutAuth>
</template>

<script setup>
    import LayoutAuth from "@/components/LayoutAuth.vue";
    import InputMotDePasse from "@/components/InputMotDePasse.vue";
    import { useConnexion } from "@/composables/useConnexion.js";

    const {
      email,
      motDePasse,
      chargement,
      messageErreur,
      seConnecter,
      allerAInscription: inscription
    } = useConnexion();
</script>
