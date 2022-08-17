package br.com.rafaelvieira.paymeapi.config;

import br.com.rafaelvieira.paymeapi.modules.security.service.JwtService;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static br.com.rafaelvieira.paymeapi.modules.utils.TokenUtil.extractTokenRequest;


public class AuthorizationTokenInterceptor implements HandlerInterceptor {

    private static final String ENDPOINT_PROTEGIDO = "/api/";
    private static final String OPTIONS_METHOD = "OPTIONS";

    private final JwtService jwtService;

    public AuthorizationTokenInterceptor(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) {
        if (possuiUrlQueNecessitaAutenticacao(request)) {
            return isOptions(request) || possuiAuthorizationHeaderComTokenValido(request);
        }
        return true;
    }

    private boolean possuiUrlQueNecessitaAutenticacao(HttpServletRequest request) {
        return request.getRequestURI().contains(ENDPOINT_PROTEGIDO);
    }

    private boolean possuiAuthorizationHeaderComTokenValido(HttpServletRequest request) {
        var token = extractTokenRequest(request);
        return jwtService.possuiUsuarioAutenticado(token);
    }

    private boolean isOptions(HttpServletRequest request) {
        return request.getMethod().equals(OPTIONS_METHOD);
    }
}
