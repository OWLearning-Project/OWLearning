import axios from 'axios'

const API_BASE_URL = 'http://localhost:8080/api'

function headersAuthentifies()
{
  const token = localStorage.getItem('token')

  return {
    Authorization: `Bearer ${token}`,
  }
}

export const messagerieClient = {
  async getDiscussions() {
    const reponse = await axios.get(`${API_BASE_URL}/messagerie/mes-discussions`, {
      headers: headersAuthentifies(),
    })

    return reponse.data
  },

  async demarrerDiscussionAvecCreateur(idCreateur) {
    const reponse = await axios.post(
      `${API_BASE_URL}/messagerie/discussions/createurs/${idCreateur}`,
      null,
      {
        headers: headersAuthentifies(),
      },
    )

    return reponse.data
  },

  async getMessages(idDiscussion) {
    const reponse = await axios.get(`${API_BASE_URL}/messagerie/${idDiscussion}/messages`, {
      headers: headersAuthentifies(),
    })

    return reponse.data
  },
}
