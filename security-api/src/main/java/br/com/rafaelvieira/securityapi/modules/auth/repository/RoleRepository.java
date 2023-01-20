package br.com.rafaelvieira.securityapi.modules.auth.repository;

import br.com.rafaelvieira.securityapi.modules.auth.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author rafae
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String name);
    Optional<Role> findByAuthority(String authority);
}

