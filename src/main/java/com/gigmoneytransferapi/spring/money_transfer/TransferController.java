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

    /** addAccount method adds an Account instance to the moneytransferdb database.
     * If the account already exists then it throws an error */
    @PostMapping("/account")
    public ResponseEntity<String> addAccount(@RequestBody Account account) {

        // checks that the account to add doesn't already exist
        if (accountRepository.existsById(account.getAccountID())) {
            System.out.println("Account already exists.");
            return ResponseEntity.badRequest().body("Account already in database");
        }
        Account accountAddedToDB = accountRepository.save(account);
        System.out.println("Account saved!");
        return ResponseEntity.ok(accountAddedToDB + " account added!");
    }

    /** deleteAccount deletes an account from moneytransferdb.
     * If the account doesn't exist then it throws a notfound error.
     * The @PathVariable is used to input the id paramter into the url to retrieve the id from moneytransferdb*/
    @DeleteMapping("/accounts/{id}")
    public ResponseEntity<String> deleteAccount(@PathVariable Long id) {

        // checks to make sure the account ID exists
        if (!accountRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        accountRepository.deleteById(id);
        return ResponseEntity.ok(id + " account deleted successfully.");
    }

    /** transferMoney a chosen amount from one account to another.
     * You cannot transfer an amount of 0 or an amount that is greater than the senders balance. */
    @PostMapping("/transfer")
    public ResponseEntity<String> transferMoney(@RequestBody TransferFunds request) {

        long fromAccountID = request.getFromAccountId();
        long toAccountID = request.getToAccountId();
        double amount = request.getAmount();

        // conditional to make sure the amount being transferred is greater than 0
        if (amount <= 0) {
            return ResponseEntity.badRequest().body("Amount must be greater than 0!!");
        }

        Account fromAccount = accountRepository.findById(fromAccountID)
                .orElseThrow(() -> new RuntimeException("Source account not found"));
        Account toAccount = accountRepository.findById(toAccountID)
                .orElseThrow(() -> new RuntimeException("Destination account not found"));

        //conditional to make sure the sender has a balance greater than the amount to
        // transfer to the destination account (toAccount)  */
        if (fromAccount.getBalance() < amount) {
            return ResponseEntity.badRequest().body("Insufficient funds in source account");
        }

        // setting the new balance for both the to and from account
        fromAccount.setBalance(fromAccount.getBalance() - amount);
        toAccount.setBalance(toAccount.getBalance() + amount);

        // Saves the new balance to the fromAccount and toAccount
        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);
        System.out.println(amount + "has been sent to account " + toAccountID + " from account " + fromAccountID);
        return ResponseEntity.ok("Transfer successful");
    }

}