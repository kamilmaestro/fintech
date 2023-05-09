package kamilmarnik.fintech.bank.exception;

public class InvalidWithdrawal extends RuntimeException {

  public InvalidWithdrawal() {
    super("Can not withdraw money from an account");
  }

}
