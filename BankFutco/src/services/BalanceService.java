package services;

import java.util.List;
import java.util.Optional;

import model.Balance;
import repositories.BalanceRepository;

public class BalanceService implements IBalanceService {

    private final BalanceRepository balanceRepository;

    public BalanceService() {
        this(new BalanceRepository());
    }

    public BalanceService(BalanceRepository balanceRepository) {
        this.balanceRepository = balanceRepository;
    }

    @Override
    public Balance save(Balance balance) {
        return balanceRepository.save(balance);
    }

    @Override
    public Optional<Balance> findById(String id) {
        return balanceRepository.findById(id);
    }

    @Override
    public List<Balance> findAll() {
        return balanceRepository.findAll();
    }

    @Override
    public boolean deleteById(String id) {
        return balanceRepository.deleteById(id);
    }
}
