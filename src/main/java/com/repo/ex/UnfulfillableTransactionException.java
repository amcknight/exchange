package com.repo.ex;

public class UnfulfillableTransactionException extends Exception {
	private static final long serialVersionUID = -7418236779665088268L;
	
	private long txID;
	
	public UnfulfillableTransactionException(){
		super();
	}
	public UnfulfillableTransactionException(long txID) {
		super();
		this.txID = txID;
	}
	
	public long getTxID() {
		return txID;
	}
	public void setTxID(long txID) {
		this.txID = txID;
	}
}
