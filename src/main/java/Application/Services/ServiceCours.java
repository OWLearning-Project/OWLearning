package Application.Services;

import Domain.Models.Cours;
import Domain.Ports.IRepository.ICoursRepository;
import Domain.Ports.IServices.IServiceCours;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * Classe ServiceCours, permet de gérer le traitement des cours
 */
@Service
public class ServiceCours implements IServiceCours
{
    private final ICoursRepository coursRepository;

    /**
     * Constructeur du ServiceCours
     * @param coursRepository
     */
    public ServiceCours (ICoursRepository coursRepository)
    {
        this.coursRepository = coursRepository;
    }

    /**
     * Méthode permettant de récupérer un cours avec son id
     * @param id id du cours
     * @return l'objet Cours associé
     */
    public Cours getCoursParId(int id)
    {
        return coursRepository.trouverParId(id);
    }

    /**
     * Méthode qui permet de récupérer les cours créés par un créateur avec son id
     * Filtrage possible avec les paramètres
     * @param idCreateur id du créateur
     * @return l'ArrayList des Cours créés
     */
    public ArrayList<Cours> getCoursCrees(String titre, int idCreateur, String difficulte, String categorie, Boolean estPrive)
    {
        return coursRepository.trouverParIdCreateur(titre, idCreateur, difficulte, categorie, estPrive);
    }

    /**
     * Méthode permettant de récupérer les Cours où un élève est inscrit avec son id
     * Filtrage possible avec les paramètres
     * @param eleveId id de l'élève
     * @return l'ArrayList des Cours inscrits
     */
    public ArrayList<Cours> getCoursInscrits(int eleveId, String titre, String nomCreateur, String difficulte, String categorie, Boolean estPrive)
    {
        return coursRepository.trouverParIdEleve(eleveId, titre, nomCreateur, difficulte, categorie, estPrive);
    }

    /**
     * Méthode permettant de récupérer l'entièreté des Cours publiés
     * Filtrage possible avec les paramètres
     * @return l'ArrayList des Cours publiés
     */
    @Override
    public ArrayList<Cours> getLesCours(String titre, String nomCreateur, String difficulte, String categorie, Boolean estPrive)
    {
        return coursRepository.trouverCoursFiltre(titre, nomCreateur, difficulte, categorie, estPrive);
    }
}
