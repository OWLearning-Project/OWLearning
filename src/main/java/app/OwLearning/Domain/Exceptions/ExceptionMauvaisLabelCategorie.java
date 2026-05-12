package app.OwLearning.Domain.Exceptions;

public class ExceptionMauvaisLabelCategorie extends Exception
{
    private String label;
    private int idCours;
    public ExceptionMauvaisLabelCategorie(String message, String label, int idCours)
    {
        super(message);
        this.label = label;
        this.idCours = idCours;
    }

    public String toString()
    {
        return  super.toString() + " La categorie : " + this.label +
                " n'est pas associée au cours d'id : " + this.idCours;
    }
}
