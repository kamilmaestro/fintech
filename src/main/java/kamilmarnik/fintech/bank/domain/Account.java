package kamilmarnik.fintech.bank.domain;

import kamilmarnik.fintech.bank.dto.AccountDto;
import kamilmarnik.fintech.bank.dto.CurrencyDto;
import kamilmarnik.fintech.bank.exception.InvalidAccountCreation;
import kamilmarnik.fintech.bank.exception.InvalidDeposit;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Maksymilian Ulanecki
 */

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
final class Account {

    UUID id;
    AccountBalance accountBalance;
    Currency currency;

    static Account create(UUID accountId, CurrencyDto currency) {
        if (accountId == null) {
            throw new InvalidAccountCreation();
        }
        return new Account(accountId, AccountBalance.emptyForNewAccount(), Currency.fromDto(currency));
    }

    Account deposit(BigDecimal depositValue, Currency currency) {
        if (!this.getCurrency().equals(currency)) {
            throw new InvalidDeposit();
        }
        return new Account(id, accountBalance.deposit(depositValue), this.currency);
    }

    Account withdraw(BigDecimal value) {
        return new Account(id, accountBalance.withdraw(value), this.currency);
    }

    AccountDto dto() {
        return new AccountDto(id, accountBalance.getValueAsBigDecimal(), this.currency.toDto());
    }

}
