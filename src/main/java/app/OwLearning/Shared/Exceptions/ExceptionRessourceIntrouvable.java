package app.OwLearning.Shared.Exceptions;

public class ExceptionRessourceIntrouvable extends RuntimeException {
    public ExceptionRessourceIntrouvable(int id) {
        super("La ressource "+ id +" est introuvable");
    }
}
