<template>
  <LayoutAuth titre="Bienvenue sur OWLearning !">
    <BAlert v-if="messageErreur !== ''" variant="danger" show class="alerte-auth m-3 text-center shadow border-0">
      <i class="bi bi-exclamation-triangle-fill me-2"></i> {{ messageErreur }}
    </BAlert>

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

      <div class="mb-4">
        <div class="fw-bold small text-muted mb-2">Sécurité du mot de passe</div>
        <ul class="list-unstyled mb-0">
          <li
            v-for="regle in reglesMotDePasse"
            :key="regle.texte"
            class="small d-flex align-items-center gap-2 mb-1"
            :class="regle.valide ? 'text-success' : 'text-muted'"
          >
            <i :class="regle.valide ? 'bi bi-check-circle-fill' : 'bi bi-circle'"></i>
            <span>{{ regle.texte }}</span>
          </li>
        </ul>
      </div>

      <InputMotDePasse v-model="confirmationMdp" id="confirmation" label="Confirmer mot de passe" />

      <p
        v-if="confirmationMdp !== ''"
        class="small mb-4"
        :class="motsDePasseIdentiques ? 'text-success' : 'text-danger'"
      >
        <i :class="motsDePasseIdentiques ? 'bi bi-check-circle-fill' : 'bi bi-exclamation-circle-fill'" class="me-1"></i>
        {{ motsDePasseIdentiques ? 'Les mots de passe correspondent.' : 'Les mots de passe ne correspondent pas.' }}
      </p>

      <BFormGroup label="Je suis un(e) :" label-class="fw-bold" class="mb-4">
        <BFormRadioGroup v-model="role" name="role-choix" required>
          <BFormRadio value="CREATEUR" class="fw-normal">Créateur / Créatrice</BFormRadio>
          <BFormRadio value="ELEVE" class="fw-normal">Élève</BFormRadio>
        </BFormRadioGroup>
      </BFormGroup>

      <div class="d-grid gap-3 mt-4">
        <BButton type="submit" size="lg" class="bouton-auth-principal fw-bold" :disabled="chargement">
          <template v-if="chargement">
            <BSpinner small class="me-2"></BSpinner>
            Inscription en cours...
          </template>
          <template v-else>
            S'inscrire
          </template>
        </BButton>

        <hr>

        <div class="text-center">
          <span class="text-muted small fw-normal">Déjà un compte ?</span>
        </div>
        <BButton @click="allerAConnexion" variant="outline-dark" size="lg" class="fw-bold">
          Se connecter
        </BButton>
      </div>

    </BForm>
  </LayoutAuth>
</template>

<script setup>
    import LayoutAuth from "@/components/LayoutAuth.vue";
    import InputMotDePasse from "@/components/InputMotDePasse.vue";
    import { useInscription } from "@/composables/useInscription.js";

    const {
      nom,
      prenom,
      email,
      motDePasse,
      confirmationMdp,
      role,
      chargement,
      messageErreur,
      reglesMotDePasse,
      motsDePasseIdentiques,
      sInscrire,
      allerAConnexion,
    } = useInscription();
</script>
