package com.repo;

import com.bank.Asset;
import com.repo.ex.InvalidTransactionStateException;
import com.repo.ex.UnfulfillableTransactionException;

public class BitcoinRepository implements IRepository {

	@Override
	public void deposit(Asset asset, long accountID) {
		// TODO Auto-generated method stub
	}
	@Override
	public void withdraw(Asset asset, long accountID) {
		// TODO Auto-generated method stub	
	}
	@Override
	public long startTransaction() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public void prepareDeposit(Asset asset, long accountID, long txID) {
		// TODO Auto-generated method stub	
	}
	@Override
	public void prepareWithdraw(Asset asset, long accountID, long txID) {
		// TODO Auto-generated method stub	
	}
	@Override
	public void applyTransaction(long txID)
			throws UnfulfillableTransactionException, InvalidTransactionStateException {
		// TODO Auto-generated method stub
	}
	@Override
	public void cancelTransaction(long txID) throws InvalidTransactionStateException {
		// TODO Auto-generated method stub
	}
	@Override
	public void rollbackTransaction(long txID) throws InvalidTransactionStateException {
		// TODO Auto-generated method stub	
	}
	@Override
	public void finalizeTransaction(long txID) throws InvalidTransactionStateException {
		// TODO Auto-generated method stub
	}
	@Override
	public void endAllTransactions() {
		// TODO Auto-generated method stub	
	}
}
