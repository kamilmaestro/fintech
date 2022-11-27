package kamilmarnik.fintech.bank.domain;

import java.util.Optional;
import java.util.UUID;

interface BankRepository {

  Account save(Account account);

  Optional<Account> findById(UUID id);

}
