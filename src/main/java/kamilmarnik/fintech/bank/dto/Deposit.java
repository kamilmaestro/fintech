package kamilmarnik.fintech.bank.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record Deposit(UUID accountId, BigDecimal value) {
}
