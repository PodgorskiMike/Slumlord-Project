

public class Fraction {
	private double nume;
	private double denom;
	private double fract;
	
	public Fraction(double fract, double nume, double denom)
	{
		this.fract = fract;
		this.nume = nume;
		this.denom = denom;
	}
	
	public double add(double Hard)
	{
		double sum = this.fract + Hard;
		return sum;
	}
	public String toString()
	{
		return " " + fract +" "+ nume +" "+ denom;
	}
	
	public static void main(String args[])
	{
		Fraction myFraction = new Fraction(7.77, 7.00, 00.77);
		System.out.println(myFraction);
		System.out.println(myFraction.add(2.33));
		
	}
}
