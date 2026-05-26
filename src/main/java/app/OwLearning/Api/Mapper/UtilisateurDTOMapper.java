package app.OwLearning.Api.Mapper;

import app.OwLearning.Api.DTO.response.CreateurResponse;
import app.OwLearning.Api.DTO.response.EleveResponse;
import app.OwLearning.Api.DTO.response.UtilisateurResponse;
import app.OwLearning.Domaine.Entités.Createur;
import app.OwLearning.Domaine.Entités.Eleve;
import app.OwLearning.Domaine.Entités.Utilisateur;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UtilisateurDTOMapper
{
    public UtilisateurResponse toResponse(Utilisateur utilisateur)
    {
        if (utilisateur == null)
        {
            return null;
        }

        if (utilisateur instanceof Eleve)
        {
            return toEleveResponse((Eleve) utilisateur);
        }
        if (utilisateur instanceof Createur)
        {
            return toCreateurResponse((Createur) utilisateur);
        }
        return remplirChampsCommuns(utilisateur, new UtilisateurResponse());
    }

    public List<UtilisateurResponse> toResponseList(List<? extends Utilisateur> utilisateurs)
    {
        if (utilisateurs == null)
        {
            return List.of();
        }
        return utilisateurs.stream()
                .map(this::toResponse)
                .toList();
    }

    private EleveResponse toEleveResponse(Eleve eleve)
    {
        EleveResponse response = remplirChampsCommuns(eleve, new EleveResponse());
        response.setAge(eleve.getAge());
        response.setNiveauEtude(eleve.getNiveauEtude());
        return response;
    }

    private CreateurResponse toCreateurResponse(Createur createur)
    {
        return remplirChampsCommuns(createur, new CreateurResponse());
    }

    private <T extends UtilisateurResponse> T remplirChampsCommuns(Utilisateur utilisateur, T response)
    {
        response.setId(utilisateur.getIdUtilisateur());
        response.setNom(utilisateur.getNom());
        response.setPrenom(utilisateur.getPrenom());
        response.setPseudo(utilisateur.getPseudo());
        response.setEmail(utilisateur.getEmail());
        response.setRole(utilisateur.getRole());
        response.setDateInscription(utilisateur.getDateInscription());
        response.setDerniereActivite(utilisateur.getDerniereActivite());
        return response;
    }
}
