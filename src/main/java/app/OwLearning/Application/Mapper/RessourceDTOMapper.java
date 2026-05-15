package app.OwLearning.Application.Mapper;

import app.OwLearning.Api.DTO.request.RessourceRequest;
import app.OwLearning.Api.DTO.response.RessourceResponse;
import app.OwLearning.Domain.Models.Ressource;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RessourceDTOMapper {

    @Mapping(target = "idRessource", ignore = true)
    Ressource toDomain(RessourceRequest resquest);

    @Mapping(target = "nom", source = "nom")
    RessourceRequest toRequest(Ressource ressource);

    @Mapping(target = "id", source = "idRessource")
    RessourceResponse toResponse(Ressource ressource);

    List<RessourceRequest> toDTOList(List<Ressource> ressources);
}
