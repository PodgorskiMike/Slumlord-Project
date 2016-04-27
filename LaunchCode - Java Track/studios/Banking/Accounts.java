package Banking;

public class Accounts {

	private float balance;
	private int accountNum;
	//private String type;
	private String name;
	private int route;
	private final float min_balance = 50;
	 
	public Accounts(float balance, int accountNum, String name)
	{
		this.balance = balance;
		this.accountNum = accountNum;
		this.name = name;
		this.route = 402859275;
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
	//deposit
	//withdrawal
	//overdraft
	//transfer
	//balance check
	public static void main(String arg[])
	{
		Accounts myAccount = new Accounts(500, 314, "Mike Pod");
		System.out.println("Balance: " + myAccount.getBalance());
		myAccount.withdraw(50);
		System.out.println("Balance: " + myAccount.getBalance());
		
	}
}
