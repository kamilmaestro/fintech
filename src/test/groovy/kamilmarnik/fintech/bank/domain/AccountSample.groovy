package kamilmarnik.fintech.bank.domain

import groovy.transform.CompileStatic
import kamilmarnik.fintech.bank.dto.AccountDto
import kamilmarnik.fintech.bank.dto.CurrencyDto
import kamilmarnik.fintech.bank.dto.Deposit

/**
 * Maksymilian Ulanecki
 */


@CompileStatic
trait AccountSample {

  static UUID FIRST_ACCOUNT_ID = UUID.randomUUID()
  static UUID SECOND_ACCOUNT_ID = UUID.randomUUID()

  void saveAccountsWithBalances(BankFacade bankFacade, Collection<BigDecimal> values) {
    values.each {value ->
      AccountDto account = bankFacade.createAccount(UUID.randomUUID(), CurrencyDto.PLN)
      bankFacade.deposit(new Deposit(account.id(), value, CurrencyDto.PLN))
    }
  }

}
