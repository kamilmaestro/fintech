package kamilmarnik.fintech.bank.exception;

/**
 * Maksymilian Ulanecki
 */


public class InvalidWithdrawal extends RuntimeException {

  public InvalidWithdrawal() {
    super("Can not withdraw money from an account");
  }

}
