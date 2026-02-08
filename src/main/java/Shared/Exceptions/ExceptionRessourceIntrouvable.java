package Shared.Exceptions;

public class ExceptionRessourceIntrouvable extends RuntimeException
{
    private final int idRessource;

    public ExceptionRessourceIntrouvable(String message, int idRessource)
    {
        this.idRessource = idRessource;
        super(message);
    }
    public String toString()
    {
        return super.toString() + "La Ressource" + idRessource + " n'existe pas";
    }
}
