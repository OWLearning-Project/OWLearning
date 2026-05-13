package app.OwLearning.Application.Exceptions;

public class ExceptionChapitreIntrouvable extends RuntimeException {
    public ExceptionChapitreIntrouvable(int id) {
        super("Le chapitre "+ id + " n'existe pas.");
    }
}
