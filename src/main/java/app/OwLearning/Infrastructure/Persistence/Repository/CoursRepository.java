package app.OwLearning.Infrastructure.Persistence.Repository;

import app.OwLearning.Domain.Models.*;
import app.OwLearning.Domain.Ports.IRepository.ICoursRepository;
import app.OwLearning.Domain.Ports.IRepository.IUtilisateurRepository;
import app.OwLearning.Infrastructure.Persistence.Entity.CoursEntity;
import app.OwLearning.Infrastructure.Persistence.Interface.JpaCoursRepository;
import app.OwLearning.Infrastructure.Persistence.Mapper.CoursMapper;
import app.OwLearning.Infrastructure.Persistence.RelationReconstructor;
import app.OwLearning.Shared.Exceptions.ExceptionCoursInexistant;
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
    private final IUtilisateurRepository utilisateurRepository;
    private final CoursMapper coursMapper;
    private final RelationReconstructor relationReconstructor;

    /**
     * Constructeur de CoursRepository
     * @param jpaRepository
     * @param utilisateurRepository
     */
    public CoursRepository(JpaCoursRepository jpaRepository, IUtilisateurRepository utilisateurRepository, CoursMapper coursMapper, RelationReconstructor relationReconstructor)
    {
        this.jpaRepository = jpaRepository;
        this.utilisateurRepository = utilisateurRepository;
        this.coursMapper = coursMapper;
        this.relationReconstructor = relationReconstructor;
    }

    /**
     * Méthode pour trouver un cours par son id
     * @param id id du cours
     * @return l'objet Cours associé
     */
    @Override
    public Cours trouverParId(int id)
    {
        CoursEntity entity = jpaRepository.findById(id).orElse(null);
        if (entity == null)
        {
            throw new ExceptionCoursInexistant("Le cours n'existe pas", id);
        }
        Cours cours = coursMapper.toDomain(entity);

        relationReconstructor.reconstructCoursChapitres(cours);
        return cours;
    }

    /**
     * Méthode pour trouver les cours créés par un créateur via son id
     * @param idCreateur id du créateur
     * @return l'ArrayList des cours créés
     */
    @Override
    public ArrayList<Cours> trouverParIdCreateur(int idCreateur)
    {
        List<CoursEntity> entities = jpaRepository.findByCreateurIdUtilisateur(idCreateur);
        return toDomainCoursAvecRelations(entities);
    }

    /**
     * Méthode pour trouver les cours auxquels un élève est inscrit via son id
     * @param idEleve id de l'élève
     * @return l'ArrayList des cours inscrits
     */
    @Override
    public ArrayList<Cours> trouverParIdEleve(int idEleve)
    {
        List<CoursEntity> entities = jpaRepository.findByElevesIdUtilisateur(idEleve);
        return toDomainCoursAvecRelations(entities);
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
        CoursEntity entity = coursMapper.toEntity(cours);
        CoursEntity saved = jpaRepository.save(entity);
        Cours savedCours = coursMapper.toDomain(saved);

        relationReconstructor.reconstructCoursChapitres(savedCours);

        return savedCours;
    }

    /**
     * Methode permettant de supprimer un cours
     * @param coursId id du cours
     * @return l'objet Cours supprimé
     */
    @Override
    public Cours supprimerCours(int coursId){
        CoursEntity entity = jpaRepository.findById(coursId).orElse(null);

        if(entity == null)
            return null;
        jpaRepository.delete(entity);

        Cours cours = coursMapper.toDomain(entity);

        relationReconstructor.reconstructCoursChapitres(cours);

        return cours;
    }
    /**
     * Méthode pour trouver les cours publiés avec ou sans filtre
     * @return l'ArrayList des Cours publiés
     */
    @Override
    public ArrayList<Cours> trouverCoursPublies()
    {
        List<CoursEntity> entities = jpaRepository.findByEstPublieTrue();
        return toDomainCoursAvecRelations(entities);
    }

    /**
     * Méthode pour vérifier si un cours existe
     * @param id
     * @return true ou false
     */
    public boolean coursExiste(int id)
    {
        return this.jpaRepository.existsById(id);
    }

    /**
     * Méthode qui permet de sauvegarder un cours
     * @param cours
     */
    @Override
    public void sauvegarder(Cours cours)
    {
        CoursEntity entity = coursMapper.toEntity(cours);

        if (entity.getChapitres() != null)
        {
            for(int i = 0; i < cours.getChapitres().size(); i++)
            {
                Chapitre chapitre = cours.getChapitres().get(i);
                chapitre.setCours(cours);
            }
        }
        this.jpaRepository.save(entity);
    }

    private ArrayList<Cours> toDomainCoursAvecRelations(List<CoursEntity> entities)
    {
        ArrayList<Cours> coursList = new ArrayList<>();

        for (int i = 0; i < entities.size(); i++)
        {
            Cours cours = coursMapper.toDomain(entities.get(i));
            coursList.add(cours);
        }
        relationReconstructor.reconstructCoursChapitres(coursList);
        return coursList;
    }
}
