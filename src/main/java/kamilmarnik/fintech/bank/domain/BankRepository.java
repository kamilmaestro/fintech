package kamilmarnik.fintech.bank.domain;

import java.util.Optional;
import java.util.UUID;

interface BankRepository {

  Optional<Account> findById(UUID id);

  Account save(Account account);

}
