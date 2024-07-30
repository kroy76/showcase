package com.ltim.${{ values.app_name }}.repository;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ltim.${{ values.app_name }}.model.Account;

import java.util.List;

@Repository
public interface AccountRepository extends CrudRepository<Account, Long> {

	List<Account> findByCustomerId(int customerId);

}
