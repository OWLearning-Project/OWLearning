package app.OwLearning.Infrastructure.Bdd;

import app.OwLearning.Infrastructure.Entités.ChapitreTermineEntity;
import app.OwLearning.Infrastructure.Entités.ChapitreTermineIdEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Interface JPA pour accéder à la table chapitre_termines
 */
public interface JpaChapitreTermineRepository extends JpaRepository<ChapitreTermineEntity, ChapitreTermineIdEntity>
{
    boolean existsByChapitreTermineIdIdChapitreAndIdEleve(int idChapitre, int idEleve);
}
