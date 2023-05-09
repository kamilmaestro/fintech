package kamilmarnik.fintech.bank.domain;

import kamilmarnik.fintech.bank.dto.AccountDto;
import kamilmarnik.fintech.bank.exception.AccountBlocked;
import kamilmarnik.fintech.bank.exception.InvalidAccountCreation;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
final class Account {

  UUID id;
  AccountBalance accountBalance;

  boolean blocked;

  private Account(UUID id, AccountBalance accountBalance, boolean blocked) {
    this.id = id;
    this.accountBalance = accountBalance;
    this.blocked = blocked;
  }

  static Account create(UUID accountId) {
    if (accountId == null) {
      throw new InvalidAccountCreation();
    }
    return new Account(accountId, AccountBalance.emptyForNewAccount(), false);
  }

  Account deposit(BigDecimal depositValue) {
    if(blocked) {
      throw new AccountBlocked();
    }
    return new Account(id, accountBalance.deposit(depositValue), blocked);
  }

  Account withdraw(BigDecimal value) {
    if(blocked) {
      throw new AccountBlocked();
    }
    return new Account(id, accountBalance.withdraw(value), blocked);
  }

  Account block() {
    return new Account(id, accountBalance, true);
  }

  Account unblock() {
    return new Account(id, accountBalance, false);
  }

  AccountDto dto() {
    return new AccountDto(id, accountBalance.getValueAsBigDecimal(), blocked);
  }

}
