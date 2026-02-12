package Infrastructure.Persistence.Interface;

import Domain.Models.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JpaMessageRepository extends JpaRepository<Message, Integer>
{
    List<Message> findByDiscussionIdOrderByDateCreationAsc(int idDiscussion);
}
