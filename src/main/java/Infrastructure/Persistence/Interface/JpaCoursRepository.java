package Infrastructure.Persistence.Interface;

import Domain.Models.Cours;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Interface JpaCoursRepository permettant de récupérer les cours dans la bd avec des requêtes SQL natives
 */
public interface JpaCoursRepository extends JpaRepository<Cours, Integer>
{
    /**
     * Recherche des cours publiés dans la base de données avec une requête SQL native
     * La recherche peut prendre en compte des filtres avec des conditions devant "True" si le paramètre est null
     * @param titre chaine de caractères présente dans le titre du cours
     * @param nomCreateur chaine de caractères présente dans le nom du créateur
     * @param difficulte nom de la difficulté
     * @param categorie nom de la catégorie
     * @param estPrive Objet de type Boolean représentant l'état du cours (null si pas de filtre)
     * @return la liste des Cours publiés avec ou sans filtre, ou une liste vide sinon
     */
    @Query(value = "SELECT DISTINCT c.* FROM Cours c JOIN Utilisateur u ON c.id_createur = u.id_utilisateur LEFT JOIN categorie_cours cc USING (id_cours) WHERE c.est_publie = true "
            + "AND (:titre IS NULL OR c.titre ILIKE CONCAT('%', :titre, '%')) AND (:nom IS NULL OR u.nom ILIKE CONCAT('%', :nom, '%')) AND (:difficulte IS NULL OR c.difficulte = :difficulte) "
            + "AND (:categorie IS NULL OR cc.categorie = :categorie) AND (:prive IS NULL OR c.est_prive = :prive)", nativeQuery = true)
    public List<Cours> findAllCoursPubliesNative(@Param("titre") String titre, @Param("nom") String nomCreateur,
                                                 @Param("difficulte") String difficulte, @Param("categorie") String categorie,
                                                 @Param("prive") Boolean estPrive);

    /**
     * Recherche des cours où un élève est inscrit dans la base de données avec une requête SQL native
     * La recherche peut prendre en compte des filtres avec des conditions devant "True" si le paramètre est null
     * @param idEleve id de l'élève
     * @param titre chaine de caractères présente dans le titre du cours
     * @param nomCreateur chaine de caractères présente dans le nom du créateur
     * @param difficulte nom de la difficulté
     * @param categorie nom de la catégorie
     * @param estPrive Objet de type Boolean représentant l'état du cours (null si pas de filtre)
     * @return la liste des Cours inscrits ou une liste vide sinon
     */
    @Query(value = "SELECT DISTINCT c.* FROM Cours c JOIN inscription i USING(id_cours) JOIN Utilisateur u ON c.id_createur = u.id_utilisateur "
            + "LEFT JOIN categorie_cours cc USING(id_cours) WHERE i.id_eleve = :idEleve AND (:titre IS NULL OR c.titre ILIKE CONCAT('%', :titre, '%')) "
            + "AND (:nom IS NULL OR u.nom ILIKE CONCAT('%', :nom, '%')) AND (:difficulte IS NULL OR c.difficulte = :difficulte) "
            + "AND (:categorie IS NULL OR cc.categorie = :categorie) AND (:prive IS NULL OR c.est_prive = :prive)", nativeQuery = true)
    public List<Cours> findByIdEleveNative(@Param("idEleve") int idEleve, @Param("titre") String titre, @Param("nom") String nomCreateur,
                                           @Param("difficulte") String difficulte, @Param("categorie") String categorie,
                                           @Param("prive") Boolean estPrive);

    /**
     * Recherche des cours créés par un créateur dans la base de données avec une requête SQL native
     * La recherche peut prendre en compte des filtres avec des conditions devant "True" si le paramètre est null
     * @param titre chaine de caractères présente dans le titre du cours
     * @param idCreateur id du créateur du cours
     * @param difficulte nom de la difficulté
     * @param categorie nom de la catégorie
     * @param estPrive Objet de type Boolean représentant l'état du cours (null si pas de filtre)
     * @return la liste des Cours crées ou une liste vide sinon
     */
    @Query(value = "SELECT DISTINCT c.* FROM Cours c LEFT JOIN categorie_cours cc USING (id_cours) WHERE c.est_publie = true AND id_createur = :id_createur "
            + "AND (:titre IS NULL OR c.titre ILIKE CONCAT('%', :titre, '%')) AND (:difficulte IS NULL OR c.difficulte = :difficulte) "
            + "AND (:categorie IS NULL OR cc.categorie = :categorie) AND (:prive IS NULL OR c.est_prive = :prive)", nativeQuery = true )
    public List<Cours> findByIdCreateurNative(@Param("titre") String titre, @Param("id_createur") int idCreateur,
                                              @Param("difficulte") String difficulte, @Param("categorie") String categorie,
                                              @Param("prive") Boolean estPrive);

    /**
     * Recherche du cours via son id dans la base de données avec une requête SQL native
     * @param idCours id du cours
     * @return le cours trouvé ou null sinon
     */
    @Query(value = "SELECT * FROM Cours WHERE id_cours = :idCours", nativeQuery = true)
    public Cours findByIdNative(@Param("idCours") int idCours);
}
