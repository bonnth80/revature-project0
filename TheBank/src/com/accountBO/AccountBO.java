package com.accountBO;

import java.util.Date;
import java.util.List;

import com.bank.to.Account;

public interface AccountBO {
	Account getAccountById(int id);
	Account getAccountByUserId(int userId);
	List<Account> getAccountsByCreationDate(Date date);
	List<Account> getAccountsByStatus(int status);
	boolean addNewAccount(Account account);
	boolean updateAccountStatus(Account account, int status);
}
