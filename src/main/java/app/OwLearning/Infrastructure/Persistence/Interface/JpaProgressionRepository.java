package app.OwLearning.Infrastructure.Persistence.Interface;

import app.OwLearning.Infrastructure.Persistence.Entity.ProgressionEntity;
import app.OwLearning.Infrastructure.Persistence.Entity.ProgressionIdEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Interface JpaProgressionRepository permettant de récupérer la progression dans la bd
 */
@Repository
public interface JpaProgressionRepository extends JpaRepository<ProgressionEntity, ProgressionIdEntity> {
}
