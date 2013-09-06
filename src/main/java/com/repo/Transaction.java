package com.repo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import com.bank.Asset;
import com.repo.ex.InvalidTransactionStateTransitionException;

public class Transaction {
	private ArrayList<Modification> mods = new ArrayList<Modification>();
	private Status status = Status.EMPTY;
	
	public Transaction(){}
	
	public void add(Asset asset, long accountID, boolean isDeposit) {
		mods.add(new Modification(asset, accountID, isDeposit));
	}
	
	public HashMap<Long, ArrayList<Asset>> getDepositsByAccount() {
		HashMap<Long, ArrayList<Asset>> deps = new HashMap<Long, ArrayList<Asset>>();
		for (Modification m : mods) {
			if (m.isDeposit) {
				if (!deps.containsKey(m.accountID)) {
					deps.put(m.accountID, new ArrayList<Asset>());
				}
				deps.get(m.accountID).add(m.asset);
			}
		}
		return deps;
	}
	
	public HashMap<Long, ArrayList<Asset>> getWithdrawsByAccount() {
		HashMap<Long, ArrayList<Asset>> withs = new HashMap<Long, ArrayList<Asset>>();
		for (Modification m : mods) {
			if (!m.isDeposit) {
				if (!withs.containsKey(m.accountID)) {
					withs.put(m.accountID, new ArrayList<Asset>());
				}
				withs.get(m.accountID).add(m.asset);
			}
		}
		return withs;
	}
	
	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) throws InvalidTransactionStateTransitionException {
		synchronized (this) {
			if (Status.isValidTransition(this.status, status)) {
				this.status = status;
			} else {
				throw new InvalidTransactionStateTransitionException(this.status, status);
			}
		}
	}

	//TODO Should this be static?
	public enum Status {
		EMPTY,		// An EMPTY tx cannot be FINISHED or CLOSED, only CANCELED. This is the initial state.
		OPEN,		// A non-EMPTY but unfinished and uncanceled transaction. Can only be FINISHED or CANCELED.
		FINISHING,	// A FINISHING tx that is in the process of being FINISHED.
		FINISHED,	// A FINISHED tx is no longer OPEN. It can only be rolled back or CLOSED.
		CANCELING,	// A CANCELING tx cannot be affected. It will very soon be deleted without being finished.
		CANCELED,	// A CANCELED tx cannot be affected. It is an end state like CLOSED.
		ROLLING,	// A ROLLING back tx is being undone. It will automatically move to OPEN or CANCELING.
		CLOSING,	// A CLOSING tx is moving to CLOSED. It cannot be stopped.
		CLOSED;		// A CLOSED tx. It is an end state like CANCELED.
		
		private static HashMap<Status, List<Status>> map = new HashMap<Status, List<Status>>();
		static{
			map.put(EMPTY, Arrays.asList(EMPTY, OPEN, CANCELING));
			map.put(OPEN, Arrays.asList(EMPTY, OPEN, FINISHING, CANCELING));
			map.put(FINISHING, Arrays.asList(FINISHING, FINISHED));
			map.put(FINISHED, Arrays.asList(FINISHED, CANCELING, ROLLING, CLOSING));
			map.put(CANCELING, Arrays.asList(CANCELING, CANCELED));
			map.put(CANCELED, Arrays.asList(CANCELED));
			map.put(ROLLING, Arrays.asList(OPEN, ROLLING));
			map.put(CLOSING, Arrays.asList(CLOSING, CLOSED));
			map.put(CLOSED, Arrays.asList(CLOSED));
		}
		
		public static boolean isValidTransition(Status oldStatus, Status newStatus) {
			return map.get(oldStatus).contains(newStatus);
		}
	}
	
}

class Modification {
	public Asset asset;
	public long accountID;
	public boolean isDeposit;
	public Modification(Asset asset, long accountID, boolean isDeposit) {
		this.asset = asset;
		this.accountID = accountID;
		this.isDeposit = isDeposit;
	}
}
