package app.OwLearning.Api.DTO.request;

import lombok.Getter;
import lombok.Setter;

/**
 * DTO de requete représentant un utilisateur authentifié
 */
@Getter
@Setter
public class UtilisateurAuthentifieRequest
{
    private final int id;
    private final String email;
    private final String role;

    public UtilisateurAuthentifieRequest(int id,
                                          String email,
                                          String role)
    {
        this.id = id;
        this.email = email;
        this.role = role;
    }


}
