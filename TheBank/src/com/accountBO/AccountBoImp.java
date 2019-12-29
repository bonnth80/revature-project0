package com.accountBO;

import java.util.Date;
import java.util.List;

import com.accountDAO.AccountDAO;
import com.accountDAO.AccountDaoImp;
import com.bank.exception.BusinessException;
import com.bank.to.Account;

public class AccountBoImp implements AccountBO {

	@Override
	public Account getAccountById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Account getAccountByUserId(int userId) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public int getPendingApprovalCount() throws BusinessException {
		AccountDAO act = new AccountDaoImp();
		return act.getPendingApprovalCount();
	}

	@Override
	public List<Account> getAccountsByCreationDate(Date date) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Account> getAccountsByStatus(int status) throws BusinessException {
		AccountDAO ad = new AccountDaoImp();
		List<Account> accountList = ad.getAccountsByStatus(status);
		return accountList;
	}

	@Override
	public boolean addNewAccount(Account account) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateAccountStatus(Account account, int status) {
		// TODO Auto-generated method stub
		return false;
	}

}
