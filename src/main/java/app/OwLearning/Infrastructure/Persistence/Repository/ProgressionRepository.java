package app.OwLearning.Infrastructure.Persistence.Repository;

import app.OwLearning.Domain.Ports.IRepository.IProgressionRepository;
import app.OwLearning.Infrastructure.Persistence.Interface.JpaProgressionRepository;
import app.OwLearning.Domain.Models.Progression;
import app.OwLearning.Infrastructure.Persistence.Entity.ProgressionIdEntity;
import app.OwLearning.Infrastructure.Persistence.Mapper.ProgressionMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
/**
 * Classe ProgressionRepository pour récupérer la progression
 */
@Component
public class ProgressionRepository implements IProgressionRepository {

    private final JpaProgressionRepository jpaProgressionRepository;
    private final ProgressionMapper progressionMapper;

    /**
     * Constructeur de ProgressionRepository
     * @param unJpaProgressionRepository
     */
    public ProgressionRepository(JpaProgressionRepository unJpaProgressionRepository, ProgressionMapper progressionMapper)
    {
        this.jpaProgressionRepository = unJpaProgressionRepository;
        this.progressionMapper = progressionMapper;
    }

    /**
     * Méthode pour trouver une progression grâce à un eleveId et un coursId
     *
     * @param elevId  id de l'élève
     * @param coursId id du cours
     * @return un objet Progression
     */
    @Override
    public Progression trouverParId(int elevId, int coursId) {
        ProgressionIdEntity id = new ProgressionIdEntity(coursId, elevId);
        return jpaProgressionRepository.findById(id).map(progressionMapper::toDomain).orElse(null);
    }
}
