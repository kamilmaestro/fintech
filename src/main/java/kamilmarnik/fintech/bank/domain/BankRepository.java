package kamilmarnik.fintech.bank.domain;

import java.util.Optional;
import java.util.UUID;

/**
 * Maksymilian Ulanecki
 */


interface BankRepository {

  Account save(Account account);

  Optional<Account> findById(UUID id);

}
