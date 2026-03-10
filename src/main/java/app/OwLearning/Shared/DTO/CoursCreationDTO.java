package app.OwLearning.Shared.DTO;

import app.OwLearning.Domain.Models.Difficulte;
import org.apache.commons.lang3.builder.Diff;

/**
 * DTO pour la création d'un cours
 */
public class CoursCreationDTO {
    private String titre;
    private String description;
    private Difficulte difficulte;

    public CoursCreationDTO() {
    }

    public CoursCreationDTO(String titre, String description, Difficulte difficulte) {
        this.titre = titre;
        this.description = description;
        this.difficulte = difficulte;
    }

    public String getTitre() {
        return titre;
    }

    public String getDescription() {
        return description;
    }

    public Difficulte getDifficulte() {
        return difficulte;
    }
}