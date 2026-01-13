package Shared.Exceptions;

public class ExceptionsDiscussion extends RuntimeException
{
    public static final String messageNull = "Le message ne peut pas être null";
    public static final String utilisateurNonAutorise = "L'utilisateur ne fait pas partie de la discussion";

    public ExceptionsDiscussion(String message)
    {
        super(message);
    }
}
