package br.com.rafaelvieira.paymeapi.config.interceptor;

import br.com.rafaelvieira.paymeapi.modules.security.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.UUID;

import static org.springframework.util.ObjectUtils.isEmpty;

import static br.com.rafaelvieira.paymeapi.modules.utils.TokenUtil.extractTokenRequest;

/**
 * @author rafae
 */

public class AuthorizationTokenInterceptor implements HandlerInterceptor {

    private static final String AUTHORIZATION = "Authorization";
    private static final String TRANSACTION_ID = "transactionid";
    private static final String ENDPOINT_PROTEGIDO = "/api/";
    private static final String OPTIONS_METHOD = "OPTIONS";
    @Autowired
    private JwtService jwtService;


    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) {
        if (requiresUrlWithAuthentication(request)) {
            return isOptions(request) || authorizationHeaderWithValidToken(request);
        }
        if (isEmpty(request.getHeader(TRANSACTION_ID))) {
            //TODO Alternativa para gerar automaticamente o ID, para testes em desenvolvimento
            // *(Remover a linha em prod ou na emissão do cliente e habilitar a exception)
            request.setAttribute(TRANSACTION_ID, UUID.randomUUID().toString());
            //throw new ValidationException("The transaction id header is required.");
        }
        var authorization = request.getHeader(AUTHORIZATION);
        jwtService.validateAuthorization(authorization);
        request.setAttribute("serviceid", UUID.randomUUID().toString());
        return true;
    }

    private boolean requiresUrlWithAuthentication(HttpServletRequest request) {
        return request.getRequestURI().contains(ENDPOINT_PROTEGIDO);
    }

    private boolean authorizationHeaderWithValidToken(HttpServletRequest request) {
        var token = extractTokenRequest(request);
        return jwtService.hasAuthenticatedUser(token);
    }

    private boolean isOptions(HttpServletRequest request) {
        return request.getMethod().equals(OPTIONS_METHOD);
    }
}
