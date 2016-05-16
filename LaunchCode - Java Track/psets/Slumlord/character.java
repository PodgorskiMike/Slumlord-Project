package Slumlord;

public class character {
	//Traits
	private String Name;
	private String Description;
	private String Skill;
	private int RentRed;
	private int RedOwned;
	private int RentBlue;
	private int BlueOwned;
	private int RentGreen;
	private int GreenOwned;
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
	public character(String Name, String Description, String Skill,int LeaseNumRed, int LeaseNumBlue, int LeaseNumGreen, String Color )
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
	
	public void buy(property a)
	{
		if (a.Color == 1 && this.Bank > this.BuyRed)
		{
			this.Bank = this.Bank - this.BuyRed;
			a.Color = a.Color - this.BuyRed;
			a.Owned = true;
			a.OwnedBy = this.Color;
			this.RedOwned = this.RedOwned + 1;
			//place colored building on map
		}
		if (a.Color == 2 && this.Bank > this.BuyBlue)
		{
			this.Bank = this.Bank - this.BuyBlue;
			a.Color = a.Color - this.BuyBlue;
			a.Owned = true;
			a.OwnedBy = this.Color;
			this.BlueOwned = this.BlueOwned + 1;
			//place colored building on map
		}
		if (a.Color == 3 && this.Bank > this.BuyGreen)
		{
			this.Bank = this.Bank - this.BuyGreen;
			a.Color = a.Color - this.BuyGreen;
			a.Owned = true;
			a.OwnedBy = this.Color;
			this.GreenOwned = this.GreenOwned + 1;
			//place colored building on map
		}
	}
	
	public void upgrade(property a)
	{
		if(a.Color == 3)
		{
			System.out.println("Cannot Upgrate a Green Property");
			return;
		}
		if (this.Bank > 500 && a.OwnedBy.equals(this.Color))
		{
			this.Bank = this.Bank - 500;
			a.Upgrades = a.Upgrades + 1;
			//add upgrade marker
			if (a.Upgrades == 3)
			{
				if (a.Color == 1)
					a.Color = 2;
				if (a.Color == 2)
					a.Color = 3;
			}
		}
		else
		{
			System.out.println("Not Enough Money to Upgrade");
			return;
		}
	}
		
		public void repair(property a)
		{
			if( a.Damage > 0)
			{
				System.out.println("No Damage to Repair");
				return;
			}
			if(this.Bank > 200)
			{
				this.Bank = this.Bank - 200;
				a.Damage = a.Damage - 1;
			}
		}
		
		public String toString()
		{
			return this.Name + this.Skill;
		}
		
		public static void main(String args[])
		{
			
					
		}
		
	
}
