import { computed, ref } from 'vue'
import { useRouter } from 'vue-router'
import axios from 'axios'
import { enregistrerCoursConsulte } from '@/utils/historiqueAccueil.js'

export function useCours(idCours) {
  const router = useRouter()

  const indexChapitreActif = ref(0)
  const progression = ref(0)
  const cours = ref()
  const elevesInscrits = ref([])
  const elevesOuverts = ref(false)
  const elevesDejaCharges = ref(false)
  const modalOuverte = ref(false)
  const eleveSelectionne = ref(null)
  const estLeCreateur = ref(false)

  const chapitreActif = computed(() => {
    if (!cours.value || !cours.value.chapitres)
    {
      return null
    }
    return cours.value.chapitres[indexChapitreActif.value]
  })

  const nbChapitresFinis = computed(() =>
  {
    if (estLeCreateur.value) {
      return 999
    }
    if (!cours.value || !cours.value.chapitres)
    {
      return 0
    }
    return Math.round((progression.value / 100) * cours.value.chapitres.length)
  })

  async function chargerCours()
  {
    try
    {
      const token = localStorage.getItem('token')

      const reponseCours = await axios.get(`/api/cours/${idCours}`,
        {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      })
      cours.value = reponseCours.data

      const utilisateurConnecte = recuperationUtilisateur(token)
      const estCreateurConnecte = utilisateurConnecte.role === 'createur'

      if (cours.value.createur && cours.value.createur.id === utilisateurConnecte.id)
      {
        estLeCreateur.value = true
        progression.value = 0
        indexChapitreActif.value = 0
        enregistrerCoursConsulte(cours.value, 'cours')
      } else if (estCreateurConnecte)
      {
        router.push('/non-autorise')
        return
      } else
      {
        const reponseProgression = await axios.get(`/api/progression/${idCours}`,
          {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        })
        progression.value = reponseProgression.data.tauxProgression

        indexChapitreActif.value = nbChapitresFinis.value - 1
        enregistrerCoursConsulte(cours.value, 'cours')
      }
    } catch (erreur)
    {
      console.error('Erreur lors du chargement de la progression', erreur)

      if (erreur.response && (erreur.response.status === 403 || erreur.response.status === 404))
      {
        router.push('/non-autorise')
      } else {
        progression.value = 0
        cours.value = null
      }
    }
  }

  function ouvrirProfil(eleve)
  {
    eleveSelectionne.value = eleve
    modalOuverte.value = true
  }

  async function deroulerListeEleves()
  {
    elevesOuverts.value = !elevesOuverts.value
    elevesInscrits.value = cours.value.eleves || []
    elevesDejaCharges.value = true
  }

  function recuperationUtilisateur(token)
  {
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

  function changerChapitre(index)
  {
    if (!estLeCreateur.value && index > nbChapitresFinis.value)
    {
      return
    }
    indexChapitreActif.value = index
    window.scrollTo({ top: 0, behavior: 'smooth' })
  }

  function chapitrePrecedent()
  {
    if (indexChapitreActif.value > 0)
    {
      changerChapitre(indexChapitreActif.value - 1)
    }
  }

  async function chapitreSuivant()
  {
    if (estLeCreateur.value)
    {
      if (indexChapitreActif.value < cours.value.chapitres.length - 1)
      {
        changerChapitre(indexChapitreActif.value + 1)
      } else
      {
        retourCatalogue()
      }
      return
    }

    if (indexChapitreActif.value >= nbChapitresFinis.value)
    {
      const idChapitre = chapitreActif.value.id

      try
      {
        const token = localStorage.getItem('token')

        await axios.post(
          `/api/chapitres/${idChapitre}/terminer`,
          {},
          {
            headers:
              {
              Authorization: `Bearer ${token}`,
            },
          },
        )
        const reponseProgression = await axios.get(`/api/progression/${idCours}`, {
          headers:
            {
            Authorization: `Bearer ${token}`,
          },
        })

        progression.value = reponseProgression.data.tauxProgression
      } catch (erreur)
      {
        console.error('Erreur API : ', erreur)
      }
    }

    if (indexChapitreActif.value < cours.value.chapitres.length - 1)
    {
      changerChapitre(indexChapitreActif.value + 1)
    } else
    {
      retourCatalogue()
    }
  }

  function retourCatalogue()
  {
    if (estLeCreateur.value)
    {
      router.push('/mes-cours-publies')
      return
    }
    router.push('/mes-cours-inscrits')
  }

  return {
    cours,
    indexChapitreActif,
    progression,
    elevesInscrits,
    elevesOuverts,
    modalOuverte,
    eleveSelectionne,
    estLeCreateur,
    chapitreActif,
    nbChapitresFinis,
    chargerCours,
    ouvrirProfil,
    deroulerListeEleves,
    changerChapitre,
    chapitrePrecedent,
    chapitreSuivant,
    retourCatalogue,
  }
}
