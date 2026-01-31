package Infrastructure.Services;

import Domain.Models.Utilisateur;
import Domain.Ports.IServices.IServiceToken;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class ServiceTokenJWT implements IServiceToken
{

    private final String secretKeyString;
    private final long expirationTime;
    private final Key key;
    private final Set<String> tokenBlacklist;

    public ServiceTokenJWT(@Value("${jwt.secret}") String secretKeyString,@Value("${jwt.expiration}") long expirationTime)
    {
        this.secretKeyString = secretKeyString;
        this.expirationTime = expirationTime;
        this.key = Keys.hmacShaKeyFor(secretKeyString.getBytes());
        this.tokenBlacklist = Collections.newSetFromMap(new ConcurrentHashMap<>()); //Set qui gère les accès concurrents
    }

    @Override
    public String genererToken(Utilisateur utilisateur)
    {
        long now = System.currentTimeMillis();

        return Jwts.builder()
                .setSubject(utilisateur.getEmail()) // On stocke l'email comme identifiant
                .claim("id", utilisateur.getId())   // On ajoute l'ID dans le token
                .claim("pseuod", utilisateur.getPseudo())
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(now + expirationTime))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    @Override
    public void invaliderToken(String token)
    {
        if (token != null && !token.isEmpty())
        {
            tokenBlacklist.add(token);
        }
    }

    @Override
    public int extraireID(String token)
    {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        // On récupère le claim "id" qu'on a mis dans genererToken
        return claims.get("id", Integer.class);
    }

    public boolean validerToken(String token) {
        if (tokenBlacklist.contains(token)) {
            return false;
        }
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}