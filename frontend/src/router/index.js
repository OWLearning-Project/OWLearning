import { createRouter, createWebHistory } from 'vue-router'
import PageConnexion from '@/views/PageConnexion.vue'
import PageAccueil from '@/views/PageAccueil.vue'

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
      component: PageAccueil
    }
  ],
})

export default router
