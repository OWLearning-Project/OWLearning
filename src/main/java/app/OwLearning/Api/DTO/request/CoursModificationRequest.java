package app.OwLearning.Api.DTO.request;

import app.OwLearning.Domaine.Enumérations.Difficulte;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO de requête pour la modification d'un cours
 */
@Getter
@Setter
public class CoursModificationRequest
{
    private String titre;
    private String description;
    private Difficulte difficulte;
    private boolean estPrive;

    public CoursModificationRequest() {}

    public CoursModificationRequest(String titre,
                                    String description,
                                    Difficulte difficulte,
                                    boolean estPrive)
    {
        this.titre = titre;
        this.description = description;
        this.difficulte = difficulte;
        this.estPrive = estPrive;
    }

}
