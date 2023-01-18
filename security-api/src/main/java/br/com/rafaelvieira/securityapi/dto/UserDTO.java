package br.com.rafaelvieira.securityapi.dto;


import br.com.rafaelvieira.securityapi.model.Role;
import br.com.rafaelvieira.securityapi.model.User;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author rafae
 */

@Getter
@Setter
public class UserDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private UUID id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private List<Role> roles = new ArrayList<>();

    public UserDTO() {
    }

    public UserDTO(User user) {
        this.id = user.getId();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();;
        this.roles = user.getRoles();
    }

}
