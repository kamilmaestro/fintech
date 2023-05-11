package kamilmarnik.fintech.bank.domain

import kamilmarnik.fintech.bank.dto.AccountDto
import kamilmarnik.fintech.bank.exception.InvalidAccountCreation
import spock.lang.Unroll

class CreateStackAccountSpec extends BankBaseSpec{

    def "Stack account should be created with positive balance"() {
        when: "Creating stock account with balance 10"
        AccountDto accountDto = bankFacade.createStockAccount(FIRST_ACCOUNT_ID,BigDecimal.TEN)
        then: "a new account is created with balance 10"
        accountDto.id() == FIRST_ACCOUNT_ID
        accountDto.accountBalance() == BigDecimal.TEN
        accountDto.type() == "Stock"
    }

    @Unroll
    def "Should not create stock account with #value balance"(){
        when: "Creating stock account with balance $value"
        AccountDto accountDto = bankFacade.createStockAccount(FIRST_ACCOUNT_ID,value)
        then: "account is not created"
        thrown(InvalidAccountCreation)
        where:
         value << [null,BigDecimal.ZERO,new BigDecimal(-1)]
    }

}
