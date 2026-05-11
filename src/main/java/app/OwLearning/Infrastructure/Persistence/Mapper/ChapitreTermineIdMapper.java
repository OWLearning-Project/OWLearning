package app.OwLearning.Infrastructure.Persistence.Mapper;

import app.OwLearning.Domain.Models.ChapitreTermineId;
import app.OwLearning.Infrastructure.Persistence.Entity.ChapitreTermineIdEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ChapitreTermineIdMapper
{
    ChapitreTermineId toDomain(ChapitreTermineIdEntity entity);

    ChapitreTermineIdEntity toEntity(ChapitreTermineId id);
}
