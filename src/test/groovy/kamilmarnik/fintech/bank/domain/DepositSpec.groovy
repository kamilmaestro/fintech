package kamilmarnik.fintech.bank.domain

import kamilmarnik.fintech.bank.dto.AccountDto
import kamilmarnik.fintech.bank.dto.Deposit
import kamilmarnik.fintech.bank.exception.AccountNotFound
import kamilmarnik.fintech.bank.exception.InvalidDeposit
import spock.lang.Unroll

/**
 * Maksymilian Ulanecki
 */


class DepositSpec extends BankBaseSpec {

  @Unroll
  def "should deposit money to an account" () {
    given: "there is an account"
      AccountDto account = bankFacade.createAccount(FIRST_ACCOUNT_ID)
    when: "deposits sum of money: $value to an account"
      AccountDto depositedAccount = bankFacade.deposit(new Deposit(account.id(), value))
    then: "account has balance equal: $value"
      depositedAccount.accountBalance() == value
    where:
      value << [BigDecimal.TEN, new BigDecimal("1.45")]
  }

  @Unroll
  def "should deposit money to an already deposited account" () {
    given: "there is an account"
      AccountDto account = bankFacade.createAccount(FIRST_ACCOUNT_ID)
    and: "this account has balance equal $startingBalance"
      bankFacade.deposit(new Deposit(account.id(), startingBalance))
    when: "deposits money: $depositedValue to an account once more"
      AccountDto depositedAccount = bankFacade.deposit(new Deposit(account.id(), depositedValue))
    then: "account contains sum of money equal: $calculatedBalance"
      depositedAccount.accountBalance() == calculatedBalance
    where:
      startingBalance       | depositedValue          ||  calculatedBalance
      BigDecimal.TEN        | BigDecimal.ONE          ||  new BigDecimal("11")
      BigDecimal.ONE        | new BigDecimal("3.21")  ||  new BigDecimal("4.21")
      new BigDecimal("38")  | new BigDecimal("17")    ||  new BigDecimal("55")
  }

  def "should not deposit money to a non-existing account" () {
    when: "deposits money to an account"
      bankFacade.deposit(new Deposit(FIRST_ACCOUNT_ID, BigDecimal.TEN))
    then: "account does not exist"
      thrown(AccountNotFound)
  }

  @Unroll
  def "should not deposit money with an improper value" () {
    given: "there is an account"
      AccountDto account = bankFacade.createAccount(FIRST_ACCOUNT_ID)
    when: "deposits sum of money: $improperValue to an account"
     bankFacade.deposit(new Deposit(account.id(), improperValue))
    then: "account can not be deposited with the sum of money equal: $improperValue"
      thrown(InvalidDeposit)
    where:
      improperValue << [null, BigDecimal.ZERO, new BigDecimal("-5")]
  }

}
