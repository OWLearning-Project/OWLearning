package app.OwLearning.Shared.Exceptions;

public class ExceptionRessourceIntrouvable extends RuntimeException {
    public ExceptionRessourceIntrouvable(int idRessource, int idChapitre) {
        super("La ressource " + idRessource + " est introuvable dans le chapitre " + idChapitre);
    }
}