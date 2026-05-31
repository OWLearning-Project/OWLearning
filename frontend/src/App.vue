<template>
  <div class="min-vh-100 d-flex flex-column bg-light">

    <MenuPrincipal v-if="!$route.meta.cacheMenu" />

    <main class="flex-grow-1 d-flex flex-column">
      <router-view />
    </main>


    <AppFooter />

  </div>
</template>

<script setup>
  import MenuPrincipal from './components/MenuPrincipal.vue';
  import AppFooter from './components/Footer.vue';
</script>

<style>
  html, body {

    overscroll-behavior: none;
    
    margin: 0;
    font-family: 'Roboto', Arial, sans-serif;
    background-color: #f5f5f5; 
  }
</style>
