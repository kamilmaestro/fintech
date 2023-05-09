package kamilmarnik.fintech.bank.exception;

import java.util.UUID;

public class AccountNotFound extends RuntimeException {

  public AccountNotFound(UUID accountId) {
    super("Can not find an account with an ID: " + accountId);
  }

}
