package Shared.Exceptions;

public class ExceptionMauvaisIdCategorie extends Exception
{
    private int idCategorie;
    private int idCours;
    public ExceptionMauvaisIdCategorie(String message, int idCategorie, int idCours)
    {
        super(message);
        this.idCategorie = idCategorie;
        this.idCours = idCours;
    }

    public String toString()
    {
        return  super.toString() + " La categorie d'id : " + this.idCategorie +
                " n'est pas associée au cours d'id : " + this.idCours;
    }
}
