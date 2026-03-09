package app.OwLearning.Shared.Exceptions;

public class ExceptionDiscussionInexistante extends RuntimeException
{
    private int id;

    public ExceptionDiscussionInexistante(String message, int id)
    {
        super(message);
        this.id = id;
    }

    public String toString()
    {
        return  super.toString() + "Il n'y a pas de discussion associée à l'id : " + this.id;
    }

}
