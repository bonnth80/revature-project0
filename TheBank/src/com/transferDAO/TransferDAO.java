package com.transferDAO;

import java.util.Date;
import java.util.List;

import com.bank.exception.BusinessException;
import com.bank.to.Transfer;

public interface TransferDAO {
	Transfer getTransferById(int id);
	int getMaxTransferId() throws BusinessException;
	int getTransferCountByUserId(int userId) throws BusinessException;
	List<Transfer> getTransfersByUserId(int userId) throws BusinessException;
	List<Transfer> getTransfersBySourceAccount(int accountId);
	List<Transfer> getTransfersByDestinationAccount(int accountId);
	List<Transfer> getTransfersByStatus(int status);
	List<Transfer> getTransfersByRequestDate(Date date);
	List<Transfer> getTransfersByApprovalDate(Date date);
	boolean addNewTransfer(Transfer transfer) throws BusinessException;
	boolean updateTransferStatus(Transfer transfer, int status) throws BusinessException;
}
	
