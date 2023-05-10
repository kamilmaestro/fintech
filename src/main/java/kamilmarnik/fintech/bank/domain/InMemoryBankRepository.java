package kamilmarnik.fintech.bank.domain;

import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;

final class InMemoryBankRepository implements BankRepository {

  HashMap<UUID, Account> bank = new HashMap<>();

  @Override
  public Account save(Account account) {
    bank.put(account.getId(), account);
    return account;
  }

  @Override
  public boolean delete(Account account) {
    return bank.remove(account.getId(), account);
  }

  @Override
  public Optional<Account> findById(UUID id) {
    return Optional.ofNullable(bank.get(id));
  }

}
