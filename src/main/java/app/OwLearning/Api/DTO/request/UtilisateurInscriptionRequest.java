package app.OwLearning.Api.DTO.request;

import lombok.Getter;
import lombok.Setter;

/**
 * DTO de requête pour l'inscription d'un utilisateur
 */
@Getter
@Setter
public class UtilisateurInscriptionRequest
{
    private String nom;
    private String prenom;
    private String email;
    private String motDePasse;
    private String role;

    public UtilisateurInscriptionRequest() {}

    public UtilisateurInscriptionRequest(String nom,
                                         String prenom,
                                         String email,
                                         String motDePasse,
                                         String role)
    {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.motDePasse = motDePasse;
        this.role = role;
    }


}
