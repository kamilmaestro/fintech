package kamilmarnik.fintech.bank.domain;

import kamilmarnik.fintech.bank.exception.InvalidDeposit;
import kamilmarnik.fintech.bank.exception.InvalidWithdrawal;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.math.RoundingMode;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
final class AccountBalance implements Comparable<AccountBalance> {

  BigDecimal balance;

  static AccountBalance emptyForNewAccount() {
    return new AccountBalance(BigDecimal.ZERO);
  }

  BigDecimal getValueAsBigDecimal() {
    return balance.setScale(2, RoundingMode.HALF_EVEN);
  }

  AccountBalance withdraw(BigDecimal toWithdraw) {
    if (toWithdraw == null || toWithdraw.compareTo(this.balance) > 0) {
      throw new InvalidWithdrawal();
    }
    return new AccountBalance(this.balance.subtract(toWithdraw));
  }

  AccountBalance deposit(BigDecimal toDeposit) {
    if (toDeposit == null || BigDecimal.ZERO.compareTo(toDeposit) >= 0) {
      throw new InvalidDeposit();
    }
    return new AccountBalance(this.balance.add(toDeposit));
  }

  @Override
  public int compareTo(AccountBalance balance) {
    return getValueAsBigDecimal().compareTo(balance.getValueAsBigDecimal());
  }

}
