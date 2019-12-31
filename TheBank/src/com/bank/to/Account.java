package com.bank.to;

import java.util.Date;

import com.accountBO.AccountBoImp;
import com.bank.exception.BusinessException;

public class Account {
	int accountNumber;
	int userId;
	Date creationDate;
	int status;
	float startingBalance;
	
	// Constructors
	public Account() {};
	public Account(int accountNumber, int userId, Date creationDate, int status, float startingBalance) {
		super();
		this.accountNumber = accountNumber;
		this.userId = userId;
		this.creationDate = creationDate;
		this.status = status;
		this.startingBalance = startingBalance;
	}
	
	// Accessor and Mutators
	public int getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(int accountNumber) {
		this.accountNumber = accountNumber;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public float getStartingBalance() {
		return startingBalance;
	}
	public void setStartingBalance(int startingBalance) {
		this.startingBalance = startingBalance;
	}
	
	// Methods
	public String getUserFirstName() throws BusinessException {
		return new AccountBoImp().getUserFirstName(this);
	}
	
	public String getUserLastName() throws BusinessException {
		return new AccountBoImp().getUserLastName(this);
	}
	
}
