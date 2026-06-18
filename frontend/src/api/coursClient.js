import axios from 'axios'
import { getAuthHeaders, getAuthMultipartHeaders } from './authHeader.js'

const API_BASE_URL = 'http://localhost:8080/api';

export const coursClient = {
  async getCours(idCours) {
    const reponse = await axios.get(`${API_BASE_URL}/cours/${idCours}`, {
      headers: getAuthHeaders(),
    })

    return reponse.data
  },

  async getTousLesCours() {
    const reponse = await axios.get(`${API_BASE_URL}/cours`, {
      headers: getAuthHeaders(),
    })

    return reponse.data;
  },

  async getCoursInscrits() {
    const reponse = await axios.get(`${API_BASE_URL}/cours/utilisateurs/inscriptions`, {
      headers: getAuthHeaders(),
    })

    return reponse.data;
  },

  async getProgressionCours(idCours) {
    const reponse = await axios.get(`${API_BASE_URL}/progression/${idCours}`, {
      headers: getAuthHeaders(),
    })

    return reponse.data.tauxProgression;
  },

  async inscrireCours(idCours) {
    const reponse = await axios.post(
      `${API_BASE_URL}/inscription/etudiants/cours/${idCours}`,
      null,
      {
        headers: getAuthHeaders(),
      },
    )

    return reponse.data
  },

  async getCoursPublies() {
    const reponse = await axios.get(`${API_BASE_URL}/cours/utilisateurs/publications`, {
      headers: getAuthHeaders(),
    })

    return reponse.data.filter((cours) => cours.estPublie);
  },

  async creerCours(cours) {
    const reponse = await axios.post(
      `${API_BASE_URL}/cours`,
      {
        titre: cours.titre,
        description: cours.description || '',
        difficulte: cours.difficulte,
      },
      {
        headers: getAuthHeaders(),
      },
    )

    return reponse.data;
  },

  async ajouterCategorie(idCours, categorie) {
    const reponse = await axios.post(
      `${API_BASE_URL}/cours/${idCours}/categories`,
      JSON.stringify(categorie),
      {
        headers: {
          ...getAuthHeaders(),
          'Content-Type': 'application/json',
        },
      },
    )

    return reponse.data;
  },

  async ajouterChapitre(idCours, chapitre) {
    const reponse = await axios.post(
      `${API_BASE_URL}/cours/${idCours}/chapitres`,
      {
        titre: chapitre.titre,
        description: chapitre.description,
      },
      {
        headers: getAuthHeaders(),
      },
    )

    return reponse.data;
  },

  async uploaderRessource(fichier) {
    const donnees = new FormData()
    donnees.append('fichier', fichier)

    const reponse = await axios.post(`${API_BASE_URL}/ressources/upload`, donnees, {
      headers: getAuthMultipartHeaders(),
    })

    return reponse.data;
  },

  async ajouterRessourceChapitre(idChapitre, ressource) {
    const reponse = await axios.post(
      `${API_BASE_URL}/chapitres/${idChapitre}/ressources`,
      {
        id: ressource.id,
        nom: ressource.nom,
        url: ressource.url,
        type: ressource.type,
      },
      {
        headers: getAuthHeaders(),
      },
    )

    return reponse.data;
  },

  async publierCours(idCours) {
    const reponse = await axios.post(
      `${API_BASE_URL}/cours/${idCours}/publier`,
      null,
      {
        headers: getAuthHeaders(),
      },
    )

    return reponse.data;
  },

  async modifierCours(idCours, donneesModifiees) {
    const reponse = await axios.put(
      `${API_BASE_URL}/cours/${idCours}`,
      donneesModifiees,
      {
        headers: getAuthHeaders(),
      }
    )
    return reponse.data;
  }
}
