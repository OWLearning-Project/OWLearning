import { computed, ref } from 'vue'
import { messagerieClient } from '@/api/messagerieClient.js'
import { messagerieWebSocketClient } from '@/api/messagerieWebSocketClient.js'
import { utilisateurClient } from '@/api/utilisateurClient.js'
import {
  enregistrerDerniereDiscussionUtilisee,
  recupererDerniereDiscussionUtilisee,
} from '@/utils/historiqueAccueil.js'
import { recupererUtilisateurConnecte } from '@/utils/getUserConnect.js'
import { useRoute } from 'vue-router'

export function useMessagerie()
{
  const route = useRoute()
  const createurs = ref([])
  const discussions = ref([])
  const discussionSelectionnee = ref(null)
  const messages = ref([])
  const rechercheCreateur = ref('')
  const nouveauMessage = ref('')
  const chargement = ref(true)
  const chargementMessages = ref(false)
  const envoiEnCours = ref(false)
  const actionCreateurId = ref(null)
  const erreur = ref('')
  let annulerAbonnementDiscussion = null

  const utilisateurConnecte = computed(() => recupererUtilisateurConnecte())

  const createursFiltres = computed(() =>
  {
    const texte = rechercheCreateur.value.trim().toLowerCase()
    const idUtilisateur = utilisateurConnecte.value.id

    return createurs.value
      .filter((createur) => Number(createur.id) !== idUtilisateur)
      .filter((createur) =>
      {
        if (texte === '')
        {
          return true
        }

        return `${createur.prenom} ${createur.nom} ${createur.pseudo || ''} ${createur.email}`
          .toLowerCase()
          .includes(texte)
      })
  })

  const discussionsTriees = computed(() =>
    [...discussions.value].sort((discussionA, discussionB) =>
      timestampDernierMessage(discussionB) - timestampDernierMessage(discussionA),
    ),
  )

  async function chargerMessagerie()
  {
    chargement.value = true
    erreur.value = ''

    try
    {
      const [createursData, discussionsData] = await Promise.all([
        utilisateurClient.getCreateurs(),
        messagerieClient.getDiscussions(),
      ])

      createurs.value = createursData || []
      discussions.value = (discussionsData || [])
        .map(normaliserDiscussion)
        .filter(discussion => discussion.participants.length === 2)

      if (route.query.createurId) {
        const createur = createurs.value.find((c) => Number(c.id) === Number(route.query.createurId))
        if (createur) {
          await contacterCreateur(createur)
        }
      }
      else if (route.query.utilisateurId) {
        const utilisateur = createurs.value.find((c) => Number(c.id) === Number(route.query.utilisateurId))
        if (utilisateur) {
          await contacterUtilisateur(utilisateur)
        }
      }
      else if (discussionsTriees.value.length > 0)
      {
        const derniereDiscussionId = recupererDerniereDiscussionUtilisee()
        const discussionASelectionner = trouverDiscussion(derniereDiscussionId) || discussionsTriees.value[0]
        await selectionnerDiscussion(discussionASelectionner)
      }
    } catch (e)
    {
      console.error('Erreur de chargement de la messagerie :', e)
      erreur.value = 'Chargement de la messagerie impossible'
    } finally
    {
      chargement.value = false
    }
  }

  async function contacterCreateur(createur)
  {
    if (!createur?.id)
    {
      return
    }

    actionCreateurId.value = createur.id
    erreur.value = ''

    try
    {
      const discussion = await messagerieClient.demarrerDiscussionAvecUtilisateur(createur.id)
      ajouterOuMettreAJourDiscussion(discussion)
      await selectionnerDiscussion(discussion)
    } catch (e)
    {
      console.error('Erreur de creation de discussion :', e)
      erreur.value = 'Ouverture de la discussion impossible'
    } finally
    {
      actionCreateurId.value = null
    }
  }

  async function contacterUtilisateur(utilisateur)
  {
    if (!utilisateur?.id)
    {
      return
    }
    actionCreateurId.value = utilisateur.id
    erreur.value = ''

    try
    {
      const discussion = await messagerieClient.demarrerDiscussionAvecUtilisateur(utilisateur.id)

      ajouterOuMettreAJourDiscussion(discussion)
      await selectionnerDiscussion(discussion)
    } catch (e)
    {
      console.error('Erreur de création de discussion :', e)
      erreur.value = 'Ouverture de la discussion impossible'
    } finally
    {
      actionCreateurId.value = null
    }
  }

  async function selectionnerDiscussion(discussion)
  {
    if (!discussion?.id)
    {
      discussionSelectionnee.value = null
      messages.value = []
      desabonnerDiscussion()
      return
    }

    const discussionTrouvee = trouverDiscussion(discussion.id);

    discussionSelectionnee.value = trouverDiscussion(discussion.id) || normaliserDiscussion(discussion)
    enregistrerDerniereDiscussionUtilisee(discussion.id)
    await chargerMessages(discussion.id)
    await abonnerDiscussion(discussion.id)
  }

  async function chargerMessages(idDiscussion)
  {
    chargementMessages.value = true
    erreur.value = ''

    try
    {
      messages.value = trierMessages(await messagerieClient.getMessages(idDiscussion))
      mettreAJourMessagesDiscussion(idDiscussion, messages.value)
    } catch (e)
    {
      console.error('Erreur de chargement des messages :', e)
      erreur.value = 'Chargement des messages impossible'
    } finally
    {
      chargementMessages.value = false
    }
  }

  async function envoyerMessage(ressourceId = null)
  {
    const contenu = nouveauMessage.value.trim()

    if (!discussionSelectionnee.value || (contenu === '' & ressourceId === null))
    {
      return
    }

    envoiEnCours.value = true
    erreur.value = ''

    try
    {
      await messagerieWebSocketClient.envoyerMessage(
        discussionSelectionnee.value.id,
        utilisateurConnecte.value.id,
        contenu,
        ressourceId,
      )
      nouveauMessage.value = ''
    } catch (e)
    {
      console.error("Erreur d'envoi du message :", e)
      erreur.value = "Envoi du message impossible"
    } finally
    {
      envoiEnCours.value = false
    }
  }

  async function abonnerDiscussion(idDiscussion)
  {
    desabonnerDiscussion()

    try
    {
      annulerAbonnementDiscussion = await messagerieWebSocketClient.sAbonnerDiscussion(
        idDiscussion,
        traiterDiscussionMiseAJour,
      )
    } catch (e)
    {
      console.error("Erreur d'abonnement a la discussion :", e)
      erreur.value = 'Connexion temps reel impossible'
    }
  }

  function traiterDiscussionMiseAJour(discussion)
  {
    const discussionNormalisee = normaliserDiscussion(discussion)
    ajouterOuMettreAJourDiscussion(discussionNormalisee)

    if (discussionSelectionnee.value?.id === discussionNormalisee.id)
    {
      discussionSelectionnee.value = trouverDiscussion(discussionNormalisee.id)
      messages.value = discussionNormalisee.messages
    }
  }

  function desabonnerDiscussion()
  {
    if (annulerAbonnementDiscussion)
    {
      annulerAbonnementDiscussion()
      annulerAbonnementDiscussion = null
    }
  }

  function fermerMessagerie()
  {
    desabonnerDiscussion()
    messagerieWebSocketClient.deconnecter()
  }

  function ajouterOuMettreAJourDiscussion(discussion)
  {
    const discussionNormalisee = normaliserDiscussion(discussion)
    const index = discussions.value.findIndex((discussionExistante) => discussionExistante.id === discussionNormalisee.id)

    if (index === -1)
    {
      discussions.value = [discussionNormalisee, ...discussions.value]
      return
    }

    discussions.value.splice(index, 1, discussionNormalisee)
  }

  function mettreAJourMessagesDiscussion(idDiscussion, messagesDiscussion)
  {
    const discussion = trouverDiscussion(idDiscussion)

    if (discussion)
    {
      discussion.messages = messagesDiscussion
    }
  }

  function trouverDiscussion(idDiscussion)
  {
    const idRecherche = Number(idDiscussion);
    return discussions.value.find((discussion) => Number(discussion.id) === idRecherche)  }

  function normaliserDiscussion(discussion)
  {
    return {
      ...discussion,
      id: Number(discussion.id),
      participants: discussion.participants || [],
      messages: trierMessages(discussion.messages || []),
    }
  }

  function trierMessages(messagesDiscussion)
  {
    return [...(messagesDiscussion || [])].sort((messageA, messageB) =>
    {
      const dateA = messageA.dateCreation ? new Date(messageA.dateCreation).getTime() : 0
      const dateB = messageB.dateCreation ? new Date(messageB.dateCreation).getTime() : 0

      if (dateA === dateB)
      {
        return Number(messageA.id) - Number(messageB.id)
      }

      return dateA - dateB
    })
  }

  function interlocuteurDiscussion(discussion)
  {
    const participants = discussion?.participants || []

    const autresParticipants = participants.filter(
      (participant) => Number(participant.id) !== utilisateurConnecte.value.id
    )

    if (autresParticipants.length === 0)
    {
      return participants[0] || null
    }

    if (autresParticipants.length === 1)
    {
      return autresParticipants[0]
    }

    return {
      prenom: autresParticipants.map(p => p.prenom || p.pseudo || p.email).join(', '),
      nom: '',
      pseudo: '',
      email: '',
    }
  }

  function nomUtilisateur(utilisateur)
  {
    if (!utilisateur)
    {
      return 'Discussion'
    }

    const nomComplet = `${utilisateur.prenom || ''} ${utilisateur.nom || ''}`.trim()
    return nomComplet || utilisateur.pseudo || utilisateur.email || 'Utilisateur'
  }

  function initialesUtilisateur(utilisateur)
  {
    if (!utilisateur)
    {
      return '?'
    }

    const prenom = utilisateur.prenom?.charAt(0) || ''
    const nom = utilisateur.nom?.charAt(0) || ''
    const initiales = `${prenom}${nom}`.toUpperCase()

    return initiales || utilisateur.pseudo?.charAt(0).toUpperCase() || '?'
  }

  function dernierMessage(discussion)
  {
    const messagesDiscussion = trierMessages(discussion?.messages || [])
    return messagesDiscussion[messagesDiscussion.length - 1] || null
  }

  function estMessageAuteurConnecte(message)
  {
    return Number(message.idUtilisateur) === utilisateurConnecte.value.id
  }

  function timestampDernierMessage(discussion)
  {
    const message = dernierMessage(discussion)

    if (!message?.dateCreation)
    {
      return 0
    }

    return new Date(message.dateCreation).getTime()
  }

  return {
    createurs,
    createursFiltres,
    discussionsTriees,
    discussionSelectionnee,
    messages,
    rechercheCreateur,
    nouveauMessage,
    chargement,
    chargementMessages,
    envoiEnCours,
    actionCreateurId,
    erreur,
    chargerMessagerie,
    contacterCreateur,
    contacterUtilisateur,
    selectionnerDiscussion,
    envoyerMessage,
    fermerMessagerie,
    interlocuteurDiscussion,
    nomUtilisateur,
    initialesUtilisateur,
    dernierMessage,
    estMessageAuteurConnecte,
  }
}
