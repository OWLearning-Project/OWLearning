package app.OwLearning.Api.DTO.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO de réponse pour afficher le profil d'un élève.
 */
@Getter
@Setter
@NoArgsConstructor
public class EleveResponse extends UtilisateurResponse
{
    private int age;
    private String niveauEtude;
}
