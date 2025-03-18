package api.register.login.jwt;

import io.jsonwebtoken.io.IOException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.util.Collections;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal (HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException, java.io.IOException {

        String token = request.getHeader("Authorization"); // Obtém o token do cabeçalho Authorization da req

        if (token != null && token.startsWith("Bearer")) {
            token = token.substring(7); // remove o prefixo "Bearer" para obter o token puro

            // Valida o token
            if (jwtUtil.validateToken(token)){
                String email = jwtUtil.getClaims(token).getSubject();
                SecurityContextHolder.getContext().setAuthentication(

                        // Cria uma autenticação para o usuário
                        new UsernamePasswordAuthenticationToken(email, null, Collections.emptyList())
                );
            }
        }

        filterChain.doFilter(request, response);
    }
}