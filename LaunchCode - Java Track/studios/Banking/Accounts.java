package Banking;

public class Accounts {

	protected double balance;
	private int accountNum;
	//private boolean status;
	private String name;
	private double  minBalance = 50.00;
	 
	public Accounts(double balance, int accountNum, String name, double minBalance)
	{
		this.balance = balance;
		this.accountNum = accountNum;
		this.name = name;
		this.minBalance = minBalance;
	}
	
	public double getBalance()
	{
		return this.balance;
	}
	public boolean withdraw(double amount)
	{
		if(this.balance - minBalance >= amount)
		{
			this.balance = this.balance - amount;
			
			return true;
		}
		else
		{
			return false;
		}
	}
	//behaviors
	public void deposit(double amount)
	{	//check amount if its neg throw exception
		if(amount > 0)
		{
			this.balance += amount;
		}
	}
	
	public boolean transfer(Accounts from, double amount)
	{
		if(from.withdraw(amount))
		{
			this.deposit(amount);
			return true;
		}
		return false;
	}
	//overdraft
	//balance check
	public static void main(String arg[])
	{
		Accounts myAccount = new Accounts(500, 314, "Mike Pod", 50);
		System.out.println("Balance: " + myAccount.getBalance());
		myAccount.withdraw(50);
		System.out.println("Balance: " + myAccount.getBalance());
		Accounts zachAccount = new Accounts(500, 444, "Zach Lou", 50);
		myAccount.transfer(zachAccount, 100);
		System.out.println("Balance: " + myAccount.getBalance());
		System.out.println("Balance: " + zachAccount.getBalance());
	}
}
