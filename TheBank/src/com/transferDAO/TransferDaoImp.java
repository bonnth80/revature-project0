package com.transferDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.List;

import com.bank.exception.BusinessException;
import com.bank.to.Transfer;
import com.dbutil.OracleConnection;

public class TransferDaoImp implements TransferDAO {

	@Override
	public Transfer getTransferById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getMaxTriggerId() throws BusinessException {
		try (Connection connection = OracleConnection.getConnection()) {
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("SELECT MAX(transfer_id) FROM transfer_request");
			rs.next();
			return rs.getInt(1);
		} catch (ClassNotFoundException | SQLException e) {
			throw new BusinessException("Internal error: " + e);
		}
	}
	
	@Override
	public int getTransferCountByUserId(int userId) throws BusinessException {
		int count = 0;
		try (Connection connection = OracleConnection.getConnection()) {
			String sql =  "SELECT COUNT(*) "
					+ "FROM transfer_request tr "
					+ "INNER JOIN account a "
					+ "ON a.account_number = tr.destination_account "
					+ "WHERE a.user_id = ? "
					+ "ORDER BY tr.transfer_id";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, userId);
			ResultSet result = preparedStatement.executeQuery();
			result.next();
			count = result.getInt(1);
			return count;
		} catch (ClassNotFoundException | SQLException e) {
			throw new BusinessException("Internal error: " + e);
		}
	};

	@Override
	public List<Transfer> getTransfersBySourceAccount(int accountId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Transfer> getTransfersByDestinationAccount(int accountId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Transfer> getTransfersByStatus(int status) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Transfer> getTransfersByRequestDate(Date date) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Transfer> getTransfersByApprovalDate(Date date) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean addNewTransfer(Transfer transfer) throws BusinessException {
		try (Connection connection = OracleConnection.getConnection()) {
			String sql = "INSERT INTO transfer_request "
					+ "VALUES (?,?,?,?,?,?,?)";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1, transfer.getTransferId());
			ps.setFloat(2, transfer.getAmount());
			ps.setInt(3, transfer.getSource());
			ps.setInt(4, transfer.getDestination());
			ps.setInt(5, transfer.getStatus());
			ps.setDate(6,  new java.sql.Date(transfer.getRequestDate().getTime()));
			ps.setDate(7, null);
			
			return ps.execute();
		} catch (ClassNotFoundException | SQLException e) {
			throw new BusinessException("Internal error: " + e);
		}
	}
	
}
