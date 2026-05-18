package app.OwLearning.Infrastructure.Mapper;

import app.OwLearning.Domaine.Entités.Ressource;
import app.OwLearning.Infrastructure.Entités.RessourceEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RessourceMapper
{
    @Mapping(source = "idRessource", target = "id_ressource")
    Ressource toDomain(RessourceEntity entity);

    List<Ressource> toDomain(List<RessourceEntity> entities);

    @Mapping(source = "id_ressource", target = "idRessource")
    RessourceEntity toEntity(Ressource ressource);

    List<RessourceEntity> toEntity(List<Ressource> entities);

    @Mapping(target = "idRessource", ignore = true)
    void updateEntityFromDomain(Ressource ressource, @MappingTarget RessourceEntity entity);
}
