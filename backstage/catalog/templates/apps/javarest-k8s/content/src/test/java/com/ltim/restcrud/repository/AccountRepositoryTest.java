package com.ltim.restcrud.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.ltim.restcrud.model.Account;

@DataJpaTest
public class AccountRepositoryTest {

    @Autowired
    private AccountRepository accountRepository;

    @BeforeEach
    public void setUp() {
        accountRepository.deleteAll();

        // Insert some test data
        Account account2 = new Account();
        account2.setCustomerId(1);
        account2.setAccountNumber(1234);
        account2.setAccountType("accountType1");
        account2.setBranchAddress("Kolkata");
        account2.setCreateDt(LocalDate.of(2012, 12, 10));
        accountRepository.save(account2);

        Account account3 = new Account();
        account3.setCustomerId(1);
        account3.setAccountNumber(12345);
        account3.setAccountType("accountType1");
        account3.setBranchAddress("Kolkata");
        account3.setCreateDt(LocalDate.of(2012, 12, 10));
        accountRepository.save(account3);

        Account account4 = new Account();
        account4.setCustomerId(2);
        account4.setAccountNumber(123455);
        account4.setAccountType("accountType3");
        account4.setBranchAddress("Kolkata");
        account4.setCreateDt(LocalDate.of(2012, 12, 10));
        accountRepository.save(account4);
    }

    @Test
    public void testFindByCustomerId() {
        List<Account> accountsCustomer1 = accountRepository.findByCustomerId(1);
        assertEquals(2, accountsCustomer1.size());
        assertTrue(accountsCustomer1.stream().anyMatch(account -> account.getAccountType().equals("accountType1")));

        List<Account> accountsCustomer2 = accountRepository.findByCustomerId(2);
        assertEquals(1, accountsCustomer2.size());
        assertTrue(accountsCustomer2.stream().anyMatch(account -> account.getAccountType().equals("accountType3")));
    }

    @Test
    public void testFindByCustomerIdNoResults() {
        List<Account> accountsCustomer3 = accountRepository.findByCustomerId(3);
        assertTrue(accountsCustomer3.isEmpty());
    }
}
