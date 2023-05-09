package kamilmarnik.fintech.bank.domain

import kamilmarnik.fintech.bank.dto.AccountDto
import kamilmarnik.fintech.bank.dto.Deposit
import kamilmarnik.fintech.bank.dto.Withdrawal
import kamilmarnik.fintech.bank.exception.AccountNotFound

import javax.security.auth.login.AccountNotFoundException

class AccountCloseSpec extends BankBaseSpec {
  AccountDto account;

  def setup() {
    given: "there is an account"
      account = bankFacade.createAccount(FIRST_ACCOUNT_ID)
  }

  def "should not withdraw any money if account is closed"() {
    given: "account is closed"
      Withdrawal withdrawal = bankFacade.closeAccount(account.id())
    when: "withdraw money from closed account"
      bankFacade.withdraw(new Withdrawal(account.id(), BigDecimal.ONE))
    then: "account does not exist"
      thrown(AccountNotFound)
  }

  def "should not close account that does not exist"() {
    when: "closing not existing account"
      bankFacade.closeAccount(SECOND_ACCOUNT_ID)
    then: "account does not exist"
      thrown(AccountNotFound)
  }

  def "should get money from closed account"() {
    given: "deposit 10 pln"
      bankFacade.deposit(new Deposit(account.id(), BigDecimal.TEN))
    when: "closing account"
      Withdrawal withdrawal = bankFacade.closeAccount(account.id())
    then: "get 10 pln from closed account"
      withdrawal.value() == BigDecimal.TEN
  }

}
