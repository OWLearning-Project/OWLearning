import { computed, ref } from 'vue'
import { useRouter } from 'vue-router'
import { coursClient } from '@/api/coursClient.js'

export function useApercuCours(idCours)
{
  const router = useRouter()

  const cours = ref(null)
  const estInscrit = ref(false)
  const estCreateurDuCours = ref(false)
  const progression = ref(0)
  const chargement = ref(true)
  const actionEnCours = ref(false)
  const erreur = ref('')
  const messageAction = ref('')
  const typeMessageAction = ref('info')

  const progressionPourcent = computed(() =>
  {
    const valeur = Number(progression.value) || 0
    const pourcentage = valeur <= 1 ? valeur * 100 : valeur

    return Math.min(100, Math.max(0, Math.round(pourcentage)))
  })

  const libelleAction = computed(() =>
  {
    if (estCreateurDuCours.value)
    {
      return 'Voir le cours'
    }

    if (estInscrit.value)
    {
      return 'Reprendre'
    }

    return cours.value?.estPrive ? "Demander l'inscription" : "S'inscrire"
  })

  async function chargerApercuCours()
  {
    chargement.value = true
    erreur.value = ''
    estCreateurDuCours.value = false

    try {
      const utilisateurConnecte = recupererUtilisateurConnecte()
      const coursCharge = await coursClient.getCours(idCours)

      cours.value = coursCharge
      estCreateurDuCours.value = utilisateurConnecte.role === 'createur'
        && Number(coursCharge.createur?.id) === utilisateurConnecte.id

      if (utilisateurConnecte.role === 'createur')
      {
        if (!estCreateurDuCours.value)
        {
          router.push('/non-autorise')
          return
        }

        estInscrit.value = false
        progression.value = 0
        return
      }

      const coursInscrits = await coursClient.getCoursInscrits()
      estInscrit.value = coursInscrits.some((unCours) => Number(unCours.id) === idCours)

      if (estInscrit.value)
      {
        await chargerProgression()
      }
    } catch (e)
    {
      console.error("Erreur de chargement de l'aperçu :", e)

      if (e.response && e.response.status === 403)
      {
        router.push('/non-autorise')
        return
      }

      erreur.value = "Chargement de l'aperçu du cours impossible"
    } finally
    {
      chargement.value = false
    }
  }

  async function chargerProgression()
  {
    try
    {
      progression.value = await coursClient.getProgressionCours(idCours)
    } catch (e)
    {
      console.warn('Progression indisponible :', e)
      progression.value = 0
    }
  }

  async function actionPrincipale() {
    messageAction.value = ''

    if (estCreateurDuCours.value)
    {
      return
    }

    if (estInscrit.value)
    {
      typeMessageAction.value = 'info'
      messageAction.value = "La page de cours n'est pas encore disponible."
      return
    }

    actionEnCours.value = true

    try
    {
      await coursClient.inscrireCours(idCours)
      estInscrit.value = true
      typeMessageAction.value = 'success'
      messageAction.value = cours.value?.estPrive
        ? "Demande d'inscription envoyée."
        : 'Inscription réussie.'
      await chargerProgression()
    } catch (e)
    {
      console.error("Erreur d'inscription :", e)
      typeMessageAction.value = 'danger'
      messageAction.value = "L'inscription au cours est impossible pour le moment."
    } finally
    {
      actionEnCours.value = false
    }
  }

  function recupererUtilisateurConnecte()
  {
    const token = localStorage.getItem('token')

    if (!token)
    {
      return { id: null, role: null }
    }

    try
    {
      const payloadBase64 = token.split('.')[1]
      const decodage = JSON.parse(atob(payloadBase64))

      return {
        id: Number(decodage.id),
        role: decodage.role,
      }
    } catch (e)
    {
      console.error('Erreur token :', e)
      return { id: null, role: null }
    }
  }

  return {
    cours,
    estInscrit,
    estCreateurDuCours,
    chargement,
    actionEnCours,
    erreur,
    messageAction,
    typeMessageAction,
    progressionPourcent,
    libelleAction,
    chargerApercuCours,
    actionPrincipale
  }
}
