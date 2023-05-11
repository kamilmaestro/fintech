package kamilmarnik.fintech.bank.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record AccountBalanceDto (UUID id, BigDecimal accountBalance, Operation lastOperation){
}
