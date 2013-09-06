package com.bank.mem;

import java.util.HashMap;
import java.util.Map;

import com.bank.Asset;
import com.bank.IAccount;

public class Account implements IAccount{
	// Item Name to Amount
	private Map<String, Long> holdings = new HashMap<String, Long>();
	
	public Account(){}
	
	public void deposit(Asset asset) {
		long total = asset.getAmount();
		if (holdings.containsKey(asset.getItemName())) {
			total += holdings.get(asset.getItemName());
		}
		holdings.put(asset.getItemName(), total);
	}
	
	public void withdraw(Asset asset) throws IllegalArgumentException {
		long total = -asset.getAmount();
		if (holdings.containsKey(asset.getItemName())) {
			total += holdings.get(asset.getItemName());
		}
		if (total < 0) {
			throw new IllegalArgumentException("Insufficient Assets");
		}
		holdings.put(asset.getItemName(), total);
	}
	
	public boolean contains(Asset asset) {
		return holdings.containsKey(asset.getItemName()) && holdings.get(asset.getItemName()) >= asset.getAmount();
	}
}
