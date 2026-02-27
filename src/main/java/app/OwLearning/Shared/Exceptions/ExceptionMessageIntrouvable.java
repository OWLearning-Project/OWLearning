package app.OwLearning.Shared.Exceptions;

public class ExceptionMessageIntrouvable extends RuntimeException
{
    private final int idMessage;
    public ExceptionMessageIntrouvable(String message, int idMessage)
    {
        super(message);
        this.idMessage = idMessage;
    }
    public String toString()
    {
        return super.toString() + "Le Message" + idMessage + " n'existe pas";
    }
}
