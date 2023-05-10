package kamilmarnik.fintech.bank.domain

import kamilmarnik.fintech.bank.InstantProvider
import spock.lang.Specification

import java.time.Clock

class BankBaseSpec extends Specification implements AccountSample {
  InstantProvider instantProvider= new InstantProvider()
  BankFacade bankFacade = new BankConfiguration().bankFacade(instantProvider)

}
