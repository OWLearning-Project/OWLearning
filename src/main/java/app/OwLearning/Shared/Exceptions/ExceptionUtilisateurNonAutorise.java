package app.OwLearning.Shared.Exceptions;

public class ExceptionUtilisateurNonAutorise extends Exception
{
    private int idUtilisateur;
    private int idDiscussion;
    public ExceptionUtilisateurNonAutorise(String message,int idUtilisateur,int idDiscussion)
    {
        super(message);
        this.idUtilisateur = idUtilisateur;
        this.idDiscussion = idDiscussion;
    }
    public String toString()
    {
        return super.toString() + "L'utilisateur : " + this.idUtilisateur + " n'est pas autorisé à participer à la discussion d'id : " + this.idDiscussion;
    }
}
