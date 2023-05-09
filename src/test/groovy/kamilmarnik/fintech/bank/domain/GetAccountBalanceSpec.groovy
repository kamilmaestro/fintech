package kamilmarnik.fintech.bank.domain

import kamilmarnik.fintech.bank.dto.AccountDto
import kamilmarnik.fintech.bank.dto.Balance
import kamilmarnik.fintech.bank.dto.Deposit
import kamilmarnik.fintech.bank.exception.AccountNotFound

class GetAccountBalanceSpec extends BankBaseSpec {

    def "Should get account balance" () {
        given: "Account exist"
            AccountDto account = bankFacade.createAccount(FIRST_ACCOUNT_ID)
        and: "There is one pln on account"
            bankFacade.deposit(new Deposit(account.id(), BigDecimal.ONE))
        when: "User is getting account balance"
            Balance balance = bankFacade.getBalance(account.id())
        then: "Balance is 1 pln"
            balance.balance() == BigDecimal.ONE
    }

    def "should not get account balance from a non-existing account" () {
        when: "User is getting account balance from not existing account"
            Balance balance = bankFacade.getBalance(FIRST_ACCOUNT_ID)
        then: "account does not exist"
            thrown(AccountNotFound)
    }
}
