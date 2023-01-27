package br.com.rafaelvieira.securityapi.modules.auth.model;

import br.com.rafaelvieira.securityapi.modules.auth.dto.UserDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;

/**
 * @author rafae
 */

@Getter
@Setter
@Entity
@Builder
@ToString
@AllArgsConstructor
@Table(name = "tb_user")
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private UUID id;

    @Column(name = "PROVIDER_USER_ID")
    private String providerUserId;

    @Column(name = "FIRST_NAME", nullable = false)
    private String firstName;

    @Column(name = "LAST_NAME", nullable = false)
    private String lastName;

    @Column(name = "EMAIL", unique = true, nullable = false)
    private String email;

    @Column(name = "PASSWORD", nullable = false)
    private String password;

    @Column(name = "ENABLED", nullable = false, columnDefinition = "BIT", length = 1)
    private boolean enabled;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED_AT", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "MODIFIED_DATE", nullable = false)
    protected Date modifiedDate;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "tb_user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    public User(){
        getRoles().add(new Role(1L, "CLIENT"));
        this.enabled = false;
    }

    public User(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public User(UUID id, String providerUserId, String firstName, String lastName, String email, String password, LocalDateTime createdAt, Date modifiedDate, boolean enabled) {
        this.id = id;
        this.providerUserId = providerUserId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        getRoles().add(new Role(1L, "ROLE_USER"));
        this.createdAt = createdAt;
        this.modifiedDate = modifiedDate;
        this.enabled = enabled;
    }

    public User(UserDTO userDTO) {
        this.id = userDTO.getId();
        this.firstName = userDTO.getFirstName();
        this.lastName = userDTO.getLastName();
        this.email = userDTO.getEmail();
        this.password = userDTO.getPassword();
        //userDTO.getRoles().forEach(role -> this.roles.add(new Role(role.getId(), role.getName())));
        //this.enabled = userDTO.isEnabled();


    }
    public User(User user){
        super();
        this.id = user.getId();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.roles = user.getRoles();
    }

}
