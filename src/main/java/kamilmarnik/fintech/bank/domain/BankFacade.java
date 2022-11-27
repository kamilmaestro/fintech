package kamilmarnik.fintech.bank.domain;

import kamilmarnik.fintech.bank.dto.AccountDto;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.UUID;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public final class BankFacade {

  BankRepository bankRepository;

  public AccountDto createAccount(UUID accountId) {
    final Account account = Account.create(accountId);
    return bankRepository.save(account)
        .dto();
  }

  public AccountDto deposit(UUID accountId, BigDecimal value) {
    final Account account = getAccount(accountId);
    return bankRepository.save(account.deposit(value))
        .dto();
  }

  public AccountDto withdraw(UUID accountId, BigDecimal value) {
    final Account account = getAccount(accountId);
    return bankRepository.save(account.withdraw(value))
        .dto();
  }

  private Account getAccount(UUID accountId) {
    return bankRepository.findById(accountId)
        .orElseThrow(() -> new IllegalStateException("Can not find an account with an ID: " + accountId));
  }

}
