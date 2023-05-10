package kamilmarnik.fintech.bank.domain

import spock.lang.Specification

/**
 * Maksymilian Ulanecki
 */


class BankBaseSpec extends Specification implements AccountSample {

  BankFacade bankFacade = new BankConfiguration().bankFacade()

}
