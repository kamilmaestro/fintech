package kamilmarnik.fintech.bank.exception;

import java.math.BigDecimal;

public class WithdrawalLimitExceeded extends RuntimeException {
    public WithdrawalLimitExceeded(BigDecimal limit){super("limit: "+limit);}
}
