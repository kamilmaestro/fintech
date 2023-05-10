package kamilmarnik.fintech.bank.domain

import kamilmarnik.fintech.bank.InstantProvider
import kamilmarnik.fintech.bank.dto.Deposit
import kamilmarnik.fintech.bank.dto.Withdrawal
import kamilmarnik.fintech.bank.exception.WithdrawalLimitExceeded

import java.time.Instant
import java.time.temporal.ChronoUnit

class WithdrawalLimitSpec extends BankBaseSpec {

    def setup(){
        given: "there is an account"
            bankFacade.createAccount(FIRST_ACCOUNT_ID)
        and: "there are 10000PLN  at account "
            bankFacade.deposit(new Deposit(FIRST_ACCOUNT_ID, new BigDecimal("10000")))
    }
    def "should not withdraw money exceeding limit 1000"() {

        when: "withdrawing 1001"
            bankFacade.withdraw(new Withdrawal(FIRST_ACCOUNT_ID, new BigDecimal(1001)))
        then: "withdrawing is not possible "
            thrown(WithdrawalLimitExceeded)
    }
    def "should withdraw money under the limit "(){
        when: "withdrawing 1000"
             bankFacade.withdraw(new Withdrawal(FIRST_ACCOUNT_ID, ammount))
        then:"withdraw is possible"
            noExceptionThrown()
        where:
             ammount<<[new BigDecimal(1000),new BigDecimal(500)]
    }

    def"shouldn't withdraw money with limit day is 1500"(){
        given:"withdrawing 1000"
            bankFacade.withdraw(new Withdrawal(FIRST_ACCOUNT_ID,new BigDecimal(1000)))
        when:"withdrawing 501PLN"
            bankFacade.withdraw(new Withdrawal(FIRST_ACCOUNT_ID,new BigDecimal(501)))
        then:"withdraw is not possible"
            thrown(WithdrawalLimitExceeded)
    }
    def "day limit should expire next day"(){
        given:"withdrawing 1000"
            bankFacade.withdraw(new Withdrawal(FIRST_ACCOUNT_ID,new BigDecimal(1000)))
        and:"withdrawing 500"
            bankFacade.withdraw(new Withdrawal(FIRST_ACCOUNT_ID,new BigDecimal(500)))
        and:"set next day"
            instantProvider.setClock(instantProvider.now().plus(1, ChronoUnit.DAYS))
        when:"next day withdrawing 1000"
            bankFacade.withdraw(new Withdrawal(FIRST_ACCOUNT_ID,new BigDecimal(1000)))
        then:
        noExceptionThrown()
    }


}
