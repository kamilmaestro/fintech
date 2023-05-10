package kamilmarnik.fintech.bank.domain;

import kamilmarnik.fintech.bank.dto.CurrencyDto;
import kamilmarnik.fintech.bank.exception.InvalidCurrency;

import java.math.BigDecimal;

enum Currency {
    PLN(1.0), EUR(4.5), USD(4.0);
    private final double toPLN;

    Currency(double toPLN) {
        this.toPLN = toPLN;
    }

    CurrencyDto toDto() {
        return CurrencyDto.valueOf(this.name());
    }

    static Currency fromDto(CurrencyDto currencyDto) {
        if (currencyDto == null) {
            throw new InvalidCurrency();
        }
        return Currency.valueOf(currencyDto.name());
    }

    BigDecimal recalculateTo(BigDecimal value, Currency currency) {
        switch (currency){
            case PLN:

        }

        return
    }

}
