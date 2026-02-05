package Shared.DTO;

public class UtilisateurInscriptionDTO
{
    private String nom;
    private String prenom;
    private String email;
    private String mdpHash;
    private String role;

    public UtilisateurInscriptionDTO() {}

    public UtilisateurInscriptionDTO(String nom, String prenom, String email, String mdpHash, String role)
    {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.mdpHash = mdpHash;
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

    public String getMdp()
    {
        return this.mdpHash;
    }

    public String getRole()
    {
        return this.role;
    }
}
