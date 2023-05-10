package kamilmarnik.fintech.bank.domain;

import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;

/**
 * Maksymilian Ulanecki
 */


final class InMemoryBankRepository implements BankRepository {

  HashMap<UUID, Account> accounts = new HashMap<>();

  @Override
  public Account save(Account account) {
    accounts.put(account.getId(), account);
    return account;
  }

  @Override
  public Optional<Account> findById(UUID id) {
    return Optional.ofNullable(accounts.get(id));
  }

}
