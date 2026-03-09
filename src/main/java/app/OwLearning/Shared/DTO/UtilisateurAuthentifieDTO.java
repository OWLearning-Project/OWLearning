package app.OwLearning.Shared.DTO;

/**
 * Représente les informations de l'utilisateur authentifié, extraites du JWT et stockées dans le SecurityContext
 */
public class UtilisateurAuthentifieDTO
{
    private final int id;
    private final String email;
    private final String role;

    public UtilisateurAuthentifieDTO(int id, String email, String role)
    {
        this.id = id;
        this.email = email;
        this.role = role;
    }

    public int getId()
    {
        return id;
    }
    public String getEmail()
    {
        return email;
    }
    public String getRole()
    {
        return role;
    }
}
