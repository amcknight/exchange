package com.repo.ex;

import com.repo.Transaction.Status;

public class InvalidTransactionStateException extends Exception {
	private static final long serialVersionUID = 5722297718593685087L;
	
	private long txID;
	private Status txStatus;
	
	public InvalidTransactionStateException(){
		super();
	}
	public InvalidTransactionStateException(Throwable cause) {
		super(cause);
	}
	public InvalidTransactionStateException(String message) {
		super(message);
	}
	public InvalidTransactionStateException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public InvalidTransactionStateException(long txID) {
		this();
		this.txID = txID;
	}
	public InvalidTransactionStateException(long txID, Throwable cause) {
		this(cause);
		this.txID = txID;
	}
	public InvalidTransactionStateException(String message, long txID) {
		this(message);
		this.txID = txID;
	}
	public InvalidTransactionStateException(String message, long txID, Throwable cause) {
		this(message, cause);
		this.txID = txID;
	}
	
	public InvalidTransactionStateException(long txID, Status txStatus) {
		this(txID);
		this.txStatus = txStatus;
	}
	public InvalidTransactionStateException(long txID, Status txStatus, Throwable cause) {
		this(txID, cause);
		this.txStatus = txStatus;
	}
	public InvalidTransactionStateException(String message, long txID, Status txStatus) {
		this(message, txID);
		this.txStatus = txStatus;
	}
	public InvalidTransactionStateException(String message, long txID, Status txStatus, Throwable cause) {
		this(message, txID, cause);
		this.txStatus = txStatus;
	}
	
	public long getTxID() {
		return txID;
	}
	public void setTxID(long txID) {
		this.txID = txID;
	}
	public Status getTxStatus() {
		return txStatus;
	}
	public void setTxStatus(Status txStatus) {
		this.txStatus = txStatus;
	}
}
