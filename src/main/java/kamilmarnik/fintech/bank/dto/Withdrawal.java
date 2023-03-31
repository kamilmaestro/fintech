package kamilmarnik.fintech.bank.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record Withdrawal(UUID accountId, BigDecimal value) {
}
