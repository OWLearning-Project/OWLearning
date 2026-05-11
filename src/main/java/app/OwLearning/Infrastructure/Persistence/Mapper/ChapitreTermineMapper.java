package app.OwLearning.Infrastructure.Persistence.Mapper;

import app.OwLearning.Domain.Models.ChapitreTermine;
import app.OwLearning.Infrastructure.Persistence.Entity.ChapitreTermineEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring", uses = {ChapitreTermineIdMapper.class, ChapitreMapper.class})
public interface ChapitreTermineMapper
{
    ChapitreTermine toDomain(ChapitreTermineEntity entity);
    List<ChapitreTermine> toDomain(List<ChapitreTermineEntity> entities);

    ChapitreTermineEntity toEntity(ChapitreTermine chapitreTermine);
    List<ChapitreTermineEntity> toEntity(List<ChapitreTermine> chapitresTermines);

    @Mapping(target = "chapitreTermineId", ignore = true)
    @Mapping(target = "chapitre", ignore = true)
    @Mapping(target = "idEleve", ignore = true)
    void updateEntityFromDomain(ChapitreTermine chapitreTermine, @MappingTarget ChapitreTermineEntity entity);
}
