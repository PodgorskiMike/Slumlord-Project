package Banking;

public class CheckingAccount extends Accounts{
	
	private boolean overdraftProtection;

	public CheckingAccount(double balance, int accountNum, String name, boolean overdraftProtection)
	{
		super(balance, accountNum, name, 5);
		this.overdraftProtection = overdraftProtection;
	}	
	
}
