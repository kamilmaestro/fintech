package kamilmarnik.fintech.bank.infrastructure;

import kamilmarnik.fintech.bank.domain.BankFacade;
import kamilmarnik.fintech.bank.dto.AccountDto;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.UUID;

@RestController
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
class BankController {

  BankFacade bankFacade;

  @Autowired
  BankController(BankFacade bankFacade) {
    this.bankFacade = bankFacade;
  }

  @PostMapping("/{accountId}")
  ResponseEntity<AccountDto> createAccount(@PathVariable UUID accountId) {
    return ResponseEntity.ok(bankFacade.createAccount(accountId));
  }

  @PutMapping("/{accountId}/deposit")
  ResponseEntity<AccountDto> depositMoneyToAccount(@PathVariable UUID accountId) {
    return ResponseEntity.ok(bankFacade.deposit(accountId, BigDecimal.TEN));
  }

  @PutMapping("/{accountId}/withdraw")
  ResponseEntity<AccountDto> withdrawMoneyFromAccount(@PathVariable UUID accountId) {
    return ResponseEntity.ok(bankFacade.withdraw(accountId, BigDecimal.ONE));
  }

}
