package br.com.rafaelvieira.paymeapi.modules.card.model;

import br.com.rafaelvieira.paymeapi.modules.integration.dto.card.CardClientResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author rafae
 */

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "card")
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;
    @Column(name = "card_id", nullable = false)
    private String cardId;
    @Column(name = "user_id", nullable = false)
    private String userId;
    @Column(name = "last_digits", nullable = false)
    private String lastDigits;
    @Column(name = "brand", nullable = false)
    private String brand;
    @Column(name = "country", nullable = false)
    private String country;
    @Column(name = "create_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    @Column(name = "update_at", nullable = false)
    private LocalDateTime updatedAt;


    @PrePersist
    public void prePersist() {
        this.updatedAt = LocalDateTime.now();
    }

    public static Card convertFrom(CardClientResponse cardResponse,
                                   String userId){
        var card = new Card();
        BeanUtils.copyProperties(cardResponse, card);
        card.setCardId(cardResponse.getId());
        card.setUserId(userId);
        return card;
    }
}
