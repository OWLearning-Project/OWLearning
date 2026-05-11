package app.OwLearning.Infrastructure.Persistence.Mapper;

import app.OwLearning.Domain.Models.Createur;
import app.OwLearning.Infrastructure.Persistence.Entity.CreateurEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CreateurMapper
{
    Createur toDomain(CreateurEntity entity);
    List<Createur> toDomain(List<CreateurEntity> entities);

    CreateurEntity toEntity(Createur createur);
    List<CreateurEntity> toEntity(List<Createur> createurs);

    @Mapping(target = "idUtilisateur", ignore = true)
    @Mapping(target = "dateInscription", ignore = true)
    void updateEntityFromDomain(Createur createur, @MappingTarget CreateurEntity entity);
}
