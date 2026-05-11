package app.OwLearning.Infrastructure.Persistence.Mapper;

import app.OwLearning.Domain.Models.Message;
import app.OwLearning.Infrastructure.Persistence.Entity.MessageEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring", uses={RessourceMapper.class, UtilisateurMapper.class})
public interface MessageMapper
{
    @Mapping(source = "idMessage", target = "id_message")
    @Mapping(target = "discussion", ignore = true)
    Message toDomain(MessageEntity entity);
    List<Message> toDomain(List<MessageEntity> entities);

    @Mapping(source = "id_message", target = "idMessage")
    @Mapping(target = "discussion", ignore = true)
    MessageEntity toEntity(Message message);
    List<MessageEntity> toEntity(List<Message> messages);

    @Mapping(target = "idMessage", ignore = true)
    @Mapping(target = "dateCreation", ignore = true)
    @Mapping(target = "discussion", ignore = true)
    void updateEntityFromDomain(Message message, @MappingTarget MessageEntity entity);
}
