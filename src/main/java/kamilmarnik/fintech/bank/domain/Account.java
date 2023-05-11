package kamilmarnik.fintech.bank.domain;

import kamilmarnik.fintech.bank.dto.AccountDto;
import kamilmarnik.fintech.bank.exception.InvalidAccountCreation;
import kamilmarnik.fintech.bank.exception.InvalidWithdrawal;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
final class Account {
  UUID id;
  AccountBalance accountBalance;
  String type;

  private Account(UUID id, AccountBalance accountBalance, String type) {
    this.id = id;
    this.accountBalance = accountBalance;
    this.type = type;
  }

  static Account create(UUID accountId) {
    if (accountId == null) {
      throw new InvalidAccountCreation();
    }
    return new Account(accountId, AccountBalance.emptyForNewAccount(), "Default");
  }

  static Account createStockAccount(UUID accountId, BigDecimal balance) {
    if (accountId == null || balance == null || balance.compareTo(BigDecimal.ZERO) <= 0) {
      throw new InvalidAccountCreation();
    }
    return new Account(accountId, AccountBalance.withBalance(balance), "Stock");
  }
  static Account createSavingAccount(UUID accountId){
    if (accountId == null) {
      throw new InvalidAccountCreation();
    }
    return new Account(accountId, AccountBalance.emptyForNewAccount(), "Saving");
  }

  Account deposit(BigDecimal depositValue) {
    return new Account(id, accountBalance.deposit(depositValue), type);
  }

  Account withdraw(BigDecimal value) {
    if(Objects.equals(this.type, "Saving")){
      BigDecimal sub = this.accountBalance.getValueAsBigDecimal().subtract(value);
      if(sub.compareTo(new BigDecimal(100)) < 0){
        throw new InvalidWithdrawal();
      }
    }
    return new Account(id, accountBalance.withdraw(value), type);
  }

  AccountDto dto() {
    return new AccountDto(id, accountBalance.getValueAsBigDecimal(),type);
  }

}
