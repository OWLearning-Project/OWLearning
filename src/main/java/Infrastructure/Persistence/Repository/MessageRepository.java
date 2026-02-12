package Infrastructure.Persistence.Repository;

import Domain.Models.Message;
import Domain.Models.Ressource;
import Domain.Ports.IRepository.IMessageRepository;
import Infrastructure.Persistence.Interface.JpaMessageRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

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
        return jpaMessageRepository.save(message);
    }

    @Override
    public Message trouverParId(int id)
    {
        return jpaMessageRepository.findById(id).orElse(null);
    }

    @Override
    public List<Message> trouverParDiscussion(int id)
    {
        return jpaMessageRepository.findByDiscussionIdOrderByDateCreationAsc(id);
    }

    @Override
    public void supprimer(int id)
    {
        jpaMessageRepository.deleteById(id);
    }
}
