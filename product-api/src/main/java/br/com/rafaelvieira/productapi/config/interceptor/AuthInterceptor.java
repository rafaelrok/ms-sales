package br.com.rafaelvieira.productapi.config.interceptor;

import br.com.rafaelvieira.productapi.config.exception.ValidationException;
import br.com.rafaelvieira.productapi.modules.jwt.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.UUID;

import static org.springframework.util.ObjectUtils.isEmpty;

/**
 * @author rafae
 */
public class AuthInterceptor implements HandlerInterceptor {

    private static final String AUTHORIZATION = "Authorization";
    private static final String TRANSACTION_ID = "transactionid";

    @Autowired
    private JwtService jwtService;

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        if (isOptions(request)) {
            return true;
        }
        if (isEmpty(request.getHeader(TRANSACTION_ID))) {
            //TODO Alternativa para gerar automaticamente a fins de testes em desenvolvimento
            // *(Remover a linha em prod ou na emissão do cliente e habilitar a exception)
            request.setAttribute(TRANSACTION_ID, UUID.randomUUID().toString());
            //throw new ValidationException("The transaction id header is required.");
        }
        var authorization = request.getHeader(AUTHORIZATION);
        jwtService.validateAuthorization(authorization);
        request.setAttribute("serviceid", UUID.randomUUID().toString());
        return true;
    }

    private boolean isOptions(HttpServletRequest request) {
        return HttpMethod.OPTIONS.name().equals(request.getMethod());
    }
}
