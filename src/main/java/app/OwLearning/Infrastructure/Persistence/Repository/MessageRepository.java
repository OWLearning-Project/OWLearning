package app.OwLearning.Infrastructure.Persistence.Repository;



import app.OwLearning.Domain.Models.Message;
import app.OwLearning.Domain.Ports.IRepository.IMessageRepository;
import app.OwLearning.Infrastructure.Persistence.Interface.JpaMessageRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Component
public class MessageRepository implements IMessageRepository
{
    private final JpaMessageRepository jpaMessageRepository;

    public MessageRepository(JpaMessageRepository jpaMessageRepository)
    {
        this.jpaMessageRepository = jpaMessageRepository;
    }

    @Override
    @Transactional
    public Message sauvegarder(Message message)
    {
        log.debug("Sauvegarde du message dans la BD");
        return jpaMessageRepository.save(message);
    }

    @Override
    public Message trouverParId(int id)
    {
        log.debug("Cherche le message associé a l'id {}", id);
        return jpaMessageRepository.findById(id).orElse(null);
    }

    @Override
    public List<Message> trouverParDiscussion(int id)
    {
        log.debug("Retrouve la liste de message associé a la discussion {}", id);
        return jpaMessageRepository.findByDiscussionIdOrderByDateCreationAsc(id);
    }

    @Override
    public void supprimer(int id)
    {
        log.debug("Supprime le message associé a l'id {}", id);
        jpaMessageRepository.deleteById(id);
    }
}
