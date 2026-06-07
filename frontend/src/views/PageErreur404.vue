<template>
    <LayoutErreur titre="Oups, cette page est introuvable" code="404">
      <template #icone>
        <i class="bi bi-lock-fill"></i>
      </template>
    </LayoutErreur>
</template>

<script setup>
    import LayoutErreur from "@/components/LayoutErreur.vue";
</script>
