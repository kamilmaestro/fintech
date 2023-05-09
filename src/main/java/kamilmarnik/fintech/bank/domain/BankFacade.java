package kamilmarnik.fintech.bank.domain;

import kamilmarnik.fintech.bank.dto.*;
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

  public TerminateAccount terminateAccount(UUID accountId) {
    BigDecimal balance = this.getAccount(accountId).getAccountBalance().getValueAsBigDecimal();
    bankRepository.deleteAccount(accountId);
    return new TerminateAccount(balance);
  }

  public Transfer transferMoney(UUID fromAccountId, UUID toAccountId, BigDecimal money) {
    final Account fromAccount = getAccount(fromAccountId);
    final Account toAccount = getAccount(toAccountId);
    Account withdraw = fromAccount.withdraw(money);
    Account deposit = toAccount.deposit(money);
    bankRepository.save(withdraw);
    bankRepository.save(deposit);
    return new Transfer(fromAccountId, toAccountId, money);
  }
}
