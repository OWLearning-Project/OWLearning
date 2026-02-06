package Shared.Exceptions;

public class ExceptionTokenInvalide extends RuntimeException
{
    private String token;

    public ExceptionTokenInvalide(String message, String token)
    {
        super(message);
        this.token = token;
    }
    @Override
    public String toString()
    {
        return super.toString() + "le token invalide est : " + this.token;
    }
}
