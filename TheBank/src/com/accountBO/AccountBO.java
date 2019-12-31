package com.accountBO;

import java.util.Date;
import java.util.List;

import com.bank.exception.BusinessException;
import com.bank.to.Account;

public interface AccountBO {
	Account getAccountById(int id) throws BusinessException;
	Account getAccountByUserId(int userId);
	int getMaxAccountNumber() throws BusinessException;
	public int getPendingApprovalCount() throws BusinessException;
	List<Account> getAccountsByCreationDate(Date date);
	List<Account> getAccountsByStatus(int status) throws BusinessException;
	boolean addNewAccount(Account account) throws BusinessException;
	boolean updateAccountStatus(Account account, int status) throws BusinessException;
}
