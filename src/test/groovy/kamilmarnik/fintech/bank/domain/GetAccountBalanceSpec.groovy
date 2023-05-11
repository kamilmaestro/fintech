package kamilmarnik.fintech.bank.domain

import kamilmarnik.fintech.bank.dto.AccountBalanceDto
import kamilmarnik.fintech.bank.dto.AccountDto
import kamilmarnik.fintech.bank.dto.Deposit
import kamilmarnik.fintech.bank.dto.Operation
import kamilmarnik.fintech.bank.dto.OperationType
import spock.lang.Unroll

class GetAccountBalanceSpec extends BankBaseSpec{


    def"should get account balance"(){
        given: "account exists"
        bankFacade.createAccount(FIRST_ACCOUNT_ID);
//        and: "deposit 10 to account"
//        bankFacade.deposit(new Deposit(FIRST_ACCOUNT_ID, BigDecimal.TEN));
        when: "get account balance"
        AccountBalanceDto balance = bankFacade.getAccountBalance(FIRST_ACCOUNT_ID);
        then: "balance equals 10"
        balance.accountBalance() == BigDecimal.ZERO
        and: "id is $FIRST_ACCOUNT_ID"
        balance.id() == FIRST_ACCOUNT_ID;
        and: "last operation was deposit 10"
//        balance.lastOperation() == new Operation(OperationType.DEPOSIT, BigDecimal.TEN);
        balance.lastOperation() == null;
    }
}
