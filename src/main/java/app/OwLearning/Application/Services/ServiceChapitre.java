package app.OwLearning.Application.Services;


import app.OwLearning.Domain.Models.Chapitre;
import app.OwLearning.Domain.Models.ChapitreTermine;
import app.OwLearning.Domain.Models.Ressource;
import app.OwLearning.Domain.Ports.IRepository.IChapitreRepository;
import app.OwLearning.Domain.Ports.IRepository.IChapitreTermineRepository;
import app.OwLearning.Application.Ports.IServices.IServiceChapitre;
import app.OwLearning.Application.Exceptions.ExceptionChapitreIntrouvable;
import app.OwLearning.Application.Exceptions.ExceptionRessourceIntrouvable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Classe ServiceChapitre, permet de gérer les traitements liés aux chapitres
 */
@Slf4j
@Service
public class ServiceChapitre implements IServiceChapitre {

    private final IChapitreRepository repository;
    private final IChapitreTermineRepository chapitreTermineRepository;

    /**
     * Constructeur de ServiceChapitre
     * @param repository
     */
    public ServiceChapitre(IChapitreRepository repository,  IChapitreTermineRepository chapitreTermineRepository)
    {
        this.repository = repository;
        this.chapitreTermineRepository = chapitreTermineRepository;
    }

    /**
     * Méthode pour récupérer le contenu d'un chapitre via son id
     * @param id id du chapitre
     * @return l'objet Chapitre associé
     */
    @Override
    public Chapitre getContenuChapitre(int id) {
        log.debug("Demande de récupération du chapitre avec l'id: {}", id);
        Chapitre chapitre = this.repository.trouverParId(id);

        if (chapitre == null) {
            log.warn("Echec de la récupération du chapitre. L'id {} est introuvable dans la base", id);
            throw new ExceptionChapitreIntrouvable(id);
        }
        log.debug("Chapitre {} récupéré: {}", id, chapitre);
        return chapitre;
    }

    /**
     * Ajouter une ressource au chapitre sélectionné
     * @param id du chapitre
     * @param ressource
     */
    @Override
    public void ajouterRessource(int id, Ressource ressource) {
        log.debug("Demande l'ajout au chapitre {} la ressource: {}", id, ressource);
        Chapitre chapitre = this.repository.trouverParId(id);

        if (chapitre == null) {
            log.warn("Echec de ajout. Le chapitre {} est introuvable dans la base", id);
            throw new ExceptionChapitreIntrouvable(id);
        }

        chapitre.ajouterRessource(ressource);

        this.repository.sauvegarder(chapitre);
        log.info("La ressource a été ajoutée au chapitre {}", id);
    }

    /**
     * Permet de changer de titre ou de description pour un chapitre
     *
     * @return 1 si le chapitre est trouvé et changé. Sinon 0 s'il n'existe pas
     */
    public void modifier(int id, String titre, String description) {
        log.debug("Demande de modification du cahpitre {}", id);
        Chapitre chapitre = this.repository.trouverParId(id);

        if (chapitre == null) {
            log.warn("Echec de la modification du chapitre. L'id {} est introuvable dans la base", id);
            throw new ExceptionChapitreIntrouvable(id);
        }

        if (titre != null && !titre.isBlank()) {
            chapitre.setTitre(titre);
        }

        if (description != null && !description.isBlank()) {
            chapitre.setDescription(description);
        }
        this.repository.sauvegarder(chapitre);
        log.info("Modification du chapitre {}", id);
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
        log.debug("Demande de suppression de la ressource {} au chapitre {}", idRessource, idChapitre);
        Chapitre chapitre = this.repository.trouverParId(idChapitre);

        if (chapitre == null) {
            log.warn("Echec de la séparation ressource - chapitre. Chapitre {} est introuvable dans la base", idChapitre);
            throw new ExceptionChapitreIntrouvable(idChapitre);
        }
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
            log.info("Séparation de la ressource {} au chapitre {}", idRessource, idChapitre);
            return ressourceASupp;
        } else {
            log.warn("Echec de la séparation ressource - chapitre. Ressource {} est introuvable dans la base ou dans le chapitre {}", idRessource, idChapitre);
            throw new ExceptionRessourceIntrouvable("La ressource est introuvable",idRessource);
        }
    }

    /**
     * Méthode qui marque un chapitre comme terminé pour un élève
     * @param idChapitre
     * @param idEleve
     */
    @Override
    public void terminerChapitre(int idChapitre, int idEleve)
    {
        log.debug("Demande de terminer le chapitre {} par l'élève {}", idChapitre, idEleve);
        Chapitre chapitre = this.repository.trouverParId(idChapitre);
        if (chapitre == null)
        {
            log.warn("Echec : le chapitre {} est introuvable dans la base", idChapitre);
            throw new ExceptionChapitreIntrouvable(idChapitre);
        }

        if (this.chapitreTermineRepository.existe(idChapitre,idEleve))
        {
            log.info("Le chapitre {} est déjà terminé par l'élève {}", idChapitre, idEleve);
            return;
        }

        ChapitreTermine chapitreTermine = new ChapitreTermine(chapitre,idEleve);
        this.chapitreTermineRepository.sauvegarder(chapitreTermine);
        log.info("Chapitre {] marqué comme terminé par l'élève {}", idChapitre);
    }
}

