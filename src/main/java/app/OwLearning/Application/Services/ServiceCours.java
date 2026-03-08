package app.OwLearning.Application.Services;

import app.OwLearning.Domain.Ports.IRepository.ICoursRepository;
import app.OwLearning.Domain.Ports.IServices.IServiceCours;
import app.OwLearning.Domain.Models.*;
import app.OwLearning.Shared.Exceptions.ExceptionCoursInexistant;
import app.OwLearning.Shared.Exceptions.ExceptionMauvaisIdChapitre;
import app.OwLearning.Shared.Exceptions.ExceptionMauvaisLabelCategorie;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * Classe ServiceCours, permet de gérer le traitement des cours
 */
@Service
public class ServiceCours implements IServiceCours
{
    private final ICoursRepository coursRepository;

    /**
     * Constructeur du ServiceCours
     * @param coursRepository
     */
    public ServiceCours (ICoursRepository coursRepository)
    {
        this.coursRepository = coursRepository;
    }

    /**
     * Méthode permettant de récupérer un cours avec son id
     * @param id id du cours
     * @return l'objet Cours associé
     */
    public Cours getCoursParId(int id)
    {
        return coursRepository.trouverParId(id);
    }

    /**
     * Méthode qui permet de récupérer les cours créés par un créateur avec son id
     * Filtrage possible avec les paramètres
     * @param idCreateur id du créateur
     * @return l'ArrayList des Cours créés
     */
    public ArrayList<Cours> getCoursCrees(int idCreateur)
    {
        return coursRepository.trouverParIdCreateur(idCreateur);
    }

    /**
     * Méthode permettant de récupérer les Cours où un élève est inscrit avec son id
     * @param idEleve id de l'élève
     * @return l'ArrayList des Cours inscrits
     */
    public ArrayList<Cours> getCoursInscrits(int idEleve)
    {
        return coursRepository.trouverParIdEleve(idEleve);
    }

    /**
     * Méthode permettant de récupérer l'entièreté des Cours publiés
     * Filtrage possible avec les paramètres
     * @return l'ArrayList des Cours publiés
     */
    @Override
    public ArrayList<Cours> getCoursPublies()
    {
        return coursRepository.trouverCoursPublies();
    }

    /**
     * Cette méthode crée un nouveau cours après vérification des données
     * @param titre titre du cours
     * @param description description du cours
     * @param difficulte catégorie du cours
     * @param createurId identifiant du créateur
     * @return le cours créé
     */
    @Override
    public Cours creerCours(String titre, String description, Difficulte difficulte , int createurId)
    {
        if (titre == null || titre.isBlank()) throw new IllegalArgumentException("Le titre n'est pas valide");
        if (description == null || description.isBlank()) throw new IllegalArgumentException("La description n'est pas valide");
        if (difficulte == null) throw new IllegalArgumentException("Difficulté non valide");
        if (createurId <= 0) throw new IllegalArgumentException("Identifiant du créateur invalide");

        Cours cours = coursRepository.creerCours(titre, description, difficulte, createurId);

        if (cours == null) throw new IllegalStateException("La création du cours a échoué");

        return cours;
    }

    /**
     * Publier un cours
     * @param coursId identifiant du cours
     */
    @Override
    public void publierCours(int coursId)
    {
        if (coursId <= 0) throw new IllegalArgumentException("Identifiant du cours invalide");
        if (!coursRepository.coursExiste(coursId)){
            throw new ExceptionCoursInexistant("Le cours n'existe pas", coursId);
        }
        Cours cours = coursRepository.trouverParId(coursId);
        cours.publier();
        coursRepository.sauvegarder(cours);
    }

    /**
     * Modifie le titre et la description d’un cours
     * @param coursId identifiant du cours
     * @param titre nouveau titre
     * @param description nouvelle description
     */
    @Override
    public void modifierInformationsCours(int coursId, String titre, String description, Difficulte difficulte, boolean estPrive)
    {
        if (coursId <= 0) throw new IllegalArgumentException("L'Id du cours n'est pas valide");
        if (titre == null || titre.isBlank()) throw new IllegalArgumentException("Titre non valide");
        if (description == null || description.isBlank()) throw new IllegalArgumentException("Description est vide");
        if (difficulte == null) throw new IllegalArgumentException("La difficulté du cours n'est pas renseignée");
        if (!coursRepository.coursExiste(coursId)){
            throw new ExceptionCoursInexistant("Le cours n'existe pas", coursId);
        }

        Cours leCours = coursRepository.trouverParId(coursId);

        leCours.setTitre(titre);
        leCours.setDescription(description);
        leCours.setDifficulte(difficulte);
        leCours.visibilite(estPrive);

        coursRepository.sauvegarder(leCours);
    }

    /**
     * Methode permettant de supprimer un cours
     * @param coursId identifiant du cours
     * @return le cours supprimé
     */
    @Override
    public Cours supprimerCours(int coursId)
    {
        if (coursId <= 0) throw new IllegalArgumentException("Identifiant du cours invalide");

        Cours coursSupprime = coursRepository.supprimerCours(coursId);

        if (coursSupprime == null) throw new IllegalStateException("Impossible de suppression le cours");

        return coursSupprime;
    }

    /**
     * Methode qui va verifier qu'un chapitre n'est pas null avant de l'ajouter à un cours
     * @param coursId id du cours
     * @param chapitre chapitre à ajouter
     */
    @Override
    public void ajouterChapitre(int coursId, Chapitre chapitre) {
        if(chapitre == null){
            throw new IllegalArgumentException("Un chapitre à ajouter ne peut pas être null");
        }
        if (!coursRepository.coursExiste(coursId)){
            throw new ExceptionCoursInexistant("Le cours n'existe pas", coursId);
        }
        Cours leCours = coursRepository.trouverParId(coursId);
        leCours.ajouterChapitre(chapitre);
        coursRepository.sauvegarder(leCours);
    }

    /**
     * Methode permettant de retirer un chapitre à un cours grâce a leurs id
     * @param coursId id du cours
     * @param chapitreId id du chapitre
     * @return l'objet chapitre qui est retirer
     */
    @Override
    public void retirerChapitre(int coursId, int chapitreId) throws ExceptionMauvaisIdChapitre {
        if(coursId <= 0 || chapitreId <= 0) {
            throw new IllegalArgumentException("l'id du cours ou l'id du chapitre est invalide");
        }
        if (!coursRepository.coursExiste(coursId)){
            throw new ExceptionCoursInexistant("Le cours n'existe pas", coursId);
        }
        Cours leCours = coursRepository.trouverParId(coursId);
        leCours.retirerChapitre(chapitreId);
        coursRepository.sauvegarder(leCours);
    }

    /**
     * Methode qui permet d'ajouter une categorie à un cours si il existe et leve une exception si les cours n'existe pas
     * @param coursId id du cours
     * @param categorieAjouter categorie à ajouter au cours
     */
    @Override
    public void ajouterCategorieCours(int coursId, Categorie categorieAjouter) {
        Cours cours = coursRepository.trouverParId(coursId);
        if (cours == null){
            throw new ExceptionCoursInexistant("le cours n'existe pas", coursId);
        }
        cours.ajouterCategorie(categorieAjouter);
        coursRepository.sauvegarder(cours);
    }

    /**
     * Methode qui permet de supprimer une categorie d'un cours et leve une exception si le cours n'existe pas
     * @param coursId id du cours
     * @param categorieASupprimer categorie a supprimer
     * @return un objet categorie, celle qui a été supprimer
     * @throws ExceptionMauvaisLabelCategorie
     */
    @Override
    public void supprimerCategorieCours(int coursId, Categorie categorieASupprimer) throws ExceptionMauvaisLabelCategorie {
        Cours cours = coursRepository.trouverParId(coursId);
        if (cours == null){
            throw new ExceptionCoursInexistant("le cours n'existe pas", coursId);
        }
        cours.supprimerCategorie(categorieASupprimer.getLabel());
        coursRepository.sauvegarder(cours);
    }

    // Ajouter accepterEleve et refuserEleve apres les merge.
}
