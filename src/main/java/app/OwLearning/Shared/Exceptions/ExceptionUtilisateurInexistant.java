package app.OwLearning.Shared.Exceptions;

public class ExceptionUtilisateurInexistant extends RuntimeException
{
    private String email;

    public ExceptionUtilisateurInexistant(String message, String email)
    {
        super(message);
        this.email = email;
    }

    public String toString()
    {
        return  super.toString() + "Il n'y a pas d'utilisateur associé à l'email : " + this.email;
    }
}
