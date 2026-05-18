package app.OwLearning.Infrastructure.Config;

import app.OwLearning.Api.DTO.request.UtilisateurAuthentifieRequest;
import app.OwLearning.Domaine.Entités.Utilisateur;
import app.OwLearning.Infrastructure.ServicesExternes.ServiceTokenJWT;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
public class JwtFilter extends OncePerRequestFilter
{
    private final ServiceTokenJWT serviceTokenJWT;

    public JwtFilter(ServiceTokenJWT unServiceTokenJWT)
    {
        this.serviceTokenJWT = unServiceTokenJWT;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException
    {
        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer "))
        {
            String token = authHeader.substring(7);
            if (serviceTokenJWT.validerToken(token))
            {
                int id = serviceTokenJWT.extraireID(token);
                String email = serviceTokenJWT.extraireEmail(token);
                String role = serviceTokenJWT.extraireRole(token);

                UtilisateurAuthentifieRequest utilisateurAuthentifieDTO = new UtilisateurAuthentifieRequest(id,email,role);

                SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role.toUpperCase());

                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        utilisateurAuthentifieDTO,
                        null,
                        Collections.singletonList(authority)
                );
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        filterChain.doFilter(request, response);
    }
}
