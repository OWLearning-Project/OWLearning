package app.OwLearning.Api.Mapper;

import app.OwLearning.Api.DTO.request.CoursCreationRequest;
import app.OwLearning.Api.DTO.response.CoursResponse;
import app.OwLearning.Domaine.Entités.Cours;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring", uses = {ChapitreDTOMapper.class, UtilisateurDTOMapper.class})
public interface CoursDTOMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dateCreation", ignore = true)
    @Mapping(target = "estPublie", ignore = true)
    @Mapping(target = "createur", ignore = true)
    @Mapping(target = "eleves", ignore = true)
    @Mapping(target = "chapitres", ignore = true)
    Cours toDomain(CoursCreationRequest request);

    CoursResponse toResponse(Cours cours);

    List<CoursResponse> toResponseList(List<Cours> cours);

    @Named("sansChapitres")
    @Mapping(target = "chapitres", ignore = true)
    CoursResponse toResponseSansChapitres(Cours cours);

    @IterableMapping(qualifiedByName = "sansChapitres")
    List<CoursResponse> toResponseListSansChapitres(List<Cours> cours);
}
