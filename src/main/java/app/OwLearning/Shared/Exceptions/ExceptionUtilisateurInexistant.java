package app.OwLearning.Shared.Exceptions;

public class ExceptionUtilisateurInexistant extends RuntimeException
{
    private String email;
    private int id;

    public ExceptionUtilisateurInexistant(String message, String email)
    {
        super(message);
        this.email = email;
    }

    public ExceptionUtilisateurInexistant(String message, int id)
    {
        super(message);
        this.id = id;
    }

    public String toString()
    {
        if (this.email != null)
            return  super.toString() + "Il n'y a pas d'utilisateur associé à l'email : " + this.email;
        else
            return super.toString() + "Il n'y a pas d'utilisateur associé à l'id : " + this.id;
    }
}
