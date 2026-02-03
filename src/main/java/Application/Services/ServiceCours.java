package Application.Services;

import Domain.Models.Cours;
import Domain.Ports.IRepository.ICoursRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ServiceCours
{
    private final ICoursRepository coursRepository;

    public ServiceCours (ICoursRepository coursRepository)
    {
        this.coursRepository = coursRepository;
    }

    public ArrayList<Cours> getTousLesCours()
    {
        List<Cours> listeCours = coursRepository.trouverTousLesCours();
        return new ArrayList<Cours>(listeCours);
    }

    public ArrayList<Cours> getCoursParTitre(String titre)
    {
        List<Cours> listeCours = coursRepository.trouverParTitre(titre);
        return new ArrayList<Cours>(listeCours);
    }

    public Cours getCoursParId(int id)
    {
        return coursRepository.trouverParId(id);
    }

    public ArrayList<Cours> getCoursPublies(int createurId)
    {
        List<Cours> listeCours = coursRepository.trouverParIdCreateur(createurId);
        return new ArrayList<Cours>(listeCours);
    }

    public ArrayList<Cours> getCoursInscrits(int eleveId)
    {
        List<Cours> listeCours = coursRepository.trouverParIdEleve(eleveId);
        return new ArrayList<>(listeCours);
    }

}
