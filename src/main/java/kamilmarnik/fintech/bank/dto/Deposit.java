package kamilmarnik.fintech.bank.dto;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Maksymilian Ulanecki
 */


public record Deposit(UUID accountId, BigDecimal value, CurrencyDto currency) {
}
