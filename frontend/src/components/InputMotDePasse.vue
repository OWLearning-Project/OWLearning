<template>
  <BFormGroup class="mb-4 fw-bold">
    <template #label>{{ label }} <span class="text-danger">*</span> :</template>

    <BInputGroup size="lg">
      <BFormInput
        :id="id"
        :type="voirMotDePasse ? 'text' : 'password'"
        v-model="valeur"
        class="border-end-0"
        required
      ></BFormInput>

      <BInputGroupText
        @click="voirMotDePasse = !voirMotDePasse"
        class="icone-mdp-toggle bg-white border-start-0"
        style="cursor: pointer;"
      >
        <i :class="voirMotDePasse ? 'bi bi-eye-slash' : 'bi bi-eye'" class="text-muted"></i>
      </BInputGroupText>
    </BInputGroup>
  </BFormGroup>
</template>

<script setup>
import {ref} from 'vue';

const valeur = defineModel({type: String, required: true});
const voirMotDePasse = ref(false);

defineProps({
  label: {type: String, default: 'Mot de passe'},
  id: {type: String, default: 'motdepasse'}
});
</script>

<style scoped>

</style>
