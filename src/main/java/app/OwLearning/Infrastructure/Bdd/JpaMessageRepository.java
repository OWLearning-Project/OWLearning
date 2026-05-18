package app.OwLearning.Infrastructure.Bdd;

import app.OwLearning.Infrastructure.Entités.MessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JpaMessageRepository extends JpaRepository<MessageEntity, Integer>
{
    List<MessageEntity> findByDiscussionIdOrderByDateCreationAsc(int idDiscussion);
}
