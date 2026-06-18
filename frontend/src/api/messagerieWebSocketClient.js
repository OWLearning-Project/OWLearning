import { Client } from '@stomp/stompjs'

const WS_BASE_URL = `${window.location.protocol === 'https:' ? 'wss' : 'ws'}://localhost:8080/ws-messagerie`

let client = null
let connexionEnCours = null

export const messagerieWebSocketClient = {
  async sAbonnerDiscussion(idDiscussion, callback) {
    const clientStomp = await connecter()

    const abonnement = clientStomp.subscribe(`/topic/discussion/${idDiscussion}`, (message) => {
      callback(JSON.parse(message.body))
    })

    return () => abonnement.unsubscribe()
  },

  async envoyerMessage(idDiscussion, auteurId, contenu, ressourceId = null) {
    const clientStomp = await connecter()

    clientStomp.publish({
      destination: `/app/messagerie/${idDiscussion}/envoyer`,
      body: JSON.stringify({
        auteurId,
        contenu,
        ressourceId,
      }),
    })
  },

  deconnecter() {
    if (client)
    {
      client.deactivate()
    }

    client = null
    connexionEnCours = null
  },
}

function connecter()
{
  if (client?.connected)
  {
    return Promise.resolve(client)
  }

  if (connexionEnCours)
  {
    return connexionEnCours
  }

  client = new Client({
    brokerURL: WS_BASE_URL,
    reconnectDelay: 3000,
    heartbeatIncoming: 0,
    heartbeatOutgoing: 0,
  })

  connexionEnCours = new Promise((resolve, reject) =>
  {
    client.onConnect = () =>
    {
      connexionEnCours = null
      resolve(client)
    }

    client.onStompError = (frame) =>
    {
      connexionEnCours = null
      reject(new Error(frame.body || 'Erreur STOMP'))
    }

    client.onWebSocketError = () =>
    {
      connexionEnCours = null
      reject(new Error('Connexion WebSocket impossible'))
    }

    client.activate()
  })

  return connexionEnCours
}
