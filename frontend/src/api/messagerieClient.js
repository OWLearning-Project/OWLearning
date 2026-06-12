import axios from 'axios'
import { getAuthHeaders } from './authHeader.js'

const API_BASE_URL = 'http://localhost:8080/api'

export const messagerieClient = {
  async getDiscussions() {
    const reponse = await axios.get(`${API_BASE_URL}/messagerie/mes-discussions`, {
      headers: getAuthHeaders(),
    })

    return reponse.data
  },

  async uploaderRessource(fichier) {
    const formData = new FormData()
    formData.append('fichier', fichier)

    const reponse = await axios.post(
      `${API_BASE_URL}/ressources/upload`,
      formData,
      {
        headers: getAuthHeaders(),
      },
    )

    return reponse.data
  },

  async demarrerDiscussionAvecCreateur(idCreateur) {
    const reponse = await axios.post(
      `${API_BASE_URL}/messagerie/discussions/createurs/${idCreateur}`,
      null,
      {
        headers: getAuthHeaders(),
      },
    )

    return reponse.data
  },

  async getMessages(idDiscussion) {
    const reponse = await axios.get(`${API_BASE_URL}/messagerie/${idDiscussion}/messages`, {
      headers: getAuthHeaders(),
    })

    return reponse.data
  },

  async demarrerDiscussionAvecUtilisateur(idEleve) {
    const reponse = await axios.post(
      `${API_BASE_URL}/messagerie/discussions/eleves/${idEleve}`,
      null,
      {
        headers: getAuthHeaders(),
      },
    )

    return reponse.data
  }
}
