package app.OwLearning.Infrastructure.Persistence.Repository;

import app.OwLearning.Domain.Models.*;
import app.OwLearning.Domain.Ports.IRepository.ICoursRepository;
import app.OwLearning.Infrastructure.Persistence.Interface.JpaCoursRepository;
import app.OwLearning.Shared.Exceptions.ExceptionCategorieDejaPresente;
import app.OwLearning.Shared.Exceptions.ExceptionCoursInexistant;
import app.OwLearning.Shared.Exceptions.ExceptionMauvaisIdChapitre;
import app.OwLearning.Shared.Exceptions.ExceptionMauvaisLabelCategorie;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe CoursRepository pour récupérer les cours
 */
@Component
public class CoursRepository implements ICoursRepository
{
    private final JpaCoursRepository jpaRepository;

    public CoursRepository(JpaCoursRepository jpaRepository)
    {
        this.jpaRepository = jpaRepository;
    }

    /**
     * Méthode pour trouver un cours par son id
     * @param id id du cours
     * @return l'objet Cours associé
     */
    @Override
    public Cours trouverParId(int id)
    {
        return jpaRepository.findById(id).orElseThrow(() -> new ExceptionCoursInexistant("Le cours n'existe pas", id));
    }

    /**
     * Méthode pour trouver les cours créés par un créateur via son id
     * @param idCreateur id du créateur
     * @return l'ArrayList des cours créés
     */
    @Override
    public ArrayList<Cours> trouverParIdCreateur(int idCreateur)
    {
        return new ArrayList<>(jpaRepository.findByCreateurIdUtilisateur(idCreateur));
    }

    /**
     * Méthode pour trouver les cours auxquels un élève est inscrit via son id
     * @param idEleve id de l'élève
     * @return l'ArrayList des cours inscrits
     */
    @Override
    public ArrayList<Cours> trouverParIdEleve(int idEleve)
    {
        return new ArrayList<>(jpaRepository.findByElevesIdUtilisateur(idEleve));
    }

    /**
     * Cette méthode crée un nouveau cours après vérification des données
     * @param titre titre du cours
     * @param description description du cours
     * @param categorie catégorie du cours
     * @param createurId id du créateur
     * @return le cours créé
     */
    @Override
    public Cours creerCours(String titre, String description, String categorie, int createurId){
        if(titre == null || titre.isBlank()) throw new IllegalArgumentException("Ce titre n'est pas valide");
        if(description == null || description.isBlank()) throw new IllegalArgumentException("Ce description n'est pas valide");

        // Création d'une liste de catégories
        ArrayList<Categorie> categories = new ArrayList<>();
        if (categorie != null && !categorie.isBlank()){ categories.add(Categorie.valueOf(categorie));}

        // Création du cours
        Cours cours = new Cours(
                titre, description, false, categories, Difficulte.DEBUTANT, null
        );
        return jpaRepository.save(cours);
    }

    /**
     * Publier un cours
     * @param coursId id du cours à publier
     */
    @Override
    public void publierCours(int coursId){
        Cours cours = jpaRepository.findById(coursId).orElse(null);
        if (cours == null) throw new IllegalArgumentException("Cours introuvable");
        cours.publier();
        jpaRepository.save(cours);
    }

    /**
     * Modifie le titre et la description d’un cours
     * @param coursId id du cours
     * @param titre nouveau titre du cours
     * @param description nouvelle description du cours
     */
    @Override
    public void modifierInformationsCours(int coursId, String titre, String description){

        Cours cours = jpaRepository.findById(coursId).orElse(null);
        if (cours == null) throw new IllegalArgumentException("Cours introuvable");
        if (titre !=null && !titre.isBlank()) cours.setTitre(titre);
        if (description !=null && !description.isBlank()) cours.setDescription(description);

        jpaRepository.save(cours);
    }

    /**
     * Cette methode change le statut privé ou public d’un cours
     * @param coursId id du cours
     * @param estPrive nouveau statut du cours
     */
    @Override
    public void coursPrive(int coursId, boolean estPrive){
        Cours cours = jpaRepository.findById(coursId).orElse(null);

        if (cours == null) throw new IllegalArgumentException("Cours introuvable");

        cours.setEstPrive(estPrive);
        jpaRepository.save(cours);
    }

    /**
     * Methode permettant de supprimer un cours
     * @param coursId id du cours
     * @return l'objet Cours supprimé
     */
    @Override
    public Cours supprimerCours(int coursId){
        Cours cours = jpaRepository.findById(coursId).orElse(null);

        if(cours == null)
            return null;
        jpaRepository.delete(cours);
        return cours;
    }

    /**
     * Methode qui permet d'ajouter un chapitre a un cours
     * @param coursId id du cours
     * @param chapitre chapitre à ajouter
     */
    @Override
    public void ajouterChapitre(int coursId, Chapitre chapitre) {
        Cours cours = this.jpaRepository.findById(coursId).orElse(null);
        if (cours != null) {
            cours.ajouterChapitre(chapitre);
            this.jpaRepository.save(cours);
        }
    }

    /**
     * Methode qui permet de supprimer un chapitre d'un cours
     * @param coursId id du cours
     * @param chapitreId id du chapitre
     * @return l'obejet chapitre retirer
     * @throws ExceptionMauvaisIdChapitre
     */
    @Override
    public Chapitre retirerChapitre(int coursId, int chapitreId) throws ExceptionMauvaisIdChapitre {
        Cours cours = this.jpaRepository.findById(coursId).orElse(null);
        if (cours != null){
            Chapitre chapitreRetirer = cours.retirerChapitre(chapitreId);
            this.jpaRepository.save(cours);
            return chapitreRetirer;
        }
        return null;
    }

    /**
     * Methode qui permet de modifier la difficulté d'un cours grace a l'id
     * @param coursId id du cours
     * @param difficulte la difficulté à modifier
     */
    @Override
    public void modifierDifficulteCours(int coursId, Difficulte difficulte) {
        Cours cours = this.jpaRepository.findById(coursId).orElse(null);
        if (cours != null){
            cours.setDifficulte(difficulte);
            this.jpaRepository.save(cours);
        }

    }

    /**
     * Methode qui permet d'ajouter une categorie à un cours grace a l'id du cours
     * @param coursId id du cours
     * @param categorie categorie à ajouter au cours
     */
    @Override
    public void ajouterCategorieCours(int coursId, Categorie categorie) {
        Cours cours = this.jpaRepository.findById(coursId).orElse(null);
        if(cours != null){
            try{
                cours.ajouterCategorie(categorie);
                this.jpaRepository.save(cours);
            } catch (ExceptionCategorieDejaPresente e){
                throw new ExceptionCategorieDejaPresente("Erreur la catégorie est déjà liées à ce cours.", categorie.getLabel(), coursId);
            } catch (Exception e){
                throw new RuntimeException("Erreur technique lors de l'ajoute", e);
            }
        }
    }

    /**
     * Methode qui permet de supprimer une catégorie d'un cours
     * @param coursId id du cours
     * @param categorie categorie a supprimer
     * @return
     */
    @Override
    public Categorie supprimerCategorieCours(int coursId, Categorie categorie) throws ExceptionMauvaisLabelCategorie {
        Cours cours = this.jpaRepository.findById(coursId).orElse(null);
        if (cours != null) {
            try{
                Categorie categorieSupprimee = cours.supprimerCategorie(categorie.getLabel());
                this.jpaRepository.save(cours);
                return categorieSupprimee;
            } catch (ExceptionMauvaisLabelCategorie e){
                throw new ExceptionMauvaisLabelCategorie("label de categorie inexistant", categorie.getLabel(), coursId);
            } catch (Exception e) {
                throw new RuntimeException("Erreur technique lors de la suppression");
            }
        }
        return null;
    }

    /**
     * Méthode pour trouver les cours publiés avec ou sans filtre
     * @return l'ArrayList des Cours publiés
     */
    @Override
    public ArrayList<Cours> trouverCoursPublies()
    {
        return new ArrayList<>(jpaRepository.findByEstPublieTrue());
    }

    public boolean coursExiste(int id){
        return this.jpaRepository.existsById(id);
    }

    @Override
    public void sauvegarder(Cours cours) {
        this.jpaRepository.save(cours);
    }
}
