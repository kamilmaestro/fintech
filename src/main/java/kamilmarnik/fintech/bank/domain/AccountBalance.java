package kamilmarnik.fintech.bank.domain;

import kamilmarnik.fintech.bank.dto.Operation;
import kamilmarnik.fintech.bank.exception.InvalidDeposit;
import kamilmarnik.fintech.bank.exception.InvalidWithdrawal;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import static kamilmarnik.fintech.bank.domain.BankOperation.*;
import static kamilmarnik.fintech.bank.domain.BankOperation.Type.*;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
final class AccountBalance implements Comparable<AccountBalance> {

  BigDecimal balance;
  @Getter
  List<BankOperation> history;

  static AccountBalance emptyForNewAccount() {
    return new AccountBalance(BigDecimal.ZERO, new ArrayList<>());
  }

  BigDecimal getValueAsBigDecimal() {
    return balance.setScale(2, RoundingMode.HALF_EVEN);
  }

  AccountBalance withdraw(BigDecimal toWithdraw) {
    if (toWithdraw == null || toWithdraw.compareTo(this.balance) > 0 || BigDecimal.ZERO.compareTo(toWithdraw) >= 0 ) {
      throw new InvalidWithdrawal();
    }
    List<BankOperation> collect = addOperation(WITHDRAWAL, toWithdraw);
    return new AccountBalance(this.balance.subtract(toWithdraw),collect);
  }

  AccountBalance deposit(BigDecimal toDeposit) {
    if (toDeposit == null || BigDecimal.ZERO.compareTo(toDeposit) >= 0) {
      throw new InvalidDeposit();
    }
    List<BankOperation> collect = addOperation(DEPOSIT, toDeposit);
    return new AccountBalance(this.balance.add(toDeposit),collect);
  }

  private List<BankOperation> addOperation(Type type, BigDecimal value ) {
    List<BankOperation> collect = new ArrayList<>(history);
    collect.add(new BankOperation(type, value));
    return collect;
  }

  Operation getLastOperation(){
    return history.get(history.size()-1).dto();
  }

  @Override
  public int compareTo(AccountBalance balance) {
    return getValueAsBigDecimal().compareTo(balance.getValueAsBigDecimal());
  }

}
