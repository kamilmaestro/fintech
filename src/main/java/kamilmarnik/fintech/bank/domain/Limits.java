package kamilmarnik.fintech.bank.domain;

import kamilmarnik.fintech.bank.exception.WithdrawalLimitExceeded;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.ZoneId;


class Limits {

    private BigDecimal dayOperationSum;
    private Instant currentDate;
    private final static BigDecimal LIMIT = new BigDecimal(1000);
    private final static BigDecimal DAY_LIMIT = new BigDecimal(1500);

    public Limits(BigDecimal dayOperationSum, Instant currentDate) {
        this.currentDate = currentDate;
        this.dayOperationSum = dayOperationSum;
    }

    static Limits emptyForNewAccount(Instant currentDate) {
        return new Limits(BigDecimal.ZERO, currentDate);
    }

    void checkLimits(BigDecimal value, Instant currentDate) {
        if (value != null) {
            if (value.compareTo(LIMIT) > 0) {
                throw new WithdrawalLimitExceeded(LIMIT);
            }
            checkDayLimits(value, currentDate);
        }
    }

    void checkDayLimits(BigDecimal value, Instant currentDate) {
        if (currentDate.atZone(ZoneId.systemDefault()).getDayOfYear() != this.currentDate.atZone(ZoneId.systemDefault()).getDayOfYear()) {
            dayOperationSum = BigDecimal.ZERO;
        }
        if (value.add(dayOperationSum).compareTo(DAY_LIMIT) > 0) {
            throw new WithdrawalLimitExceeded(DAY_LIMIT);
        } else {
            this.currentDate = currentDate;
            dayOperationSum = dayOperationSum.add(value);
        }
    }

}
