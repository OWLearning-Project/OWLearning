package Shared.DTO;

public class UtilisateurConnexionDTO
{
    private String email;
    private  String motDePasse;

    public UtilisateurConnexionDTO()
    {}

    public UtilisateurConnexionDTO(String email,String motDePasse)
    {
        this.email = email;
        this.motDePasse = motDePasse;
    }
    public String getEmail()
    {
        return this.email;
    }
    public String getMotDePasse()
    {
        return this.motDePasse;
    }
}
