package br.com.rafaelvieira.securityapi.modules.auth.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

/**
 * @author rafae
 */

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "token_Validation")
public class TokenValidation implements Serializable{
    private static final long serialVersionUID = 1L;
    private static final int EXPIRATION = 60*24;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private UUID id;
    @Column(name = "token", nullable = false)
    private String token;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "expiry_date", nullable = false)
    private Date expiryDate;

    public TokenValidation(String token) {
        this.token = token;
        this.expiryDate = calculateExpiryDate(EXPIRATION);
    }

    public TokenValidation(final String token, final User user) {
        this.token = token;
        this.user = user;
        this.expiryDate = calculateExpiryDate(EXPIRATION);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TokenValidation that = (TokenValidation) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    private Date calculateExpiryDate(final int expiryTimeInMinutes) {
        final Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(System.currentTimeMillis()) ;
        cal.add(Calendar.MINUTE, expiryTimeInMinutes);
        return new Date(cal.getTime().getTime());
    }

    public void updateToken(final String token){
        this.token = token;
        this.expiryDate = calculateExpiryDate(EXPIRATION);
    }
}
