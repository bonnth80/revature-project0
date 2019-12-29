package com.accountDAO;

import java.util.Date;
import java.util.List;

import com.bank.to.Account;

public class AccountDaoImp implements AccountDAO {

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
	public List<Account> getAccountsByCreationDate(Date date) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Account> getAccountsByStatus(int status) {
		// TODO Auto-generated method stub
		return null;
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
