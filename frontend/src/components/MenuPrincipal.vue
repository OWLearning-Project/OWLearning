<template>
    <header class="navbar">
        <div class="logo-container">
            <div id="logo-icon">logo</div>
            <h1 id="logo-text">OWLearning</h1>
        </div>

        <nav class="liens-nav">
            <router-link to="/">Accueil</router-link>
            <router-link to="/messages">Messages</router-link>
            <router-link to="/cours">Cours</router-link>
        </nav>

        <div class="actions">
            <button class="btn-profil">Mon profil</button>
        </div>
    </header>
</template>

<style scoped>

    .navbar {
        display: flex;
        justify-content: space-between;
        align-items: center;
        padding: 1rem 4rem; 
        background-color: #a17c5b; 
        color: white;
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

    .liens-nav {
        display: flex;
        gap: 2rem;
    }

    .liens-nav a {
        text-decoration: none;
        color: white; 
        font-weight: 500;
        font-size: 1.1rem;
    }

    .liens-nav a:hover,
    .liens-nav a.router-link-active { 
        font-weight: 700;
        text-decoration: underline;
    }

    .actions {
        display: flex;
        gap: 1rem;
        align-items: center;
    }

    .btn-profil {
        background: none;
        border: none;
        color: white;
        font-size: 1.2rem;
        cursor: pointer;
    }

</style>