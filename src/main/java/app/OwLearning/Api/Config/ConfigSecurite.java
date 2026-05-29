package app.OwLearning.Api.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Classe de ConfigSecurite qui définit l'algorithme de Hachage
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class ConfigSecurite
{
    private final JwtFilter jwtFilter;

    public ConfigSecurite(JwtFilter jwtFilter)
    {
        this.jwtFilter = jwtFilter;
    }

    /**
     *
     * @param http
     * @return retirer l'authentification en entrée du swagger
     * @throws Exception
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/v3/api-docs/**",
                                "/swagger-ui/**",
                                "/swagger-ui.html").permitAll()
                        .requestMatchers("/api/authentification/connexion","/api/authentification/inscription").permitAll()
                        //Faille de sécurité connu à corriger avec le front
                        .requestMatchers("/ws-messagerie/**").permitAll()
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

}
