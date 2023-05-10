package kamilmarnik.fintech.bank.domain;

import kamilmarnik.fintech.bank.dto.AccountDto;
import kamilmarnik.fintech.bank.exception.InvalidAccountCreation;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Maksymilian Ulanecki
 */

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
final class Account {

  UUID id;
  AccountBalance accountBalance;

  static Account create(UUID accountId) {
    if (accountId == null) {
      throw new InvalidAccountCreation();
    }
    return new Account(accountId, AccountBalance.emptyForNewAccount());
  }

  Account deposit(BigDecimal depositValue) {
    return new Account(id, accountBalance.deposit(depositValue));
  }

  Account withdraw(BigDecimal value) {
    return new Account(id, accountBalance.withdraw(value));
  }

  AccountDto dto() {
    return new AccountDto(id, accountBalance.getValueAsBigDecimal());
  }

}
