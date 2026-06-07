import axios from 'axios'

const API_BASE_URL = 'http://localhost:8080/api';

function entetesAuth() {
  const token = localStorage.getItem('token')

  return {
    Authorization: `Bearer ${token}`,
  }
}

export const coursClient = {
  async getCours(idCours) {
    const reponse = await axios.get(`${API_BASE_URL}/cours/${idCours}`, {
      headers: entetesAuth(),
    })

    return reponse.data
  },

  async getTousLesCours() {
    const reponse = await axios.get(`${API_BASE_URL}/cours`, {
      headers: entetesAuth(),
    })

    return reponse.data;
  },

  async getCoursInscrits() {
    const reponse = await axios.get(`${API_BASE_URL}/cours/utilisateurs/inscriptions`, {
      headers: entetesAuth(),
    })

    return reponse.data;
  },

  async getProgressionCours(idCours) {
    const reponse = await axios.get(`${API_BASE_URL}/progression/${idCours}`, {
      headers: entetesAuth(),
    })

    return reponse.data.tauxProgression;
  },

  async inscrireCours(idCours) {
    const reponse = await axios.post(
      `${API_BASE_URL}/inscription/etudiants/cours/${idCours}`,
      null,
      {
        headers: entetesAuth(),
      },
    )

    return reponse.data
  },

  async getCoursPublies() {
    const reponse = await axios.get(`${API_BASE_URL}/cours/utilisateurs/publications`, {
      headers: entetesAuth(),
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
        headers: entetesAuth(),
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
          ...entetesAuth(),
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
        headers: entetesAuth(),
      },
    )

    return reponse.data;
  },

  async uploaderRessource(fichier) {
    const donnees = new FormData()
    donnees.append('fichier', fichier)

    const reponse = await axios.post(`${API_BASE_URL}/ressources/upload`, donnees, {
      headers: entetesAuth(),
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
        headers: entetesAuth(),
      },
    )

    return reponse.data;
  },

  async publierCours(idCours) {
    const reponse = await axios.post(
      `${API_BASE_URL}/cours/${idCours}/publier`,
      null,
      {
        headers: entetesAuth(),
      },
    )

    return reponse.data;
  },
}
