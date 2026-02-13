package app.OwLearning.Shared.Exceptions;

public class ExceptionMauvaisIdChapitre extends Exception
{
    private int idChapitre;
    private int idCours;
    public ExceptionMauvaisIdChapitre(String message, int idChapitre, int idCours)
    {
        super(message);
        this.idChapitre = idChapitre;
        this.idCours = idCours;
    }

    public String toString()
    {
        return  super.toString() + " Le chapitre d'id : " + this.idChapitre +
                " n'est pas dans le cours d'id : " + this.idCours;
    }
}
