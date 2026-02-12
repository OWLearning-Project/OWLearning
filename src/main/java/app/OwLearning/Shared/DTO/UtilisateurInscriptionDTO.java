package app.OwLearning.Shared.DTO;

public class UtilisateurInscriptionDTO
{
    private String nom;
    private String prenom;
    private String email;
    private String motDePasse;
    private String role;

    public UtilisateurInscriptionDTO() {}

    public UtilisateurInscriptionDTO(String nom, String prenom, String email, String motDePasse, String role)
    {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.motDePasse = motDePasse;
        this.role = role;
    }

    public String getNom()
    {
        return this.nom;
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
        return this.motDePasse;
    }

    public String getRole()
    {
        return this.role;
    }

}
