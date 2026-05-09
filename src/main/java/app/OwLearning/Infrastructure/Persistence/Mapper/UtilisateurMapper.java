package app.OwLearning.Infrastructure.Persistence.Mapper;

import app.OwLearning.Domain.Models.Utilisateur;
import app.OwLearning.Infrastructure.Persistence.Entity.UtilisateurEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UtilisateurMapper
{
    Utilisateur toDomain(UtilisateurEntity entity);
    List<Utilisateur> toDomain(List<UtilisateurEntity> entities);

    UtilisateurEntity toEntity(Utilisateur utilisateur);
    List<UtilisateurEntity> toEntity(List<Utilisateur> utilisateurs);

    @Mapping(target = "idUtilisateur", ignore = true)
    @Mapping(target = "dateInscription", ignore = true)
    void updateEntityFromDomain(Utilisateur utilisateur, @MappingTarget UtilisateurEntity entity);
}
