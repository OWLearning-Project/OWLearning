package Domain.TestModel;

import Domain.Models.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

public class TestProgression {
    private Progression progression;

    @BeforeEach
    public void setUp()
    {
        progression = new Progression();
    }

    @Test
    @DisplayName("Devrait calculer 50% pour 1 chapitre fini sur 2")
    void testCalculerPourcentageStandard() {
        progression.calculerPourcentage(1, 2);
        assertEquals(50.0f, progression.getTauxProgression(), "Le calcul simple 1/2 devrait donner 50.0");
    }

    @Test
    @DisplayName("Devrait retourner 0% si le cours n'a pas de chapitres (Division par zéro)")
    void testCalculerPourcentageSansChapitre() {
        progression.calculerPourcentage(0, 0);
        assertEquals(0.0f, progression.getTauxProgression(), "Si total est 0, le taux doit être 0 ");
    }

    @Test
    @DisplayName("Devrait gérer les arrondis (ex: 1/3)")
    void testCalculerPourcentageArrondi() {
        progression.calculerPourcentage(1, 3);
        assertTrue(progression.getTauxProgression() >= 33.3f && progression.getTauxProgression() <= 33.4f);
    }

    @Test
    @DisplayName("estCoursTermine devrait être faux si taux < 100")
    void testEstCoursTermineFaux() {
        progression.calculerPourcentage(9, 10); // 90%
        assertFalse(progression.estCoursTermine(), "Le cours ne devrait pas être terminé à 90%");
    }

    @Test
    @DisplayName("estCoursTermine devrait être vrai si taux == 100")
    void testEstCoursTermineVrai() {
        progression.calculerPourcentage(5, 5); // 100%
        assertTrue(progression.estCoursTermine(), "Le cours devrait être marqué comme terminé à 100%");
    }

    @Test
    @DisplayName("Initialisation par défaut")
    void testInitialisation() {
        assertEquals(0.0f, progression.getTauxProgression(), "Un nouvel objet progression doit démarrer à 0");
    }
}
