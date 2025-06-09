package com.gigmoneytransferapi.spring.money_transfer;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
public class TransferControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AccountRepository accountRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    /** clears the database before every test */
    @BeforeEach
    void setup() {
        accountRepository.deleteAll();
    }

    /** Tests that showAccounts method returns a list of all accounts in the database */
    @Test
    void testGetAllAccounts() throws Exception {
        accountRepository.save(new Account(2, "Gandalf", 500.0));
        accountRepository.save(new Account(22, "Saruman", 700.0));

        mockMvc.perform(get("/all-accounts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Gandalf"))
                .andExpect(jsonPath("$[1].name").value("Saruman"));
    }




    /** ensure we can add an account that doesn't already exist in the moneytransferdb database */
    @Test
    void testAddAccount() throws Exception {
        Account account = new Account(1, "Legolas", 500.0);

        mockMvc.perform(post("/account")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(account)))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Legolas")));
    }

    /** ensures we can't add an account that already exist in the database */
    @Test
    void testAddAccountAlreadyExists() throws Exception {
        Account account = new Account(1, "Gollum", 500.0);
        accountRepository.save(account);

        mockMvc.perform(post("/account")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(account)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("Account already in database")));
    }




    /** Ensures an existing account can be deleted */
    @Test
    void testDeleteAccount() throws Exception {
        accountRepository.save(new Account(7, "Aragorn", 500.0));

        mockMvc.perform(delete("/accounts/7"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("account deleted successfully.")));
    }

    /** Tests notfound when trying to delete an ID that doesn't exist in the moneytransferdb */
    @Test
    void testDeleteNotFoundAccount() throws Exception {
        long notExists = 999; // some ID that does not exist

        mockMvc.perform(delete("/accounts/{id}", notExists))
                .andExpect(status().isNotFound());
    }




    /** This is a test to make sure that when we transfer an amount that is less
     * than the source accounts balance the transaction goes through */
    @Test
    void testTransferMoney() throws Exception {
        accountRepository.save(new Account(3, "Frodo", 1000.0));
        accountRepository.save(new Account(4, "Bilbo", 1000.0));

        TransferFunds transfer = new TransferFunds(3, 4, 500.0);

        mockMvc.perform(post("/transfer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(transfer)))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Transfer successful")));
    }
    /** This tests functionality to make sure we can transfer an amount that is the entire balance
     * of the source account */
    @Test
    void testTransferMoneyEqualAmount() throws Exception {
        accountRepository.save(new Account(300, "Sauron", 1000.0));
        accountRepository.save(new Account(400, "Galadriel", 1000.0));

        TransferFunds transfer = new TransferFunds(300, 400, 1000.0);

        mockMvc.perform(post("/transfer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(transfer)))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Transfer successful")));
    }

    /** This ensures a message is shown warning you can't transfer 0 */
    @Test
    void testTransferAmountZero() throws Exception {
        accountRepository.save(new Account(5, "Pippin", 1000.0));
        accountRepository.save(new Account(6, "Merry", 1000.0));

        TransferFunds transfer = new TransferFunds(5, 6, 0.0); // amount = 0, invalid

        mockMvc.perform(post("/transfer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(transfer)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("Amount must be greater than 0!!")));
    }

    /** This ensures you can't transfer an amount greater than your account balance */
    @Test
    void testTransferInsufficientFunds() throws Exception {
        accountRepository.save(new Account(50, "Pippin", 1000.0));
        accountRepository.save(new Account(60, "Merry", 1000.0));

        TransferFunds transfer = new TransferFunds(50, 60, 2000.0); // amount = 0, invalid

        mockMvc.perform(post("/transfer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(transfer)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("Insufficient funds in source account")));
    }
}