package app.OwLearning.Api.Mapper;

import app.OwLearning.Api.DTO.response.UtilisateurResponse;
import app.OwLearning.Domaine.Entités.Utilisateur;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UtilisateurDTOMapper {

    @Mapping(target = "id", source ="idUtilisateur")
    @Mapping(target = "role", expression = "java(utilisateur.getRole())")
    UtilisateurResponse toResponse(Utilisateur utilisateur);

    List<UtilisateurResponse> toResponseList(List<Utilisateur> utilisateurs);

}
