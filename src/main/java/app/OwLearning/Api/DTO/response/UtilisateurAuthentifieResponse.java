package app.OwLearning.Api.DTO.response;

import lombok.Getter;
import lombok.Setter;

/**
 * DTO de réponse représentant un utilisateur authentifié
 */
@Getter
@Setter
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


}
