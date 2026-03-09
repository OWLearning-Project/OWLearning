package app.OwLearning.Infrastructure.Persistence.Repository;


import app.OwLearning.Domain.Ports.IRepository.IChapitreRepository;
import app.OwLearning.Infrastructure.Persistence.Interface.JpaChapitreRepository;
import app.OwLearning.Domain.Models.Chapitre;
import app.OwLearning.Domain.Models.Ressource;
import org.springframework.stereotype.Component;

/**
 * Classe ChapitreRepository pour récupérer les chapitres
 */
@Component
public class ChapitreRepository implements IChapitreRepository {

    private final JpaChapitreRepository jpaRepository;

    /**
     * Constructeur de ChapitreRepository
     * @param jpaRepository
     */
    public ChapitreRepository(JpaChapitreRepository jpaRepository){
        this.jpaRepository = jpaRepository;
    }

    /**
     * Sauvegarde un chapitre dans la bd
     * @param chapitre à sauvegarder
     * @return l'id crée auquel le chapitre est inscrit dans la bd
     */
    public int sauvegarder(Chapitre chapitre){
        Chapitre chapitreSave = this.jpaRepository.save(chapitre);
        return chapitreSave.getId();
    }

    /**
     * Recherche un chapitre par son id
     * @param id du chapitre
     * @return chapitre si trouvé. Sinon null si non trouvé.
     */
    public Chapitre trouverParId(int id){
        return this.jpaRepository.findById(id).orElse(null);
    }

    /**
     * Supprimé le chapitre
     * @param id du chapitre
     * @return le chapitre qui a été supprimé
     */
    public Chapitre supprimerParId(int id){
        Chapitre chapitreSupr = this.jpaRepository.findById(id).orElse(null);
        if(chapitreSupr!=null)
            this.jpaRepository.delete(chapitreSupr);
        return chapitreSupr;
    }

    /**
     * Vérification de l'existence du chapitre
     * @param id id du chapitre
     * @return True si le chapitre existe, sinon false s'il n'existe pas
     */
     public boolean existe(int id){
         return this.jpaRepository.existsById(id);
     }
}
