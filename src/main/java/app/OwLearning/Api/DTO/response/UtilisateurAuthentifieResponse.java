package app.OwLearning.Api.DTO.response;

/**
 * DTO de réponse représentant un utilisateur authentifié
 */
public class UtilisateurAuthentifieResponse
{
    private final int id;
    private final String email;
    private final String role;

    public UtilisateurAuthentifieResponse(int id,
                                          String email,
                                          String role)
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
