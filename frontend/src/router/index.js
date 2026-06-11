import { createRouter, createWebHistory } from 'vue-router'
import PageConnexion from '@/views/PageConnexion.vue'
import PageAccueil from '@/views/PageAccueil.vue'
import PageInscription from '@/views/PageInscription.vue'
import PageCatalogue from '@/views/PageCatalogue.vue'
import PageErreur404 from '@/views/PageErreur404.vue'
import PageCoursInscrits from '@/views/PageCoursInscrits.vue'
import PageApercuCours from '@/views/PageApercuCours.vue'
import PageErreur403 from '@/views/PageErreur403.vue'
import PageCours from '@/views/PageCours.vue'
import PageCoursPublies from '@/views/PageCoursPublies.vue'
import PageProfil from '@/views/PageProfil.vue'
import PageMessages from '@/views/PageMessages.vue'
import PageCreationCours from '@/views/PageCreationCours.vue'
import PageModificationCours from '@/views/PageModificationCours.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/connexion',
      name: 'connexion',
      component: PageConnexion,
      meta: { cacheMenu: true }
    },
    {
      path: '/',
      name: 'accueil',
      component: PageAccueil,
      meta: { requiresAuth: true }
    },
    {
      path: '/inscription',
      name: 'inscription',
      component: PageInscription,
      meta: { cacheMenu: true }
    },
    {
      path: '/catalogue',
      name: 'catalogue',
      component: PageCatalogue,
      meta: { requiresAuth: true }
    },
    {
      path: '/catalogue/:idCours/apercu',
      name: 'apercuCours',
      component: PageApercuCours,
      meta: { requiresAuth: true }
    },
    {
      path: '/mes-cours-inscrits',
      name: 'mesCoursInscrits',
      component: PageCoursInscrits,
      meta: {requiresAuth: true, roleRequis: 'eleve'}
    },
    {
      path: '/mes-cours-publies',
      name: 'mesCoursPublies',
      component: PageCoursPublies,
      meta: { requiresAuth: true, roleRequis: 'createur' }
    },
    {
      path: '/cours/creation',
      name: 'creationCours',
      component: PageCreationCours,
      meta: { requiresAuth: true, roleRequis: 'createur' }
    },
    {
      path: '/cours/:id',
      name: 'cours',
      component: PageCours,
      meta: { requiresAuth: true }
    },
    {
      path: '/profil',
      name: 'profil',
      component: PageProfil,
      meta: { requiresAuth: true }
    },
    {
      path: '/messages',
      name: 'messages',
      component: PageMessages,
      meta: { requiresAuth: true }
    },
    {
      path: '/non-autorise',
      name: 'non-autorise',
      component: PageErreur403
    },
    {
      path: '/cours/:id/modifier',
      name: 'modifierCours',
      component: PageModificationCours,
      meta: { requiresAuth: true, roleRequis: 'createur' }
    },
    {
      path: '/:pathMatch(.*)*',
      name: 'erreur-404',
      component: PageErreur404
    }
  ],
})

router.beforeEach((to, from, next) => {
  const token = localStorage.getItem('token');

  let role = null;
  if (token) {
    try {
      role = JSON.parse(atob(token.split('.')[1])).role;
    }
    catch(e) { /* empty */ }
  }
  if(to.meta.requiresAuth && !token) {
    next('/connexion');
  }
  else if ((to.name === 'connexion' || to.name === 'inscription') && token) {
    next('/');
  }
  else if (to.meta.roleRequis && to.meta.roleRequis !== role) {
    next('/non-autorise');
  }
  else {
    next();
  }
})

export default router
