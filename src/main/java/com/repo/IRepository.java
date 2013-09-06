package com.repo;

import com.bank.Asset;
import com.repo.ex.InvalidTransactionStateException;
import com.repo.ex.UnfulfillableTransactionException;

public interface IRepository {

	public void deposit(Asset asset, long accountID);
	public void withdraw(Asset asset, long accountID);
	
	public long startTransaction();
	
	public void prepareDeposit(Asset asset, long accountID, long txID);
	public void prepareWithdraw(Asset asset, long accountID, long txID);
	
	public void applyTransaction(long txID) throws UnfulfillableTransactionException, InvalidTransactionStateException;
	public void cancelTransaction(long txID) throws InvalidTransactionStateException;
	public void rollbackTransaction(long txID) throws InvalidTransactionStateException;
	public void finalizeTransaction(long txID) throws InvalidTransactionStateException;
	
	public void endAllTransactions();
}