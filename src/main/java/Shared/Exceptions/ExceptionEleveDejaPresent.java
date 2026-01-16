package Shared.Exceptions;

public class ExceptionEleveDejaPresent extends RuntimeException
{
    private int idEleve;
    private int idCours;
    public ExceptionEleveDejaPresent(String message, int idEleve, int idCours)
    {
        super(message);
        this.idEleve = idEleve;
        this.idCours = idCours;
    }

    public String toString()
    {
        return  super.toString() + " L'élève d'id: " + this.idEleve +
                " est déjà associé au cours d'id : " + this.idCours;
    }
}
