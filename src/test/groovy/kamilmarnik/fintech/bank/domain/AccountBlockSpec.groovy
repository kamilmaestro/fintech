package kamilmarnik.fintech.bank.domain

import kamilmarnik.fintech.bank.dto.AccountDto
import kamilmarnik.fintech.bank.dto.Deposit
import kamilmarnik.fintech.bank.dto.Withdrawal
import kamilmarnik.fintech.bank.exception.AccountBlocked
import kamilmarnik.fintech.bank.exception.AccountNotFound

class AccountBlockSpec extends BankBaseSpec {
  AccountDto account;

  def setup() {
    given: "there is an account"
    account = bankFacade.createAccount(FIRST_ACCOUNT_ID)
  }

  def "should not withdraw money if account is blocked"() {
    given: "there is some money on account"
      bankFacade.deposit(new Deposit(account.id(), BigDecimal.ONE))
    and:"account is blocked"
      bankFacade.blockAccount(account.id())
    when: "withdraw money from blocked account"
      bankFacade.withdraw(new Withdrawal(account.id(), BigDecimal.ONE))
    then: "account blocked"
      thrown(AccountBlocked)
  }

  def "should not block account if this account does not exist"() {
    when: "account that does not exist is blocked"
      bankFacade.blockAccount(SECOND_ACCOUNT_ID)
    then: "account does not exist"
      thrown(AccountNotFound)
  }

  def "should unblock account if this account is blocked"() {
    given: "account is blocked"
      bankFacade.blockAccount(account.id())
    and: "account is open"
      bankFacade.unblockAccount(account.id())
    when: "deposit money to this account"
      bankFacade.deposit(new Deposit(account.id(), BigDecimal.ONE))
    then: "successfully deposit money"
      noExceptionThrown()
  }
}
