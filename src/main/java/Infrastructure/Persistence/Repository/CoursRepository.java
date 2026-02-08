package Infrastructure.Persistence.Repository;

import Domain.Models.Categorie;
import Domain.Models.Chapitre;
import Domain.Models.Cours;
import Domain.Models.Difficulte;
import Domain.Ports.IRepository.ICoursRepository;
import Infrastructure.Persistence.Interface.JpaCoursRepository;
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
        return jpaRepository.findByIdNative(id);
    }

    /**
     * Méthode pour trouver les cours crées par un créateur via son id
     * @param createurId id du créateur
     * @return l'ArrayList des cours crées
     */
    @Override
    public ArrayList<Cours> trouverParIdCreateur(String titre, int createurId, String difficulte, String categorie, Boolean estPrive)
    {
        return new ArrayList<>(jpaRepository.findByIdCreateurNative(titre, createurId, difficulte, categorie, estPrive));
    }

    /**
     * Méthode pour trouver les cours où un élève est inscrit via son id
     * @param eleveId id de l'élève
     * @return l'ArrayList des cours inscrits
     */
    @Override
    public ArrayList<Cours> trouverParIdEleve(int eleveId, String titre, String nomCreateur, String difficulte, String categorie, Boolean estPrive)
    {
        return new ArrayList<>(jpaRepository.findByIdEleveNative(eleveId, titre, nomCreateur, difficulte, categorie, estPrive));
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
    public ArrayList<Cours> trouverCoursFiltre(String titre, String nomCreateur, String difficulte, String categorie, Boolean estPrive)
    {
        return new ArrayList<>(jpaRepository.findAllCoursPubliesNative(titre, nomCreateur, difficulte, categorie, estPrive));
    }
}
