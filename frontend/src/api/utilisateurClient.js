import axios from 'axios'

const API_BASE_URL = 'http://localhost:8080/api'

export const utilisateurClient = {
  async getProfil(idUtilisateur) {
    const token = localStorage.getItem('token')
    const reponse = await axios.get(`${API_BASE_URL}/utilisateurs/${idUtilisateur}`, {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    })
    return reponse.data
  },

  async getCreateurs() {
    const token = localStorage.getItem('token')
    const reponse = await axios.get(`${API_BASE_URL}/utilisateurs/createurs`, {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    })

    return reponse.data
  },

  async modifierProfil(profil) {
    const token = localStorage.getItem('token')
    const reponse = await axios.put(`${API_BASE_URL}/utilisateurs/edit_profil`, null, {
      headers: {
        Authorization: `Bearer ${token}`,
      },
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
