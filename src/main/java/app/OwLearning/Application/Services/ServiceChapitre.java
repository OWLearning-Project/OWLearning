package app.OwLearning.Application.Services;


import app.OwLearning.Domain.Models.Chapitre;
import app.OwLearning.Domain.Models.Ressource;
import app.OwLearning.Domain.Ports.IRepository.IChapitreRepository;
import app.OwLearning.Domain.Ports.IServices.IServiceChapitre;
import app.OwLearning.Shared.Exceptions.ExceptionChapitreIntrouvable;
import app.OwLearning.Shared.Exceptions.ExceptionRessourceIntrouvableDansChap;
import org.springframework.stereotype.Service;

/**
 * Classe ServiceChapitre, permet de gérer les traitements liés aux chapitres
 */
@Service
public class ServiceChapitre implements IServiceChapitre {

    private final IChapitreRepository repository;

    /**
     * Constructeur de ServiceChapitre
     * @param repository
     */
    public ServiceChapitre(IChapitreRepository repository) {
        this.repository = repository;
    }

    /**
     * Méthode pour récupérer le contenu d'un chapitre via son id
     * @param id id du chapitre
     * @return l'objet Chapitre associé
     */
    @Override
    public Chapitre getContenuChapitre(int id) {
        Chapitre chapitre = this.repository.trouverParId(id);

        if (chapitre == null) {
            throw new ExceptionChapitreIntrouvable(id);
        }
        return chapitre;
    }

    /**
     * Ajouter une ressource au chapitre sélectionné
     * @param id du chapitre
     * @param ressource
     */
    @Override
    public void ajouterRessource(int id, Ressource ressource) {
        Chapitre chapitre = this.repository.trouverParId(id);

        if (chapitre == null) {
            throw new ExceptionChapitreIntrouvable(id);
        }

        chapitre.ajouterRessource(ressource);

        this.repository.sauvegarder(chapitre);
    }

    /**
     * Permet de changer de titre ou de description pour un chapitre
     *
     * @return 1 si le chapitre est trouvé et changé. Sinon 0 s'il n'existe pas
     */
    public void modifier(int id, String titre, String description) {
        Chapitre chapitre = this.repository.trouverParId(id);

        if (chapitre == null) {
            throw new ExceptionChapitreIntrouvable(id);
        }

        if (titre != null && !titre.isBlank()) {
            chapitre.setTitre(titre);
        }

        if (description != null && !description.isBlank()) {
            chapitre.setDescription(description);
        }
        this.repository.sauvegarder(chapitre);
    }

    /**
     * Détache la ressource du chapitre
     *
     * @param idChapitre
     * @param idRessource
     * @return
     */
    @Override
    public Ressource retirerRessource(int idChapitre, int idRessource) {
        Chapitre chapitre = this.repository.trouverParId(idChapitre);

        if (chapitre == null) {
            throw new ExceptionChapitreIntrouvable(idChapitre);
        }
        // TODO: Faire un lambda
        Ressource ressourceASupp = null;
        for (Ressource r : chapitre.getRessources()) {
            if (r.getId_ressource() == idRessource) {
                ressourceASupp = r;
                break;
            }
        }

        if (ressourceASupp != null) {
            chapitre.getRessources().remove(ressourceASupp);
            this.repository.sauvegarder(chapitre);
            return ressourceASupp;
        } else {
            throw new ExceptionRessourceIntrouvableDansChap(idRessource, idChapitre);
        }
    }
}

