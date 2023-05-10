package kamilmarnik.fintech.bank.domain

import kamilmarnik.fintech.bank.dto.AccountDto
import kamilmarnik.fintech.bank.dto.CurrencyDto
import kamilmarnik.fintech.bank.dto.Deposit
import kamilmarnik.fintech.bank.exception.AccountNotFound
import kamilmarnik.fintech.bank.exception.InvalidCurrency
import kamilmarnik.fintech.bank.exception.InvalidDeposit
import spock.lang.Unroll

/**
 * Maksymilian Ulanecki
 */


class DepositSpec extends BankBaseSpec {
  AccountDto account;

  def setup(){
    given: "there is an account"
      account = bankFacade.createAccount(FIRST_ACCOUNT_ID, CurrencyDto.PLN)
  }

  @Unroll
  def "should deposit money to an account" () {
    when: "deposits sum of money: $value to an account"
      AccountDto depositedAccount = bankFacade.deposit(new Deposit(account.id(), value, CurrencyDto.PLN))
    then: "account has balance equal: $value"
      depositedAccount.accountBalance() == value
    where:
      value << [BigDecimal.TEN, new BigDecimal("1.45")]
  }

  @Unroll
  def "should deposit money to an already deposited account" () {
    given: "this account has balance equal $startingBalance"
      bankFacade.deposit(new Deposit(account.id(), startingBalance, CurrencyDto.PLN))
    when: "deposits money: $depositedValue to an account once more"
      AccountDto depositedAccount = bankFacade.deposit(new Deposit(account.id(), depositedValue, CurrencyDto.PLN))
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
      bankFacade.deposit(new Deposit(SECOND_ACCOUNT_ID, BigDecimal.TEN, CurrencyDto.PLN))
    then: "account does not exist"
      thrown(AccountNotFound)
  }

  @Unroll
  def "should not deposit money with an improper value" () {
    when: "deposits sum of money: $improperValue to an account"
     bankFacade.deposit(new Deposit(account.id(), improperValue, CurrencyDto.PLN))
    then: "account can not be deposited with the sum of money equal: $improperValue"
      thrown(InvalidDeposit)
    where:
      improperValue << [null, BigDecimal.ZERO, new BigDecimal("-5")]
  }

//  def "should not deposit money with different currency"(){
//    when: "deposits sum of 1 EUR to an account"
//      bankFacade.deposit(new Deposit(account.id(), BigDecimal.ONE, CurrencyDto.EUR))
//    then: "account can not be deposited with the sum of money in different currency:"
//      thrown(InvalidDeposit)
//  }

  @Unroll
  def "should deposit money with different currency" () {
    when: "deposits sum of money: $value to an account in: $currency"
      bankFacade.deposit(new Deposit(account.id(), value, currency))
    then: "account has balance equal: $valueInDifferentCurrency"
      account.accountBalance() == valueInDifferentCurrency;
    where:
      currency        |     value               ||  valueInDifferentCurrency
      CurrencyDto.EUR |  new BigDecimal("100")  ||  new BigDecimal("450")
      CurrencyDto.USD |  new BigDecimal("100")  ||  new BigDecimal("400")
  }


  def "should not deposit money without currency"(){
    when: "deposits sum of 1 EUR to an account"
      bankFacade.deposit(new Deposit(account.id(), BigDecimal.ONE, null))
    then: "account can not be deposited with the sum of money in different currency:"
      thrown(InvalidCurrency)
  }

}
