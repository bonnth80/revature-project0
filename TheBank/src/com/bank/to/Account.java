package com.bank.to;

import java.util.Date;

public class Account {
	int accountNumber;
	String userFirstName;
	String userLastName;
	Date creationDate;
	int status;
	
	// Constructors
	public Account() {};
	public Account(int accountNumber, String userFirstName, String userLastName, Date creationDate, int status) {
		super();
		this.accountNumber = accountNumber;
		this.userFirstName = userFirstName;
		this.userLastName = userLastName;
		this.creationDate = creationDate;
		this.status = status;
	}
	
	// Accessor and Mutators
	public int getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(int accountNumber) {
		this.accountNumber = accountNumber;
	}
	public String getUserFirstName() {
		return userFirstName;
	}
	public void setUserFirstName(String userFirstName) {
		this.userFirstName = userFirstName;
	}
	public String getUserLastName() {
		return userLastName;
	}
	public void setUserLastName(String userLastName) {
		this.userLastName = userLastName;
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
	
	
}
