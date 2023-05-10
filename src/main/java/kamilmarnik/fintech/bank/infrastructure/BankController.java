package kamilmarnik.fintech.bank.infrastructure;

import kamilmarnik.fintech.bank.domain.BankFacade;
import kamilmarnik.fintech.bank.dto.AccountDto;
import kamilmarnik.fintech.bank.dto.Deposit;
import kamilmarnik.fintech.bank.dto.Withdrawal;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Maksymilian Ulanecki
 */


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

  @PutMapping("/deposit")
  ResponseEntity<AccountDto> depositMoneyToAccount(@RequestBody Deposit deposit) {
    return ResponseEntity.ok(bankFacade.deposit(deposit));
  }

  @PutMapping("/withdraw")
  ResponseEntity<AccountDto> withdrawMoneyFromAccount(@RequestBody Withdrawal withdrawal) {
    return ResponseEntity.ok(bankFacade.withdraw(withdrawal));
  }

}
