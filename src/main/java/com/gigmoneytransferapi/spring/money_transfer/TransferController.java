package com.gigmoneytransferapi.spring.money_transfer;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TransferController {

    private final AccountRepository accountRepository;

    public TransferController(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }
    /** showAccounts method returns a list of all accounts in the database */
    @GetMapping("/all-accounts")
    public ResponseEntity<List<Account>> showAccounts() {
        List<Account> accounts = accountRepository.findAll();
        return ResponseEntity.ok(accounts);
    }

    @PostMapping("/account")
    public ResponseEntity<String> addAccount(@RequestBody Account account) {
        if (accountRepository.existsById(account.getAccountID())) {
            System.out.println("Account already exists.");
            return ResponseEntity.badRequest().body("Account already in database");
        }
        Account accountAddedToDB = accountRepository.save(account);
        System.out.println("Account saved!");
        return ResponseEntity.ok(accountAddedToDB + " account added!");
    }

    @DeleteMapping("/accounts/{id}")
    public ResponseEntity<String> deleteAccount(@PathVariable Long id) {
        if (!accountRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        accountRepository.deleteById(id);
        return ResponseEntity.ok(id + " account deleted successfully.");
    }

    @PostMapping("/transfer")
    public ResponseEntity<String> transferMoney(@RequestBody TransferFunds request) {

        long fromAccountID = request.getFromAccountId();
        long toAccountID = request.getToAccountId();
        double amount = request.getAmount();

        if (amount <= 0) {
            return ResponseEntity.badRequest().body("Amount must be greater than 0!!");
        }

        Account fromAccount = accountRepository.findById(fromAccountID)
                .orElseThrow(() -> new RuntimeException("Source account not found"));
        Account toAccount = accountRepository.findById(toAccountID)
                .orElseThrow(() -> new RuntimeException("Destination account not found"));

        if (fromAccount.getBalance() < amount) {
            return ResponseEntity.badRequest().body("Insufficient funds in source account");
        }

        fromAccount.setBalance(fromAccount.getBalance() - amount);
        toAccount.setBalance(toAccount.getBalance() + amount);

        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);
        System.out.println(amount + "has been sent to account " + toAccountID + " from account " + fromAccountID);
        return ResponseEntity.ok("Transfer successful");
    }

}









