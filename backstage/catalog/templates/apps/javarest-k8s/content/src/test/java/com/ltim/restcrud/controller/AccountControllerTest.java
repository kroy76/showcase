package com.ltim.restcrud.controller;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ltim.restcrud.model.Account;
import com.ltim.restcrud.repository.AccountRepository;

@SpringBootTest
@AutoConfigureMockMvc
public class AccountControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AccountRepository accountRepository;

    @Test
    public void createAccount() throws Exception 
    {
        Account account = new Account();
        account.setCustomerId(1);
        account.setAccountNumber(12345);
        account.setAccountType("AWS");
        account.setBranchAddress("Kolkata");
        account.setCreateDt(LocalDate.of(2012, 12, 10));

        Mockito.when(accountRepository.save(Mockito.any(Account.class))).thenReturn(account);

            mvc.perform(MockMvcRequestBuilders
                    .post("/account")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(account)))
                    .andExpect(status().isCreated())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.customerId").exists());
    }

    @Test
    public void testGetAccountDetails() throws Exception {
        int customerId = 1;
        Account account1 = new Account();
        account1.setCustomerId(0);
        account1.setAccountNumber(12345);
        account1.setAccountType("AWS");
        account1.setBranchAddress("Kolkata");
        account1.setCreateDt(LocalDate.of(2012, 12, 10));

        Account account2 = new Account();
        account2.setCustomerId(1);
        account2.setAccountNumber(12345);
        account2.setAccountType("AWS");
        account2.setBranchAddress("Kolkata");
        account2.setCreateDt(LocalDate.of(2012, 12, 10));
        List<Account> mockAccounts = Arrays.asList(account1, account2);

        when(accountRepository.findByCustomerId(customerId)).thenReturn(mockAccounts);

        MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/account/{customerId}", customerId))
                .andReturn();

        assertEquals(200, result.getResponse().getStatus());
        String expectedJson = "[{\"customerId\":0,\"accountNumber\":12345,\"accountType\":\"AWS\",\"branchAddress\":\"Kolkata\",\"createDt\":\"2012-12-10\"},{\"customerId\":1,\"accountNumber\":12345,\"accountType\":\"AWS\",\"branchAddress\":\"Kolkata\",\"createDt\":\"2012-12-10\"}]";
        assertEquals(expectedJson, result.getResponse().getContentAsString());
    }

    @Test
    public void testGetAccountDetailsNoAccounts() throws Exception {
        int customerId = 2;

        when(accountRepository.findByCustomerId(customerId)).thenReturn(null);

        MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/account/{customerId}", customerId))
                .andReturn();

        assertEquals(200, result.getResponse().getStatus());
        assertEquals("", result.getResponse().getContentAsString());
    }

}