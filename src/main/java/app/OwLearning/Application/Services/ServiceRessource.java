package app.OwLearning.Application.Services;

import app.OwLearning.Domain.Models.Ressource;
import app.OwLearning.Domain.Models.TypeRessource;
import app.OwLearning.Domain.Ports.IRepository.IRessourceRepository;
import app.OwLearning.Domain.Ports.IServices.IServiceRessource;
import app.OwLearning.Shared.Exceptions.ExceptionRessourceIntrouvable;
import app.OwLearning.Shared.Exceptions.ExceptionRessourceIntrouvableDansChap;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ServiceRessource implements IServiceRessource {

    private final IRessourceRepository repository;

    public ServiceRessource(IRessourceRepository repository) {
        this.repository = repository;
    }

    @Override
    public Ressource getContenuRessource(int id) {
        log.debug("Demande de la récupération de l'id de la ressource: {}", id);
        Ressource ressource = this.repository.trouverParId(id);

        if (ressource == null) {
            log.warn("La ressource {} est introuvable dans la base", id);
            throw new ExceptionRessourceIntrouvable("La ressource est introuvable", id);
        }
        log.debug("Ressource {} récupérée: {}", id, ressource);
        return ressource;
    }

    @Override
    public Ressource creeRessource(String nom, String url, TypeRessource type) {
        log.debug("Demande de création d'une ressource: nom='{}', url='{}', type='{}'", nom, url, type);
        if (nom == null || nom.isBlank()){
            log.warn("Echec de création de la ressource: le nom n'est pas disponible");
            throw new IllegalArgumentException("Le nom n'est pas valide");
        }
        if (url == null || url.isBlank()){
            log.error("Echec de création de la ressource: l'url n'est pas disponible");
            throw new IllegalArgumentException("L'url n'est pas valide");
        }
        if (type == null){
            log.error("Echec de création de la ressource: le type de ressource n'est pas disponible ");
            throw new IllegalArgumentException("Type de ressource non selectionné");
        }

        Ressource ressource = this.repository.sauvegarder(new Ressource(nom,type,url));

        log.info("Création de ressource: {}", ressource);
        return ressource;
    }

    @Override
    public Ressource supprimerRessource(int id) {
        log.debug("Demande de suppression de la ressource (id): {}", id);
        Ressource ressource = this.repository.trouverParId(id);

        if (ressource == null){
            log.warn("Echec de la suppression de la ressource (id): {}, id introuvable dans la base", id);
            throw new ExceptionRessourceIntrouvable("La ressource est introuvable", id);
        }

        this.repository.supprimer(id);
        log.info("Suppression de la ressource: {}", ressource);
        return ressource;
    }

    @Override
    public void modifier(int id, String nom, String url, TypeRessource type) {
        log.debug("Demande de modification de la ressource ID {}: nom='{}', url='{}', type='{}", id, nom, url, type);
        Ressource ressource = this.repository.trouverParId(id);

        if (ressource == null){
            log.warn("Echec de la modification. L'id {} est introuvable dans la base", id);
            throw new ExceptionRessourceIntrouvable("La ressource est introuvable", id);
        }

        if (nom != null && !nom.isBlank()) ressource.setNom(nom);

        if (url != null && !url.isBlank()) ressource.setUrl(url);

        if (type != null ) ressource.setType(type);

        this.repository.sauvegarder(ressource);
        log.info("Modification de la ressource ID {}", id);

    }
}
