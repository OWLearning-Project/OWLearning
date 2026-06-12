import { computed, ref } from 'vue'
import { messagerieClient } from '@/api/messagerieClient.js'
import {
  enregistrerDerniereDiscussionUtilisee,
  recupererCoursConsultesRecents,
  recupererDerniereDiscussionUtilisee,
} from '@/utils/historiqueAccueil.js'
import { recupererUtilisateurConnecte } from '@/utils/getUserConnect.js'

export function useAccueil()
{
  const coursRecents = ref([])
  const discussions = ref([])
  const chargement = ref(true)
  const erreur = ref('')

  const discussionsTriees = computed(() =>
    [...discussions.value].sort((discussionA, discussionB) =>
      timestampDernierMessage(discussionB) - timestampDernierMessage(discussionA),
    ),
  )

  const derniereDiscussion = computed(() =>
  {
    const idDerniereDiscussion = recupererDerniereDiscussionUtilisee()

    if (idDerniereDiscussion)
    {
      const discussionMemorisee = discussions.value.find((discussion) => discussion.id === idDerniereDiscussion)

      if (discussionMemorisee)
      {
        return discussionMemorisee
      }
    }

    return discussionsTriees.value[0] || null
  })

  async function chargerAccueil()
  {
    chargement.value = true
    erreur.value = ''
    coursRecents.value = recupererCoursConsultesRecents()

    try
    {
      discussions.value = (await messagerieClient.getDiscussions() || []).map(normaliserDiscussion)
    } catch (e)
    {
      console.error("Erreur de chargement de l'accueil :", e)
      erreur.value = 'Chargement des discussions impossible'
    } finally
    {
      chargement.value = false
    }
  }

  function memoriserDiscussionAccueil(idDiscussion)
  {
    enregistrerDerniereDiscussionUtilisee(idDiscussion)
  }

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

  function dernierMessage(discussion)
  {
    const messagesDiscussion = trierMessages(discussion?.messages || [])
    return messagesDiscussion[messagesDiscussion.length - 1] || null
  }

  function interlocuteurDiscussion(discussion)
  {
    const utilisateurConnecte = recupererUtilisateurConnecte()
    const participants = discussion?.participants || []
    return participants.find((participant) => Number(participant.id) !== utilisateurConnecte.id) || participants[0] || null
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
    coursRecents,
    derniereDiscussion,
    chargement,
    erreur,
    chargerAccueil,
    memoriserDiscussionAccueil,
    dernierMessage,
    interlocuteurDiscussion,
    nomUtilisateur,
    initialesUtilisateur,
  }
}
