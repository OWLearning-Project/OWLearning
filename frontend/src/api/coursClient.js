import axios from 'axios'

const API_BASE_URL = 'http://localhost:8080/api';

export const coursClient = {
  async getCours(idCours) {
    const token = localStorage.getItem('token')
    const reponse = await axios.get(`${API_BASE_URL}/cours/${idCours}`, {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    })

    return reponse.data
  },

  async getTousLesCours() {
    const token = localStorage.getItem('token');
    const reponse = await axios.get(`${API_BASE_URL}/cours`, {
      headers: {
        Authorization: `Bearer ${token}`,
      }
    });

    return reponse.data;
  },

  async getCoursInscrits() {
    const token = localStorage.getItem('token');
    const reponse = await axios.get(`${API_BASE_URL}/cours/utilisateurs/inscriptions`, {
      headers: {
        Authorization: `Bearer ${token}` }
    });

    return reponse.data;
  },

  async getProgressionCours(idCours) {
    const token = localStorage.getItem('token');
    const reponse = await axios.get(`${API_BASE_URL}/progression/${idCours}`, {
      headers: {
        Authorization: `Bearer ${token}` }
    });

    return reponse.data.tauxProgression;
  },

  async inscrireCours(idCours) {
    const token = localStorage.getItem('token')
    const reponse = await axios.post(
      `${API_BASE_URL}/inscription/etudiants/cours/${idCours}`,
      null,
      {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      },
    )

    return reponse.data
  },

  async getCoursPublies(){
    const token = localStorage.getItem('token');
    const reponse = await axios.get(`${API_BASE_URL}/cours/utilisateurs/publications`, {
      headers: {
        Authorization: `Bearer ${token}`
      }
    });

    return reponse.data.filter((cours) => cours.estPublie);
  }
}
