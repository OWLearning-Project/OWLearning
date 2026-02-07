package Domain.Ports.IServices;

import Domain.Models.Cours;

import java.util.ArrayList;

public interface IServiceProgression {
/**
    public ArrayList<Cours> getCoursTermines(int eleveId);

    public ArrayList<Cours> getCoursPasTermines(int eleveId);

    public void majProgression(int elevId, int coursId, int chapitreId);
**/
    /**
     * Méthode qui permet de récupérer le taux de progression d'un élève sur un cours
     * @param eleveId id de l'élève
     * @param coursId id du cours
     * @return un float qui correspond au taux de progression
     */
    public float getProgressionEleve(int eleveId, int coursId);
}
