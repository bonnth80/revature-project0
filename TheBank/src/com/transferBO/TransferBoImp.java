package com.transferBO;

import java.util.Date;
import java.util.List;

import com.bank.exception.BusinessException;
import com.bank.to.Transfer;
import com.transferDAO.TransferDAO;
import com.transferDAO.TransferDaoImp;

public class TransferBoImp implements TransferBO {

	@Override
	public Transfer getTransferById(int id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public int getTransferCount(int accountId) throws BusinessException {
		TransferDAO dao = new TransferDaoImp();
		return dao.getTransferCount(accountId);
	}
	
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
