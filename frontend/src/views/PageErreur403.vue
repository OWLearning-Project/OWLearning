<template>
  <LayoutErreur code="403" titre="Accès refusé" message="Désolé, on dirait que vous n'avez pas les permissions nécessaires...">
    <template #icone>
      <i class="bi bi-lock-fill"></i>
    </template>
  </LayoutErreur>
</template>

<script setup>
import LayoutErreur from "@/components/LayoutErreur.vue";
</script>
