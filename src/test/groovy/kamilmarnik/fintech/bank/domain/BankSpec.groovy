package kamilmarnik.fintech.bank.domain

import kamilmarnik.fintech.bank.dto.AccountDto
import spock.lang.Specification
import spock.lang.Unroll

class BankSpec extends Specification implements AccountSample {

  BankFacade bankFacade = new BankFacade(new InMemoryBankRepository())

//  Aplikacja bankowa
//  Jako firma zajmująca się szeroko pojętym fintechem potrzebujemy mikroserwisu do przechowywania stanu konta naszych użytkowników.
//
//      Wymagania:
//  - doładowanie konta użytkownika przez interfejs webowy
//  - wycofanie środków z konta użytkownika przez interfejs webowy
//  - pobranie 10 użytkowników z największym stanem kont
//  - nie musimy implementować komunikacji z bazą danych, na razie zrobimy repozytorium in memory
//  - nie jest na chwilę obecną ważne dla nas security
//  - aplikacja powinna być n
//  - aplikacja powinna być napisana w Javie 17 i Springu.

  @Unroll
  def "should create an account" () {
    when: "creates a new account"
      AccountDto account = bankFacade.createAccount(accountId)
    then: "a new account is created with an empty balance"
      account.id() == accountId
      account.accountBalance() == BigDecimal.ZERO
    where:
      accountId << [JOHN_ACCOUNT_ID, MARC_ACCOUNT_ID]
  }

  def "should not create a new account with an improper ID" () {
    when: "creates a new account with an improper ID"
      bankFacade.createAccount(null)
    then: "account is not created"
      thrown(IllegalStateException)
  }

  @Unroll
  def "should deposit money to an account" () {
    given: "there is an account"
      AccountDto account = bankFacade.createAccount(JOHN_ACCOUNT_ID)
    when: "deposits sum of money: $value to an account"
      AccountDto depositedAccount = bankFacade.deposit(account.id(), value)
    then: "account has balance equal: $value"
      depositedAccount.accountBalance() == value
    where:
      value << [BigDecimal.TEN, BigDecimal.ONE]
  }

  @Unroll
  def "should deposit money to an already deposited account" () {
    given: "there is an account"
      AccountDto account = bankFacade.createAccount(JOHN_ACCOUNT_ID)
    and: "this account has balance equal $startingBalance"
      bankFacade.deposit(account.id(), startingBalance)
    when: "deposits money: $depositedValue to an account once more"
      AccountDto depositedAccount = bankFacade.deposit(account.id(), depositedValue)
    then: "account contains sum of money equal: $calculatedBalance"
      depositedAccount.accountBalance() == calculatedBalance
    where:
      startingBalance       | depositedValue        ||  calculatedBalance
      BigDecimal.TEN        | BigDecimal.ONE        ||  new BigDecimal("11")
      BigDecimal.ONE        | new BigDecimal("3")   ||  new BigDecimal("4")
      new BigDecimal("38")  | new BigDecimal("17")  ||  new BigDecimal("55")
  }

  def "should not deposit money to a non-existing account" () {
    when: "deposits money to an account"
      bankFacade.deposit(JOHN_ACCOUNT_ID, BigDecimal.TEN)
    then: "account does not exist"
      thrown(IllegalStateException)
  }

  @Unroll
  def "should not deposit money with an improper value" () {
    given: "there is an account"
      AccountDto account = bankFacade.createAccount(JOHN_ACCOUNT_ID)
    when: "deposits sum of money: $improperValue to an account"
      bankFacade.deposit(account.id(), improperValue)
    then: "account can not be deposited with the sum of money equal: $improperValue"
      thrown(IllegalStateException)
    where:
      improperValue << [null, BigDecimal.ZERO, new BigDecimal("-5")] // new BigDecimal("1.56")?
  }

  @Unroll
  def "should withdraw money from an account" () {
    given: "there is an account"
      AccountDto account = bankFacade.createAccount(JOHN_ACCOUNT_ID)
    and: "this account has balance equal $startingBalance"
      bankFacade.deposit(account.id(), startingBalance)
    when: "withdraws amount of money: $withrawnValue from this account"
      AccountDto afterWithdrawal = bankFacade.withdraw(account.id(), withrawnValue)
    then: "account balance is changed to: $calculatedBalance"
      afterWithdrawal.accountBalance() == calculatedBalance
    where:
      startingBalance       | withrawnValue         ||  calculatedBalance
      BigDecimal.TEN        | BigDecimal.ONE        ||  new BigDecimal("9")
      new BigDecimal("3")   | new BigDecimal("3")   ||  BigDecimal.ZERO
      new BigDecimal("53")  | new BigDecimal("21")  ||  new BigDecimal("32")
  }

  @Unroll
  def "should not withdraw money from an account when exceeding the current balance" () {
    given: "there is an account"
      AccountDto account = bankFacade.createAccount(JOHN_ACCOUNT_ID)
    and: "this account has balance equal $startingBalance"
      bankFacade.deposit(account.id(), startingBalance)
    when: "withdraws amount of money: $withrawnValue from this account"
      bankFacade.withdraw(account.id(), withrawnValue)
    then: "money can not be withdrawn from the account"
      thrown(IllegalStateException)
    where:
      startingBalance       | withrawnValue
      BigDecimal.TEN        | new BigDecimal("21")
      new BigDecimal("3")   | new BigDecimal("3.5")
      new BigDecimal("53")  | new BigDecimal("54")
  }

  def "should not withdraw money from a non-existing account" () {
    when: "withdraws money from a non-existing account"
      bankFacade.withdraw(JOHN_ACCOUNT_ID, BigDecimal.TEN)
    then: "money can not be withdrawn from a non-existing account"
      thrown(IllegalStateException)
  }

//  def "should get 10 accounts with the biggest balance" () {
//    given: ""
//    when: ""
//    then: ""
//  }

}
