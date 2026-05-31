package app.OwLearning.Api.DTO.response;

import app.OwLearning.Domaine.Enumérations.Categorie;
import app.OwLearning.Domaine.Enumérations.Difficulte;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
public class CoursResponse {
    private int id;
    private String titre;
    private String description;
    private Timestamp dateCreation;
    private boolean estPrive;
    private boolean estPublie;
    private Difficulte difficulte;
    private List<Categorie> categories;
    private UtilisateurResponse createur;
    private List<ChapitreResponse> chapitres;
    private List<UtilisateurResponse> eleves;


    public CoursResponse() {}

}
