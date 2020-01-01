package com.transactionBO;

import java.util.Date;
import java.util.List;

import com.bank.exception.BusinessException;
import com.bank.to.Transaction;

public interface TransactionBO {
	Transaction getTransactionById(int id);
	int getMaxTransactionId() throws BusinessException;
	List<Transaction> getTransactionsByAccountId(int accountId);
	List<Transaction> getTransactionsByActingParty(String actingParty);
	List<Transaction> getTransactionByDate(Date date);
	Transaction getTransactionByTransferId(int id);
	
	boolean addTransaction(Transaction transaction) throws BusinessException;
}
