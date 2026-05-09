package app.OwLearning.Infrastructure.Persistence.Mapper;

import app.OwLearning.Domain.Models.ProgressionId;
import app.OwLearning.Infrastructure.Persistence.Entity.ProgressionIdEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProgressionIdMapper
{
    ProgressionId toDomain(ProgressionIdEntity entity);

    ProgressionIdEntity toEntity(ProgressionId id);
}
