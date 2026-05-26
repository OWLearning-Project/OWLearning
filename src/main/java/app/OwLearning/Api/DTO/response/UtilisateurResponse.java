package app.OwLearning.Api.DTO.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

/**
 * DTO de réponse pour afficher le profil d'un utilisateur.
 */
@Getter
@Setter
@NoArgsConstructor
public class UtilisateurResponse
{
    private int id;
    private String nom;
    private String prenom;
    private String pseudo;
    private String email;
    private String role;
    private Timestamp dateInscription;
    private Timestamp derniereActivite;
}
