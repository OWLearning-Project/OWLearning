package app.OwLearning.Domain.Ports.IRepository;

import app.OwLearning.Domain.Models.ChapitreTermine;

public interface IChapitreTermineRepository
{
    /**
     * Méthode qui permet d'enregistrer un chapitre qui a été terminé
     * @param chapitreTermine
     */
    void sauvegarder(ChapitreTermine chapitreTermine);

    /**
     * Méthode qui permet de vérifier si un élève à déjà terminer un chapitre
     * @param idChapitre
     * @param idEleve
     * @return true si déjà terminé, false sinon
     */
    boolean existe(int idChapitre,int idEleve);
}
