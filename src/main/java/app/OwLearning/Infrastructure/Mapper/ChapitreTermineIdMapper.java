package app.OwLearning.Infrastructure.Mapper;

import app.OwLearning.Domaine.Entités.ChapitreTermineId;
import app.OwLearning.Infrastructure.Entités.ChapitreTermineIdEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ChapitreTermineIdMapper
{
    ChapitreTermineId toDomain(ChapitreTermineIdEntity entity);

    ChapitreTermineIdEntity toEntity(ChapitreTermineId id);
}
