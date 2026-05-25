package app.OwLearning.Api.Mapper;

import app.OwLearning.Api.DTO.response.UtilisateurAuthentifieResponse;
import app.OwLearning.Domaine.Entités.Utilisateur;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UtilisateurDTOMapper {

    @Mapping(target = "id", source ="idUtilisateur")
    @Mapping(target = "role", expression = "java(utilisateur.getRole())")
    UtilisateurAuthentifieResponse toResponse(Utilisateur utilisateur);

    List<UtilisateurAuthentifieResponse> toResponseList(List<Utilisateur> utilisateurs);

}
