import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import axios from 'axios';

import { createBootstrap } from 'bootstrap-vue-next'
import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap-vue-next/dist/bootstrap-vue-next.css'
import 'bootstrap-icons/font/bootstrap-icons.css'
import './assets/style.css'

const app = createApp(App)

app.use(router)
app.use(createBootstrap())

axios.interceptors.response.use( 
    (reponse) => {
        return reponse;
    },
    (erreur) => {
        if (erreur.response && (erreur.response.status === 401 || erreur.response.status === 403)) {
            console.warn("Alerte : token invalide ou expiré !");
            localStorage.removeItem('token');
            router.push('/connexion');
        }

        return Promise.reject(erreur);
    }
)

app.mount('#app')
