package app.OwLearning.Infrastructure.Persistence.Repository;



import app.OwLearning.Domain.Models.Message;
import app.OwLearning.Domain.Ports.IRepository.IMessageRepository;
import app.OwLearning.Infrastructure.Persistence.Entity.MessageEntity;
import app.OwLearning.Infrastructure.Persistence.Interface.JpaMessageRepository;
import app.OwLearning.Infrastructure.Persistence.Mapper.MessageMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Component
public class MessageRepository implements IMessageRepository
{
    private final JpaMessageRepository jpaMessageRepository;
    private final MessageMapper messageMapper;

    public MessageRepository(JpaMessageRepository jpaMessageRepository, MessageMapper messageMapper)
    {
        this.jpaMessageRepository = jpaMessageRepository;
        this.messageMapper = messageMapper;
    }

    @Override
    @Transactional
    public Message sauvegarder(Message message)
    {
        MessageEntity entity = messageMapper.toEntity(message);
        MessageEntity saved = jpaMessageRepository.save(entity);
        return messageMapper.toDomain(saved);
    }

    @Override
    public Message trouverParId(int id)
    {
        return jpaMessageRepository.findById(id).map(messageMapper::toDomain).orElse(null);
    }

    @Override
    public List<Message> trouverParDiscussion(int id)
    {
        return jpaMessageRepository.findByDiscussionIdOrderByDateCreationAsc(id)
                .stream()
                .map(messageMapper::toDomain)
                .toList();
    }

    @Override
    public void supprimer(int id)
    {
        jpaMessageRepository.deleteById(id);
    }
}
