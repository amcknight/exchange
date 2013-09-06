package com.bank.mem;

import java.util.HashMap;
import java.util.Map;

import com.bank.Asset;
import com.bank.IAccount;
import com.bank.IBank;
import com.bank.Item;
import com.repo.IRepository;
import com.repo.Transaction;
import com.repo.Transaction.Status;
import com.repo.ex.InvalidTransactionStateException;
import com.repo.ex.InvalidTransactionStateTransitionException;
import com.repo.ex.UnfulfillableTransactionException;

/**
 * Holds a set of root accounts and a catalog of items that this Bank supports.
 * TODO Test this first.
 */
public class Bank implements IBank, IRepository {
	
	private long ID = 1;
	private HashMap<Long, Transaction> transactions = new HashMap<Long, Transaction>();
	
	/** Mapping from accountID to account */
	public Map<Long, IAccount> accounts = new HashMap<Long, IAccount>();
	
	/** Mapping from itemName to item */
	public Map<String, Item> catalog = new HashMap<String, Item>();
	
	public Bank(){}
	
	public long addAccount(IAccount account) {
		long id;
		do {
			id = (long)(Math.random()*Long.MAX_VALUE);
		} while (containsAccount(id));
		accounts.put(id, account);
		return id;
	}
	@Override
	public boolean containsAccount(long accountID) {
		return accounts.containsKey(accountID);
	}
	@Override
	public boolean containsItem(String itemName) {
		return catalog.containsKey(itemName);
	}
	@Override
	public void deposit(Asset asset, long accountID) {
		accounts.get(accountID).deposit(asset);
	}
	@Override
	public void withdraw(Asset asset, long accountID) throws IllegalArgumentException {
		accounts.get(accountID).withdraw(asset);
	}

	@Override
	public long startTransaction() {
		long id = generateID();
		transactions.put(id, new Transaction());
		return id;
	}
	
	@Override
	public void prepareDeposit(Asset asset, long accountID, long txID) {
		prepareModification(asset, accountID, txID, true);
	}

	@Override
	public void prepareWithdraw(Asset asset, long accountID, long txID) {
		prepareModification(asset, accountID, txID, false);
	}
	
	public void prepareModification(Asset asset, long accountID, long txID, boolean isDeposit) {
		if (!transactions.containsKey(txID)) {
			throw new IllegalStateException(
					"There is no matching active transaction ID. Get a proper transaction started by using startTransaction()");
		}
		transactions.get(txID).add(asset, accountID, isDeposit);
	}
	
	@Override
	public void applyTransaction(long txID) throws UnfulfillableTransactionException, InvalidTransactionStateException {
		Transaction tx = null;
		synchronized(transactions) {
			if (transactions.containsKey(txID)) {
				throw new InvalidTransactionStateException(txID);
			}
			tx = transactions.get(txID);
		}
		synchronized(tx) {
			try {
				tx.setStatus(Status.FINISHING);
				if (!isValidTransaction(txID)) {
					throw new UnfulfillableTransactionException(txID);
				}
				//TODO UUUUgly. Should probably break down to the account level and merge tx where possible.
				for (long k: tx.getDepositsByAccount().keySet()) {
					for (Asset a: tx.getDepositsByAccount().get(k)) {
						accounts.get(k).deposit(a);
					}
				}
				for (long k: tx.getWithdrawsByAccount().keySet()) {
					for (Asset a: tx.getWithdrawsByAccount().get(k)) {
						accounts.get(k).withdraw(a);
					}
				}
				tx.setStatus(Status.FINISHED);
			} catch (InvalidTransactionStateTransitionException ex) {
				throw new InvalidTransactionStateException("Couldn't apply the transaction", txID, tx.getStatus(), ex);
			}
		}
	}
	
	@Override
	public void cancelTransaction(long txID) throws InvalidTransactionStateException {
		Transaction tx;
		synchronized(transactions) {
			if (transactions.containsKey(txID)) {
				throw new InvalidTransactionStateException(txID);
			}
			tx = transactions.get(txID);
		}
		synchronized(tx) {
			try {
				tx.setStatus(Status.CANCELING);
				//TODO We can't ignore rollbacks!
				tx.setStatus(Status.CANCELED);
			} catch (InvalidTransactionStateTransitionException ex) {
				throw new InvalidTransactionStateException("Couldn't cancel the transaction", txID, tx.getStatus(), ex);
			}
		}
	}

	@Override
	public void rollbackTransaction(long txID) throws InvalidTransactionStateException {
		Transaction tx;
		synchronized(transactions) {
			if (transactions.containsKey(txID)) {
				throw new InvalidTransactionStateException(txID);
			}
			tx = transactions.get(txID);
		}
		synchronized(tx) {
			//TODO Fill
		}
	}

	@Override
	public void finalizeTransaction(long txID) throws InvalidTransactionStateException {
		Transaction tx;
		synchronized(transactions) {
			if (transactions.containsKey(txID)) {
				throw new InvalidTransactionStateException(txID);
			}
			tx = transactions.get(txID);
		}
		synchronized(tx) {
			try {
				tx.setStatus(Status.CLOSING);
				//TODO We can't ignore rollbacks!
				tx.setStatus(Status.CLOSED);
			} catch (InvalidTransactionStateTransitionException ex) {
				throw new InvalidTransactionStateException("Couldn't close the transaction", txID, tx.getStatus(), ex);
			}
		}
	}
	
	@Override
	public void endAllTransactions() {
		synchronized(transactions) {
			transactions.clear();
		}
	}
	
	public boolean isValidTransaction(long txID) throws InvalidTransactionStateException {
		if (transactions.containsKey(txID)) {
			throw new InvalidTransactionStateException(txID);
		}
		return true;
	}
	
	private long generateID() {
		return ID++;
	}
}