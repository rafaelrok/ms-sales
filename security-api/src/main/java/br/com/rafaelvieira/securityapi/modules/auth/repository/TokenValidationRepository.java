package br.com.rafaelvieira.securityapi.modules.auth.repository;

import br.com.rafaelvieira.securityapi.modules.auth.model.TokenValidation;
import br.com.rafaelvieira.securityapi.modules.auth.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * @author rafae
 */
@Repository
public interface TokenValidationRepository extends JpaRepository<TokenValidation, UUID> {
    Optional<TokenValidation> findByToken(String token);
    Optional<TokenValidation> findByUserId(User user);
}
