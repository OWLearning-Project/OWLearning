package app.OwLearning.Infrastructure.Persistence.Mapper;

import app.OwLearning.Domain.Models.Eleve;
import app.OwLearning.Infrastructure.Persistence.Entity.EleveEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EleveMapper
{
    Eleve toDomain(EleveEntity entity);
    List<Eleve> toDomain(List<EleveEntity> entities);

    EleveEntity toEntity(Eleve eleve);
    List<EleveEntity> toEntity(List<Eleve> eleves);

    @Mapping(target = "idUtilisateur", ignore = true)
    @Mapping(target = "dateInscription", ignore = true)
    void updateEntityFromDomain(Eleve eleve, @MappingTarget EleveEntity entity);
}
