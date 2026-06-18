import { computed, ref } from 'vue'
import { coursClient } from '@/api/coursClient.js'
import { enregistrerCoursConsulte } from '@/utils/historiqueAccueil.js'
import { recupererUtilisateurConnecte } from '@/utils/getUserConnect.js'

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

  const peutVoirCours = computed(() => estInscrit.value || estCreateurDuCours.value)
  const libelleAction = computed(() => peutVoirCours.value ? 'Voir le cours' : 'S\'inscrire')

  async function chargerApercuCours()
  {
    chargement.value = true
    erreur.value = ''
    estInscrit.value = false
    estCreateurDuCours.value = false
    progression.value = 0

    try
    {
      const utilisateurConnecte = recupererUtilisateurConnecte()
      const coursCharge = await coursClient.getCours(idCours)

      cours.value = coursCharge
      enregistrerCoursConsulte(coursCharge, 'apercuCours')
      estCreateurDuCours.value = utilisateurConnecte.role === 'createur'
        && Number(coursCharge.createur?.id) === utilisateurConnecte.id

      if (utilisateurConnecte.role !== 'eleve')
      {
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

  async function actionPrincipale()
    {
      messageAction.value = ''
      if (peutVoirCours.value){
        router.push({ name: 'cours', params: { id: idCours } })
        return
      }
      actionEnCours.value = true
      try{
        await coursClient.inscrireCours(idCours)
        estInscrit.value = true
        typeMessageAction.value = 'success'
        messageAction.value = 'Inscription réussie.'
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
    peutVoirCours,
    libelleAction,
    chargerApercuCours,
    actionPrincipale,
  }
}
