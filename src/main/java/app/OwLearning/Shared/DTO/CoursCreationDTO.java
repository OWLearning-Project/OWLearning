package app.OwLearning.Shared.DTO;

import app.OwLearning.Domain.Models.Difficulte;
import org.apache.commons.lang3.builder.Diff;

public class CoursCreationDTO {
    private String titre;
    private String description;
    private Difficulte difficulte;
    private int createurId;

    public String getTitre() { return titre; }
    public String getDescription() { return description; }
    public Difficulte getDifficulte() { return difficulte; }
    public int getCreateurId() { return createurId; }}
