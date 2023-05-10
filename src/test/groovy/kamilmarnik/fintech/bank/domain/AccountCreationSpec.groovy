package kamilmarnik.fintech.bank.domain

import kamilmarnik.fintech.bank.dto.AccountDto
import kamilmarnik.fintech.bank.exception.InvalidAccountCreation
import spock.lang.Unroll

/**
 * Maksymilian Ulanecki
 */


class AccountCreationSpec extends BankBaseSpec {

  @Unroll
  def "should create an account" () {
    when: "creates a new account"
      AccountDto account = bankFacade.createAccount(accountId)
    then: "a new account is created with an empty balance"
      account.id() == accountId
      account.accountBalance() == BigDecimal.ZERO
    where:
      accountId << [FIRST_ACCOUNT_ID, SECOND_ACCOUNT_ID]
  }

  def "should not create a new account with an improper ID" () {
    when: "creates a new account with an improper ID"
      bankFacade.createAccount(null)
    then: "account is not created"
      thrown(InvalidAccountCreation)
  }

  def "should not create a new account with an already existing ID" () {
    given: "creates a new account"
     bankFacade.createAccount(FIRST_ACCOUNT_ID)
    when: "creates a new account with the same ID"
     bankFacade.createAccount(FIRST_ACCOUNT_ID)
    then: "account is not created"
      thrown(InvalidAccountCreation)
  }

}
