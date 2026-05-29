import { createRouter, createWebHistory } from 'vue-router'
import PageConnexion from '@/views/PageConnexion.vue'
import PageAccueil from '@/views/PageAccueil.vue'
import PageInscription from '@/views/PageInscription.vue'
import PageCatalogue from '@/views/PageCatalogue.vue'

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
    }
  ],
})

router.beforeEach((to, from, next) => {
  const token = localStorage.getItem('token');

  if(to.meta.requiresAuth && !token) {
    next('/connexion');
  }
  else if ((to.name === 'connexion' || to.name === 'inscription') && token) {
    next('/');
  }
  else {
    next();
  }
})

export default router
