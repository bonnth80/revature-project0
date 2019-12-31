package com.accountDAO;

import java.util.Date;
import java.util.List;

import com.bank.exception.BusinessException;
import com.bank.to.Account;

public interface AccountDAO {
	Account getAccountByAccountNumber(int accountNumber) throws BusinessException;
	List<Account> getAccountsByUserId(int userId) throws BusinessException;
	int getMaxAccountNumber() throws BusinessException;
	int getPendingApprovalCount() throws BusinessException;
	List<Account> getAccountsByCreationDate(Date date);
	List<Account> getAccountsByStatus(int status) throws BusinessException;
	boolean addNewAccount(Account account) throws BusinessException;
	boolean updateAccountStatus(Account account, int status) throws BusinessException;
}
