package kamilmarnik.fintech.bank.domain;

import kamilmarnik.fintech.bank.dto.AccountDto;
import kamilmarnik.fintech.bank.dto.Deposit;
import kamilmarnik.fintech.bank.dto.Withdrawal;
import kamilmarnik.fintech.bank.exception.AccountNotFound;
import kamilmarnik.fintech.bank.exception.InvalidAccountCreation;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public final class BankFacade {

  BankRepository bankRepository;

  private AccountDto saveAccount(Account account) {
    bankRepository.findById(account.getId())
        .ifPresent(a -> { throw new InvalidAccountCreation(); });
    return bankRepository.save(account)
            .dto();
  }
  public AccountDto createAccount(UUID accountId) {
    return saveAccount(Account.create(accountId));
  }
  public AccountDto createStockAccount(UUID accountId, BigDecimal balance){
    return saveAccount(Account.createStockAccount(accountId,balance));
  }
  public AccountDto createSavingAccount(UUID accountId){
    return saveAccount(Account.createSavingAccount(accountId));
  }


  public AccountDto deposit(Deposit deposit) {
    final Account account = getAccount(deposit.accountId());
    return bankRepository.save(account.deposit(deposit.value()))
        .dto();
  }

  public AccountDto withdraw(Withdrawal withdrawal) {
    final Account account = getAccount(withdrawal.accountId());
    return bankRepository.save(account.withdraw(withdrawal.value()))
        .dto();
  }

  private Account getAccount(UUID accountId) {
    return bankRepository.findById(accountId)
        .orElseThrow(() -> new AccountNotFound(accountId));
  }

}
