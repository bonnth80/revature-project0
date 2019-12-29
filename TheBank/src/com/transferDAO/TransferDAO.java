package com.transferDAO;

import java.util.Date;
import java.util.List;

import com.bank.to.Transfer;

public interface TransferDAO {
	Transfer getTransferById(int id);
	List<Transfer> getTransfersBySourceAccount(int accountId);
	List<Transfer> getTransfersByDestinationAccount(int accountId);
	List<Transfer> getTransfersByStatus(int status);
	List<Transfer> getTransfersByRequestDate(Date date);
	List<Transfer> getTransfersByApprovalDate(Date date);
}
	
