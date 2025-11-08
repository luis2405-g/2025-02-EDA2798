package repositories;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import model.Cards;

public class CardRepository {

    private final List<Cards> cards = new ArrayList<>();

    public Cards save(Cards card) {
        Optional<Cards> existing = findById(card.getCardNumber());
        existing.ifPresent(cards::remove);
        cards.add(card);
        return card;
    }

    public Optional<Cards> findById(String cardNumber) {
        return cards.stream()
                .filter(c -> c.getCardNumber().equals(cardNumber))
                .findFirst();
    }

    public List<Cards> findAll() {
        return new ArrayList<>(cards);
    }

    public boolean deleteById(String cardNumber) {
        return findById(cardNumber)
                .map(cards::remove)
                .orElse(false);
    }
}
