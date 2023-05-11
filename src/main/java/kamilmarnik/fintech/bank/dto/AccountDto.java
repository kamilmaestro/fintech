package kamilmarnik.fintech.bank.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record AccountDto(UUID id, BigDecimal accountBalance,String type) {
}
