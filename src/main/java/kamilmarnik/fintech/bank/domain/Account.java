package kamilmarnik.fintech.bank.domain;

import kamilmarnik.fintech.bank.dto.AccountDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.UUID;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
final class Account {

  UUID id;
  BigDecimal accountBalance;

  static Account create(UUID accountId) {
    if (accountId == null) {
      throw new IllegalStateException("Can not create an account without an ID");
    }
    return new Account(accountId, BigDecimal.ZERO);
  }

  Account deposit(BigDecimal depositValue) {
    if (depositValue == null || BigDecimal.ZERO.compareTo(depositValue) >= 0) {
      throw new IllegalStateException();
    }
    return new Account(id, accountBalance.add(depositValue));
  }

  Account withdraw(BigDecimal value) {
    if (value == null) {
      throw new IllegalStateException();
    }
    if (value.compareTo(accountBalance) > 0) {
      throw new IllegalStateException();
    }
    return new Account(id, accountBalance.subtract(value));
  }

  AccountDto dto() {
    return new AccountDto(id, accountBalance);
  }

}
