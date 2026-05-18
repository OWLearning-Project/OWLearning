package app.OwLearning.Api.DTO.request;

import lombok.Getter;
import lombok.Setter;

/**
 * DTO de requête pour la connexion d'un utilisateur
 */
@Getter
@Setter
public class UtilisateurConnexionRequest
{
    private String email;
    private String motDePasse;

    public UtilisateurConnexionRequest() {}

    public UtilisateurConnexionRequest(String email,
                                       String motDePasse)
    {
        this.email = email;
        this.motDePasse = motDePasse;
    }

}
