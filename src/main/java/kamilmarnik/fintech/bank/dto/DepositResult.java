package kamilmarnik.fintech.bank.dto;

import java.math.BigDecimal;

public record DepositResult(BigDecimal accountBalance, String type, BigDecimal deposited) {
}
