package br.com.rafaelvieira.paymeapi.modules.card.repository;

import br.com.rafaelvieira.paymeapi.modules.card.model.Card;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * @author rafae
 */

public interface CardRepository extends JpaRepository<Card, Integer> {

    List<Card> findAllByUserId(String userId);
    Optional<Card> findByCardId(String cardId);
    Optional<Card> findByCardIdAndUserId(String cardId, String userId);
    Boolean existsByCardIdAndUserId(String cardId, String userId);
}
