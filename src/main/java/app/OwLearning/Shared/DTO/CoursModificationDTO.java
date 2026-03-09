package app.OwLearning.Shared.DTO;

import app.OwLearning.Domain.Models.Difficulte;

/**
 * DTO pour la modification d'un cours
 */
public class CoursModificationDTO {
    private String titre;
    private String description;
    private Difficulte difficulte;
    private boolean estPrive;

    public CoursModificationDTO(){}

    public CoursModificationDTO(String titre, String description, Difficulte difficulte, boolean estPrive)
    {
        this.titre = titre;
        this.description = description;
        this.difficulte = difficulte;
        this.estPrive = estPrive;
    }

    public String getTitre() { return titre; }
    public String getDescription() { return description; }
    public Difficulte getDifficulte() {
        return this.difficulte;
    }
    public boolean getEstPrive(){
        return this.estPrive;
    }
}
