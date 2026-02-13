package app.OwLearning.Infrastructure.Persistence.Repository;

import app.OwLearning.Domain.Models.*;
import app.OwLearning.Domain.Ports.IRepository.ICoursRepository;
import app.OwLearning.Infrastructure.Persistence.Interface.JpaCoursRepository;
import app.OwLearning.Shared.Exceptions.ExceptionCoursInexistant;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Optional;

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

    @Override
    public void ajouterChapitre(int coursId, Chapitre chapitre) {

    }

    @Override
    public Chapitre retirerChapitre(int coursId, int ChapitreId) {
        return null;
    }

    @Override
    public void modifierDifficulteCours(int coursId, Difficulte difficulte) {

    }

    @Override
    public void ajouterCategorieCours(int coursId, Categorie categorie) {

    }

    @Override
    public Categorie supprimerCategorieCours(int coursId, Categorie categorie) {
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
}
