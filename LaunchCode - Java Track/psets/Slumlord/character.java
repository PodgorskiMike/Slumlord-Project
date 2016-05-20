package Slumlord;

import java.util.ArrayList;

public class character {
	//Traits
	protected String Name;
	protected String Description;
	protected String Skill;
	protected int RentRed;
	protected int RedOwned;
	protected int RedOccupied;
	protected int RentBlue;
	protected int BlueOwned;
	protected int BlueOccupied;
	protected int RentGreen;
	protected int GreenOwned;
	protected int GreenOccupied;
	protected int DepRed;
	protected int DepBlue;
	protected int DepGreen;
	protected int LeaseNumRed;
	protected int LeaseNumBlue;
	protected int LeaseNumGreen;
	protected int MODamRed;
	protected int MODamBlue;
	protected int MODamGreen;
	protected int BuyRed;
	protected int BuyBlue;
	protected int BuyGreen;
	protected int Bank;
	protected String Color;
	protected ArrayList<property> AllOwned = new ArrayList<property>();
	
	//behaviors
	
	//constructor
	public character(String Name, String Description, String Skill,int LeaseNumRed, int LeaseNumBlue, int LeaseNumGreen, String Color )
	{
		this.Name = Name;
		this.Description = Description;
		this.Skill = Skill;
		this.LeaseNumRed = LeaseNumRed;
		this.LeaseNumBlue = LeaseNumBlue;
		this.LeaseNumGreen = LeaseNumGreen;
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
		this.Bank = 1300;
		this.RedOccupied = 0;
		this.GreenOccupied = 0;
		this.BlueOccupied = 0;
		this.AllOwned = AllOwned;
	}
	
	public void buy(property a)
	{
				
		if (a.Color == 1 && this.Bank > this.BuyRed && a.Owned == false)
		{
			this.Bank = this.Bank - this.BuyRed;
			//a.Color = a.Color - this.BuyRed;
			a.Owned = true;
			a.OwnedBy = this.Color;
			this.RedOwned = this.RedOwned + 1;
			this.AllOwned.add(a);
			//place colored building on map
		}

		
		if (a.Color == 2 && this.Bank > this.BuyBlue && a.Owned == false)
		{
			this.Bank = this.Bank - this.BuyBlue;
			//a.Color = a.Color - this.BuyBlue;
			a.Owned = true;
			a.OwnedBy = this.Color;
			this.BlueOwned = this.BlueOwned + 1;
			//place colored building on map
		}
		if (a.Color == 3 && this.Bank > this.BuyGreen && a.Owned == false)
		{
			this.Bank = this.Bank - this.BuyGreen;
			//a.Color = a.Color - this.BuyGreen;
			a.Owned = true;
			a.OwnedBy = this.Color;
			this.GreenOwned = this.GreenOwned + 1;
			//place colored building on map
		}
	}
	
	public String upgrade(property a)
	{
		if(a.Color == 3)
		{
			return "Cannot Upgrate a Green Property";
		}
		if (this.Bank > 500 )
		{
			this.Bank = this.Bank - 500;
			a.Upgrades = a.Upgrades + 1;
			return "Upgraded";
		}
		else
		{	
			return "Not Enough Money to Upgrade";
		}
	}
		
		public String repair(property a)
		{	
			if( a.Damage == 0)
			{
				return "No Damage to Repair";
				
			}
			if( this.Bank >= 200)
			{
				this.Bank = this.Bank - 200;
				a.Damage = a.Damage - 1;
				return "Damage Repaired";
			}
			if(this.Bank < 200)
			{
				return "Not Enough Money to Repair";
			}
			return "Something Happened";
		}
		
		public String charCard()
		{
			return "<html>" + this.Name + "<br>" + this.Description + "<br>" + this.Skill + "<br>" + "Current Bank Balance " + this.Bank + "<br>" + "Tenant Color  Rent  Deopsit  Leasing Number  Move Out Damage" +
		"<br>" + "Red" + this.RentRed + "   " + this.DepRed + "   " + this.LeaseNumRed + "   " + this.MODamRed + "<br>"
		+ "Blue" + this.RentBlue + "   " + this.DepBlue + "   " + this.LeaseNumBlue + "   " + this.MODamBlue + "<br>"
		+ "Green" + this.RentGreen + "   " + this.DepGreen + "   " + this.LeaseNumGreen + "   " + this.MODamGreen + "</html>";
		}
		public String toString()
		{
			return this.Name +"  "+ this.Skill;
		}
		
		public static void main(String args[])
		{
			character Chester = new character("Chester", "Descript", "Skill",2,4,6,"Purple");
			System.out.println(Chester);
			property r1 = new property("1",2);
			Chester.buy(r1);
			Chester.Bank = 1000;
			System.out.println(r1);
			r1.Damage = 3;
			System.out.println(Chester);
			String f = Chester.repair(r1);
			System.out.println(Chester);
			System.out.println(r1);
			System.out.println(f);
					
		}
		
	
}
