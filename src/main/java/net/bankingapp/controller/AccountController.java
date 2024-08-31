package net.bankingapp.controller;

import net.bankingapp.dto.AccountDTO;
import net.bankingapp.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    //Add account RestApi
    @PostMapping("/create")
    public ResponseEntity<AccountDTO> addAccount(@RequestBody AccountDTO accountDTO) {

        AccountDTO createdAccount = accountService.createAccount(accountDTO);

        return new ResponseEntity<>(createdAccount, HttpStatus.CREATED);
    }

    // Get Account Rest API
    @GetMapping("/{AccountId}")
    public ResponseEntity<AccountDTO> getAccountById(@PathVariable(name = "AccountId") Long id) {

        AccountDTO accountDTO = accountService.getAccountById(id);

        return ResponseEntity.ok(accountDTO);
    }

    //Deposit Rest API
    @PutMapping("/{id}/deposit")
    public ResponseEntity<AccountDTO> deposit(@PathVariable Long id,
                                              @RequestBody Map<String, Double> request) {

        double amount = request.get("amount");

        AccountDTO accountDTO = accountService.deposit(id, amount);

        return ResponseEntity.ok(accountDTO);
    }

    //Withdraw Rest API
    @PutMapping("/{id}/withdraw")
    public ResponseEntity<AccountDTO> withdraw(@PathVariable Long id,
                                               @RequestBody Map<String, Double> request) {

        double amount = request.get("amount");

        AccountDTO accountDTO = accountService.withdraw(id, amount);

        return ResponseEntity.ok(accountDTO);
    }

    //get All Accounts Rest API
    @GetMapping
    public ResponseEntity<List<AccountDTO>> getAllAccounts() {

        List<AccountDTO> accounts = accountService.getAllAccounts();

        return ResponseEntity.ok(accounts);
    }

    // Delete Account Rest API

    @DeleteMapping("/{AccountId}")
    public ResponseEntity<String> deleteAccountById(@PathVariable(name = "AccountId") Long id) {

        accountService.deleteAccount(id);

        return ResponseEntity.ok("Account deleted Successfully..!");
    }
}
