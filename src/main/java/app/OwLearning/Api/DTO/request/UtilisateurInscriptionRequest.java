package app.OwLearning.Api.DTO.request;

/**
 * DTO de requête pour l'inscription d'un utilisateur
 */
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

    public String getNom()
    {
        return nom;
    }

    public String getPrenom()
    {
        return prenom;
    }

    public String getEmail()
    {
        return email;
    }

    public String getMotDePasse()
    {
        return motDePasse;
    }

    public String getRole()
    {
        return role;
    }
}
