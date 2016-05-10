package Banking;

public class SavingsAccount extends Accounts{

	private double interest;
	private final int MAXWITHDRAW = 6;
	private int withdrawals;
	
	public SavingsAccount(double balance,  int accountNum, String name, double interest)
	{
		super(balance, accountNum, name, 500);
		this.interest = interest;
		this.withdrawals = 0;
	}  
	
	public void compoundInterest(int periods)
	{
		double rOverN = 1 + this.interest / periods;
		rOverN = Math.pow(rOverN,  periods);
		this.balance = this.getBalance() * rOverN;
		
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
