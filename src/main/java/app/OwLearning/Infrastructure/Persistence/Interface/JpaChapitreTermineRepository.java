package app.OwLearning.Infrastructure.Persistence.Interface;

import app.OwLearning.Infrastructure.Persistence.Entity.ChapitreTermineEntity;
import app.OwLearning.Infrastructure.Persistence.Entity.ChapitreTermineIdEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Interface JPA pour accéder à la table chapitre_termines
 */
public interface JpaChapitreTermineRepository extends JpaRepository<ChapitreTermineEntity, ChapitreTermineIdEntity>
{
    boolean existsByChapitreTermineIdIdChapitreAndIdEleve(int idChapitre, int idEleve);
}
