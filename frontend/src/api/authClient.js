import axios from 'axios';

const API_BASE_URL = 'http://localhost:8080/api';

export const authClient = {
  async connexion(email, motDePasse) {
    const reponse = await axios.post(`${API_BASE_URL}/authentification/connexion`, {
      email, motDePasse
    });

    return reponse.data;
  },

  async inscription(nom, prenom, email, motDePasse, role)
  {
    const reponse = await axios.post(`${API_BASE_URL}/authentification/inscription`, {
      nom,
      prenom,
      email,
      motDePasse,
      role,
    });

    return reponse.data;
  }
}
