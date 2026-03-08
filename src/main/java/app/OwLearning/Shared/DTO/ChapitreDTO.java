package app.OwLearning.Shared.DTO;

public class ChapitreDTO {

    private String titre;
    private String description;

    public ChapitreDTO(){}

    public String getTitre(){
        return this.titre;
    }
    public void setTitre(String unTitre){
        this.titre = unTitre;
    }

    public String getDescription(){
        return this.description;
    }
    public void setDescription(String uneDescription){
        this.description = uneDescription;
    }
}
