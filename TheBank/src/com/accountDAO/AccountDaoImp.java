package com.accountDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.bank.exception.BusinessException;
import com.bank.to.Account;
import com.dbutil.OracleConnection;

public class AccountDaoImp implements AccountDAO {

	@Override
	public Account getAccountByAccountNumber(int id) throws BusinessException {
		try (Connection connection = OracleConnection.getConnection()) {
			String sql = "SELECT a.account_number, "
					+ "        bu.user_id, "
					+ "        a.creation_date, "
					+ "        a.status, "
					+ "        a.starting_balance, "
					+ "        a.starting_balance + SUM(th.credit) - SUM(th.debit) as avail "
					+ "FROM account a "
					+ "INNER JOIN bank_user bu "
					+ "ON a.user_id = bu.user_id "
					+ "INNER JOIN transaction_history th "
					+ "ON th.account_number = a.account_number "
					+ "WHERE a.account_number = ? "
					+ "group by a.account_number, bu.user_id, a.creation_date, a.status, a.starting_balance; ";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			
			rs.next();
			return new Account(
						rs.getInt(1),
						rs.getInt(2),
						rs.getDate(3),
						rs.getInt(4),
						rs.getFloat(5),
						rs.getFloat(6));
		} catch (ClassNotFoundException | SQLException e) {
			throw new BusinessException("Internal error: " + e);
		}
	}
	
	@Override
	public int getMaxAccountNumber() throws BusinessException {
		try (Connection connection = OracleConnection.getConnection()) {
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("SELECT MAX(account_number) FROM account");
			rs.next();
			return rs.getInt(1);
		} catch (ClassNotFoundException | SQLException e) {
			throw new BusinessException("Internal error: " + e);
		}
	}
	
	@Override
	public List<Account> getAccountsByUserId(int userId) throws BusinessException {
		List<Account> accounts = new ArrayList<>();
		
		try (Connection connection = OracleConnection.getConnection()) {
			String sql = "SELECT a.account_number, "
					+ "        bu.user_id, "
					+ "        a.creation_date, "
					+ "        a.status, "
					+ "        a.starting_balance, "
					+ "        a.starting_balance + SUM(th.credit) - SUM(th.debit) as avail "
					+ "FROM account a "
					+ "INNER JOIN bank_user bu "
					+ "ON a.user_id = bu.user_id "
					+ "INNER JOIN transaction_history th "
					+ "ON th.account_number = a.account_number "
					+ "WHERE a.user_id = ? "
					+ "group by a.account_number, bu.user_id, a.creation_date, a.status, a.starting_balance; ";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1, userId);
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				accounts.add(new Account(
						rs.getInt(1),
						rs.getInt(2),
						rs.getDate(3),
						rs.getInt(4),
						rs.getFloat(5),
						rs.getFloat(6)
						));
			}
			
			return accounts;
		} catch (ClassNotFoundException | SQLException e) {
			throw new BusinessException("Internal error: " + e);
		}
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
			String sql = "SELECT a.account_number, "
					+ "        bu.user_id, "
					+ "        a.creation_date, "
					+ "        a.status, "
					+ "        a.starting_balance, "
					+ "        a.starting_balance + SUM(th.credit) - SUM(th.debit) as avail "
					+ "FROM account a "
					+ "INNER JOIN bank_user bu "
					+ "ON a.user_id = bu.user_id "
					+ "INNER JOIN transaction_history th "
					+ "ON th.account_number = a.account_number "
					+ "WHERE a.status = ? "
					+ "group by a.account_number, bu.user_id, a.creation_date, a.status, a.starting_balance; ";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1, status);
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				accounts.add(new Account(
						rs.getInt(1),
						rs.getInt(2),
						rs.getDate(3),
						rs.getInt(4),
						rs.getFloat(5),
						rs.getFloat(6)
						));
			}
			
			return accounts;
		} catch (ClassNotFoundException | SQLException e) {
			throw new BusinessException("Internal error: " + e);
		}
	}

	@Override
	public boolean addNewAccount(Account account) throws BusinessException {
		try (Connection connection = OracleConnection.getConnection()) {
			String sql = "INSERT INTO account "
					+ "VALUES (?,?,?,?,?)";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1, account.getAccountNumber());
			ps.setInt(2, account.getUserId());
			ps.setDate(3, new java.sql.Date(account.getCreationDate().getTime()));
			ps.setInt(4, account.getStatus());
			ps.setFloat(5, account.getStartingBalance());
			return ps.execute();
		} catch (ClassNotFoundException | SQLException e) {
			throw new BusinessException("Internal error: " + e);
		}
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
	
	//Methods
	public String getUserFirstName(Account account) throws BusinessException {
		try (Connection connection = OracleConnection.getConnection()) {
			String sql = "SELECT bu.first_name "
					+ "FROM account a "
					+ "INNER JOIN bank_user bu "
					+ "ON a.user_id = bu.user_id "
					+ "WHERE a.account_number = ?";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1, account.getAccountNumber());
			ResultSet rs = ps.executeQuery();
			rs.next();
			return rs.getString(1);
		} catch (ClassNotFoundException | SQLException e) {
			throw new BusinessException("Internal error: " + e);
		}
	}

	public String getUserLastName(Account account) throws BusinessException {
		try (Connection connection = OracleConnection.getConnection()) {
			String sql = "SELECT bu.last_name "
					+ "FROM account a "
					+ "INNER JOIN bank_user bu "
					+ "ON a.user_id = bu.user_id "
					+ "WHERE a.account_number = ?";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1, account.getAccountNumber());
			ResultSet rs = ps.executeQuery();
			rs.next();
			return rs.getString(1);			
		} catch (ClassNotFoundException | SQLException e) {
			throw new BusinessException("Internal error: " + e);
		}
	}

}
