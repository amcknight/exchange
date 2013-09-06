package com.bank;

public class Item {
	
	public static final int DEFAULT_GRANULARITY = 9;
	
	private String name;
	private int granularity;
	
	public Item(String name) {
		this(name, DEFAULT_GRANULARITY);
	}
	public Item(String name, int granularity) {
		this.name = name;
		this.granularity = granularity;
	}
	
	public Asset createAsset(long amount) {
		return new Asset(name, amount);
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getGranularity() {
		return granularity;
	}
	public void setGranularity(int granularity) {
		this.granularity = granularity;
	}
}
