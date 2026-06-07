package app.OwLearning.Services.Services;

import app.OwLearning.Domaine.Enumérations.Categorie;
import app.OwLearning.Domaine.Enumérations.Difficulte;
import app.OwLearning.Domaine.Interfaces.ICoursRepository;
import app.OwLearning.Services.Interfaces.IServiceCours;
import app.OwLearning.Domaine.Entités.*;
import app.OwLearning.Domaine.Exceptions.ExceptionCoursInexistant;
import app.OwLearning.Domaine.Exceptions.ExceptionMauvaisIdChapitre;
import app.OwLearning.Domaine.Exceptions.ExceptionMauvaisLabelCategorie;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Classe ServiceCours, permet de gérer le traitement des cours
 */
@Slf4j
@Service
public class ServiceCours implements IServiceCours
{
    private final ICoursRepository coursRepository;
    /**
     * Constructeur du ServiceCours
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
        log.debug("Récupération du cours avec l'id {}", id);
        Cours cours = coursRepository.trouverParId(id);
        if (cours == null){
            log.warn("Echec. Id introuvable ");
        } else{
            log.debug("Cours {} récupéré: {}", id, cours);
        }
        return cours;
    }

    /**
     * Méthode qui permet de récupérer les cours créés par un créateur avec son id
     * @param idCreateur id du créateur
     * @return l'ArrayList des Cours créés
     */
    public ArrayList<Cours> getCoursCrees(int idCreateur)
    {
        log.debug("Demande de récupération des cours créés par un createur {}", idCreateur);
        ArrayList<Cours> cours = coursRepository.trouverParIdCreateur(idCreateur);
        log.info("Récupération de {} cours du createur", idCreateur);
        return cours;
    }

    /**
     * Méthode permettant de récupérer les Cours où un élève est inscrit avec son id
     * @param idEleve id de l'élève
     * @return l'ArrayList des Cours inscrits
     */
    public ArrayList<Cours> getCoursInscrits(int idEleve)
    {
        log.debug("Demande de récupération des cours inscrit pour l'élève {}", idEleve);
        ArrayList<Cours> cours = coursRepository.trouverParIdEleve(idEleve);
        log.info("Récupération de {} cours", idEleve);
        return cours;
    }

    /**
     * Méthode permettant de récupérer l'entièreté des Cours publiés
     * Filtrage possible avec les paramètres
     * @return l'ArrayList des Cours publiés
     */
    @Override
    public ArrayList<Cours> getCoursPublies()
    {
        log.debug("Demande de récupération des cours publiés");
        ArrayList<Cours> coursPublies = coursRepository.trouverCoursPublies();
        log.info("Récupération de {} cours publiées" , coursPublies.size());
        return coursPublies;
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
        log.debug("Création d'un cours avec le titre '{}' pour le créateur {}", titre, createurId);
        if (titre == null || titre.isBlank()){
            log.warn("Echec. Le titre est invalde");
            throw new IllegalArgumentException("Le titre n'est pas valide");
        }
        if (description == null){
            description = "";
        }
        if (difficulte == null){
            log.warn("Echec. Le difficulte est invalde");
            throw new IllegalArgumentException("Difficulté non valide");
        }
        if (createurId <= 0){
            log.warn("Echec. L'identifiant du createur n'est pas valide");
            throw new IllegalArgumentException("Identifiant du créateur invalide");
        }

        Cours cours = coursRepository.creerCours(titre, description, difficulte, createurId);

        if (cours == null) {
            log.warn("Echec de création du cours '{}' pour le createur {}", titre, createurId);
            throw new IllegalStateException("La création du cours a échoué");
        }
        log.info("Cours créé avec succès");
        return cours;
    }

    /**
     * Publier un cours via son identifiant
     * @param coursId identifiant du cours
     */
    @Override
    public void publierCours(int coursId)
    {
        log.debug("Publication de cours{} ", coursId);
        if (coursId <= 0){
            log.warn("Echec de la publication. Votre identifiant cours {} n'est pas valide.", coursId);
            throw new IllegalArgumentException("Identifiant du cours invalide");
        }
        if (!coursRepository.coursExiste(coursId)){
            log.warn("Echec de la publication. Le cours {} est introuvable dans la base.", coursId);
            throw new ExceptionCoursInexistant("Le cours n'existe pas", coursId);
        }
        Cours cours = coursRepository.trouverParId(coursId);
        cours.publier();
        coursRepository.sauvegarder(cours);

        log.info("Cours {} publié avec succès", coursId);
    }

    /**
     * Modifie le titre, la description, la difficulté et la visibilité d'un cours
     * @param coursId identifiant du cours
     * @param titre nouveau titre
     * @param description nouvelle description
     * @param estPrive visibilité du cours
     */
    @Override
    public void modifierInformationsCours(int coursId, String titre, String description, Difficulte difficulte, boolean estPrive)
    {
        log.debug("Modification des informations du cours {}", coursId);
        if (coursId <= 0){
            log.warn("Echec de la modification. l'id n'est pas valide. ");
            throw new IllegalArgumentException("L'Id du cours n'est pas valide");
        }
        if (titre == null || titre.isBlank()){
            log.warn("Echec de la modification. Le titre est invalde");
            throw new IllegalArgumentException("Titre non valide");
        }
        if (description == null || description.isBlank()){
            log.warn("Echec de la modification. La description est invalde");
            throw new IllegalArgumentException("Description est vide");
        }
        if (difficulte == null){
            log.warn("Echec de la modification. La difficulté n'est pas renseigné");
            throw new IllegalArgumentException("La difficulté du cours n'est pas renseignée");
        }
        if (!coursRepository.coursExiste(coursId)){
            log.warn("Echec de la modification. Le cours n'existe pas dans la base");
            throw new ExceptionCoursInexistant("Le cours n'existe pas", coursId);
        }

        Cours leCours = coursRepository.trouverParId(coursId);

        leCours.setTitre(titre);
        leCours.setDescription(description);
        leCours.setDifficulte(difficulte);
        leCours.visibilite(estPrive);

        coursRepository.sauvegarder(leCours);
        log.info("Cours {} modifié avec succès", coursId);
    }

    /**
     * Methode permettant de supprimer un cours
     * @param coursId identifiant du cours
     * @return le cours supprimé
     */
    @Override
    public Cours supprimerCours(int coursId)
    {
        log.debug("Suppression du cours {}", coursId);
        if (coursId <= 0){
            log.warn("Echec de la suppression. l'id {} n'est pas valide. ", coursId);
            throw new IllegalArgumentException("Identifiant du cours invalide");
        }

        Cours coursSupprime = coursRepository.supprimerCours(coursId);

        if (coursSupprime == null){
            log.warn("Echec de la suppression du cours {}. Aucun cours supprimé", coursId);
            throw new IllegalStateException("Impossible de suppression le cours");
        }
        log.info("Cours {} supprimé!", coursId);
        return coursSupprime;
    }

    /**
     * Methode qui ajoute un chapitre à un cours
     * @param coursId id du cours
     * @param chapitre chapitre à ajouter
     */
    @Override
    public Chapitre ajouterChapitre(int coursId, Chapitre chapitre) {
        log.debug("Ajout d'un chapitre {}", coursId);
        if(chapitre == null){
            log.warn("L'ajout du chapitre a échoué");
            throw new IllegalArgumentException("Un chapitre à ajouter ne peut pas être null");
        }
        if (!coursRepository.coursExiste(coursId)){
            log.warn("L'ajout du chapitre a échoué. Le cours {} est introuvable dans la base", coursId);
            throw new ExceptionCoursInexistant("Le cours n'existe pas", coursId);
        }
        Cours leCours = coursRepository.trouverParId(coursId);

        Set<Integer> idsChapitresExistants = new HashSet<>();
        if (leCours.getChapitres() != null) {
            List<Chapitre> chapitresDuCours = leCours.getChapitres();
            for (int i = 0; i < chapitresDuCours.size(); i++) {
                Chapitre chapitreExistant = chapitresDuCours.get(i);
                idsChapitresExistants.add(chapitreExistant.getId());
            }
        }

        leCours.ajouterChapitre(chapitre);
        Cours coursSauvegarde = coursRepository.sauvegarder(leCours);

        log.info("Chapitre ajouté avec succès au cours {}",  coursId);
        return trouverChapitreAjoute(coursSauvegarde, idsChapitresExistants, chapitre);
    }

    private Chapitre trouverChapitreAjoute(Cours coursSauvegarde, Set<Integer> idsChapitresExistants, Chapitre chapitreInitial)
    {
        if (coursSauvegarde != null && coursSauvegarde.getChapitres() != null)
        {
            for (Chapitre chapitre : coursSauvegarde.getChapitres())
            {
                if (!idsChapitresExistants.contains(chapitre.getId()))
                {
                    return chapitre;
                }
            }
        }

        return chapitreInitial;
    }

    /**
     * Methode permettant de retirer un chapitre à un cours grâce à leurs id
     * @param coursId id du cours
     * @param chapitreId id du chapitre
     */
    @Override
    public void retirerChapitre(int coursId, int chapitreId) throws ExceptionMauvaisIdChapitre {
        log.debug("Suppression du chapitre {}", coursId);
        if(coursId <= 0 || chapitreId <= 0) {
            log.warn("La suppression du chapitre a échoué. L'identifiant du cours{} ou du chapitre{} n'est pas valide!",  coursId, chapitreId);
            throw new IllegalArgumentException("l'id du cours ou l'id du chapitre est invalide");
        }
        if (!coursRepository.coursExiste(coursId)){
            log.warn("La suppression du chapitre a échoué. Le cours {} est introuvable",  coursId);
            throw new ExceptionCoursInexistant("Le cours n'existe pas", coursId);
        }
        Cours leCours = coursRepository.trouverParId(coursId);
        leCours.retirerChapitre(chapitreId);
        coursRepository.sauvegarder(leCours);

        log.info("Chapitre {} supprimé du cours {} avec succès.",chapitreId, coursId);
    }

    /**
     * Methode qui permet d'ajouter une categorie à un cours s'il existe et lève une exception si le cours n'existe pas
     * @param coursId id du cours
     * @param categorieAjouter catégorie à ajouter au cours
     */
    @Override
    public void ajouterCategorieCours(int coursId, Categorie categorieAjouter) {
        log.debug("Ajout de la categorie {} du cours {}",categorieAjouter, coursId);
        Cours cours = coursRepository.trouverParId(coursId);
        if (cours == null){
            log.warn("Echec de l'ajout de la catégorie. Le cours {} est introuvable.", coursId);
            throw new ExceptionCoursInexistant("le cours n'existe pas", coursId);
        }
        cours.ajouterCategorie(categorieAjouter);
        coursRepository.sauvegarder(cours);
        log.info("Catégorie {} ajoutée au cours {} avec succès", categorieAjouter, coursId);
    }

    /**
     * Methode qui permet de supprimer une categorie d'un cours et lève une exception si le cours n'existe pas
     * @param coursId id du cours
     * @param categorieASupprimer catégorie a supprimer
     * @throws ExceptionMauvaisLabelCategorie
     */
    @Override
    public void supprimerCategorieCours(int coursId, Categorie categorieASupprimer) throws ExceptionMauvaisLabelCategorie {
        log.debug("Suppression de la catégorie {} du cours {}",categorieASupprimer,coursId);
        Cours cours = coursRepository.trouverParId(coursId);
        if (cours == null){
            log.warn("La suppresion de la catégorie a échoué. Le cours {} est introuvable dans la base.", coursId);
            throw new ExceptionCoursInexistant("le cours n'existe pas", coursId);
        }
        cours.supprimerCategorie(categorieASupprimer.getLabel());
        coursRepository.sauvegarder(cours);
        log.info("Catégorie {} supprimée du cours {} avec succès.", categorieASupprimer,coursId);
    }

    // Ajouter accepterEleve et refuserEleve apres les merge.
}
