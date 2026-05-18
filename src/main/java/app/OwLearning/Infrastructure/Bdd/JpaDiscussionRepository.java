package app.OwLearning.Infrastructure.Bdd;

import app.OwLearning.Infrastructure.Entités.DiscussionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Interface JpaDiscussionRepository permettant de récupérer les discussions dans la bd
 */
@Repository
public interface JpaDiscussionRepository extends JpaRepository<DiscussionEntity, Integer>
{
    public List<DiscussionEntity> findByParticipantsIdUtilisateur(int id);
}
