package com.repo.ex;

import com.repo.Transaction.Status;

public class InvalidTransactionStateTransitionException extends Exception {
	private static final long serialVersionUID = -1169342378966628508L;
	
	private Status oldStatus;
	private Status newStatus;
	
	public InvalidTransactionStateTransitionException(){
		super();
	}
	public InvalidTransactionStateTransitionException(Status oldStatus, Status newStatus) {
		this();
		this.oldStatus = oldStatus;
		this.newStatus = newStatus;
	}
	
	public Status getOldStatus() {
		return oldStatus;
	}
	public void setOldStatus(Status oldStatus) {
		this.oldStatus = oldStatus;
	}
	
	public Status getNewStatus() {
		return newStatus;
	}
	public void setNewStatus(Status newStatus) {
		this.newStatus = newStatus;
	}
}
