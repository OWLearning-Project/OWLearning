package Shared.DTO;

// Objet pris en paramètre par le back pour se connecter
public class RequeteConnexionDTO 
{
    private String email;
    private String motDePasse;

    public RequeteConnexionDTO() {
    }

    public RequeteConnexionDTO(String email, String motDePasse) {
        this.email = email;
        this.motDePasse = motDePasse;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String unEmail) {
        this.email = unEmail;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String unMotDePasse) {
        this.motDePasse = unMotDePasse;
    }
}
