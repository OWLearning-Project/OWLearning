package app.OwLearning.Infrastructure.Persistence.Interface;

import app.OwLearning.Domain.Models.ChapitreTermine;
import app.OwLearning.Domain.Models.ChapitreTermineId;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Interface JPA pour accéder à la table chapitre_termines
 */
public interface JpaChapitreTermineRepository extends JpaRepository<ChapitreTermine, ChapitreTermineId>
{
    boolean existsByChapitreTermineIdIdChapitreAndIdEleve(int idChapitre, int idEleve);
}
