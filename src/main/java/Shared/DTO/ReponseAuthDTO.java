package Shared.DTO;

// Objet renvoyé par le Backend après une connexion réussi
public class ReponseAuthDTO 
{
    private String token;
    private String type = "Bearer";
    private String pseudo;
    private String role;

    public ReponseAuthDTO()
    {}

    public ReponseAuthDTO(String token, String pseudo, String role)
    {
        this.token = token;
        this.pseudo = pseudo;
        this.role = role;
    }

    public String getToken()
    {
        return this.token;
    }

    public String getType() 
    { 
        return this.type; 
    }

    public String getPseudo()
    {
        return this.pseudo;
    }

    public void setPseudo(String unPseudo)
    {
        this.pseudo = unPseudo;
    }
    
    public String getRole()
    {
        return this.role;
    }

    public void setRole(String unRole)
    {
        this.role = unRole;
    }

}


