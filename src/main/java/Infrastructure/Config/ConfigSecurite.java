package Infrastructure.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Classe de ConfigSecurite qui définit l'algorithme de Hachage
 */
@Configuration
public class ConfigSecurite
{
    /**
     * Définit Bcrypt comme algorithme de hachage.
     * @return PasswordEncoder
     */
    @Bean
    public PasswordEncoder mdpEncoder()
    {
        return new BCryptPasswordEncoder();
    }
}
