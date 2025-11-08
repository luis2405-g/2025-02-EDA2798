package repositories;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import model.Balance;

public class BalanceRepository {

    private final List<Balance> balances = new ArrayList<>();

    // Guarda o reemplaza un balance
    public Balance save(Balance balance) {
        Optional<Balance> existing = findById(balance.getDate().toString());
        existing.ifPresent(balances::remove); // si ya existe, lo reemplaza
        balances.add(balance);
        return balance;
    }

    // Busca por ID (en este caso, el id = fecha convertida a String)
    public Optional<Balance> findById(String id) {
        return balances.stream()
                .filter(b -> b.getDate().toString().equals(id))
                .findFirst();
    }

    // Retorna todos los balances
    public List<Balance> findAll() {
        return new ArrayList<>(balances);
    }

    // Elimina por ID
    public boolean deleteById(String id) {
        return findById(id)
                .map(balances::remove)
                .orElse(false);
    }
}
