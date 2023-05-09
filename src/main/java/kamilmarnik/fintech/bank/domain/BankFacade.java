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

  public AccountDto createAccount(UUID accountId) {
    bankRepository.findById(accountId)
        .ifPresent(account -> { throw new InvalidAccountCreation(); });
    final Account account = Account.create(accountId);
    return bankRepository.save(account)
        .dto();
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

  public Withdrawal closeAccount(UUID uuid) {
    Account account = getAccount(uuid);

    bankRepository.deleteById(account.getId());

    return new Withdrawal(uuid, account.dto().accountBalance());
  }

  public void blockAccount(UUID uuid) {

    Account account = getAccount(uuid);

    bankRepository.save(account.block());
  }

  public void unblockAccount(UUID uuid) {
    Account account = getAccount(uuid);

    bankRepository.save(account.unblock());
  }
}
