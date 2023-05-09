package kamilmarnik.fintech.bank.exception;

public class InvalidAccountCreation extends RuntimeException {

  public InvalidAccountCreation() {
    super("Can not create an account");
  }

}
