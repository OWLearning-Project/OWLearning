package app.OwLearning.Api.Mapper;

import app.OwLearning.Api.DTO.request.RessourceRequest;
import app.OwLearning.Api.DTO.response.RessourceResponse;
import app.OwLearning.Domaine.Entités.Ressource;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RessourceDTOMapper {

    @Mapping(target = "id_ressource", source = "id")
    Ressource toDomain(RessourceRequest resquest);

    @Mapping(target = "id", source = "id_ressource")
    @Mapping(target = "nom", source = "nom")
    RessourceRequest toRequest(Ressource ressource);

    @Mapping(target = "id", source = "id_ressource")
    RessourceResponse toResponse(Ressource ressource);

    List<RessourceRequest> toDTOList(List<Ressource> ressources);
}
