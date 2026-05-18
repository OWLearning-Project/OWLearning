package app.OwLearning.Infrastructure.Mapper;

import app.OwLearning.Domaine.Entités.Progression;
import app.OwLearning.Infrastructure.Entités.ProgressionEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring", uses = {ProgressionIdMapper.class, CoursMapper.class, EleveMapper.class})
public interface ProgressionMapper
{
    Progression toDomain(ProgressionEntity entity);
    List<Progression> toDomain(List<ProgressionEntity> entities);

    ProgressionEntity toEntity(Progression progression);
    List<ProgressionEntity> toEntity(List<Progression> progressions);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "cours", ignore = true)
    @Mapping(target = "eleve", ignore = true)
    void updateEntityFromDomain(Progression progression, @MappingTarget ProgressionEntity entity);
}
