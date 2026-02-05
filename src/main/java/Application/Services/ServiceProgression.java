package Application.Services;

import Domain.Models.Chapitre;
import Domain.Models.Cours;
import Domain.Models.Progression;
import Domain.Ports.IRepository.IProgressionRepository;
import Domain.Ports.IServices.IServiceProgression;
import jakarta.persistence.EntityNotFoundException;

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
        Progression progressionExistante = progressionRepository.trouverParId(elevId, coursId);
        //vérifie si l'élève est inscrit
        if(progressionExistante == null){
            throw new EntityNotFoundException("L'élève n'est pas inscrit au cours");
        }

        ArrayList<Chapitre> nb_chapitres = progressionExistante.getCours().getChapitres();

        //Pour ne pas diviser par 0
        if(nb_chapitres != null && !nb_chapitres.isEmpty()){
            int nb_chapitre_total = progressionExistante.getCours().getChapitres().size();
            float nouvelleProgression = ((float) chapitreId / nb_chapitre_total)*100;

            progressionExistante.setTauxProgression(nouvelleProgression);
            progressionRepository.maj(progressionExistante);
        }
    }

    @Override
    public float getProgressionEleve(int eleveId, int coursId) {
        Progression progression = progressionRepository.trouverParId(eleveId, coursId);
        if(progression == null){
            return 0.0f;
        }
        else{
            return progression.getTauxProgression();
        }
    }
}
