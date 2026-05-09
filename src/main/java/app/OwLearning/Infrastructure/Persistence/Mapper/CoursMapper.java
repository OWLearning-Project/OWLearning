package app.OwLearning.Infrastructure.Persistence.Mapper;

import app.OwLearning.Domain.Models.Cours;
import app.OwLearning.Infrastructure.Persistence.Entity.CoursEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring", uses = {ChapitreMapper.class, CreateurMapper.class, EleveMapper.class})
public interface CoursMapper
{
    Cours toDomain(CoursEntity entity);
    List<Cours> toDomain(List<CoursEntity> entities);


    CoursEntity toEntity(Cours cours);
    List<CoursEntity> toEntity(List<Cours> cours);


    @Mapping(target = "id", ignore = true)
    @Mapping(target="dateCreation", ignore = true)
    void updateEntityFromDomain(Cours cours, @MappingTarget CoursEntity entity);
}
