package kamilmarnik.fintech.bank.domain;

import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;

final class InMemoryBankRepository implements BankRepository {

  HashMap<UUID, Account> bank = new HashMap<>();

  @Override
  public Optional<Account> findById(UUID id) {
    return Optional.ofNullable(bank.get(id));
  }

  @Override
  public Account save(Account account) {
    bank.put(account.getId(), account);
    return account;
  }

}
