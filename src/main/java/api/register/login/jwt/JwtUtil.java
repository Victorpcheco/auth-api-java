package api.register.login.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.expiration}")
    private Long expiration;

    private SecretKey key;


    @PostConstruct
    public void init() {this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));}


    // Metodo que para gerar um token JWT
    public String generateToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setExpiration(new Date(System.currentTimeMillis() + expiration)) // Data de exp do token
                .signWith(key, SignatureAlgorithm.HS512) // assina o token com a secret key e o algoritimo HS512
                .compact(); // constrói o token e o converte em uma string
    }

    // Metodo para validar um token JWT
    public boolean validateToken(String token) {
        Claims claims = getClaims(token);
        if (claims != null) {
            String email = claims.getSubject(); // Obtém o email (subject) do token
            Date expirationDate = claims.getExpiration(); // Obtém a data de expiração do token
            Date now = new Date(System.currentTimeMillis());// obtém a data atual
            return email != null && expirationDate != null && now.before(expirationDate); // verifica validade do token
        }
        return false; // false se o token não for válido
    }

    // Extrai as reivindicações de Claims
    public Claims getClaims(String token) {

        try {
            return Jwts.parser()
                    .setSigningKey(secret.getBytes()) // Define a chave secreta para verificar a assinatura do token
                    .parseClaimsJws(token)// Faz o parsing do token e verifica a assinatura
                    .getBody(); // Retorna as reivindicações (claims) do token
        } catch (Exception e) {
            return null; // Retorna null em caso de erro (token inválido)
        }
    }









}
