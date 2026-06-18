package app.OwLearning.Api.Mapper;

import app.OwLearning.Api.DTO.response.MessageResponse;
import app.OwLearning.Domaine.Entités.Message;
import app.OwLearning.Api.DTO.response.RessourceResponse;
import app.OwLearning.Domaine.Entités.Ressource;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MessageDTOMapper {
    @Mapping(target = "id", source = "id_message")
    @Mapping(target = "idDiscussion", source = "discussion.idDiscussion")
    @Mapping(target = "idUtilisateur", source = "utilisateur.idUtilisateur")
    MessageResponse toResponse(Message message);

    List<MessageResponse> toResponseList(List<Message> messages);

    @Mapping(target = "id", source = "id_ressource")
    RessourceResponse toRessourceResponse(Ressource ressource);
}
