package br.com.rafaelvieira.securityapi.modules.jwt.middleware;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

/**
 * @author rafae
 */
@Configuration
public class AuthMiddleware {

    public static final String UNAUTHORIZED = "UNAUTHORIZED";
    public static final String INTERNAL_SERVER_ERROR = "INTERNAL_SERVER_ERROR";
    private static final String EMPTY_SPACE = " ";

    @Value("${app-config.secrets.api-secret}")
    private String apiSecret;

    public void handle(HttpServletRequest request, HttpServletResponse response, javax.servlet.FilterChain chain) throws IOException {
        String authorization = request.getHeader("authorization");

        if (authorization == null) {
            response.setStatus(401);
            response.setContentType("application/json");
            response.getWriter().write("{\"status\": \"" + UNAUTHORIZED + "\",\"message\": \"Access token was not informed.\"}");
            return;
        }

        String accessToken = authorization;
        if (accessToken.contains(EMPTY_SPACE)) {
            accessToken = accessToken.split(EMPTY_SPACE)[1];
        } else {
            accessToken = authorization;
        }
        CompletableFuture<Void> future = new CompletableFuture<>();
        Jwts.parser().setSigningKey(apiSecret).parseClaimsJws(accessToken).getBody();
        future.thenAccept(decoded -> {
            request.setAttribute("authUser", decoded.equals("authUser"));
            try {
                chain.doFilter(request, response);
            } catch (Exception e) {
                throw new CompletionException(e);
            }
        });
        future.exceptionally(e -> {
            response.setStatus(500);
            response.setContentType("application/json");
            try {
                response.getWriter().write("{\"status\": \"" + INTERNAL_SERVER_ERROR + "\",\"message\": \""+ e.getMessage() + "\"}");
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            return null;
        });
    }
}

