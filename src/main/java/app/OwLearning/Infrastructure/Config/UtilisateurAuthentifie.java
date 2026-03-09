package app.OwLearning.Infrastructure.Config;

/**
 * Représente les informations de l'utilisateur authentifié, extraites du JWT et stockées dans le SecurityContext
 */
public class UtilisateurAuthentifie
{
    private final int id;
    private final String email;
    private final String role;

    public UtilisateurAuthentifie(int id, String email, String role)
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
