package Domain.Ports.IServices;

import Domain.Models.Cours;

import java.util.ArrayList;

public interface IServiceProgression {

    public ArrayList<Cours> getCoursTermines(int eleveId);

    public ArrayList<Cours> getCoursPasTermines(int eleveId);

    public void majProgression(int elevId, int coursId, int chapitreId);

    public float getProgressionEleve(int eleveId, int coursId);
}
