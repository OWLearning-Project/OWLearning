import { computed, ref } from 'vue'
import { coursClient } from '@/api/coursClient.js'

const difficultesPossibles = [
  { text: 'Débutant', value: 'DEBUTANT' },
  { text: 'Intermédiaire', value: 'INTERMEDIAIRE' },
  { text: 'Avancé', value: 'AVANCE' },
]

const categoriesPossibles = [
  { text: 'Programmation algorithmique', value: 'PROGRAMMATION_ALGORITHMIQUE' },
  { text: 'Développement web', value: 'DEVELOPPEMENT_WEB' },
  { text: 'Base de données', value: 'BASE_DE_DONNEES' },
  { text: 'Systèmes et réseaux', value: 'SYSTEMES_RESEAUX' },
  { text: 'Intelligence artificielle et DataSciences', value: 'IA_DATASCIENCES' },
  { text: 'Développement mobile', value: 'DEVELOPPEMENT_MOBILE' },
  { text: "Histoire de l'informatique", value: 'HISTOIRE_INFORMATIQUE' },
  { text: 'Management et gestion', value: 'MANAGEMENT_GESTION' },
  { text: 'Mathématiques', value: 'MATHEMATIQUES' },
  { text: 'Architecture', value: 'ARCHITECTURE' },
]

let compteurChapitre = 0

function creerChapitreVide() {
  compteurChapitre += 1

  return {
    uid: compteurChapitre,
    titre: '',
    description: '',
    fichier: null,
  }
}

export function useCreationCours() {
  const formulaire = ref({
    titre: '',
    description: '',
    difficulte: '',
    categorie: '',
  })

  const chapitres = ref([creerChapitreVide()])
  const erreurs = ref({})
  const erreursChapitres = ref([])
  const erreur = ref('')
  const messageSucces = ref('')
  const chargement = ref(false)
  const coursCree = ref(null)

  const formulaireValide = computed(() => {
    const coursValide = champRenseigne(formulaire.value.titre)
      && champRenseigne(formulaire.value.difficulte)
      && champRenseigne(formulaire.value.categorie)

    const chapitresValides = chapitres.value.length > 0
      && chapitres.value.every((chapitre) =>
        champRenseigne(chapitre.titre)
        && champRenseigne(chapitre.description)
        && chapitre.fichier,
      )

    return coursValide && chapitresValides
  })

  function ajouterChapitre() {
    chapitres.value.push(creerChapitreVide())
  }

  function retirerChapitre(index) {
    if (chapitres.value.length === 1) {
      chapitres.value = [creerChapitreVide()]
      erreursChapitres.value = []
      return
    }

    chapitres.value.splice(index, 1)
    erreursChapitres.value.splice(index, 1)
  }

  function selectionnerFichier(index, evenement) {
    const fichier = evenement.target.files?.[0] || null
    chapitres.value[index].fichier = fichier
  }

  function validerFormulaire() {
    const erreursFormulaire = {}
    const erreursParChapitre = []

    if (!champRenseigne(formulaire.value.titre)) {
      erreursFormulaire.titre = 'Le titre du cours est obligatoire.'
    }

    if (!champRenseigne(formulaire.value.difficulte)) {
      erreursFormulaire.difficulte = 'La difficulté est obligatoire.'
    }

    if (!champRenseigne(formulaire.value.categorie)) {
      erreursFormulaire.categorie = 'La catégorie est obligatoire.'
    }

    if (chapitres.value.length === 0) {
      erreursFormulaire.chapitres = 'Ajoutez au moins un chapitre.'
    }

    chapitres.value.forEach((chapitre, index) => {
      const erreursChapitre = {}

      if (!champRenseigne(chapitre.titre)) {
        erreursChapitre.titre = 'Le titre du chapitre est obligatoire.'
      }

      if (!champRenseigne(chapitre.description)) {
        erreursChapitre.description = 'La description du chapitre est obligatoire.'
      }

      if (!chapitre.fichier) {
        erreursChapitre.fichier = 'La pièce jointe du chapitre est obligatoire.'
      }

      erreursParChapitre[index] = erreursChapitre
    })

    erreurs.value = erreursFormulaire
    erreursChapitres.value = erreursParChapitre

    return Object.keys(erreursFormulaire).length === 0
      && erreursParChapitre.every((erreurChapitre) => Object.keys(erreurChapitre).length === 0)
  }

  async function creerCoursComplet() {
    erreur.value = ''
    messageSucces.value = ''
    coursCree.value = null

    if (!validerFormulaire()) {
      erreur.value = 'Veuillez compléter les champs obligatoires avant de créer le cours.'
      return null
    }

    chargement.value = true

    try {
      const cours = await coursClient.creerCours({
        titre: formulaire.value.titre.trim(),
        description: formulaire.value.description.trim(),
        difficulte: formulaire.value.difficulte,
      })
      const idCours = extraireId(cours, 'cours')

      await coursClient.ajouterCategorie(idCours, formulaire.value.categorie)

      for (const chapitre of chapitres.value) {
        const chapitreCree = await coursClient.ajouterChapitre(idCours, {
          titre: chapitre.titre.trim(),
          description: chapitre.description.trim(),
        })
        const idChapitre = extraireId(chapitreCree, 'chapitre')

        const ressource = await coursClient.uploaderRessource(chapitre.fichier)
        await coursClient.ajouterRessourceChapitre(idChapitre, ressource)
      }

      coursCree.value = cours
      messageSucces.value = 'Le cours a été créé avec succès.'
      return cours
    }
    catch (e) {
      console.error('Erreur de création du cours :', e)
      erreur.value = messageErreurApi(e)
      return null
    }
    finally {
      chargement.value = false
    }
  }

  return {
    formulaire,
    chapitres,
    erreurs,
    erreursChapitres,
    erreur,
    messageSucces,
    chargement,
    coursCree,
    difficultesPossibles,
    categoriesPossibles,
    formulaireValide,
    ajouterChapitre,
    retirerChapitre,
    selectionnerFichier,
    creerCoursComplet,
  }
}

function champRenseigne(valeur) {
  return typeof valeur === 'string' ? valeur.trim() !== '' : Boolean(valeur)
}

function extraireId(entite, libelle) {
  const id = entite?.id

  if (!id) {
    throw new Error(`L'identifiant du ${libelle} créé est introuvable.`)
  }

  return id
}

function messageErreurApi(e) {
  const donnees = e.response?.data

  if (typeof donnees === 'string' && donnees.trim() !== '') {
    return donnees
  }

  if (donnees?.message) {
    return donnees.message
  }

  return 'La création du cours a échoué. Vérifiez les informations saisies puis réessayez.'
}
