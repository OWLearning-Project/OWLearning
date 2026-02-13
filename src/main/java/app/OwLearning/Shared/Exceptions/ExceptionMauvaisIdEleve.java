package app.OwLearning.Shared.Exceptions;

public class ExceptionMauvaisIdEleve extends Exception
{
    private int idEleve;
    private int idCours;
    public ExceptionMauvaisIdEleve(String message, int idEleve, int idCours)
    {
        super(message);
        this.idEleve = idEleve;
        this.idCours = idCours;
    }

    public String toString()
    {
        return  super.toString() + " L'eleve d'id : " + this.idEleve +
                " n'est pas inscrit dans le cours d'id : " + this.idCours;
    }
}
