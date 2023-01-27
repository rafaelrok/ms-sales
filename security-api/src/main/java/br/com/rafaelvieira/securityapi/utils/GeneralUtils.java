package br.com.rafaelvieira.securityapi.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import br.com.rafaelvieira.securityapi.modules.auth.dto.SocialProvider;
import br.com.rafaelvieira.securityapi.modules.auth.model.Role;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

/**
 * @author rafae
 */

public class GeneralUtils {
    public static List<SimpleGrantedAuthority> buildSimpleGrantedAuthorities(final Set<Role> roles) {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        for (Role role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }
        return authorities;
    }

    public static SocialProvider toSocialProvider(String providerId) {
        for (SocialProvider socialProvider : SocialProvider.values()) {
            if (socialProvider.getProviderType().equals(providerId)) {
                return socialProvider;
            }
        }
        return SocialProvider.LOCAL;
    }
}
