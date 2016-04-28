package Banking;

public class Accounts {

	private float balance;
	private int accountNum;
	//private boolean status;
	private String name;
	private final float  min_balance = 50;
	 
	public Accounts(float balance, int accountNum, String name)
	{
		this.balance = balance;
		this.accountNum = accountNum;
		this.name = name;
	}
	
	public float getBalance()
	{
		return this.balance;
	}
	public boolean withdraw(float amount)
	{
		if(this.balance - min_balance >= amount)
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
	public void deposit(float amount)
	{	//check amount if its neg throw exception
		if(amount > 0)
		{
			this.balance += amount;
		}
	}
	
	public boolean transfer(Accounts from, float amount)
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
		Accounts myAccount = new Accounts(500, 314, "Mike Pod");
		System.out.println("Balance: " + myAccount.getBalance());
		myAccount.withdraw(50);
		System.out.println("Balance: " + myAccount.getBalance());
		Accounts zachAccount = new Accounts(500, 444, "Zach Lou");
		myAccount.transfer(zachAccount, 100);
		System.out.println("Balance: " + myAccount.getBalance());
		System.out.println("Balance: " + zach Account.getBalance());
	}
}
