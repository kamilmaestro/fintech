package kamilmarnik.fintech.bank.domain;

import kamilmarnik.fintech.bank.dto.AccountDto;
import kamilmarnik.fintech.bank.dto.Withdrawal;
import kamilmarnik.fintech.bank.exception.InvalidAccountCreation;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
final class Account {

  UUID id;
  AccountBalance accountBalance;

  private Account(UUID id, AccountBalance accountBalance) {
    this.id = id;
    this.accountBalance = accountBalance;
  }

  static Account create(UUID accountId) {
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
