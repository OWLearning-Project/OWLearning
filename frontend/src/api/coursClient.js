import axios from 'axios'

const API_BASE_URL = 'http://localhost:8080/api';

export const coursClient = {

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
