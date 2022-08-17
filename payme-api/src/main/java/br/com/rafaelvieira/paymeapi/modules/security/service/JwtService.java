package br.com.rafaelvieira.paymeapi.modules.security.service;

import br.com.rafaelvieira.paymeapi.config.exceptions.AuthenticationException;
import br.com.rafaelvieira.paymeapi.modules.security.dto.JwtResponse;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import static br.com.rafaelvieira.paymeapi.modules.utils.RequestUtil.getCurrentRequest;
import static br.com.rafaelvieira.paymeapi.modules.utils.TokenUtil.extractTokenRequest;
import static org.springframework.util.ObjectUtils.isEmpty;


@Slf4j
@Service
public class JwtService {

    private static final AuthenticationException TOKEN_ACESSO_INVALIDO
            = new AuthenticationException("Token de acesso inválido.");

    @Value("${jwt.secret}")
    private String secret;

    public boolean possuiUsuarioAutenticado(String token) {
        var usuario = recuperarDadosDoUsuarioDoToken(token);
        return !isEmpty(usuario);
    }

    public JwtResponse recuperarUsuarioAutenticado() {
        var token = extractTokenRequest(getCurrentRequest());
        return recuperarDadosDoUsuarioDoToken(token);
    }

    public JwtResponse recuperarDadosDoUsuarioDoToken(String jwt) {
        try {
            return JwtResponse.of(descriptografarJwt(jwt).getBody());
        } catch (Exception ex) {
            log.error("Erro ao processar token de acesso: ".concat(jwt).concat(". Erro: "), ex);
            throw TOKEN_ACESSO_INVALIDO;
        }
    }

    private Jws<Claims> descriptografarJwt(String jwt) {
        try {
            return Jwts
                    .parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor(secret.getBytes()))
                    .build()
                    .parseClaimsJws(jwt);
        } catch (Exception ex) {
            log.error("Erro ao processar token de acesso: ".concat(jwt).concat(". Erro: "), ex);
            throw TOKEN_ACESSO_INVALIDO;
        }
    }
}
