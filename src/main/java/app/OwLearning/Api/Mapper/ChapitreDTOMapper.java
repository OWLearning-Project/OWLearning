package app.OwLearning.Api.Mapper;

import app.OwLearning.Api.DTO.request.ChapitreRequest;
import app.OwLearning.Api.DTO.response.ChapitreResponse;
import app.OwLearning.Domaine.Entités.Chapitre;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {RessourceDTOMapper.class})
public interface ChapitreDTOMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "cours", ignore = true)
    @Mapping(target = "ressources", ignore = true)
    Chapitre toDomain(ChapitreRequest request);

    @Mapping(target = "id", source = "id")
    ChapitreResponse toResponse(Chapitre chapitre);

    List<ChapitreResponse> toResponseList(List<Chapitre> chapitres);
}
