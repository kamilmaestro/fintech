package kamilmarnik.fintech.bank.exception;

/**
 * Maksymilian Ulanecki
 */


public class InvalidAccountCreation extends RuntimeException {

  public InvalidAccountCreation() {
    super("Can not create an account");
  }

}
