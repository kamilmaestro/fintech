package kamilmarnik.fintech.bank.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record WithdrawalHistory(UUID accountId, BigDecimal value, TypeOfWithdraw typeOfWithdraw) {
}
