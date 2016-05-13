package Banking;

public class NonContinuous implements Compoundable{

	private int periods;
	private double interest;
	
	public NonContinuous(int periods, double interest)
	{
		this.periods = periods;
		this.interest = interest;
	}
	@Override
	public double compoundInterest(double balance) {
		double rOverN = 1 + this.interest / periods;
		rOverN = Math.pow(rOverN,  periods);
		balance = balance *rOverN;
		return balance;
		
	}

	
}
