package app.OwLearning.Services.Mapper;

import app.OwLearning.Api.DTO.response.DiscussionResponse;
import app.OwLearning.Domaine.Entités.Discussion;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {MessageDTOMapper.class, UtilisateurDTOMapper.class})
public interface DiscussionDTOMapper {
    @Mapping(target = "id", source = "idDiscussion")
    DiscussionResponse toResponse(Discussion discussion);

    List<DiscussionResponse> toResponseList(List<Discussion> discussions);
}
