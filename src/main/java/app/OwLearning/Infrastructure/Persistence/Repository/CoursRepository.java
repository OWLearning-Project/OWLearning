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

    @Override
    public Cours creerCours(String titre, String description, String categorie, int createurId) {
        return null;
    }

    @Override
    public void publierCours(int coursId) {

    }

    @Override
    public void modifierInformationsCours(int coursId, String titre, String description) {

    }

    @Override
    public void coursPrive(int coursId, boolean estPrive) {

    }

    @Override
    public Cours supprimerCours(int coursId) {
        return null;
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
