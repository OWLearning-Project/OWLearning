import { computed, ref } from 'vue'
import { useRouter } from 'vue-router'
import { utilisateurClient } from '@/api/utilisateurClient.js'

export function useProfil()
{
  const router = useRouter()

  const utilisateur = ref(null)
  const pseudo = ref('')
  const email = ref('')
  const age = ref(null)
  const niveauEtude = ref('')
  const chargement = ref(true)
  const sauvegardeEnCours = ref(false)
  const erreur = ref('')
  const messageSucces = ref('')

  const estEleve = computed(() => utilisateur.value?.role === 'ELEVE')

  async function chargerProfil()
  {
    chargement.value = true
    erreur.value = ''
    messageSucces.value = ''

    try
    {
      const utilisateurConnecte = recupererUtilisateurConnecte()

      if (!utilisateurConnecte.id)
      {
        router.push('/connexion')
        return
      }

      utilisateur.value = await utilisateurClient.getProfil(utilisateurConnecte.id)
      remplirFormulaire(utilisateur.value)
    } catch (e)
    {
      console.error('Erreur de chargement du profil :', e)
      erreur.value = 'Chargement du profil impossible'
    } finally
    {
      chargement.value = false
    }
  }

  async function modifierProfil()
  {
    sauvegardeEnCours.value = true
    erreur.value = ''
    messageSucces.value = ''

    try
    {
      const profilModifie = await utilisateurClient.modifierProfil({
        pseudo: pseudo.value,
        email: email.value,
        age: estEleve.value ? age.value : null,
        niveauEtude: estEleve.value ? niveauEtude.value : null,
      })

      utilisateur.value = profilModifie
      remplirFormulaire(profilModifie)
      messageSucces.value = 'Profil mis a jour'
    } catch (e)
    {
      console.error('Erreur de modification du profil :', e)
      erreur.value = "Modification du profil impossible"
    } finally
    {
      sauvegardeEnCours.value = false
    }
  }

  function remplirFormulaire(profil)
  {
    pseudo.value = profil.pseudo || ''
    email.value = profil.email || ''
    age.value = profil.age ?? null
    niveauEtude.value = profil.niveauEtude || ''
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
      const payload = JSON.parse(atob(token.split('.')[1]))

      return {
        id: Number(payload.id),
        role: payload.role,
      }
    } catch (e)
    {
      console.error('Token invalide', e)
      return { id: null, role: null }
    }
  }

  return {
    utilisateur,
    pseudo,
    email,
    age,
    niveauEtude,
    chargement,
    sauvegardeEnCours,
    erreur,
    messageSucces,
    estEleve,
    chargerProfil,
    modifierProfil,
  }
}
