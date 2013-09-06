package com.bank;

public interface IAccount {
	
	public void deposit(Asset asset);
	
	public void withdraw(Asset asset) throws IllegalArgumentException;
	
	public boolean contains(Asset asset);
}
