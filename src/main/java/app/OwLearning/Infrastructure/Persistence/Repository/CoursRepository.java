package app.OwLearning.Infrastructure.Persistence.Repository;

import app.OwLearning.Domain.Models.*;
import app.OwLearning.Domain.Ports.IRepository.ICoursRepository;
import app.OwLearning.Domain.Ports.IRepository.IUtilisateurRepository;
import app.OwLearning.Infrastructure.Persistence.Interface.JpaCoursRepository;
import app.OwLearning.Shared.Exceptions.ExceptionCoursInexistant;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

/**
 * Classe CoursRepository pour récupérer les cours
 */
@Component
public class CoursRepository implements ICoursRepository
{
    private final JpaCoursRepository jpaRepository;
    private final IUtilisateurRepository utilisateurRepository;

    public CoursRepository(JpaCoursRepository jpaRepository, IUtilisateurRepository utilisateurRepository)
    {
        this.jpaRepository = jpaRepository;
        this.utilisateurRepository = utilisateurRepository;
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
     * @param difficulte difficulté du cours
     * @param createurId id du créateur
     * @return le cours créé
     */
    @Override
    public Cours creerCours(String titre, String description, Difficulte difficulte, int createurId)
    {
        // Création d'une liste de catégories vide
        ArrayList<Categorie> categories = new ArrayList<>();
        Createur createur = (Createur) utilisateurRepository.trouverParId(createurId);

        // Création du cours
        Cours cours = new Cours(titre, description, false, categories, difficulte, createur);
        return jpaRepository.save(cours);
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
     * Méthode pour trouver les cours publiés avec ou sans filtre
     * @return l'ArrayList des Cours publiés
     */
    @Override
    public ArrayList<Cours> trouverCoursPublies()
    {
        return new ArrayList<>(jpaRepository.findByEstPublieTrue());
    }

    public boolean coursExiste(int id)
    {
        return this.jpaRepository.existsById(id);
    }

    @Override
    public void sauvegarder(Cours cours)
    {
        this.jpaRepository.save(cours);
    }
}
