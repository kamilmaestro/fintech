package kamilmarnik.fintech.bank.domain

import kamilmarnik.fintech.bank.dto.Deposit
import kamilmarnik.fintech.bank.dto.TerminateAccount
import kamilmarnik.fintech.bank.dto.Transfer
import kamilmarnik.fintech.bank.exception.AccountNotFound

class TransferMoneySpec extends BankBaseSpec{

    def "User should transfer money from one account to another"(){
        given: "there is an account"
            bankFacade.createAccount(FIRST_ACCOUNT_ID)
            bankFacade.deposit(new Deposit(FIRST_ACCOUNT_ID, BigDecimal.TEN))
        and: "there is second account"
            bankFacade.createAccount(SECOND_ACCOUNT_ID)
        when: "user transfers money"
            Transfer transfer = bankFacade.transferMoney(FIRST_ACCOUNT_ID, SECOND_ACCOUNT_ID, 10)
        then: "money transfered"
            transfer != null
    }

    def "user should not be able to transfer money from not existing account"(){
        given: "there is second account"
            bankFacade.createAccount(SECOND_ACCOUNT_ID)
        when: "user transfers money"
            Transfer transfer = bankFacade.transferMoney(FIRST_ACCOUNT_ID, SECOND_ACCOUNT_ID, 10)
        then: "money transfered"
            thrown(AccountNotFound)
    }
}
