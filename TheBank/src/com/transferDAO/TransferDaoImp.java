package com.transferDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
	public int getTransferCount(int accountId) throws BusinessException {
		int count = 0;
		try (Connection connection = OracleConnection.getConnection()) {
			String sql = "SELECT COUNT(*) FROM transfer_request "
					+ "WHERE status = 0 AND source = ?";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, accountId);
			ResultSet result = preparedStatement.executeQuery();
			result.next();
			count = result.getInt(1);
		} catch (ClassNotFoundException | SQLException e) {
			throw new BusinessException("Internal error: " + e);
		}
		return count;
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
	
}
