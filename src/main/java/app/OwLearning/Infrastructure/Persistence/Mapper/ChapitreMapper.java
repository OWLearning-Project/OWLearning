package app.OwLearning.Infrastructure.Persistence.Mapper;

import app.OwLearning.Domain.Models.Chapitre;
import app.OwLearning.Infrastructure.Persistence.Entity.ChapitreEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel="spring", uses = {RessourceMapper.class})
public interface ChapitreMapper
{
    @Mapping(target = "cours", ignore = true)
    Chapitre toDomain(ChapitreEntity entity);
    List<Chapitre> toDomain(List<ChapitreEntity> entities);

    @Mapping(target = "cours", ignore = true)
    ChapitreEntity toEntity(Chapitre chapitre);
    List<ChapitreEntity> toEntity(List<Chapitre> chapitres);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "cours", ignore = true)
    void updateEntityFromDomain(Chapitre chapitre, @MappingTarget ChapitreEntity entity);


}
