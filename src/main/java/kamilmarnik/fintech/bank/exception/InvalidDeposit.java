package kamilmarnik.fintech.bank.exception;

/**
 * Maksymilian Ulanecki
 */


public class InvalidDeposit extends RuntimeException {

  public InvalidDeposit() {
    super("Can not deposit money to an account");
  }

}
