package app.OwLearning.Infrastructure.Mapper;

import app.OwLearning.Domaine.Entités.ProgressionId;
import app.OwLearning.Infrastructure.Entités.ProgressionIdEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProgressionIdMapper
{
    ProgressionId toDomain(ProgressionIdEntity entity);

    ProgressionIdEntity toEntity(ProgressionId id);
}
