package Application.Services;

import Domain.Models.Categorie;
import Domain.Models.Chapitre;
import Domain.Models.Cours;
import Domain.Models.Difficulte;
import Domain.Ports.IRepository.ICoursRepository;
import Domain.Ports.IServices.IServiceCours;
import Shared.Exceptions.ExceptionMauvaisIdChapitre;
import Shared.Exceptions.ExceptionMauvaisLabelCategorie;
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
    public ArrayList<Cours> getCoursCrees(String titre, int idCreateur, String difficulte, String categorie, Boolean estPrive)
    {
        return coursRepository.trouverParIdCreateur(titre, idCreateur, difficulte, categorie, estPrive);
    }

    /**
     * Méthode permettant de récupérer les Cours où un élève est inscrit avec son id
     * Filtrage possible avec les paramètres
     * @param eleveId id de l'élève
     * @return l'ArrayList des Cours inscrits
     */
    public ArrayList<Cours> getCoursInscrits(int eleveId, String titre, String nomCreateur, String difficulte, String categorie, Boolean estPrive)
    {
        return coursRepository.trouverParIdEleve(eleveId, titre, nomCreateur, difficulte, categorie, estPrive);
    }

    /**
     * Méthode permettant de récupérer l'entièreté des Cours publiés
     * Filtrage possible avec les paramètres
     * @return l'ArrayList des Cours publiés
     */
    @Override
    public ArrayList<Cours> getLesCours(String titre, String nomCreateur, String difficulte, String categorie, Boolean estPrive)
    {
        return coursRepository.trouverCoursFiltre(titre, nomCreateur, difficulte, categorie, estPrive);
    }

    /**
     * Cette méthode crée un nouveau cours après vérification des données
     * @param titre titre du cours
     * @param description description du cours
     * @param categorie catégorie du cours
     * @param createurId identifiant du créateur
     * @return le cours créé
     */
    @Override
    public Cours creerCours(String titre, String description, String categorie, int createurId)
    {
        if (titre == null || titre.isBlank()) throw new IllegalArgumentException("Le titre n'est pas valide");
        if (description == null || description.isBlank()) throw new IllegalArgumentException("La description n'est pas valide");
        if (categorie == null || categorie.isBlank()) throw new IllegalArgumentException("Categorie non valide");
        if (createurId <= 0) throw new IllegalArgumentException("Identifiant du créateur invalide");

        Cours cours = coursRepository.creerCours(titre, description, categorie, createurId);

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

        coursRepository.publierCours(coursId);
    }

    /**
     * Modifie le titre et la description d’un cours
     * @param coursId identifiant du cours
     * @param titre nouveau titre
     * @param description nouvelle description
     */
    @Override
    public void modifierInformationsCours(int coursId, String titre, String description)
    {
        if (coursId <= 0) throw new IllegalArgumentException("L'Id du cours n'est pas valide");
        if (titre == null || titre.isBlank()) throw new IllegalArgumentException("Titre non valide");
        if (description == null || description.isBlank()) throw new IllegalArgumentException("Description est vide");

        coursRepository.modifierInformationsCours(coursId, titre, description);
    }

    /**
     * Cette methode change le statut privé ou public d’un cours
     * @param coursId identifiant du cours
     * @param estPrive nouveau statut
     */
    @Override
    public void coursPrive(int coursId, boolean estPrive)
    {
        if (coursId <= 0) throw new IllegalArgumentException("Identifiant du cours invalide");

        coursRepository.coursPrive(coursId, estPrive);
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
            throw new IllegalArgumentException("Le cours n'existe pas");
        }
        coursRepository.ajouterChapitre(coursId, chapitre);
    }

    /**
     * Methode permettant de retirer un chapitre à un cours grâce a leurs id
     * @param coursId id du cours
     * @param chapitreId id du chapitre
     * @return l'objet chapitre qui est retirer
     */
    @Override
    public Chapitre retirerChapitre(int coursId, int chapitreId) throws ExceptionMauvaisIdChapitre {
        if(coursId <= 0 || chapitreId <= 0) {
            throw new IllegalArgumentException("l'id du cours ou l'id du chapitre est invalide");
        }
        return coursRepository.retirerChapitre(coursId, chapitreId);
    }

    /**
     * Methode permettant de modifié la difficulté d'un cours
     * @param coursId id du cours
     * @param nouvelleDifficulte la difficulté à modifier
     */
    @Override
    public void modifierDifficulteCours(int coursId, Difficulte nouvelleDifficulte) {
        Cours coursAvecDifficulteModifie = coursRepository.trouverParId(coursId);
        if (coursAvecDifficulteModifie == null){
            throw new IllegalArgumentException("Le cours n'existe pas");
        }
        coursAvecDifficulteModifie.setDifficulte(nouvelleDifficulte);
        coursRepository.sauvegarder(coursAvecDifficulteModifie);
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
            throw new IllegalArgumentException("le cours n'existe pas");
        }
        cours.ajouterCategorie(categorieAjouter);
        coursRepository.ajouterCategorieCours(coursId, categorieAjouter);
    }

    /**
     * Methode qui permet de supprimer une categorie d'un cours et leve une exception si le cours n'existe pas
     * @param coursId id du cours
     * @param categorieASupprimer categorie a supprimer
     * @return un objet categorie, celle qui a été supprimer
     * @throws ExceptionMauvaisLabelCategorie
     */
    @Override
    public Categorie supprimerCategorieCours(int coursId, Categorie categorieASupprimer) throws ExceptionMauvaisLabelCategorie {
        Cours cours = coursRepository.trouverParId(coursId);
        if (cours == null){
            throw new IllegalArgumentException("le cours n'existe pas");
        }
        Categorie categorieSupprimer = cours.supprimerCategorie(categorieASupprimer.getLabel());
        coursRepository.supprimerCategorieCours(coursId, categorieASupprimer);
        return categorieSupprimer;
    }
}
