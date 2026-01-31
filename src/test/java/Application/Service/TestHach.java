package Application.Service;

import Application.Services.Hach;
import Domain.Ports.IServices.IHach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;

public class TestHach
{
    private IHach hacher = new Hach(new BCryptPasswordEncoder());

    @Test
    public void leMotDePasseEstHache()
    {
        // Arrange
        String motDePasse = "secret123";

        // Act
        String motDePasseHache = hacher.hacher(motDePasse);

        // Assert
        assertNotEquals(motDePasse, motDePasseHache);
        assertNotNull(motDePasseHache);
        assertFalse(motDePasseHache.isEmpty());
    }

    @Test
    public void leMotDePasseHacheEstValide()
    {
        // Arrange
        String motDePasse = "secret123";

        // Act
        String motDePasseHache = hacher.hacher(motDePasse);
        boolean verif = hacher.valider(motDePasse, motDePasseHache);

        // Assert
        assertTrue(verif);
    }

    @Test
    public void leMotDePasseHacheEstInvalide()
    {
        // Arrange
        String motDePasse = "secret123";
        String mauvaisMotDePasse = "unMauvaisMdp456";

        // Act
        String motDePasseHache = hacher.hacher(motDePasse);
        boolean verif = hacher.valider(mauvaisMotDePasse, motDePasseHache);

        // Assert
        assertFalse(verif);
    }

}
