package br.com.rafaelvieira.securityapi.dto;

import br.com.rafaelvieira.securityapi.model.Role;
import lombok.*;

import java.io.Serializable;

/**
 * @author rafae
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RoleDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String name;
    private String authority;

    public RoleDTO(Role role) {
        super();
        id = role.getId();
        name = role.getName();
        authority = role.getAuthority();
    }
}
