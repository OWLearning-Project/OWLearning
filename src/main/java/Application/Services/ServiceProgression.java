package Application.Services;

import Domain.Models.Cours;
import Domain.Models.Progression;
import Domain.Ports.IRepository.IProgressionRepository;
import Domain.Ports.IServices.IServiceProgression;

import java.util.ArrayList;
import java.util.List;

public class ServiceProgression implements IServiceProgression {

    private final IProgressionRepository progressionRepository;

    public ServiceProgression(IProgressionRepository progressionRepository) {
        this.progressionRepository = progressionRepository;
    }

    @Override
    public ArrayList<Cours> getCoursTermines(int eleveId) {
        List<Progression> toutesLesProgressions = progressionRepository.recupererToutesLesProgressionsParEleve(eleveId);
        ArrayList<Cours> listeCoursTermines = new ArrayList<>();

        for(int i = 0; i < toutesLesProgressions.size(); i++){
            Progression coursFini = toutesLesProgressions.get(i);
            if(coursFini.getTauxProgression() >= 100.0f){
                listeCoursTermines.add(coursFini.getCours());
            }
        }
        return listeCoursTermines;
    }

    @Override
    public ArrayList<Cours> getCoursPasTermines(int eleveId) {
        List<Progression> toutesLesProgressions = progressionRepository.recupererToutesLesProgressionsParEleve(eleveId);
        ArrayList<Cours> listeCoursPasTermines = new ArrayList<>();

        for(int i = 0; i < toutesLesProgressions.size(); i++){
            Progression coursPasFini = toutesLesProgressions.get(i);
            if(coursPasFini.getTauxProgression() < 100.0f){
                listeCoursPasTermines.add(coursPasFini.getCours());
            }
        }
        return listeCoursPasTermines;
    }

    @Override
    public void majProgression(int elevId, int coursId, int chapitreId) {

    }

    @Override
    public float getProgressionEleve(int eleveId, int coursId) {
        return 0;
    }
}
