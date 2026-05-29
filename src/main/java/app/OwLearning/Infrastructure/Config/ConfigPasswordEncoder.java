package app.OwLearning.Infrastructure.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class ConfigPasswordEncoder {
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
