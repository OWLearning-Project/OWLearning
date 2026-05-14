package app.OwLearning.Api.DTO.request;

/**
 * DTO de requête pour la connexion d'un utilisateur
 */
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

    public String getEmail()
    {
        return email;
    }

    public String getMotDePasse()
    {
        return motDePasse;
    }
}
