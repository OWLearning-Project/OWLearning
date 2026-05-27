<template>
  <div id="app">
    <MenuPrincipal v-if="!$route.meta.cacheMenu" />
    <main class="contenu-principal">
      <router-view />
    </main>
  </div>
</template>

<script setup>
import MenuPrincipal from './components/MenuPrincipal.vue';

</script>

<style>
body {
  margin: 0;
  font-family: 'Roboto', Arial, sans-serif;
  background-color: #f5f5f5; 
}

#app {
  display: flex;
  flex-direction: column;
  min-height: 100vh;
}

.contenu-principal {
  flex: 1; 
  display: flex;
  flex-direction: column;
}
</style>
