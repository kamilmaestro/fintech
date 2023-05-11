package kamilmarnik.fintech.bank.domain

import kamilmarnik.fintech.bank.dto.AccountDto
import kamilmarnik.fintech.bank.dto.Deposit
import kamilmarnik.fintech.bank.dto.Withdrawal
import kamilmarnik.fintech.bank.exception.InvalidAccountCreation
import kamilmarnik.fintech.bank.exception.InvalidWithdrawal
import spock.lang.Unroll

class CreateSavingAccountSpec extends BankBaseSpec{

    def "Saving account should be created "() {
        when: "Creating saving account"
        AccountDto accountDto = bankFacade.createSavingAccount(FIRST_ACCOUNT_ID)
        then: "a new account is created"
        accountDto.id() == FIRST_ACCOUNT_ID
        accountDto.accountBalance() == BigDecimal.ZERO
        accountDto.type() == "Saving"
    }

    def "should not create a new saving account with an improper ID" () {
        when: "creates a new account with an improper ID"
        bankFacade.createSavingAccount(null)
        then: "account is not created"
        thrown(InvalidAccountCreation)
    }

    def "should not withdraw money form saving account where balance after operation is below 100"(){
        given: "there is saving account"
        AccountDto accountDto = bankFacade.createSavingAccount(FIRST_ACCOUNT_ID)
        and: "there is 150 pln"
        bankFacade.deposit(new Deposit(FIRST_ACCOUNT_ID,new BigDecimal(150)))
        when: "withdrawing 51 pln"
        bankFacade.withdraw(new Withdrawal(FIRST_ACCOUNT_ID,new BigDecimal(51)))
        then: "withdraw is not possible"
        thrown(InvalidWithdrawal)
    }

    def "should withdraw money form saving account where balance after operation is equal 100" (){
        given: "there is saving account"
        AccountDto accountDto = bankFacade.createSavingAccount(FIRST_ACCOUNT_ID)
        and: "there is 150 pln"
        bankFacade.deposit(new Deposit(FIRST_ACCOUNT_ID,new BigDecimal(150)))
        when: "withdrawing 50 pln"
        AccountDto accountDto1 =  bankFacade.withdraw(new Withdrawal(FIRST_ACCOUNT_ID,new BigDecimal(50)))
        then: "on saving account balance is equal 100"
        accountDto1.accountBalance() == new BigDecimal(100);
    }



}
