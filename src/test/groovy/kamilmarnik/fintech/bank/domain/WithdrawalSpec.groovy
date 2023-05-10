package kamilmarnik.fintech.bank.domain

import kamilmarnik.fintech.bank.dto.AccountDto
import kamilmarnik.fintech.bank.dto.Deposit
import kamilmarnik.fintech.bank.dto.Withdrawal
import kamilmarnik.fintech.bank.exception.AccountNotFound
import kamilmarnik.fintech.bank.exception.InvalidWithdrawal
import spock.lang.Unroll

class WithdrawalSpec extends BankBaseSpec {

  @Unroll
  def "should withdraw money from an account" () {
    given: "there is an account"
      AccountDto account = bankFacade.createAccount(FIRST_ACCOUNT_ID)
    and: "this account has balance equal $startingBalance"
      bankFacade.deposit(new Deposit(account.id(), startingBalance))
    when: "withdraws amount of money: $withrawnValue from this account"
      AccountDto afterWithdrawal = bankFacade.withdraw(new Withdrawal(account.id(), withrawnValue))
    then: "account balance is changed to: $calculatedBalance"
      afterWithdrawal.accountBalance() == calculatedBalance
    where:
      startingBalance         | withrawnValue           ||  calculatedBalance
      BigDecimal.TEN          | BigDecimal.ONE          ||  new BigDecimal("9")
      new BigDecimal("3.50")  | new BigDecimal("3.50")  ||  BigDecimal.ZERO
      new BigDecimal("53")    | new BigDecimal("21.10") ||  new BigDecimal("31.90")
  }

  def "should not withdraw money from a non-existing account" () {
    when: "withdraws money from a non-existing account"
      bankFacade.withdraw(new Withdrawal(FIRST_ACCOUNT_ID, BigDecimal.TEN))
    then: "money can not be withdrawn from a non-existing account"
      thrown(AccountNotFound)
  }

  def "should not withdraw an improper amount of money" () {
    given: "there is an account"
      AccountDto account = bankFacade.createAccount(FIRST_ACCOUNT_ID)
    and: "this account has balance equal $BigDecimal.TEN"
      bankFacade.deposit(new Deposit(account.id(), BigDecimal.TEN))
    when: "withdraws an improper amount of money"
      bankFacade.withdraw(new Withdrawal(FIRST_ACCOUNT_ID, null))
    then: "money can not be withdrawn due to invalid value"
      thrown(InvalidWithdrawal)
  }

  @Unroll
  def "should not withdraw money from an account when exceeding the current balance" () {
    given: "there is an account"
      AccountDto account = bankFacade.createAccount(FIRST_ACCOUNT_ID)
    and: "this account has balance equal $startingBalance"
      bankFacade.deposit(new Deposit(account.id(), startingBalance))
    when: "withdraws amount of money: $withrawnValue from this account"
      bankFacade.withdraw(new Withdrawal(account.id(), withrawnValue))
    then: "money can not be withdrawn from the account"
      thrown(InvalidWithdrawal)
    where:
      startingBalance       | withrawnValue
      BigDecimal.TEN        | new BigDecimal("21")
      new BigDecimal("3")   | new BigDecimal("3.5")
      new BigDecimal("53")  | new BigDecimal("54")
  }

}
