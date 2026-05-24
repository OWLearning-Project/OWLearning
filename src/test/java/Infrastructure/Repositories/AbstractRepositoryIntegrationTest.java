package Infrastructure.Repositories;

import app.OwLearning.Domaine.Enumérations.Difficulte;
import app.OwLearning.Domaine.Enumérations.TypeRessource;
import app.OwLearning.Main;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;

@ActiveProfiles("test")
@Transactional
@SpringBootTest(classes = Main.class, webEnvironment = SpringBootTest.WebEnvironment.NONE)
abstract class AbstractRepositoryIntegrationTest
{
    @Autowired
    protected JdbcTemplate jdbcTemplate;

    @Autowired
    protected EntityManager entityManager;

    protected void synchroniserPersistenceContext()
    {
        entityManager.flush();
        entityManager.clear();
    }

    protected int insererUtilisateur(String prefixeEmail)
    {
        String suffixe = String.valueOf(System.nanoTime());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO utilisateur (nom, prenom, email, pseudo, mot_de_passe, date_inscription) VALUES (?, ?, ?, ?, ?, CURRENT_TIMESTAMP)",
                    Statement.RETURN_GENERATED_KEYS
            );
            ps.setString(1, "Nom " + prefixeEmail);
            ps.setString(2, "Prenom " + prefixeEmail);
            ps.setString(3, prefixeEmail + "-" + suffixe + "@test.local");
            ps.setString(4, prefixeEmail + "-" + suffixe);
            ps.setString(5, "motdepasse");
            return ps;
        }, keyHolder);
        return keyHolder.getKey().intValue();
    }

    protected int insererCreateur(String prefixeEmail)
    {
        int utilisateurId = insererUtilisateur(prefixeEmail);
        jdbcTemplate.update("INSERT INTO createur (id_utilisateur) VALUES (?)", utilisateurId);
        return utilisateurId;
    }

    protected int insererEleve(String prefixeEmail)
    {
        int utilisateurId = insererUtilisateur(prefixeEmail);
        jdbcTemplate.update(
                "INSERT INTO eleve (id_utilisateur, age, niveau_etude) VALUES (?, ?, ?)",
                utilisateurId,
                20,
                "BUT2 Informatique"
        );
        return utilisateurId;
    }

    protected int insererCours(int createurId, String titre, boolean estPublie)
    {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO cours (titre, description, date_creation, est_prive, est_publie, difficulte, id_createur) VALUES (?, ?, CURRENT_TIMESTAMP, ?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS
            );
            ps.setString(1, titre);
            ps.setString(2, "Description " + titre);
            ps.setBoolean(3, false);
            ps.setBoolean(4, estPublie);
            ps.setString(5, Difficulte.DEBUTANT.name());
            ps.setInt(6, createurId);
            return ps;
        }, keyHolder);
        return keyHolder.getKey().intValue();
    }

    protected int insererChapitre(int coursId, String titre)
    {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO chapitre (titre, description, id_cours) VALUES (?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS
            );
            ps.setString(1, titre);
            ps.setString(2, "Description " + titre);
            ps.setInt(3, coursId);
            return ps;
        }, keyHolder);
        return keyHolder.getKey().intValue();
    }

    protected int insererRessource(String nom, TypeRessource type)
    {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO ressource (nom, url, type_ressource) VALUES (?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS
            );
            ps.setString(1, nom);
            ps.setString(2, "https://test.local/temp");
            ps.setString(3, type.name());
            return ps;
        }, keyHolder);
        int ressourceId = keyHolder.getKey().intValue();
        jdbcTemplate.update(
                "UPDATE ressource SET url = ? WHERE id_ressource = ?",
                "https://test.local/ressource-" + ressourceId,
                ressourceId
        );
        return ressourceId;
    }

    protected int insererDiscussion()
    {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO discussion DEFAULT VALUES",
                    Statement.RETURN_GENERATED_KEYS
            );
            return ps;
        }, keyHolder);
        return keyHolder.getKey().intValue();
    }

    protected int insererMessage(int discussionId, int utilisateurId, String contenu, String dateCreation)
    {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO message (date_creation, contenu, statut, id_discussion, id_utilisateur) VALUES (?, ?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS
            );
            ps.setTimestamp(1, Timestamp.valueOf(dateCreation));
            ps.setString(2, contenu);
            ps.setString(3, "ENVOYE");
            ps.setInt(4, discussionId);
            ps.setInt(5, utilisateurId);
            return ps;
        }, keyHolder);
        return keyHolder.getKey().intValue();
    }
}
