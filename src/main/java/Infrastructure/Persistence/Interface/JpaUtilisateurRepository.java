package Infrastructure.Persistence.Interface;

import Domain.Models.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface JpaUtilisateurRepository extends JpaRepository<Utilisateur, Integer>
{
    @Query(value = "SELECT * FROM Utilisateur WHERE email = :email", nativeQuery = true)
    public Utilisateur findByEmailNative (@Param("email") String email);


    @Modifying
    @Transactional
    @Query(value = "INSERT INTO Utilisateur (nom, prenom, email, mot_de_passe) VALUES (:nom, :prenom, :email, :mdpHash)", nativeQuery = true)
    public int insertUtilisateurNative (@Param("nom") String nom, @Param("prenom") String prenom,
                                                @Param("email") String email, @Param("mdpHash") String mdpHash);
}
