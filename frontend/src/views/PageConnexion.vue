<template>
    <div class="layout-connexion">
        <header class="bandeau-marron">
        <div class="logo-container">
            <div id="logo-icon">Logo</div>
            <h1 id="logo-text">OWLearning</h1>
        </div>
        </header>

        <div class="page-centree">
            <div class="connexion-card">
                <h1 class="logo">OWLearning</h1>
                <form @submit.prevent="seConnecter" class="formulaire">
                    <div class="champs-texte">
                        <label for="identifiant">
                            Identifiant étudiant :
                        </label>
                        <input type="text" id="identifiant" v-model="identifiant" />
                    </div>

                    <div class="champs-texte">
                        <label for="motdepasse">
                            Mot de passe :
                        </label>
                        <input type="password" id="motdepasse" v-model="motDePasse" />
                    </div>

                    <button type="submit" class="btn-primary">
                        Connexion
                    </button>
                </form>

                <button @click="inscription" class="btn-secondary">Créer un compte</button>
            </div>
        </div>
    </div>
</template>

<script setup>
    import {ref} from 'vue';
    import { useRouter } from 'vue-router';

    const router = useRouter();

    const identifiant = ref('');
    const motDePasse = ref('');

    const seConnecter = () => {
        console.log("Connexion avec : ", identifiant.value, motDePasse.value);
        //appel api
    }

    const inscription = () => {
        console.log("Redirection vers la page d'inscription")
        //router.push('/inscription');
    }
</script>

<style lang="css" scoped>

    .layout-connexion {
        display: flex;
        flex-direction: column;
        min-height: 100vh;
        width: 100%;
    }

    .bandeau-marron {
        background-color: #a17c5b; 
        color: white;
        padding: 1rem 4rem; 
        display: flex;
        align-items: center;
    }

    .logo-container {
        display: flex;
        align-items: center;
        gap: 0.5rem;
    }

    #logo-icon {
        font-size: 1.5rem;
    }

    #logo-text {
        font-size: 1.5rem;
        font-weight: bold;
        margin: 0;
    }

    .page-centree {
        display: flex;
        justify-content: center;
        align-items: center;
        flex: 1;
        padding: 2rem;
    }

    .connexion-card {
        background-color: white;
        padding: 3rem;
        border-radius: 12px;
        box-shadow: 0 4px 20px rgba(0, 0, 0, 0.05);
        width: 100%;
        max-width: 500px;
        display: flex;
        flex-direction: column;
    }

    .logo {
      text-align: center;
      color: #000;
      font-size: 24px;
      margin-top: 0;
      margin-bottom: 2rem;
    }

    .formulaire {
      display: flex;
      flex-direction: column;
    }

    .champs-texte {
      display: flex;
      flex-direction: column;
      margin-bottom: 1rem;
    }

    .champs-texte label {
      font-weight: 600;
      margin-bottom: 0.5rem;
      color: #333;
    }

    .champs-texte input {
      padding: 0.8rem;
      border: 1px solid #ddd;
      border-radius: 8px;
      font-size: 16px;
      outline: none;
    }

    .champs-texte input:focus {
      border-color: #f6c05d;
    }

    .btn-primary {
      background-color: #f6c05d;
      color: #000;
      border: none;
      padding: 1rem;
      border-radius: 8px;
      font-size: 16px;
      font-weight: bold;
      cursor: pointer;
      margin-bottom: 3rem;
    }

    .btn-primary:hover {
      background-color: #e5b04b;
    }

    .btn-secondary {
      background-color: white;
      color: #000;
      border: 1px solid #000;
      padding: 1rem;
      border-radius: 8px;
      font-size: 16px;
      font-weight: bold;
      cursor: pointer;
    }

    .btn-secondary:hover {
      background-color: #f9f9f9;
    }
</style>
