package kamilmarnik.fintech.bank.domain;

import kamilmarnik.fintech.bank.InstantProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;

@Configuration
class BankConfiguration {

  @Bean
  BankFacade bankFacade() {
    return bankFacade(new InstantProvider());
  }
  BankFacade bankFacade(InstantProvider instantProvider){
    return new BankFacade(new InMemoryBankRepository(),instantProvider);
  }

}
