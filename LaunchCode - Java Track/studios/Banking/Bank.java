package Banking;

import java.util.ArrayList;

public class Bank {
	
	
	private int route;
	private String name;
	private String location;
	private ArrayList<Accounts> accounts; 
	
	public Bank(int route, String name, String location)
	{	
		this.accounts = new ArrayList<Accounts>();
		this.route = route;
		this.name = name;
		this.location = location;
	}
	
	public void createAccount(float balance, String owner)
	{
		int accountNum = accounts.size();
		Accounts newAccount = new Accounts(balance, accountNum, owner);
		accounts.add(newAccount);
	}
	
	public void deposit(float amount, int accountNum)
	{
		Accounts a = accounts.get(accountNum);
		a.deposit(amount); 
		
	}
	/*
	 * behaviors
	 * rob 
	 * deposit
	 * withdraw
	 * create account
	 * close account
	 * 
	 */

}
