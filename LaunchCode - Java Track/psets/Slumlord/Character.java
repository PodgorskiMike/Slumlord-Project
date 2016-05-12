package Slumlord;

public class Character {
	//Traits
	private String Name;
	private String Description;
	private String Skill;
	private int RentRed;
	private int RentBlue;
	private int RentGreen;
	private int DepRed;
	private int DepBlue;
	private int DepGreen;
	private int LeaseNumRed;
	private int LeaseNumBlue;
	private int LeaseNumGreen;
	private int MODamRed;
	private int MODamBlue;
	private int MODamGreen;
	private int BuyRed;
	private int BuyBlue;
	private int BuyGreen;
	private int Bank;
	private String Color;
	
	//behaviors
	
	//constructor
	public Character(String Name, String Description, String Skill,int LeaseNumRed, int LeaseNumBlue, int LeaseNumGreen, String Color )
	{
		this.Name = Name;
		this.Description = Description;
		this.Skill = Skill;
		this.Color = Color;
		this.RentRed = 400;
		this.RentBlue = 600;
		this.RentGreen = 1000;
		this.DepRed = 200;
		this.DepBlue = 600;
		this.DepGreen = 1000;
		this.MODamRed = -2;
		this.MODamBlue = -1;
		this.MODamGreen = 0;
		this.BuyRed = 800;
		this.BuyBlue = 1200;
		this.BuyGreen = 2000;
		this.Bank = 500;
	}
	

}
