package app.OwLearning.Shared.Exceptions;

public class ExceptionCoursInexistant extends RuntimeException
{
    private int id;
    public ExceptionCoursInexistant(String message, int id)
    {
        super(message);
        this.id = id;
    }

    public String toString()
    {
        return super.toString() + " il n'y a pas de cours avec pour id : " + this.id;
    }
}
