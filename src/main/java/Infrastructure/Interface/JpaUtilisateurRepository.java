package Infrastructure.Persistence.Interface;

import Domain.Models.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Interface permettant de réutiliser une interface de JPA pour redfinir nos requêtes SQL
 */
@Repository
public interface JpaUtilisateurRepository extends JpaRepository<Utilisateur, Integer>
{
    @Query(value = "SELECT * FROM Utilisateur WHERE email = :email", nativeQuery = true)
    public Utilisateur findByEmailNative (@Param("email") String email);

    @Query(value = "SELECT id_utilisateur FROM utilisateur WHERE email = :email", nativeQuery = true)
    public Integer findIdByEmailNative (@Param("email") String email);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO Utilisateur (nom, prenom, email, mot_de_passe) VALUES (:nom, :prenom, :email, :mdpHash)", nativeQuery = true)
    public int insertUtilisateurNative (@Param("nom") String nom, @Param("prenom") String prenom,
                                        @Param("email") String email, @Param("mdpHash") String mdpHash);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO Createur (id_utilisateur) VALUES (:id)", nativeQuery = true)
    public int insertCreateurNative (@Param("id") int id);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO Eleve (id_utilisateur) VALUES (:id)", nativeQuery = true)
    public int insertEleveNative (@Param("id") int id);

    @Query(value = "SELECT * FROM utilisateur WHERE id_utilisateur = :id", nativeQuery = true)
    public Utilisateur findByIdNative(@Param("id") int id);

    @Modifying
    @Transactional
    @Query(value = "UPDATE utilisateur SET pseudo = :pseudo, email = :email WHERE id_utilisateur = :id", nativeQuery = true)
    public int updateProfilNative(@Param("id") int id,
                                  @Param("pseudo") String pseudo,
                                  @Param("email") String email);

}
