package app.OwLearning.Shared.Exceptions;

public class ExceptionCompteExistant extends RuntimeException
{
    private String email;
    public ExceptionCompteExistant(String message, String email)
    {
        super(message);
        this.email = email;
    }

    public String toString()
    {
        return super.toString() + " il y a déjà un compte lié à : " + this.email;
    }
}
