package app.OwLearning.Api.DTO.response;

import app.OwLearning.Domain.Models.Categorie;
import app.OwLearning.Domain.Models.Difficulte;
import app.OwLearning.Domain.Models.Eleve;
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
    private UtilisateurAuthentifieResponse createur;
    private List<ChapitreResponse> chapitres;
    private List<Eleve> eleves;


    public CoursResponse() {}

}
