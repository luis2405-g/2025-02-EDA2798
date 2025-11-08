package repositories;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import model.Loans;

public class LoansRepository {

    private final List<Loans> loansList = new ArrayList<>();

    public Loans save(Loans loan) {
        Optional<Loans> existing = findById(loan.getDate().toString());
        existing.ifPresent(loansList::remove);
        loansList.add(loan);
        return loan;
    }

    public Optional<Loans> findById(String id) {
        return loansList.stream()
                .filter(l -> l.getDate().toString().equals(id))
                .findFirst();
    }

    public List<Loans> findAll() {
        return new ArrayList<>(loansList);
    }

    public boolean deleteById(String id) {
        return findById(id)
                .map(loansList::remove)
                .orElse(false);
    }
}
