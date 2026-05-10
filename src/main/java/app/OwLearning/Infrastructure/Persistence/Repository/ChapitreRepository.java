package app.OwLearning.Infrastructure.Persistence.Repository;


import app.OwLearning.Domain.Ports.IRepository.IChapitreRepository;
import app.OwLearning.Infrastructure.Persistence.Entity.ChapitreEntity;
import app.OwLearning.Infrastructure.Persistence.Interface.JpaChapitreRepository;
import app.OwLearning.Domain.Models.Chapitre;
import app.OwLearning.Infrastructure.Persistence.Mapper.ChapitreMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Classe ChapitreRepository pour récupérer les chapitres
 */
@Component
public class ChapitreRepository implements IChapitreRepository {

    private final JpaChapitreRepository jpaRepository;
    private final ChapitreMapper chapitreMapper;

    /**
     * Constructeur de ChapitreRepository
     * @param jpaRepository
     */
    public ChapitreRepository(JpaChapitreRepository jpaRepository, ChapitreMapper chapitreMapper)
    {
        this.jpaRepository = jpaRepository;
        this.chapitreMapper = chapitreMapper;
    }

    /**
     * Sauvegarde un chapitre dans la bd
     * @param chapitre à sauvegarder
     * @return l'id crée auquel le chapitre est inscrit dans la bd
     */
    public int sauvegarder(Chapitre chapitre)
    {
        ChapitreEntity chapitreEntity = chapitreMapper.toEntity(chapitre);
        ChapitreEntity savedChapitreEntity = jpaRepository.save(chapitreEntity);
        return savedChapitreEntity.getId();
    }

    /**
     * Recherche un chapitre par son id
     * @param id du chapitre
     * @return chapitre si trouvé. Sinon null si non trouvé.
     */
    public Chapitre trouverParId(int id)
    {
        return this.jpaRepository.findById(id).map(chapitreMapper::toDomain).orElse(null);
    }

    /**
     * Supprimé le chapitre
     * @param id du chapitre
     * @return le chapitre qui a été supprimé
     */
    public Chapitre supprimerParId(int id)
    {
        ChapitreEntity entity = jpaRepository.findById(id).orElse(null);
        if(entity!=null)
        {
            this.jpaRepository.delete(entity);
            return chapitreMapper.toDomain(entity);
        }
        return null;
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
