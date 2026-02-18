package Application.Services;

import Domain.Models.Chapitre;
import Domain.Models.Ressource;
import Domain.Ports.IRepository.IChapitreRepository;
import Domain.Ports.IServices.IServiceChapitre;
import Shared.Exceptions.ExceptionChapitreIntrouvable;
import Shared.Exceptions.ExceptionRessourceIntrouvable;

public class ServiceChapitre implements IServiceChapitre {

    private final IChapitreRepository repository;

    public ServiceChapitre(IChapitreRepository repository) {
        this.repository = repository;
    }

    @Override
    public Chapitre getContenuChapitre(int id) {
        Chapitre chapitre = this.repository.trouverParId(id);

        if(chapitre == null){
            throw new ExceptionChapitreIntrouvable(id);
        }
        return chapitre;
    }

    /**
     * Ajout une ressource au chapitre selectionné
     * @param id du chapitre
     * @param ressource
     */
    @Override
    public void ajouterRessource(int id, Ressource ressource) {
        Chapitre chapitre = this.repository.trouverParId(id);

        if (chapitre == null){
            throw new ExceptionChapitreIntrouvable(id);
        }

        chapitre.ajouterRessource(ressource);

        this.repository.sauvegarder(chapitre);
    }

    /**
     * Permet de changer de titre ou de description pour un chapitre
     *
     *
     * @return 1 si le chapitre est trouvé et changé. sinon 0 si il n'existe pas
     */
    @Override
    public void modifier(int id, String titre, String description) {
        Chapitre chapitre = this.repository.trouverParId(id);

        if (chapitre == null){
            throw new ExceptionChapitreIntrouvable(id);
        }

        if (titre != null && !titre.isBlank()){
            chapitre.setTitre(titre);
        }

        if (description != null && !description.isBlank()){
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
            throw new ExceptionRessourceIntrouvable(idRessource, idChapitre);
        }
    }


}
