package kamilmarnik.fintech.bank.dto;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Maksymilian Ulanecki
 */


public record AccountDto(UUID id, BigDecimal accountBalance, CurrencyDto currency) {
}
