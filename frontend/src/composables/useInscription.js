import { computed, ref } from 'vue'
import { useRouter } from 'vue-router'
import { authClient } from '@/api/authClient.js'

export function useInscription()
{
  const router = useRouter()

  const nom = ref('')
  const prenom = ref('')
  const email = ref('')
  const motDePasse = ref('')
  const confirmationMdp = ref('')
  const role = ref('ELEVE')
  const chargement = ref(false)
  const messageErreur = ref('')

  const reglesMotDePasse = computed(() => [
    {
      texte: 'Au moins 8 caractères',
      valide: motDePasse.value.length >= 8,
    },
    {
      texte: 'Une lettre minuscule',
      valide: /[a-z]/.test(motDePasse.value),
    },
    {
      texte: 'Une lettre majuscule',
      valide: /[A-Z]/.test(motDePasse.value),
    },
    {
      texte: 'Un chiffre',
      valide: /\d/.test(motDePasse.value),
    },
    {
      texte: 'Un caractère spécial',
      valide: /[^A-Za-z0-9]/.test(motDePasse.value),
    },
  ])

  const motDePasseComplexe = computed(() =>
    reglesMotDePasse.value.every((regle) => regle.valide)
  )

  const motsDePasseIdentiques = computed(() =>
    motDePasse.value !== '' && motDePasse.value === confirmationMdp.value
  )

  async function sInscrire()
  {
    messageErreur.value = ''

    if (!motDePasseComplexe.value)
    {
      messageErreur.value = 'Le mot de passe ne respecte pas les règles de sécurité.'
      return
    }

    if (!motsDePasseIdentiques.value)
    {
      messageErreur.value = 'Les mots de passe ne correspondent pas.'
      return
    }

    chargement.value = true

    try
    {
      await authClient.inscription(nom.value, prenom.value, email.value, motDePasse.value, role.value)
      router.push({ name: 'connexion', query: { compteCree: '1' } })
    } catch (erreur)
    {
      if (erreur.response && erreur.response.status === 409)
      {
        messageErreur.value = 'Un compte existe déjà avec cet email.'
      } else
      {
        messageErreur.value = "Une erreur est survenue durant l'inscription."
      }
    } finally
    {
      chargement.value = false
    }
  }

  function allerAConnexion()
  {
    router.push('/connexion')
  }

  return {
    nom,
    prenom,
    email,
    motDePasse,
    confirmationMdp,
    role,
    chargement,
    messageErreur,
    reglesMotDePasse,
    motDePasseComplexe,
    motsDePasseIdentiques,
    sInscrire,
    allerAConnexion,
  }
}
