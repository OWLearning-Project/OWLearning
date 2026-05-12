package app.OwLearning.Domain.Exceptions;

public class ExceptionCategorieInexistante extends RuntimeException
{
    String label;

    public ExceptionCategorieInexistante(String message, String label)
    {
        super(message);
        this.label = label;
    }

    public String toString()
    {
        return super.toString() + " La catégorie: " + this.label + " n'existe pas ";
    }
}
