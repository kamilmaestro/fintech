package kamilmarnik.fintech.bank.domain;

import kamilmarnik.fintech.bank.dto.Operation;
import kamilmarnik.fintech.bank.dto.OperationType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
@Getter
@FieldDefaults(level= AccessLevel.PRIVATE,makeFinal = true)
class BankOperation {
    Type type;
    BigDecimal value;


    BankOperation(Type type, BigDecimal value) {
        this.type = type;
        this.value = value;
    }

    enum Type{
        WITHDRAWAL, DEPOSIT;
    }

    Operation dto(){
        return new Operation(convertToDto(type),value);
    }

    private OperationType convertToDto(Type type) {
        return OperationType.valueOf(type.name());
    }
}
