package com.accountBO;

import java.util.Date;
import java.util.List;

import com.bank.exception.BusinessException;
import com.bank.to.Account;

public interface AccountBO {
	Account getAccountById(int id);
	Account getAccountByUserId(int userId);
	public int getPendingApprovalCount() throws BusinessException;
	List<Account> getAccountsByCreationDate(Date date);
	List<Account> getAccountsByStatus(int status) throws BusinessException;
	boolean addNewAccount(Account account);
	boolean updateAccountStatus(Account account, int status);
}
