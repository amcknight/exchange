package com.bank;

/**
 * An Asset is an amount of an Item
 */
public class Asset implements Cloneable{
	
	private String itemName;
	private long amount;
	
	public Asset(String itemName, long amount) {
		this.itemName = itemName;
		this.amount = amount;
	}
	
	@Override
	public Asset clone() {
		return new Asset(itemName, amount);
	}
	
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public long getAmount() {
		return amount;
	}
	public void setAmount(long amount) {
		this.amount = amount;
	}
}
