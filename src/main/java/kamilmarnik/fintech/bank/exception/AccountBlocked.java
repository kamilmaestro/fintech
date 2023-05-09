package kamilmarnik.fintech.bank.exception;

public class AccountBlocked extends RuntimeException {

  public AccountBlocked() {
    super("Account is blocked");
  }
}
