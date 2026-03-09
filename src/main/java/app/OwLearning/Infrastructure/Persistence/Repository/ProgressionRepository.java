package app.OwLearning.Infrastructure.Persistence.Repository;

import app.OwLearning.Domain.Ports.IRepository.IProgressionRepository;
import app.OwLearning.Infrastructure.Persistence.Interface.JpaProgressionRepository;
import app.OwLearning.Domain.Models.Progression;
import app.OwLearning.Domain.Models.ProgressionId;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
/**
 * Classe ProgressionRepository pour récupérer la progression
 */
@Component
public class ProgressionRepository implements IProgressionRepository {

    private final JpaProgressionRepository jpaProgressionRepository;

    /**
     * Constructeur de ProgressionRepository
     * @param unJpaProgressionRepository
     */
    public ProgressionRepository(JpaProgressionRepository unJpaProgressionRepository)
    {
        this.jpaProgressionRepository = unJpaProgressionRepository;
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
        ProgressionId id = new ProgressionId(coursId, elevId);
        return jpaProgressionRepository.findById(id).orElse(null);
    }
}