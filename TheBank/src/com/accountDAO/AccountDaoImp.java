package com.accountDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.bank.exception.BusinessException;
import com.bank.to.Account;
import com.dbutil.OracleConnection;

public class AccountDaoImp implements AccountDAO {

	@Override
	public Account getAccountById(int id) throws BusinessException {
		try (Connection connection = OracleConnection.getConnection()) {
			String sql = "SELECT a.account_number, bu.first_name, bu.last_name, a.creation_date, a.status "
					+ "FROM account a "
					+ "INNER JOIN bank_user bu "
					+ "ON a.user_id = bu.user_id "
					+ "WHERE a.account_number = ?";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			
			rs.next();
			return new Account(
						rs.getInt(1),
						rs.getString(2),
						rs.getString(3),
						rs.getDate(4),
						rs.getInt(5));
		} catch (ClassNotFoundException | SQLException e) {
			throw new BusinessException("Internal error: " + e);
		}
	}

	@Override
	public Account getAccountByUserId(int userId) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public int getPendingApprovalCount() throws BusinessException {
		int count = 0;
		
		try (Connection connection = OracleConnection.getConnection()) {
			String sql = "SELECT COUNT(*) FROM account "
					+ "WHERE status = 0";
			PreparedStatement ps = connection.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			rs.next();
			count = rs.getInt(1);			
			
		} catch (ClassNotFoundException | SQLException e) {
			throw new BusinessException("Internal error: " + e);
		}
		return count;
	}

	@Override
	public List<Account> getAccountsByCreationDate(Date date) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Account> getAccountsByStatus(int status) throws BusinessException {
		List<Account> accounts = new ArrayList<>();
		
		try (Connection connection = OracleConnection.getConnection()) {
			String sql = "SELECT a.account_number, bu.first_name, bu.last_name, a.creation_date, a.status "
					+ "FROM account a "
					+ "INNER JOIN bank_user bu "
					+ "ON a.user_id = bu.user_id "
					+ "WHERE a.status = ?";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1, status);
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				accounts.add(new Account(
						rs.getInt(1),
						rs.getString(2),
						rs.getString(3),
						rs.getDate(4),
						rs.getInt(5)
						));
			}
			
			return accounts;
		} catch (ClassNotFoundException | SQLException e) {
			throw new BusinessException("Internal error: " + e);
		}
	}

	@Override
	public boolean addNewAccount(Account account) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateAccountStatus(Account account, int status) throws BusinessException {
		try (Connection connection = OracleConnection.getConnection()) {
			String sql = "UPDATE account "
					+ "SET status = ? "
					+ "WHERE account_number = ?";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1, status);
			ps.setInt(2, account.getAccountNumber());
			return ps.execute();
		} catch (ClassNotFoundException | SQLException e) {
			throw new BusinessException("Internal error: " + e);
		}
	}

}
