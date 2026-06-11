import axios from 'axios'
import { getAuthHeaders } from './authHeader.js'

const API_BASE_URL = 'http://localhost:8080/api'

export const utilisateurClient = {
  async getProfil(idUtilisateur) {
    const token = localStorage.getItem('token')
    const reponse = await axios.get(`${API_BASE_URL}/utilisateurs/${idUtilisateur}`, {
      headers: getAuthHeaders(),
    })
    return reponse.data
  },

  async getCreateurs() {
    const token = localStorage.getItem('token')
    const reponse = await axios.get(`${API_BASE_URL}/utilisateurs/createurs`, {
      headers: getAuthHeaders(),
    })

    return reponse.data
  },

  async modifierProfil(profil) {
    const token = localStorage.getItem('token')
    const reponse = await axios.put(`${API_BASE_URL}/utilisateurs/edit_profil`, null, {
      headers: getAuthHeaders(),
      params: {
        pseudo: profil.pseudo,
        email: profil.email,
        age: profil.age,
        niveauEtude: profil.niveauEtude,
      },
    })

    return reponse.data
  }
}
