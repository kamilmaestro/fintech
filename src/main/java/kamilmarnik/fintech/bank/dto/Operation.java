package kamilmarnik.fintech.bank.dto;

import lombok.Getter;

import java.math.BigDecimal;


public record Operation(OperationType operationType, BigDecimal value) {

    public OperationType getOperationType() {
        return operationType;
    }

    public BigDecimal getValue() {
        return value;
    }
}
