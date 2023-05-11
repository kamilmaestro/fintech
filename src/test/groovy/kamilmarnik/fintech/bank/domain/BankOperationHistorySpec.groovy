package kamilmarnik.fintech.bank.domain

import kamilmarnik.fintech.bank.dto.AccountDto
import kamilmarnik.fintech.bank.dto.Deposit
import kamilmarnik.fintech.bank.dto.Operation
import kamilmarnik.fintech.bank.dto.OperationType
import kamilmarnik.fintech.bank.dto.Withdrawal
import kamilmarnik.fintech.bank.exception.AccountNotFound
import lombok.With
import spock.lang.Unroll

class BankOperationHistorySpec extends BankBaseSpec{

    @Unroll
    def"should save operations in history"(){
        given: "account exists"
        AccountDto account = bankFacade.createAccount(FIRST_ACCOUNT_ID)
        and: "deposit 10"
        bankFacade.deposit(new Deposit(account.id(), BigDecimal.TEN))
        and: "withdraw 5"
        bankFacade.withdraw(new Withdrawal(account.id(), new BigDecimal(5)))
        when: "get account history"
        List<Operation> operations= bankFacade.getAccountHistory(account.id())
        then: "there are 2 operations in account history"
        operations.size() == 2
        and: "operations type is [deposit, withdrawal]"
        operations.operationType == [OperationType.DEPOSIT, OperationType.WITHDRAWAL]
        and: "operations values is [10, 5]"
        operations.value == [BigDecimal.TEN, new BigDecimal(5)]

    }

    def "should not get history from non-existing account"(){
        when: "get history from non-existing account"
        bankFacade.getAccountHistory(SECOND_ACCOUNT_ID)
        then: "account does not exist"
        thrown(AccountNotFound)
    }
}
