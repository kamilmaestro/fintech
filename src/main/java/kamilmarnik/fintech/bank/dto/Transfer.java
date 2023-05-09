package kamilmarnik.fintech.bank.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record Transfer(UUID fromAccountId, UUID toAccountId, BigDecimal value) {
}
