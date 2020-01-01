package com.transactionDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.List;

import com.bank.exception.BusinessException;
import com.bank.to.Transaction;
import com.dbutil.OracleConnection;

public class TransactionDaoImp implements TransactionDAO {

	@Override
	public Transaction getTransactionById(int id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public int getMaxTransactionId() throws BusinessException{
		try (Connection connection = OracleConnection.getConnection()) {
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("SELECT MAX(transaction_id) FROM transaction_history");
			rs.next();
			return rs.getInt(1);
		} catch (ClassNotFoundException | SQLException e) {
			throw new BusinessException("Internal error: " + e);
		}
	}

	@Override
	public List<Transaction> getTransactionsByAccountId(int accountId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Transaction> getTransactionsByActingParty(String actingParty) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Transaction> getTransactionByDate(Date date) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Transaction getTransactionByTransferId(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean addTransaction(Transaction transaction) throws BusinessException {

		try (Connection connection = OracleConnection.getConnection()) {
			String sql = "INSERT INTO transaction_history "
					+ "VALUES (?,?,?,?,?,?,?)";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1, transaction.getTransactionId());
			ps.setInt(2, transaction.getAccountId());
			ps.setString(3, transaction.getActingParty());
			ps.setFloat(4, transaction.getCredit());
			ps.setFloat(5,  transaction.getDebit());
			ps.setDate(6, new java.sql.Date(transaction.getTransactionDate().getTime()));
			ps.setInt(7, transaction.getTransferId());
			return ps.execute();			
		} catch (ClassNotFoundException | SQLException e) {
			throw new BusinessException("Internal error: " + e);
		}
	}

}
