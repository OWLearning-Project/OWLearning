package Application.Services;

import Domain.Models.Cours;
import Domain.Ports.IRepository.ICoursRepository;
import Domain.Ports.IServices.IServiceCours;
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





}
