package kamilmarnik.fintech.bank.domain;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Maksymilian Ulanecki
 */


@Configuration
class BankConfiguration {

  @Bean
  BankFacade bankFacade() {
    return new BankFacade(new InMemoryBankRepository());
  }

}
