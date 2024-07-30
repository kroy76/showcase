/**
 * 
 */
package com.ltim.${{ values.app_name }}.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ltim.${{ values.app_name }}.model.Account;
import com.ltim.${{ values.app_name }}.repository.AccountRepository;

import java.rmi.ServerException;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

/**
 * @author Gopal Barik
 *
 */

@RestController
public class AccountController {
	
	@Autowired
	private AccountRepository accountsRepository;

	@GetMapping("/account/{customerId}")
	public List<Account> getAccountDetails(@PathVariable("customerId") int  customerId) {

		List<Account> accounts = accountsRepository.findByCustomerId(customerId);
		if (accounts != null) {
			return accounts;
		} else {
			return null;
		}

	}

	@PostMapping("/account")
	public ResponseEntity<Account> getAccountDetails(@RequestBody Account account)throws Exception {

		account.setCreateDt(new Date().toInstant()
				.atZone(ZoneId.systemDefault())
				.toLocalDate());
		Account act = accountsRepository.save(account);
		if(null != act){
			return new ResponseEntity<>(act, HttpStatus.CREATED);
		} else {
			throw new ServerException("");
		}
	}

}
