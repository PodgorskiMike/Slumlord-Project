package Banking;

public class SavingsAccount extends Accounts{

	private final int MAXWITHDRAW = 6;
	private int withdrawals;
	private Compoundable interestStrategy;
	
	public SavingsAccount(double balance,  int accountNum, String name, double interest, Compoundable strategy)
	{
		super(balance, accountNum, name, 500);
		this.withdrawals = 0;
		this.interestStrategy = strategy;
	}  
	
	public void compoundInterest(int periods)
	{
		this.balance = this.interestStrategy.compoundInterest(this.balance);
		
	}
	public boolean withdraw(double amount)
	{
		if(this.withdrawals < this.MAXWITHDRAW)
		{
			boolean result = super.withdraw(amount);
			if(result == true)
			{
				this.withdrawals++;
			}
			return result;
		}
		return false;
		
	}
	
}
