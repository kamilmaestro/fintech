package kamilmarnik.fintech.bank.domain;

import kamilmarnik.fintech.bank.dto.AccountDto;
import kamilmarnik.fintech.bank.exception.InvalidAccountCreation;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
final class Account {

  UUID id;
  AccountBalance accountBalance;
  Limits limits;
  private Account(UUID id, AccountBalance accountBalance, Limits limits) {
    this.id = id;
    this.accountBalance = accountBalance;
    this.limits = limits;
  }

  static Account create(UUID accountId,Instant currentDate) {
    if (accountId == null) {
      throw new InvalidAccountCreation();
    }
    return new Account(accountId, AccountBalance.emptyForNewAccount(), Limits.emptyForNewAccount(currentDate));
  }

  AccountDto dto() {
    return new AccountDto(id, accountBalance.getValueAsBigDecimal());
  }

  Account withdraw(BigDecimal value, Instant currentDate) {
    limits.checkLimits(value,currentDate);
    return new Account(id, accountBalance.withdraw(value), limits);
  }

  Account deposit(BigDecimal depositValue) {
    return new Account(id, accountBalance.deposit(depositValue), limits);
  }

}
