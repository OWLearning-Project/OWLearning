package app.OwLearning.Infrastructure.Mapper;

import app.OwLearning.Domaine.Entités.Createur;
import app.OwLearning.Domaine.Entités.Eleve;
import app.OwLearning.Domaine.Entités.Utilisateur;
import app.OwLearning.Infrastructure.Entités.CreateurEntity;
import app.OwLearning.Infrastructure.Entités.EleveEntity;
import app.OwLearning.Infrastructure.Entités.UtilisateurEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.SubclassMapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UtilisateurMapper {

    @SubclassMapping(source = EleveEntity.class, target = Eleve.class)
    @SubclassMapping(source = CreateurEntity.class, target = Createur.class)
    Utilisateur toDomain(UtilisateurEntity entity);

    @SubclassMapping(source = Eleve.class, target = EleveEntity.class)
    @SubclassMapping(source = Createur.class, target = CreateurEntity.class)
    UtilisateurEntity toEntity(Utilisateur utilisateur);

    List<Utilisateur> toDomain(List<UtilisateurEntity> entities);
    List<UtilisateurEntity> toEntity(List<Utilisateur> utilisateurs);

    @Mapping(target = "idUtilisateur", ignore = true)
    @Mapping(target = "dateInscription", ignore = true)
    void updateEntityFromDomain(Utilisateur utilisateur, @MappingTarget UtilisateurEntity entity);
}
