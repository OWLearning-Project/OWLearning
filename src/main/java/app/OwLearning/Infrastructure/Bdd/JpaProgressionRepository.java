package app.OwLearning.Infrastructure.Bdd;

import app.OwLearning.Infrastructure.Entités.ProgressionEntity;
import app.OwLearning.Infrastructure.Entités.ProgressionIdEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Interface JpaProgressionRepository permettant de récupérer la progression dans la bd
 */
@Repository
public interface JpaProgressionRepository extends JpaRepository<ProgressionEntity, ProgressionIdEntity> {
}
