package kamilmarnik.fintech.bank.domain

import groovy.transform.CompileStatic
import kamilmarnik.fintech.bank.dto.AccountDto
import kamilmarnik.fintech.bank.dto.Deposit

@CompileStatic
trait AccountSample {

  static UUID FIRST_ACCOUNT_ID = UUID.randomUUID()
  static UUID SECOND_ACCOUNT_ID = UUID.randomUUID()

  void saveAccountsWithBalances(BankFacade bankFacade, Collection<BigDecimal> values) {
    values.each {value ->
      AccountDto account = bankFacade.createAccount(UUID.randomUUID())
      bankFacade.deposit(new Deposit(account.id(), value))
    }
  }

}
