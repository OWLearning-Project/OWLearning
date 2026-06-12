import { computed, ref } from 'vue'
import { coursClient } from '@/api/coursClient.js'
import { enregistrerCoursConsulte } from '@/utils/historiqueAccueil.js'
import { recupererUtilisateurConnecte } from '@/utils/getUserConnect.js'

export function useApercuCours(idCours)
{
  const cours = ref(null)
  const estInscrit = ref(false)
  const estCreateurDuCours = ref(false)
  const progression = ref(0)
  const chargement = ref(true)
  const erreur = ref('')

  const progressionPourcent = computed(() =>
  {
    const valeur = Number(progression.value) || 0
    const pourcentage = valeur <= 1 ? valeur * 100 : valeur

    return Math.min(100, Math.max(0, Math.round(pourcentage)))
  })

  const peutVoirCours = computed(() => estInscrit.value || estCreateurDuCours.value)
  const libelleAction = computed(() => peutVoirCours.value ? 'Voir le cours' : '')

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

  return {
    cours,
    estInscrit,
    estCreateurDuCours,
    chargement,
    erreur,
    progressionPourcent,
    peutVoirCours,
    libelleAction,
    chargerApercuCours,
  }
}
