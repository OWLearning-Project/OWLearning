package Shared.DTO;

public class RegisterRequestDTO 
{
    private String nom;
    private String prenom;
    private String email;
    private String motDePasse;
    private String role;
    public RegisterRequestDTO() {
    }

    public RegisterRequestDTO(String nom, String prenom, String email, String motDePasse, String role) {
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
    public void setNom(String unNom) 
    { 
        this.nom = unNom; 
    }

    public String getPrenom() 
    { 
        return prenom; 
    }
    public void setPrenom(String unPrenom) 
    {
         this.prenom = unPrenom;
    }

    public String getEmail() 
    { 
        return email; 
    }
    public void setEmail(String unEmail) 
    { 
        this.email = unEmail; 
    }

    public String getMotDePasse() 
    { 
        return motDePasse; 
    }
    public void setMotDePasse(String unMotDePasse) 
    { 
        this.motDePasse = unMotDePasse; 
    }

    public String getRole() 
    { 
        return role; 
    }
    public void setRole(String unRole) 
    { 
        this.role = unRole; 
    }
}
