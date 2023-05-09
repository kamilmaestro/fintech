package kamilmarnik.fintech.bank.domain

import kamilmarnik.fintech.bank.dto.AccountDto
import kamilmarnik.fintech.bank.dto.Deposit
import kamilmarnik.fintech.bank.dto.TerminateAccount
import kamilmarnik.fintech.bank.dto.Withdrawal
import kamilmarnik.fintech.bank.exception.AccountNotFound


class TerminateAccountSpec extends BankBaseSpec{

    def "User should terminate account"(){
        given: "there is an account"
            bankFacade.createAccount(FIRST_ACCOUNT_ID)
        when: "user terminates account"
            TerminateAccount terminateAccount = bankFacade.terminateAccount(FIRST_ACCOUNT_ID)
        then: "account terminated"
            terminateAccount != null

    }

    def "User should not be able to terminate not existing account"(){
        when: "user terminates not existing account"
            bankFacade.terminateAccount(FIRST_ACCOUNT_ID)
        then: "account terminated"
            thrown(AccountNotFound)

    }

    def "User should withdraw all his deposit when terminating account"(){
        given: "there is an account"
            bankFacade.createAccount(FIRST_ACCOUNT_ID)
        and: "deposit 10PLN to the account"
            bankFacade.deposit(new Deposit(FIRST_ACCOUNT_ID, BigDecimal.TEN))
        when: "user terminates account"
            TerminateAccount account = bankFacade.terminateAccount(FIRST_ACCOUNT_ID)
        then: "money is withdrawn"
            account.balance() == BigDecimal.TEN
    }

    def "User should not withdraw any money from terminated account"(){
        given: "there is an account"
            bankFacade.createAccount(FIRST_ACCOUNT_ID)
        and: "deposit 10PLN to the account"
            bankFacade.deposit(new Deposit(FIRST_ACCOUNT_ID, BigDecimal.TEN))
        and: "user terminates account"
            TerminateAccount account = bankFacade.terminateAccount(FIRST_ACCOUNT_ID)
        when: "user withdraw money from terminated account"
            bankFacade.withdraw(new Withdrawal(FIRST_ACCOUNT_ID, BigDecimal.TEN))
        then: "account terminated"
            thrown(AccountNotFound)
    }
}
