package br.com.rafaelvieira.securityapi.config.middleware;

import org.springframework.context.annotation.Configuration;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

/**
 * @author rafae
 */

@Configuration
public class TracingMiddleware {
    private static final int BAD_REQUEST = 400;
    public void handle(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        String transactionId = request.getHeader("transactionid");

        if(transactionId == null){
            response.setStatus(BAD_REQUEST);
            response.setContentType("application/json");
            response.getWriter().write("{\"status\": " + BAD_REQUEST +
                    ",\"message\": \"The transactionid header is required.\"}");
        }

        request.setAttribute("serviceid", UUID.randomUUID().toString());
        chain.doFilter(request, response);
    }
}
