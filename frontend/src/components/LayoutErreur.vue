<template>
  <BContainer fluid class="page-erreur flex-grow-1 d-flex align-items-center justify-content-center px-4 py-5">
    <section class="contenu-erreur text-center">
      <p class="code-erreur mb-3">{{ code }}</p>

      <h1 class="titre-erreur fw-normal mb-4">{{ titre }}</h1>

      <div class="illustration-wrapper mx-auto mb-4" aria-hidden="true">
        <span class="point-interrogation fw-bold">
          <slot name="icone"></slot>
        </span>
        <img src="../assets/OwleEcharpe.svg" alt="" class="illustration-chouette" />
      </div>

      <p v-if="message" class="text-muted fs-5 mb-5">
        {{ message }}
      </p>

      <div class="actions-erreur d-flex flex-column align-items-center gap-4">
        <BButton class="bouton-accueil fw-bold border-0 shadow-sm" size="lg" @click="retourAccueil">
          Retour à l'accueil
        </BButton>

        <BButton class="bouton-retour fw-bold border-0 shadow-sm" @click="retourPrecedent">
          Retourner à la page précédente &gt;
        </BButton>
      </div>
    </section>
  </BContainer>
</template>

<script setup>
import {useRouter} from 'vue-router'

defineProps({
  code: {type: String, required: true},
  titre: {type: String, required: true},
  message: {type: String, default: ''}
});

const router = useRouter();

function retourAccueil() {
  if (window.history.length > 1) {
    router.back();
    return;
  }
  router.push('/');
}
</script>

<style scoped>

</style>
