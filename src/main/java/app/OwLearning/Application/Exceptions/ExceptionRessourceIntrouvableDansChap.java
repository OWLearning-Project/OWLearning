package app.OwLearning.Application.Exceptions;

public class ExceptionRessourceIntrouvableDansChap extends RuntimeException {
    public ExceptionRessourceIntrouvableDansChap(int idRessource, int idChapitre) {
        super("La ressource " + idRessource + " est introuvable dans le chapitre " + idChapitre);
    }
}