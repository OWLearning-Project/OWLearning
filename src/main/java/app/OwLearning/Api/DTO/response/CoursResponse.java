package app.OwLearning.Api.DTO.response;

import app.OwLearning.Domain.Models.Categorie;
import app.OwLearning.Domain.Models.Difficulte;
import app.OwLearning.Domain.Models.Eleve;

import java.sql.Timestamp;
import java.util.List;

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

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTitre() { return titre; }
    public void setTitre(String titre) { this.titre = titre; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Timestamp getDateCreation() { return dateCreation; }
    public void setDateCreation(Timestamp dateCreation) { this.dateCreation = dateCreation; }

    public boolean isEstPrive() { return estPrive; }
    public void setEstPrive(boolean estPrive) { this.estPrive = estPrive; }

    public boolean isEstPublie() { return estPublie; }
    public void setEstPublie(boolean estPublie) { this.estPublie = estPublie; }

    public Difficulte getDifficulte() { return difficulte; }
    public void setDifficulte(Difficulte difficulte) { this.difficulte = difficulte; }

    public List<Categorie> getCategories() { return categories; }
    public void setCategories(List<Categorie> categories) { this.categories = categories; }

    public UtilisateurAuthentifieResponse getCreateur() { return createur; }
    public void setCreateur(UtilisateurAuthentifieResponse createur) { this.createur = createur; }

    public List<ChapitreResponse> getChapitres() { return chapitres; }
    public void setChapitres(List<ChapitreResponse> chapitres) { this.chapitres = chapitres; }

    public List<Eleve> getEleves() { return eleves; }
    public void setEleves(List<Eleve> eleves) { this.eleves = eleves; }
}
