package Slumlord;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.SoftBevelBorder;

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
	protected String PlayerColor;
	protected ArrayList<property> AllOwned = new ArrayList<property>();
	protected int reroll;
	protected int numDice;
	protected int DamageVar;
	protected int SkillNum;
	protected int PlayerNumber;
	
	
	//behaviors
	
	//constructor
	public character(String Name, String Description, String Skill,int LeaseNumRed, int LeaseNumBlue, int LeaseNumGreen, String PlayerColor, int SkillNum )
	{
		this.Name = Name;
		this.Description = Description;
		this.Skill = Skill;
		this.LeaseNumRed = LeaseNumRed;
		this.LeaseNumBlue = LeaseNumBlue;
		this.LeaseNumGreen = LeaseNumGreen;
		this.PlayerColor = PlayerColor;
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
		this.Bank = 800;
		this.RedOccupied = 0;
		this.GreenOccupied = 0;
		this.BlueOccupied = 0;
		this.AllOwned = AllOwned;
		this.reroll = 0;
		this.numDice = 0;
		this.DamageVar = -1;
		this.SkillNum = SkillNum;
		this.PlayerNumber = -1;
	}
	
	public void buy(property a)
	{
				
		if (a.Color == 1 && this.Bank >= this.BuyRed && a.Owned == false)
		{
			this.Bank = this.Bank - this.BuyRed;
			a.Owned = true;
			a.OwnedBy = this.PlayerColor;
			this.RedOwned = this.RedOwned + 1;
			this.AllOwned.add(a);
		}

		
		if (a.Color == 2 && this.Bank >= this.BuyBlue && a.Owned == false)
		{
			this.Bank = this.Bank - this.BuyBlue;
			a.Owned = true;
			a.OwnedBy = this.PlayerColor;
			this.BlueOwned = this.BlueOwned + 1;
			this.AllOwned.add(a);
		}
		if (a.Color == 3 && this.Bank >= this.BuyGreen && a.Owned == false)
		{
			this.Bank = this.Bank - this.BuyGreen;
			a.Owned = true;
			a.OwnedBy = this.PlayerColor;
			this.GreenOwned = this.GreenOwned + 1;
			this.AllOwned.add(a);
		}
	}
	
	public String upgrade(property a)
	{
		if(a.Color == 3)
		{
			return "Cannot Upgrate a Green Property";
		}
		if (this.Bank >= 500 )
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
		
		public String finalScore()
		{
			return "<html>" + this.Name + " Red Buildings Owned " + this.RedOwned + " Blue Buildings Owned " + this.BlueOwned + " Green Building Owned " + this.GreenOwned + " Total Bank " + this.Bank;
		}
		
		
		public String charCard()
		{
			return "<html>&nbsp;" + this.Name + "<br>&nbsp;" + this.Skill + "<br>" + "&nbsp;Current Bank Balance:  " + this.Bank + "<br>" + "&nbsp;Green Dice Needed to Move Tenant In<br>"+ 
		"<table border=1>"
		+ "<tr>"
		+ "<th>Tenant/Building</th>"
		+ "<th>Red Building</th>"
		+ "<th>Blue Building</th>"
		+ "<th>Green Building</th>"
		+ "</tr>"
		+ "<tr>"
		+ "<td>Red Tenant</td>"
		+ "<td>"+this.LeaseNumRed+"</td>"
		+ "<td>"+(this.LeaseNumRed + 1)+"</td>"
		+ "<td>"+(this.LeaseNumRed + 3)+"</td>"
		+ "</tr>"
		+ "<tr>"
		+ "<td>Blue Tenant</td>"
		+ "<td>"+(this.LeaseNumBlue + 1)+"</td>"
		+ "<td>"+(this.LeaseNumBlue)+"</td>"
		+ "<td>"+(this.LeaseNumBlue + 2)+"</td>"
		+ "</tr>"
		+ "<tr>"
		+ "<td>Green Tenant</td>"
		+ "<td>"+(this.LeaseNumGreen + 3)+"</td>"
		+ "<td>"+(this.LeaseNumGreen + 1)+"</td>"
		+ "<td>"+(this.LeaseNumGreen)+"</td>";
		}
	
		public String toString()
		{
			return this.Name +"  "+ this.Skill;
		}
		
		//Method for all move in red
		public dice MoveInRed(property r1, ArrayList playerChars, int playersTurn, int phase, int currentTenant, dice currentRoll, dice gameDice, JLabel diceResults, JLabel Turn, 
				JButton btnRedpropone, tenantTokens avaliable, JLabel avaliableTenants, int round, JButton btnDrawCard, JButton btnNextPhase)
		{
			if(r1.OwnedBy.equals(((character)playerChars.get(playersTurn)).PlayerColor) && DamageVar == -1 && r1.Occupied == false && phase == 1 && numDice != 0)
			{
				//method for moving in red tenant
				if(currentTenant == 1)
				{
					dice LastRoll = new dice(0,0,0);
					if(reroll == 1)
					{
						LastRoll = new dice(currentRoll.Red, currentRoll.Blue, currentRoll.Green);
					}
					else
					{
						LastRoll.Red = 0;
						LastRoll.Blue = 0;
						LastRoll.Green = 0;
					}
					currentRoll = gameDice.Roll(numDice);
					currentRoll.Red = currentRoll.Red + LastRoll.Red;
					currentRoll.Blue = currentRoll.Blue + LastRoll.Blue;
					currentRoll.Green = currentRoll.Green + LastRoll.Green;
					
					diceResults.setVisible(true);
					diceResults.setText("<html>Dice Roll Results" + "<br>" + "Red:" + currentRoll.Red + "<br>Blue:" + currentRoll.Blue + "<br>Green:" + currentRoll.Green + "</html>");
					if(currentRoll.Green >= ((character)playerChars.get(playersTurn)).LeaseNumRed)
					{
						Turn.setText("<html>You Succsfully Moved the Tenant In! You Rolled Some Red's Click on the Draw Cards Button");
						((character)playerChars.get(playersTurn)).RedOccupied = ((character)playerChars.get(playersTurn)).RedOccupied + 1;
						if(r1.Damage == 0)
						{
							btnRedpropone.setHorizontalTextPosition(SwingConstants.CENTER);
							btnRedpropone.setVerticalAlignment(SwingConstants.CENTER);
							btnRedpropone.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 42));
							btnRedpropone.setText("O");
						}
						if(r1.Damage == 1)
						{
							btnRedpropone.setHorizontalTextPosition(SwingConstants.CENTER);
							btnRedpropone.setVerticalAlignment(SwingConstants.CENTER);
							btnRedpropone.setFont(new Font("Copperplate Gothic Bold", Font.BOLD, 22));
							btnRedpropone.setText("<html>O<br>X</html>");
						}
						if(r1.Damage == 2)
						{
							btnRedpropone.setHorizontalTextPosition(SwingConstants.CENTER);
							btnRedpropone.setVerticalAlignment(SwingConstants.CENTER);
							btnRedpropone.setFont(new Font("Copperplate Gothic Bold", Font.BOLD, 22));
							btnRedpropone.setText("<html>&nbsp;O<br>XX</html>");
						}
						if(r1.Damage == 3)
						{
							Turn.setText("<html>You Moved a Tenant in With 3 Damage. Be Sure To Repair Right Away or You Will Lose the Tenant</html>");
							btnRedpropone.setHorizontalTextPosition(SwingConstants.CENTER);
							btnRedpropone.setVerticalAlignment(SwingConstants.CENTER);
							btnRedpropone.setFont(new Font("Copperplate Gothic Bold", Font.BOLD, 20));
							btnRedpropone.setText("<html>&nbsp;&nbsp;&nbsp;O<br>XXX</html>");
						}
						
						r1.Occupied = true;
						((character)playerChars.get(playersTurn)).Bank = ((character)playerChars.get(playersTurn)).Bank + ((character)playerChars.get(playersTurn)).DepRed;
						numDice = 0;
						avaliable.RedTenant = avaliable.RedTenant - 1;
						avaliableTenants.setText("<html>Round" + round + "<br>" + "Tenants Avaliable<br>Red:" + avaliable.RedTenant + "<br>Blue:" + avaliable.BlueTenant + "<br>Green:" + avaliable.GreenTenant + "</html>");
						if(currentRoll.Red > 0)
						{
							btnDrawCard.setVisible(true);
							return currentRoll;
						}
						if(currentRoll.Blue > 0)
						{
							btnDrawCard.setVisible(true);
							return currentRoll;
						}
						else
						{
							btnNextPhase.setVisible(true);
							Turn.setText("<html>You Moved the tenant in and rolled no reds Click on Next Phase to Tally your Monthly Earnings and Move on to the Buying and Repairing Phase</html>");
							return currentRoll;
						}
						
					}
					else if(reroll < 1)
					{
						
						numDice = currentRoll.Red;
						reroll++;
						if(currentRoll.Red > 0)
						{
							Turn.setText("<html>Sorry the Dice Must Hate You!  You can Reroll your Red dice if you like");
							btnDrawCard.setVisible(true);
							return currentRoll;
						}
						if(currentRoll.Blue > 0)
						{
							Turn.setText("<html>Nothing to Reroll, Click Draw Card to Draw for Your Blue Roll.");
							btnDrawCard.setVisible(true);
							return currentRoll;
						}
						else
						{
							btnNextPhase.setVisible(true);
							Turn.setText("<html>Couldnt Get it Done, And no Red Dice to Reroll, Click on Next Phase to Tally your Monthly Earnings and Move on to the Buying and Repairing Phase</html>");
							return currentRoll;
						}
					}
					else if(reroll >= 1)
					{
						Turn.setText("<html>Sorry the Dice Still Hate You! You Failed to Move the Tenant In Click on the Draw Card Button</html>");
						numDice = 0;
						reroll = 2;
						if(currentRoll.Red > 0)
						{
							btnDrawCard.setVisible(true);
							return currentRoll;
						}
						if(currentRoll.Blue > 0)
						{
							btnDrawCard.setVisible(true);
							return currentRoll;
						}
						else
						{
							btnNextPhase.setVisible(true);
							Turn.setText("<html>Couldnt Get it Done, And no Red Dice to Reroll,Click on Next Phase to Tally your Monthly Earnings and Move on to the Buying and Repairing Phase</html>");
							return currentRoll;
						}
					}
				
				}
				//method for moving in blue tenant
				if(currentTenant == 2)
				{
					dice LastRoll = new dice(0,0,0);
					if(reroll == 1)
					{
						LastRoll = new dice(currentRoll.Red, currentRoll.Blue, currentRoll.Green);
					}
					else
					{
						LastRoll.Red = 0;
						LastRoll.Blue = 0;
						LastRoll.Green = 0;
					}
					currentRoll = gameDice.Roll(numDice);
					currentRoll.Red = currentRoll.Red + LastRoll.Red;
					currentRoll.Blue = currentRoll.Blue + LastRoll.Blue;
					currentRoll.Green = currentRoll.Green + LastRoll.Green;
					
					diceResults.setVisible(true);
					diceResults.setText("<html>Dice Roll Results" + "<br>" + "Red:" + currentRoll.Red + "<br>Blue:" + currentRoll.Blue + "<br>Green:" + currentRoll.Green + "</html>");
					if(currentRoll.Green >= ((character)playerChars.get(playersTurn)).LeaseNumBlue + 1)
					{
						Turn.setText("<html>You Succsfully Moved the Tenant In! You Rolled Some Red's Click on the Draw Cards Button");
						((character)playerChars.get(playersTurn)).BlueOccupied = ((character)playerChars.get(playersTurn)).BlueOccupied + 1;
						r1.TenantNotMatch = r1.TenantNotMatch + 1;
						if(r1.Damage == 0)
						{
							btnRedpropone.setHorizontalTextPosition(SwingConstants.CENTER);
							btnRedpropone.setBorder(new BevelBorder(BevelBorder.RAISED, null, Color.BLUE, null, new Color(0, 0, 255)));
							btnRedpropone.setVerticalAlignment(SwingConstants.CENTER);
							btnRedpropone.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 42));
							btnRedpropone.setText("O");
						}
						if(r1.Damage == 1)
						{
							btnRedpropone.setHorizontalTextPosition(SwingConstants.CENTER);
							btnRedpropone.setBorder(new BevelBorder(BevelBorder.RAISED, null, Color.BLUE, null, new Color(0, 0, 255)));
							btnRedpropone.setVerticalAlignment(SwingConstants.CENTER);
							btnRedpropone.setFont(new Font("Copperplate Gothic Bold", Font.BOLD, 22));
							btnRedpropone.setText("<html>O<br>X</html>");
						}
						if(r1.Damage == 2)
						{
							btnRedpropone.setHorizontalTextPosition(SwingConstants.CENTER);
							btnRedpropone.setBorder(new BevelBorder(BevelBorder.RAISED, null, Color.BLUE, null, new Color(0, 0, 255)));
							btnRedpropone.setVerticalAlignment(SwingConstants.CENTER);
							btnRedpropone.setFont(new Font("Copperplate Gothic Bold", Font.BOLD, 22));
							btnRedpropone.setText("<html>&nbsp;O<br>XX</html>");
						}
						if(r1.Damage == 3)
						{
							Turn.setText("<html>You Moved a Tenant in With 3 Damage. Be Sure To Repair Right Away or You Will Lose the Tenant</html>");
							btnRedpropone.setHorizontalTextPosition(SwingConstants.CENTER);
							btnRedpropone.setVerticalAlignment(SwingConstants.CENTER);
							btnRedpropone.setFont(new Font("Copperplate Gothic Bold", Font.BOLD, 20));
							btnRedpropone.setText("<html>&nbsp;&nbsp;&nbsp;O<br>XXX</html>");
						}
						r1.Occupied = true;
						((character)playerChars.get(playersTurn)).Bank = ((character)playerChars.get(playersTurn)).Bank + ((character)playerChars.get(playersTurn)).DepBlue;
						numDice = 0;
						avaliable.BlueTenant = avaliable.BlueTenant - 1;
						avaliableTenants.setText("<html>Round" + round + "<br>" + "Tenants Avaliable<br>Red:" + avaliable.RedTenant + "<br>Blue:" + avaliable.BlueTenant + "<br>Green:" + avaliable.GreenTenant + "</html>");
						if(currentRoll.Red > 0)
						{
							btnDrawCard.setVisible(true);
							return currentRoll;
						}
						if(currentRoll.Blue > 0)
						{
							btnDrawCard.setVisible(true);
							return currentRoll;
						}
						else
						{
							btnNextPhase.setVisible(true);
							Turn.setText("<html>You Moved the tenant in and rolled no reds! Click on Next Phase to Tally your Monthly Earnings and Move on to the Buying and Repairing Phase</html>");
							return currentRoll;
						}
					}
					else if(reroll < 1)
					{
						
						numDice = currentRoll.Red;
						reroll++;
						if(currentRoll.Red > 0)
						{
							Turn.setText("<html>Sorry the Dice Must Hate You! You can Reroll your Red dice if you like</html>");
							btnDrawCard.setVisible(true);
							return currentRoll;
						}
						if(currentRoll.Blue > 0)
						{
							Turn.setText("<html>Nothing to Reroll, Click Draw Card to Draw for Your Blue Roll.");
							btnDrawCard.setVisible(true);
							return currentRoll;
						}
						else
						{
							btnNextPhase.setVisible(true);
							Turn.setText("<html>Couldnt Get it Done, And no Red Dice to Reroll, Click on Next Phase to Tally your Monthly Earnings and Move on to the Buying and Repairing Phase</html>");
							return currentRoll;
						}
					}
					else if(reroll >= 1)
					{
						Turn.setText("<html>Sorry the Dice Still Hate You! You Failed to Move the Tenant In. Click on the Draw Card Button</html>");
						numDice = 0;
						reroll = 2;
						if(currentRoll.Red > 0)
						{
							btnDrawCard.setVisible(true);
							return currentRoll;
						}
						if(currentRoll.Blue > 0)
						{
							btnDrawCard.setVisible(true);
							return currentRoll;
						}
						else
						{
							btnNextPhase.setVisible(true);
							Turn.setText("<html>Couldnt Get it Done, And no Red Dice to Reroll,Click on Next Phase to Tally your Monthly Earnings and Move on to the Buying and Repairing Phase</html>");
							return currentRoll;
						}
					}
				
				}
			}
			//method for moving in green tenant
			if(currentTenant == 3)
			{
				dice LastRoll = new dice(0,0,0);
				if(reroll == 1)
				{
					LastRoll = new dice(currentRoll.Red, currentRoll.Blue, currentRoll.Green);
				}
				else
				{
					LastRoll.Red = 0;
					LastRoll.Blue = 0;
					LastRoll.Green = 0;
				}
				currentRoll = gameDice.Roll(numDice);
				currentRoll.Red = currentRoll.Red + LastRoll.Red;
				currentRoll.Blue = currentRoll.Blue + LastRoll.Blue;
				currentRoll.Green = currentRoll.Green + LastRoll.Green;
				
				diceResults.setVisible(true);
				diceResults.setText("<html>Dice Roll Results" + "<br>" + "Red:" + currentRoll.Red + "<br>Blue:" + currentRoll.Blue + "<br>Green:" + currentRoll.Green + "</html>");
				if(currentRoll.Green >= ((character)playerChars.get(playersTurn)).LeaseNumGreen + 3)
				{
					Turn.setText("<html>You Succsfully Moved the Tenant In!  You Rolled Some Red's. Click on the Draw Cards Button");
					((character)playerChars.get(playersTurn)).GreenOccupied = ((character)playerChars.get(playersTurn)).GreenOccupied + 1;
					r1.TenantNotMatch = r1.TenantNotMatch + 2;
					if(r1.Damage == 0)
					{
						btnRedpropone.setHorizontalTextPosition(SwingConstants.CENTER);
						btnRedpropone.setBorder(new BevelBorder(BevelBorder.RAISED, null, Color.GREEN, null, new Color(0, 0, 255)));
						btnRedpropone.setVerticalAlignment(SwingConstants.CENTER);
						btnRedpropone.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 42));
						btnRedpropone.setText("O");
					}
					if(r1.Damage == 1)
					{
						btnRedpropone.setHorizontalTextPosition(SwingConstants.CENTER);
						btnRedpropone.setBorder(new BevelBorder(BevelBorder.RAISED, null, Color.GREEN, null, new Color(0, 0, 255)));
						btnRedpropone.setVerticalAlignment(SwingConstants.CENTER);
						btnRedpropone.setFont(new Font("Copperplate Gothic Bold", Font.BOLD, 22));
						btnRedpropone.setText("<html>O<br>X</html>");
					}
					if(r1.Damage == 2)
					{
						btnRedpropone.setHorizontalTextPosition(SwingConstants.CENTER);
						btnRedpropone.setBorder(new BevelBorder(BevelBorder.RAISED, null, Color.GREEN, null, new Color(0, 0, 255)));
						btnRedpropone.setVerticalAlignment(SwingConstants.CENTER);
						btnRedpropone.setFont(new Font("Copperplate Gothic Bold", Font.BOLD, 22));
						btnRedpropone.setText("<html>&nbsp;O<br>XX</html>");
					}
					if(r1.Damage == 3)
					{
						Turn.setText("<html>You Moved a Tenant in With 3 Damage. Be Sure To Repair Right Away or You Will Lose the Tenant</html>");
						btnRedpropone.setHorizontalTextPosition(SwingConstants.CENTER);
						btnRedpropone.setVerticalAlignment(SwingConstants.CENTER);
						btnRedpropone.setFont(new Font("Copperplate Gothic Bold", Font.BOLD, 20));
						btnRedpropone.setText("<html>&nbsp;&nbsp;&nbsp;O<br>XXX</html>");
					}
					r1.Occupied = true;
					((character)playerChars.get(playersTurn)).Bank = ((character)playerChars.get(playersTurn)).Bank + ((character)playerChars.get(playersTurn)).DepGreen;
					numDice = 0;
					avaliable.GreenTenant = avaliable.GreenTenant - 1;
					avaliableTenants.setText("<html>Round" + round + "<br>" + "Tenants Avaliable<br>Red:" + avaliable.RedTenant + "<br>Blue:" + avaliable.BlueTenant + "<br>Green:" + avaliable.GreenTenant + "</html>");
					if(currentRoll.Red > 0)
					{
						btnDrawCard.setVisible(true);
						return currentRoll;
					}
					if(currentRoll.Blue > 0)
					{
						btnDrawCard.setVisible(true);
						return currentRoll;
					}
					else
					{
						btnNextPhase.setVisible(true);
						Turn.setText("<html>Click on Next Phase to Tally your Monthly Earnings and Move on to the Buying and Repairing Phase</html>");
						return currentRoll;
					}
				}
				else if(reroll < 1)
					{
						
						numDice = currentRoll.Red;
						reroll++;
						if(currentRoll.Red > 0)
						{
							Turn.setText("<html>Sorry the Dice Must Hate You! You can Reroll your Red dice if you like");
							btnDrawCard.setVisible(true);
							return currentRoll;
						}
						if(currentRoll.Blue > 0)
						{
							Turn.setText("<html>Nothing to Reroll, Click Draw Card to Draw for Your Blue Roll.");
							btnDrawCard.setVisible(true);
							return currentRoll;
						}
						else
						{
							btnNextPhase.setVisible(true);
							Turn.setText("<html>Couldnt Get it Done, And no Red Dice to Reroll, Click on Next Phase to Tally your Monthly Earnings and Move on to the Buying and Repairing Phase</html>");
							return currentRoll;
						}
					}
				else if(reroll >= 1)
					{
						Turn.setText("<html>Sorry the Dice Still Hate You! You Failed to Move the Tenant In. Click on the Draw Card Button</html>");
						numDice = 0;
						reroll = 2;
						if(currentRoll.Red > 0)
						{
							btnDrawCard.setVisible(true);
							return currentRoll;
						}
						if(currentRoll.Blue > 0)
						{
							btnDrawCard.setVisible(true);
							return currentRoll;
						}
						else
						{
							btnNextPhase.setVisible(true);
							Turn.setText("<html>Couldnt Get it Done, And no Red Dice to Reroll,Click on Next Phase to Tally your Monthly Earnings and Move on to the Buying and Repairing Phase</html>");
							return currentRoll;
						}
					}
			
			}
			return currentRoll;
		}
		
		//Method for all move in blue
				public dice MoveInBlue(property r1, ArrayList playerChars, int playersTurn, int phase, int currentTenant, dice currentRoll, dice gameDice, JLabel diceResults, JLabel Turn, 
						JButton btnRedpropone, tenantTokens avaliable, JLabel avaliableTenants, int round, JButton btnDrawCard, JButton btnNextPhase)
				{
					if(r1.OwnedBy.equals(((character)playerChars.get(playersTurn)).PlayerColor) && DamageVar == -1 && r1.Occupied == false && phase == 1 && numDice != 0)
					{
						//method for moving in red tenant
						if(currentTenant == 1)
						{
							dice LastRoll = new dice(0,0,0);
							if(reroll == 1)
							{
								LastRoll = new dice(currentRoll.Red, currentRoll.Blue, currentRoll.Green);
							}
							else
							{
								LastRoll.Red = 0;
								LastRoll.Blue = 0;
								LastRoll.Green = 0;
							}
							currentRoll = gameDice.Roll(numDice);
							currentRoll.Red = currentRoll.Red + LastRoll.Red;
							currentRoll.Blue = currentRoll.Blue + LastRoll.Blue;
							currentRoll.Green = currentRoll.Green + LastRoll.Green;
							
							diceResults.setVisible(true);
							diceResults.setText("<html>Dice Roll Results" + "<br>" + "Red:" + currentRoll.Red + "<br>Blue:" + currentRoll.Blue + "<br>Green:" + currentRoll.Green + "</html>");
							if(currentRoll.Green >= ((character)playerChars.get(playersTurn)).LeaseNumRed +1)
							{
								Turn.setText("<html>You Succsfully Moved the Tenant In! You Rolled Some Red's. Click on the Draw Cards Button");
								((character)playerChars.get(playersTurn)).RedOccupied = ((character)playerChars.get(playersTurn)).RedOccupied + 1;
								r1.TenantNotMatch = r1.TenantNotMatch - 1;
								if(r1.Damage == 0)
								{
									btnRedpropone.setHorizontalTextPosition(SwingConstants.CENTER);
									btnRedpropone.setBorder(new BevelBorder(BevelBorder.RAISED, null, Color.RED, null, new Color(0, 0, 255)));
									btnRedpropone.setVerticalAlignment(SwingConstants.CENTER);
									btnRedpropone.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 42));
									btnRedpropone.setText("O");
								}
								if(r1.Damage == 1)
								{
									btnRedpropone.setHorizontalTextPosition(SwingConstants.CENTER);
									btnRedpropone.setBorder(new BevelBorder(BevelBorder.RAISED, null, Color.RED, null, new Color(0, 0, 255)));
									btnRedpropone.setVerticalAlignment(SwingConstants.CENTER);
									btnRedpropone.setFont(new Font("Copperplate Gothic Bold", Font.BOLD, 22));
									btnRedpropone.setText("<html>O<br>X</html>");
								}
								if(r1.Damage == 2)
								{
									btnRedpropone.setHorizontalTextPosition(SwingConstants.CENTER);
									btnRedpropone.setBorder(new BevelBorder(BevelBorder.RAISED, null, Color.RED, null, new Color(0, 0, 255)));
									btnRedpropone.setVerticalAlignment(SwingConstants.CENTER);
									btnRedpropone.setFont(new Font("Copperplate Gothic Bold", Font.BOLD, 22));
									btnRedpropone.setText("<html>&nbsp;O<br>XX</html>");
								}
								if(r1.Damage == 3)
								{
									Turn.setText("<html>You Moved a Tenant in With 3 Damage. Be Sure To Repair Right Away or You Will Lose the Tenant</html>");
									btnRedpropone.setHorizontalTextPosition(SwingConstants.CENTER);
									btnRedpropone.setVerticalAlignment(SwingConstants.CENTER);
									btnRedpropone.setFont(new Font("Copperplate Gothic Bold", Font.BOLD, 20));
									btnRedpropone.setText("<html>&nbsp;&nbsp;&nbsp;O<br>XXX</html>");
								}
								r1.Occupied = true;
								((character)playerChars.get(playersTurn)).Bank = ((character)playerChars.get(playersTurn)).Bank + ((character)playerChars.get(playersTurn)).DepRed;
								numDice = 0;
								avaliable.RedTenant = avaliable.RedTenant - 1;
								avaliableTenants.setText("<html>Round" + round + "<br>" + "Tenants Avaliable<br>Red:" + avaliable.RedTenant + "<br>Blue:" + avaliable.BlueTenant + "<br>Green:" + avaliable.GreenTenant + "</html>");
								if(currentRoll.Red > 0)
								{
									btnDrawCard.setVisible(true);
									return currentRoll;
								}
								if(currentRoll.Blue > 0)
								{
									btnDrawCard.setVisible(true);
									return currentRoll;
								}
								else
								{
									btnNextPhase.setVisible(true);
									Turn.setText("<html>You Moved the tenant in and rolled no reds! Click on Next Phase to Tally your Monthly Earnings and Move on to the Buying and Repairing Phase</html>");
									return currentRoll;
								}
								
							}
							else if(reroll < 1)
							{
								
								numDice = currentRoll.Red;
								reroll++;
								if(currentRoll.Red > 0)
								{
									Turn.setText("<html>Sorry the Dice Must Hate You!  You can Reroll your Red dice if you like");
									btnDrawCard.setVisible(true);
									return currentRoll;
								}
								if(currentRoll.Blue > 0)
								{
									Turn.setText("<html>Nothing to Reroll, Click Draw Card to Draw for Your Blue Roll.");
									btnDrawCard.setVisible(true);
									return currentRoll;
								}
								else
								{
									btnNextPhase.setVisible(true);
									Turn.setText("<html>Couldnt Get it Done, And no Red Dice to Reroll, Click on Next Phase to Tally your Monthly Earnings and Move on to the Buying and Repairing Phase</html>");
									return currentRoll;
								}
							}
							else if(reroll >= 1)
							{
								Turn.setText("<html>Sorry the Dice Still Hate You! You Failed to Move the Tenant In. Click on the Draw Card Button</html>");
								numDice = 0;
								reroll = 2;
								if(currentRoll.Red > 0)
								{
									btnDrawCard.setVisible(true);
									return currentRoll;
								}
								if(currentRoll.Blue > 0)
								{
									btnDrawCard.setVisible(true);
									return currentRoll;
								}
								else
								{
									btnNextPhase.setVisible(true);
									Turn.setText("<html>Couldnt Get it Done, And no Red Dice to Reroll,Click on Next Phase to Tally your Monthly Earnings and Move on to the Buying and Repairing Phase</html>");
									return currentRoll;
								}
							}
						
						}
						//method for moving in blue tenant
						if(currentTenant == 2)
						{
							dice LastRoll = new dice(0,0,0);
							if(reroll == 1)
							{
								LastRoll = new dice(currentRoll.Red, currentRoll.Blue, currentRoll.Green);
							}
							else
							{
								LastRoll.Red = 0;
								LastRoll.Blue = 0;
								LastRoll.Green = 0;
							}
							currentRoll = gameDice.Roll(numDice);
							currentRoll.Red = currentRoll.Red + LastRoll.Red;
							currentRoll.Blue = currentRoll.Blue + LastRoll.Blue;
							currentRoll.Green = currentRoll.Green + LastRoll.Green;
							
							diceResults.setVisible(true);
							diceResults.setText("<html>Dice Roll Results" + "<br>" + "Red:" + currentRoll.Red + "<br>Blue:" + currentRoll.Blue + "<br>Green:" + currentRoll.Green + "</html>");
							if(currentRoll.Green >= ((character)playerChars.get(playersTurn)).LeaseNumBlue)
							{
								Turn.setText("<html>You Succsfully Moved the Tenant In! You Rolled Some Red's. Click on the Draw Cards Button");
								((character)playerChars.get(playersTurn)).BlueOccupied = ((character)playerChars.get(playersTurn)).BlueOccupied;
								if(r1.Damage == 0)
								{
									btnRedpropone.setHorizontalTextPosition(SwingConstants.CENTER);
									btnRedpropone.setVerticalAlignment(SwingConstants.CENTER);
									btnRedpropone.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 42));
									btnRedpropone.setText("O");
								}
								if(r1.Damage == 1)
								{
									btnRedpropone.setHorizontalTextPosition(SwingConstants.CENTER);
									btnRedpropone.setVerticalAlignment(SwingConstants.CENTER);
									btnRedpropone.setFont(new Font("Copperplate Gothic Bold", Font.BOLD, 22));
									btnRedpropone.setText("<html>O<br>X</html>");
								}
								if(r1.Damage == 2)
								{
									btnRedpropone.setHorizontalTextPosition(SwingConstants.CENTER);
									btnRedpropone.setVerticalAlignment(SwingConstants.CENTER);
									btnRedpropone.setFont(new Font("Copperplate Gothic Bold", Font.BOLD, 22));
									btnRedpropone.setText("<html>&nbsp;O<br>XX</html>");
								}
								if(r1.Damage == 3)
								{
									Turn.setText("<html>You Moved a Tenant in With 3 Damage. Be Sure To Repair Right Away or You Will Lose the Tenant</html>");
									btnRedpropone.setHorizontalTextPosition(SwingConstants.CENTER);
									btnRedpropone.setVerticalAlignment(SwingConstants.CENTER);
									btnRedpropone.setFont(new Font("Copperplate Gothic Bold", Font.BOLD, 20));
									btnRedpropone.setText("<html>&nbsp;&nbsp;&nbsp;O<br>XXX</html>");
								}
								r1.Occupied = true;
								((character)playerChars.get(playersTurn)).Bank = ((character)playerChars.get(playersTurn)).Bank + ((character)playerChars.get(playersTurn)).DepBlue;
								numDice = 0;
								avaliable.BlueTenant = avaliable.BlueTenant - 1;
								avaliableTenants.setText("<html>Round" + round + "<br>" + "Tenants Avaliable<br>Red:" + avaliable.RedTenant + "<br>Blue:" + avaliable.BlueTenant + "<br>Green:" + avaliable.GreenTenant + "</html>");
								if(currentRoll.Red > 0)
								{
									btnDrawCard.setVisible(true);
									return currentRoll;
								}
								if(currentRoll.Blue > 0)
								{
									btnDrawCard.setVisible(true);
									return currentRoll;
								}
								else
								{
									btnNextPhase.setVisible(true);
									Turn.setText("<html>You Moved the tenant in and rolled no reds! Click on Next Phase to Tally your Monthly Earnings and Move on to the Buying and Repairing Phase</html>");
									return currentRoll;
								}
							}
							else if(reroll < 1)
							{
								
								numDice = currentRoll.Red;
								reroll++;
								if(currentRoll.Red > 0)
								{
									Turn.setText("<html>Sorry the Dice Must Hate You! You can Reroll your Red dice if you like");
									btnDrawCard.setVisible(true);
									return currentRoll;
								}
								if(currentRoll.Blue > 0)
								{
									Turn.setText("<html>Nothing to Reroll, Click Draw Card to Draw for Your Blue Roll.");
									btnDrawCard.setVisible(true);
									return currentRoll;
								}
								else
								{
									btnNextPhase.setVisible(true);
									Turn.setText("<html>Couldnt Get it Done, And no Red Dice to Reroll, Click on Next Phase to Tally your Monthly Earnings and Move on to the Buying and Repairing Phase</html>");
									return currentRoll;
								}
							}
							else if(reroll >= 1)
							{
								Turn.setText("<html>Sorry the Dice Still Hate You! You Failed to Move the Tenant In. Click on the Draw Card Button</html>");
								numDice = 0;
								reroll = 2;
								if(currentRoll.Red > 0)
								{
									btnDrawCard.setVisible(true);
									return currentRoll;
								}
								if(currentRoll.Blue > 0)
								{
									btnDrawCard.setVisible(true);
									return currentRoll;
								}
								else
								{
									btnNextPhase.setVisible(true);
									Turn.setText("<html>Couldnt Get it Done, And no Red Dice to Reroll,Click on Next Phase to Tally your Monthly Earnings and Move on to the Buying and Repairing Phase</html>");
									return currentRoll;
								}
							}
						
						}
					}
					//method for moving in green tenant
					if(currentTenant == 3)
					{
						dice LastRoll = new dice(0,0,0);
						if(reroll == 1)
						{
							LastRoll = new dice(currentRoll.Red, currentRoll.Blue, currentRoll.Green);
						}
						else
						{
							LastRoll.Red = 0;
							LastRoll.Blue = 0;
							LastRoll.Green = 0;
						}
						currentRoll = gameDice.Roll(numDice);
						currentRoll.Red = currentRoll.Red + LastRoll.Red;
						currentRoll.Blue = currentRoll.Blue + LastRoll.Blue;
						currentRoll.Green = currentRoll.Green + LastRoll.Green;
						
						diceResults.setVisible(true);
						diceResults.setText("<html>Dice Roll Results" + "<br>" + "Red:" + currentRoll.Red + "<br>Blue:" + currentRoll.Blue + "<br>Green:" + currentRoll.Green + "</html>");
						if(currentRoll.Green >= ((character)playerChars.get(playersTurn)).LeaseNumGreen + 1)
						{
							Turn.setText("<html>You Succsfully Moved the Tenant In! You Rolled Some Red's. Click on the Draw Cards Button");
							((character)playerChars.get(playersTurn)).GreenOccupied = ((character)playerChars.get(playersTurn)).GreenOccupied + 1;
							r1.TenantNotMatch = r1.TenantNotMatch + 1;
							if(r1.Damage == 0)
							{
								btnRedpropone.setHorizontalTextPosition(SwingConstants.CENTER);
								btnRedpropone.setBorder(new BevelBorder(BevelBorder.RAISED, null, Color.GREEN, null, new Color(0, 0, 255)));
								btnRedpropone.setVerticalAlignment(SwingConstants.CENTER);
								btnRedpropone.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 42));
								btnRedpropone.setText("O");
							}
							if(r1.Damage == 1)
							{
								btnRedpropone.setHorizontalTextPosition(SwingConstants.CENTER);
								btnRedpropone.setBorder(new BevelBorder(BevelBorder.RAISED, null, Color.GREEN, null, new Color(0, 0, 255)));
								btnRedpropone.setVerticalAlignment(SwingConstants.CENTER);
								btnRedpropone.setFont(new Font("Copperplate Gothic Bold", Font.BOLD, 22));
								btnRedpropone.setText("<html>O<br>X</html>");
							}
							if(r1.Damage == 2)
							{
								btnRedpropone.setHorizontalTextPosition(SwingConstants.CENTER);
								btnRedpropone.setBorder(new BevelBorder(BevelBorder.RAISED, null, Color.GREEN, null, new Color(0, 0, 255)));
								btnRedpropone.setVerticalAlignment(SwingConstants.CENTER);
								btnRedpropone.setFont(new Font("Copperplate Gothic Bold", Font.BOLD, 22));
								btnRedpropone.setText("<html>&nbsp;O<br>XX</html>");
							}
							if(r1.Damage == 3)
							{
								Turn.setText("<html>You Moved a Tenant in With 3 Damage. Be Sure To Repair Right Away or You Will Lose the Tenant</html>");
								btnRedpropone.setHorizontalTextPosition(SwingConstants.CENTER);
								btnRedpropone.setVerticalAlignment(SwingConstants.CENTER);
								btnRedpropone.setFont(new Font("Copperplate Gothic Bold", Font.BOLD, 20));
								btnRedpropone.setText("<html>&nbsp;&nbsp;&nbsp;O<br>XXX</html>");
							}
							r1.Occupied = true;
							((character)playerChars.get(playersTurn)).Bank = ((character)playerChars.get(playersTurn)).Bank + ((character)playerChars.get(playersTurn)).DepGreen;
							numDice = 0;
							avaliable.GreenTenant = avaliable.GreenTenant - 1;
							avaliableTenants.setText("<html>Round" + round + "<br>" + "Tenants Avaliable<br>Red:" + avaliable.RedTenant + "<br>Blue:" + avaliable.BlueTenant + "<br>Green:" + avaliable.GreenTenant + "</html>");
							if(currentRoll.Red > 0)
							{
								btnDrawCard.setVisible(true);
								return currentRoll;
							}
							if(currentRoll.Blue > 0)
							{
								btnDrawCard.setVisible(true);
								return currentRoll;
							}
							else
							{
								btnNextPhase.setVisible(true);
								Turn.setText("<html>Click on Next Phase to Tally your Monthly Earnings and Move on to the Buying and Repairing Phase</html>");
								return currentRoll;
							}
						}
						else if(reroll < 1)
							{
								
								numDice = currentRoll.Red;
								reroll++;
								if(currentRoll.Red > 0)
								{
									Turn.setText("<html>Sorry the Dice Must Hate You!  You can Reroll your Red dice if you like");
									btnDrawCard.setVisible(true);
									return currentRoll;
								}
								if(currentRoll.Blue > 0)
								{
									Turn.setText("<html>Nothing to Reroll, Click Draw Card to Draw for Your Blue Roll.");
									btnDrawCard.setVisible(true);
									return currentRoll;
								}
								else
								{
									btnNextPhase.setVisible(true);
									Turn.setText("<html>Couldnt Get it Done, And no Red Dice to Reroll, Click on Next Phase to Tally your Monthly Earnings and Move on to the Buying and Repairing Phase</html>");
									return currentRoll;
								}
							}
						else if(reroll >= 1)
							{
								Turn.setText("<html>Sorry the Dice Still Hate You! You Failed to Move the Tenant In. Click on the Draw Card Button</html>");
								numDice = 0;
								reroll = 2;
								if(currentRoll.Red > 0)
								{
									btnDrawCard.setVisible(true);
									return currentRoll;
								}
								if(currentRoll.Blue > 0)
								{
									btnDrawCard.setVisible(true);
									return currentRoll;
								}
								else
								{
									btnNextPhase.setVisible(true);
									Turn.setText("<html>Couldnt Get it Done, And no Red Dice to Reroll,Click on Next Phase to Tally your Monthly Earnings and Move on to the Buying and Repairing Phase</html>");
									return currentRoll;
								}
							}
					
					}
					return currentRoll;
				}
				
				//Method for all move in green building
				public dice MoveInGreen(property r1, ArrayList playerChars, int playersTurn, int phase, int currentTenant, dice currentRoll, dice gameDice, JLabel diceResults, JLabel Turn, 
						JButton btnRedpropone, tenantTokens avaliable, JLabel avaliableTenants, int round, JButton btnDrawCard, JButton btnNextPhase)
				{
					if(r1.OwnedBy.equals(((character)playerChars.get(playersTurn)).PlayerColor) && DamageVar == -1 && r1.Occupied == false && phase == 1 && numDice != 0)
					{
						//method for moving in red tenant
						if(currentTenant == 1)
						{
							dice LastRoll = new dice(0,0,0);
							if(reroll == 1)
							{
								LastRoll = new dice(currentRoll.Red, currentRoll.Blue, currentRoll.Green);
							}
							else
							{
								LastRoll.Red = 0;
								LastRoll.Blue = 0;
								LastRoll.Green = 0;
							}
							currentRoll = gameDice.Roll(numDice);
							currentRoll.Red = currentRoll.Red + LastRoll.Red;
							currentRoll.Blue = currentRoll.Blue + LastRoll.Blue;
							currentRoll.Green = currentRoll.Green + LastRoll.Green;
							
							diceResults.setVisible(true);
							diceResults.setText("<html>Dice Roll Results" + "<br>" + "Red:" + currentRoll.Red + "<br>Blue:" + currentRoll.Blue + "<br>Green:" + currentRoll.Green + "</html>");
							if(currentRoll.Green >= ((character)playerChars.get(playersTurn)).LeaseNumRed +3)
							{
								Turn.setText("<html>You Succsfully Moved the Tenant In! You Rolled Some Red's. Click on the Draw Cards Button");
								((character)playerChars.get(playersTurn)).RedOccupied = ((character)playerChars.get(playersTurn)).RedOccupied + 1;
								r1.TenantNotMatch = r1.TenantNotMatch - 2;
								if(r1.Damage == 0)
								{
									btnRedpropone.setHorizontalTextPosition(SwingConstants.CENTER);
									btnRedpropone.setBorder(new BevelBorder(BevelBorder.RAISED, null, Color.RED, null, new Color(0, 0, 255)));
									btnRedpropone.setVerticalAlignment(SwingConstants.CENTER);
									btnRedpropone.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 42));
									btnRedpropone.setText("O");
								}
								if(r1.Damage == 1)
								{
									btnRedpropone.setHorizontalTextPosition(SwingConstants.CENTER);
									btnRedpropone.setBorder(new BevelBorder(BevelBorder.RAISED, null, Color.RED, null, new Color(0, 0, 255)));
									btnRedpropone.setVerticalAlignment(SwingConstants.CENTER);
									btnRedpropone.setFont(new Font("Copperplate Gothic Bold", Font.BOLD, 22));
									btnRedpropone.setText("<html>O<br>X</html>");
								}
								if(r1.Damage == 2)
								{
									btnRedpropone.setHorizontalTextPosition(SwingConstants.CENTER);
									btnRedpropone.setBorder(new BevelBorder(BevelBorder.RAISED, null, Color.RED, null, new Color(0, 0, 255)));
									btnRedpropone.setVerticalAlignment(SwingConstants.CENTER);
									btnRedpropone.setFont(new Font("Copperplate Gothic Bold", Font.BOLD, 22));
									btnRedpropone.setText("<html>&nbsp;O<br>XX</html>");
								}
								if(r1.Damage == 3)
								{
									Turn.setText("<html>You Moved a Tenant in With 3 Damage. Be Sure To Repair Right Away or You Will Lose the Tenant</html>");
									btnRedpropone.setHorizontalTextPosition(SwingConstants.CENTER);
									btnRedpropone.setVerticalAlignment(SwingConstants.CENTER);
									btnRedpropone.setFont(new Font("Copperplate Gothic Bold", Font.BOLD, 20));
									btnRedpropone.setText("<html>&nbsp;&nbsp;&nbsp;O<br>XXX</html>");
								}
								r1.Occupied = true;
								((character)playerChars.get(playersTurn)).Bank = ((character)playerChars.get(playersTurn)).Bank + ((character)playerChars.get(playersTurn)).DepRed;
								numDice = 0;
								avaliable.RedTenant = avaliable.RedTenant - 1;
								avaliableTenants.setText("<html>Round" + round + "<br>" + "Tenants Avaliable<br>Red:" + avaliable.RedTenant + "<br>Blue:" + avaliable.BlueTenant + "<br>Green:" + avaliable.GreenTenant + "</html>");
								if(currentRoll.Red > 0)
								{
									btnDrawCard.setVisible(true);
									return currentRoll;
								}
								if(currentRoll.Blue > 0)
								{
									btnDrawCard.setVisible(true);
									return currentRoll;
								}
								else
								{
									btnNextPhase.setVisible(true);
									Turn.setText("<html>You Moved the tenant in and rolled no reds! Click on Next Phase to Tally your Monthly Earnings and Move on to the Buying and Repairing Phase</html>");
									return currentRoll;
								}
								
							}
							else if(reroll < 1)
							{
								
								numDice = currentRoll.Red;
								reroll++;
								if(currentRoll.Red > 0)
								{
									Turn.setText("<html>Sorry the Dice Must Hate You! You can Reroll your Red dice if you like");
									btnDrawCard.setVisible(true);
									return currentRoll;
								}
								if(currentRoll.Blue > 0)
								{
									Turn.setText("<html>Nothing to Reroll, Click Draw Card to Draw for Your Blue Roll.");
									btnDrawCard.setVisible(true);
									return currentRoll;
								}
								else
								{
									btnNextPhase.setVisible(true);
									Turn.setText("<html>Couldnt Get it Done, And no Red Dice to Reroll, Click on Next Phase to Tally your Monthly Earnings and Move on to the Buying and Repairing Phase</html>");
									return currentRoll;
								}
							}
							else if(reroll >= 1)
							{
								Turn.setText("<html>Sorry the Dice Still Hate You! You Failed to Move the Tenant In. Click on the Draw Card Button</html>");
								numDice = 0;
								reroll = 2;
								if(currentRoll.Red > 0)
								{
									btnDrawCard.setVisible(true);
									return currentRoll;
								}
								if(currentRoll.Blue > 0)
								{
									btnDrawCard.setVisible(true);
									return currentRoll;
								}
								else
								{
									btnNextPhase.setVisible(true);
									Turn.setText("<html>Couldnt Get it Done, And no Red Dice to Reroll,Click on Next Phase to Tally your Monthly Earnings and Move on to the Buying and Repairing Phase</html>");
									return currentRoll;
								}
							}
						
						}
						//method for moving in blue tenant
						if(currentTenant == 2)
						{
							dice LastRoll = new dice(0,0,0);
							if(reroll == 1)
							{
								LastRoll = new dice(currentRoll.Red, currentRoll.Blue, currentRoll.Green);
							}
							else
							{
								LastRoll.Red = 0;
								LastRoll.Blue = 0;
								LastRoll.Green = 0;
							}
							currentRoll = gameDice.Roll(numDice);
							currentRoll.Red = currentRoll.Red + LastRoll.Red;
							currentRoll.Blue = currentRoll.Blue + LastRoll.Blue;
							currentRoll.Green = currentRoll.Green + LastRoll.Green;
							
							diceResults.setVisible(true);
							diceResults.setText("<html>Dice Roll Results" + "<br>" + "Red:" + currentRoll.Red + "<br>Blue:" + currentRoll.Blue + "<br>Green:" + currentRoll.Green + "</html>");
							if(currentRoll.Green >= ((character)playerChars.get(playersTurn)).LeaseNumBlue + 1)
							{
								Turn.setText("<html>You Succsfully Moved the Tenant In! You Rolled Some Red's. Click on the Draw Cards Button");
								((character)playerChars.get(playersTurn)).BlueOccupied = ((character)playerChars.get(playersTurn)).BlueOccupied + 1;
								r1.TenantNotMatch = r1.TenantNotMatch - 1;
								if(r1.Damage == 0)
								{
									btnRedpropone.setHorizontalTextPosition(SwingConstants.CENTER);
									btnRedpropone.setBorder(new BevelBorder(BevelBorder.RAISED, null, Color.BLUE, null, new Color(0, 0, 255)));
									btnRedpropone.setVerticalAlignment(SwingConstants.CENTER);
									btnRedpropone.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 42));
									btnRedpropone.setText("O");
								}
								if(r1.Damage == 1)
								{
									btnRedpropone.setHorizontalTextPosition(SwingConstants.CENTER);
									btnRedpropone.setBorder(new BevelBorder(BevelBorder.RAISED, null, Color.BLUE, null, new Color(0, 0, 255)));
									btnRedpropone.setVerticalAlignment(SwingConstants.CENTER);
									btnRedpropone.setFont(new Font("Copperplate Gothic Bold", Font.BOLD, 22));
									btnRedpropone.setText("<html>O<br>X</html>");
								}
								if(r1.Damage == 2)
								{
									btnRedpropone.setHorizontalTextPosition(SwingConstants.CENTER);
									btnRedpropone.setBorder(new BevelBorder(BevelBorder.RAISED, null, Color.BLUE, null, new Color(0, 0, 255)));
									btnRedpropone.setVerticalAlignment(SwingConstants.CENTER);
									btnRedpropone.setFont(new Font("Copperplate Gothic Bold", Font.BOLD, 22));
									btnRedpropone.setText("<html>&nbsp;O<br>XX</html>");
								}
								if(r1.Damage == 3)
								{
									Turn.setText("<html>You Moved a Tenant in With 3 Damage. Be Sure To Repair Right Away or You Will Lose the Tenant</html>");
									btnRedpropone.setHorizontalTextPosition(SwingConstants.CENTER);
									btnRedpropone.setVerticalAlignment(SwingConstants.CENTER);
									btnRedpropone.setFont(new Font("Copperplate Gothic Bold", Font.BOLD, 20));
									btnRedpropone.setText("<html>&nbsp;&nbsp;&nbsp;O<br>XXX</html>");
								}
								r1.Occupied = true;
								((character)playerChars.get(playersTurn)).Bank = ((character)playerChars.get(playersTurn)).Bank + ((character)playerChars.get(playersTurn)).DepBlue;
								numDice = 0;
								avaliable.BlueTenant = avaliable.BlueTenant - 1;
								avaliableTenants.setText("<html>Round" + round + "<br>" + "Tenants Avaliable<br>Red:" + avaliable.RedTenant + "<br>Blue:" + avaliable.BlueTenant + "<br>Green:" + avaliable.GreenTenant + "</html>");
								if(currentRoll.Red > 0)
								{
									btnDrawCard.setVisible(true);
									return currentRoll;
								}
								if(currentRoll.Blue > 0)
								{
									btnDrawCard.setVisible(true);
									return currentRoll;
								}
								else
								{
									btnNextPhase.setVisible(true);
									Turn.setText("<html>You Moved the tenant in and rolled no reds! Click on Next Phase to Tally your Monthly Earnings and Move on to the Buying and Repairing Phase</html>");
									return currentRoll;
								}
							}
							else if(reroll < 1)
							{
								
								numDice = currentRoll.Red;
								reroll++;
								if(currentRoll.Red > 0)
								{
									Turn.setText("<html>Sorry the Dice Must Hate You! You can Reroll your Red dice if you like");
									btnDrawCard.setVisible(true);
									return currentRoll;
								}
								if(currentRoll.Blue > 0)
								{
									Turn.setText("<html>Nothing to Reroll, Click Draw Card to Draw for Your Blue Roll.");
									btnDrawCard.setVisible(true);
									return currentRoll;
								}
								else
								{
									btnNextPhase.setVisible(true);
									Turn.setText("<html>Couldnt Get it Done, And no Red Dice to Reroll, Click on Next Phase to Tally your Monthly Earnings and Move on to the Buying and Repairing Phase</html>");
									return currentRoll;
								}
							}
							else if(reroll >= 1)
							{
								Turn.setText("<html>Sorry the Dice Still Hate You! You Failed to Move the Tenant In. Click on the Draw Card Button</html>");
								numDice = 0;
								reroll = 2;
								if(currentRoll.Red > 0)
								{
									btnDrawCard.setVisible(true);
									return currentRoll;
								}
								if(currentRoll.Blue > 0)
								{
									btnDrawCard.setVisible(true);
									return currentRoll;
								}
								else
								{
									btnNextPhase.setVisible(true);
									Turn.setText("<html>Couldnt Get it Done, And no Red Dice to Reroll,Click on Next Phase to Tally your Monthly Earnings and Move on to the Buying and Repairing Phase</html>");
									return currentRoll;
								}
							}
						
						}
					}
					//method for moving in green tenant
					if(currentTenant == 3)
					{
						dice LastRoll = new dice(0,0,0);
						if(reroll == 1)
						{
							LastRoll = new dice(currentRoll.Red, currentRoll.Blue, currentRoll.Green);
						}
						else
						{
							LastRoll.Red = 0;
							LastRoll.Blue = 0;
							LastRoll.Green = 0;
						}
						currentRoll = gameDice.Roll(numDice);
						currentRoll.Red = currentRoll.Red + LastRoll.Red;
						currentRoll.Blue = currentRoll.Blue + LastRoll.Blue;
						currentRoll.Green = currentRoll.Green + LastRoll.Green;
						
						diceResults.setVisible(true);
						diceResults.setText("<html>Dice Roll Results" + "<br>" + "Red:" + currentRoll.Red + "<br>Blue:" + currentRoll.Blue + "<br>Green:" + currentRoll.Green + "</html>");
						if(currentRoll.Green >= ((character)playerChars.get(playersTurn)).LeaseNumGreen)
						{
							Turn.setText("<html>You Succsfully Moved the Tenant In! You Rolled Some Red's. Click on the Draw Cards Button");
							((character)playerChars.get(playersTurn)).GreenOccupied = ((character)playerChars.get(playersTurn)).GreenOccupied + 1;
							if(r1.Damage == 0)
							{
								btnRedpropone.setHorizontalTextPosition(SwingConstants.CENTER);
								btnRedpropone.setVerticalAlignment(SwingConstants.CENTER);
								btnRedpropone.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 42));
								btnRedpropone.setText("O");
							}
							if(r1.Damage == 1)
							{
								btnRedpropone.setHorizontalTextPosition(SwingConstants.CENTER);
								btnRedpropone.setVerticalAlignment(SwingConstants.CENTER);
								btnRedpropone.setFont(new Font("Copperplate Gothic Bold", Font.BOLD, 22));
								btnRedpropone.setText("<html>O<br>X</html>");
							}
							if(r1.Damage == 2)
							{
								btnRedpropone.setHorizontalTextPosition(SwingConstants.CENTER);
								btnRedpropone.setVerticalAlignment(SwingConstants.CENTER);
								btnRedpropone.setFont(new Font("Copperplate Gothic Bold", Font.BOLD, 22));
								btnRedpropone.setText("<html>&nbsp;O<br>XX</html>");
							}
							if(r1.Damage == 3)
							{
								Turn.setText("<html>You Moved a Tenant in With 3 Damage. Be Sure To Repair Right Away or You Will Lose the Tenant</html>");
								btnRedpropone.setHorizontalTextPosition(SwingConstants.CENTER);
								btnRedpropone.setVerticalAlignment(SwingConstants.CENTER);
								btnRedpropone.setFont(new Font("Copperplate Gothic Bold", Font.BOLD, 20));
								btnRedpropone.setText("<html>&nbsp;&nbsp;&nbsp;O<br>XXX</html>");
							}
							r1.Occupied = true;
							((character)playerChars.get(playersTurn)).Bank = ((character)playerChars.get(playersTurn)).Bank + ((character)playerChars.get(playersTurn)).DepGreen;
							numDice = 0;
							avaliable.GreenTenant = avaliable.GreenTenant - 1;
							avaliableTenants.setText("<html>Round" + round + "<br>" + "Tenants Avaliable<br>Red:" + avaliable.RedTenant + "<br>Blue:" + avaliable.BlueTenant + "<br>Green:" + avaliable.GreenTenant + "</html>");
							if(currentRoll.Red > 0)
							{
								btnDrawCard.setVisible(true);
								return currentRoll;
							}
							if(currentRoll.Blue > 0)
							{
								btnDrawCard.setVisible(true);
								return currentRoll;
							}
							else
							{
								btnNextPhase.setVisible(true);
								Turn.setText("<html>Click on Next Phase to Tally your Monthly Earnings and Move on to the Buying and Repairing Phase</html>");
								return currentRoll;
							}
						}
						else if(reroll < 1)
							{
								
								numDice = currentRoll.Red;
								reroll++;
								if(currentRoll.Red > 0)
								{
									Turn.setText("<html>Sorry the Dice Must Hate You! You can Reroll your Red dice if you like");
									btnDrawCard.setVisible(true);
									return currentRoll;
								}
								if(currentRoll.Blue > 0)
								{
									Turn.setText("<html>Nothing to Reroll, Click Draw Card to Draw for Your Blue Roll.");
									btnDrawCard.setVisible(true);
									return currentRoll;
								}
								else
								{
									btnNextPhase.setVisible(true);
									Turn.setText("<html>Couldnt Get it Done, And no Red Dice to Reroll, Click on Next Phase to Tally your Monthly Earnings and Move on to the Buying and Repairing Phase</html>");
									return currentRoll;
								}
							}
						else if(reroll >= 1)
							{
								Turn.setText("<html>Sorry the Dice Still Hate You! You Failed to Move the Tenant In. Click on the Draw Card Button</html>");
								numDice = 0;
								reroll = 2;
								if(currentRoll.Red > 0)
								{
									btnDrawCard.setVisible(true);
									return currentRoll;
								}
								if(currentRoll.Blue > 0)
								{
									btnDrawCard.setVisible(true);
									return currentRoll;
								}
								else
								{
									btnNextPhase.setVisible(true);
									Turn.setText("<html>Couldnt Get it Done, And no Red Dice to Reroll,Click on Next Phase to Tally your Monthly Earnings and Move on to the Buying and Repairing Phase</html>");
									return currentRoll;
								}
							}
					
					}
					return currentRoll;
				}


		
		//method for adding damage to property
		public void AddDamage(property r1, ArrayList playerChars, int playersTurn, int phase, JLabel characterCard, JButton btnRedpropone, dice currentRoll, JLabel diceResults, JLabel Turn, 
				JButton btnTakeBank, JButton btnDrawCard, JButton btnNextPhase)
		{
		if(r1.OwnedBy.equals(((character)playerChars.get(playersTurn)).PlayerColor) && DamageVar != -1 && phase == 2 || phase == 11)
		{
			if(DamageVar >= 0 && DamageVar < 4 || phase == 11)
			{
				if(r1.Damage < 3)
				{
					if(currentRoll.Red > 0)
					{
						currentRoll.Red = currentRoll.Red - 1;
					}
					r1.Damage = r1.Damage +1;
					diceResults.setText("<html>Dice Roll Results" + "<br>" + "Red:" + currentRoll.Red + "<br>Blue:" + currentRoll.Blue + "<br>Green:" + currentRoll.Green + "</html>");
				}
				else
				{
					Turn.setText("<html>Cannot take more Damage! Apply to another Property or you can click Take From Bank");
					btnTakeBank.setVisible(true);
					
				}
				if(currentRoll.Red > 0 || currentRoll.Blue > 0)
					{
						btnDrawCard.setVisible(true);
					}
				else
				{
					btnNextPhase.setVisible(true);
					Turn.setText("<html>Click on Next Phase to Tally your Monthly Earnings and Move on to the Buying and Repairing Phase</html>");
				}
				this.DamageVar = -1;
				phase = 9;
				if(r1.Occupied == true)
				{
					if(r1.Damage == 1)
					{
						btnRedpropone.setHorizontalTextPosition(SwingConstants.CENTER);
						btnRedpropone.setVerticalAlignment(SwingConstants.CENTER);
						btnRedpropone.setFont(new Font("Copperplate Gothic Bold", Font.BOLD, 22));
						btnRedpropone.setText("<html>O<br>X</html>");
					}
					if(r1.Damage == 2)
					{
						btnRedpropone.setHorizontalTextPosition(SwingConstants.CENTER);
						btnRedpropone.setVerticalAlignment(SwingConstants.CENTER);
						btnRedpropone.setFont(new Font("Copperplate Gothic Bold", Font.BOLD, 22));
						btnRedpropone.setText("<html>&nbsp;O<br>XX</html>");
					}
					if(r1.Damage == 3)
					{
						btnRedpropone.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 20));
						btnRedpropone.setHorizontalTextPosition(SwingConstants.CENTER);
						btnRedpropone.setVerticalAlignment(SwingConstants.CENTER);
						btnRedpropone.setText("XXX");
						Turn.setText("<html>Tenant Moved Out Because of all of the Damage!");
						r1.Occupied = false;
						if(r1.Color == 1)
						{
							((character)playerChars.get(playersTurn)).RedOccupied = ((character)playerChars.get(playersTurn)).RedOccupied -1;
						}
						if(r1.Color == 2)
						{
							((character)playerChars.get(playersTurn)).BlueOccupied = ((character)playerChars.get(playersTurn)).BlueOccupied -1;
						}
						if(r1.Color == 3)
						{
							((character)playerChars.get(playersTurn)).GreenOccupied = ((character)playerChars.get(playersTurn)).GreenOccupied -1;
						}
					}
				}
				if(r1.Occupied == false)
				{
					if(r1.Damage == 1)
					{
						btnRedpropone.setHorizontalTextPosition(SwingConstants.CENTER);
						btnRedpropone.setVerticalAlignment(SwingConstants.CENTER);
						btnRedpropone.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 42));
						btnRedpropone.setText("X");
					}
					if(r1.Damage == 2)
					{
						btnRedpropone.setHorizontalTextPosition(SwingConstants.CENTER);
						btnRedpropone.setVerticalAlignment(SwingConstants.CENTER);
						btnRedpropone.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 28));
						btnRedpropone.setText("XX");
					}
					if(r1.Damage == 3)
					{
						btnRedpropone.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 20));
						btnRedpropone.setHorizontalTextPosition(SwingConstants.CENTER);
						btnRedpropone.setVerticalAlignment(SwingConstants.CENTER);
						btnRedpropone.setText("XXX");
					}
				}
				
			}
			else
			{
				Turn.setText("<html>Cannot Damage this Property Somthing lieky went wrong</html>");
			}
		}
		
		}
		//method for buying
		public void Buying(property r1, ArrayList playerChars, int playersTurn, int phase, JLabel characterCard, JButton btnRedpropone){
		if(r1.OwnedBy.equals("")&& phase == 3)
		{
			((character) playerChars.get(playersTurn)).buy(r1);
			String Col = ((character)playerChars.get(playersTurn)).PlayerColor;
			characterCard.setVisible(true);
			characterCard.setText(((character) playerChars.get(playersTurn)).charCard());
			
			if(r1.OwnedBy.equals(Col))
			{
				if(Col.equals("Purple"))
				{
					btnRedpropone.setForeground(Color.MAGENTA);
				}
				if(Col.equals("Orange"))
				{
					btnRedpropone.setForeground(Color.ORANGE);
				}
				if(Col.equals("Yellow"))
				{
					btnRedpropone.setForeground(Color.YELLOW);
				}
				if(Col.equals("DarkGreen"))
				{
					btnRedpropone.setForeground(new Color(0, 100, 0));
				}
				if(Col.equals("Pink"))
				{
					btnRedpropone.setForeground(Color.PINK);
				}
				if(Col.equals("Peach"))
				{
					btnRedpropone.setForeground(new Color(255, 218, 185));
				}
				if(Col.equals("DarkBlue"))
				{
					btnRedpropone.setForeground(new Color(25, 25, 112));
				}
				if(Col.equals("Brown"))
				{
					btnRedpropone.setForeground(new Color(139, 69, 19));
				}
				if(Col.equals("LimeGreen"))
				{
					btnRedpropone.setForeground(new Color(124, 252, 0));
				}
				if(Col.equals("Ivory"))
				{
					btnRedpropone.setForeground(new Color(255, 255, 240));
				}
			}
		}
		}
		
		//Method for upgrading
		public void Upgrading(property r1,JButton btnRedpropone, ArrayList playerChars, int playersTurn, int phase, JLabel Turn, JLabel characterCard){
		if(r1.OwnedBy.equals(((character)playerChars.get(playersTurn)).PlayerColor) && phase == 5)
		{
			//String Upresp = ((character) playerChars.get(playersTurn)).upgrade(r1);
			if(r1.Color == 3)
			{
				Turn.setText("Cannot Upgrate a Green Property");
			}
			if(((character) playerChars.get(playersTurn)).SkillNum == 4)
			{
				if (((character) playerChars.get(playersTurn)).Bank >= 250 )
				{
					((character) playerChars.get(playersTurn)).Bank = ((character) playerChars.get(playersTurn)).Bank - 250;
					r1.Upgrades = r1.Upgrades + 1;
					Turn.setText("Upgraded");
				}
			}
			else if (((character) playerChars.get(playersTurn)).Bank >= 500 )
			{
				((character) playerChars.get(playersTurn)).Bank = ((character) playerChars.get(playersTurn)).Bank - 500;
				r1.Upgrades = r1.Upgrades + 1;
				Turn.setText("Upgraded");
			}
			else
			{	
				Turn.setText("Not Enough Money to Upgrade");
			}
			characterCard.setText(((character) playerChars.get(playersTurn)).charCard() );
			if(r1.Upgrades == 1)
			{
				btnRedpropone.setBorder(new LineBorder(Color.ORANGE, 3));
			}
			if(r1.Upgrades == 2)
			{
				btnRedpropone.setBorder(new LineBorder(Color.ORANGE, 6));
			}
			
			if(r1.Upgrades == 3)
			{
				r1.Color = 2;
				btnRedpropone.setBackground(Color.BLUE);
				btnRedpropone.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
				((character)playerChars.get(playersTurn)).RedOwned = ((character)playerChars.get(playersTurn)).RedOwned -1;
				((character)playerChars.get(playersTurn)).BlueOwned = ((character)playerChars.get(playersTurn)).BlueOwned +1;
				if(r1.Occupied == true)
				{
					((character)playerChars.get(playersTurn)).RedOccupied = ((character)playerChars.get(playersTurn)).RedOccupied -1;
					((character)playerChars.get(playersTurn)).BlueOccupied = ((character)playerChars.get(playersTurn)).BlueOccupied +1;
				}
			}
			if(r1.Upgrades == 4)
			{
				btnRedpropone.setBorder(new LineBorder(Color.ORANGE, 3));
			}
			if(r1.Upgrades == 5)
			{
				btnRedpropone.setBorder(new LineBorder(Color.ORANGE, 6));
			}
			
			if(r1.Upgrades == 6)
			{
				r1.Color = 3;
				btnRedpropone.setBackground(new Color(0, 128, 0));
				btnRedpropone.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
				((character)playerChars.get(playersTurn)).BlueOwned = ((character)playerChars.get(playersTurn)).BlueOwned -1;
				((character)playerChars.get(playersTurn)).GreenOwned = ((character)playerChars.get(playersTurn)).GreenOwned +1;
			}
			if(r1.Occupied == true)
			{
				((character)playerChars.get(playersTurn)).BlueOccupied = ((character)playerChars.get(playersTurn)).BlueOccupied -1;
				((character)playerChars.get(playersTurn)).GreenOccupied = ((character)playerChars.get(playersTurn)).GreenOccupied +1;
			}
		}
		}
		
		//method for removing tenant of your own
		public void evictOwn(property r1, int phase, ArrayList playerChars,int playersTurn, JButton btnRedpropone, dice currentRoll, JLabel diceResults, JButton btnDrawCard, JButton btnNextPhase, JLabel Turn)
		{
			if(r1.OwnedBy.equals(((character)playerChars.get(playersTurn)).PlayerColor) && phase == 6)
			{
				r1.Occupied = false;
				currentRoll.Blue = currentRoll.Blue - 1;
				diceResults.setText("<html>Dice Roll Results" + "<br>" + "Red:" + currentRoll.Red + "<br>Blue:" + currentRoll.Blue + "<br>Green:" + currentRoll.Green + "</html>");
				if(r1.Color == 1 && r1.TenantNotMatch == 1)
				{
					((character)playerChars.get(playersTurn)).RedOccupied = ((character)playerChars.get(playersTurn)).RedOccupied -1;
				}
				if(r1.Color == 2 && r1.TenantNotMatch == 1)
				{
					((character)playerChars.get(playersTurn)).BlueOccupied = ((character)playerChars.get(playersTurn)).BlueOccupied -1;
				}
				if(r1.Color == 3 && r1.TenantNotMatch == 1)
				{
					((character)playerChars.get(playersTurn)).GreenOccupied = ((character)playerChars.get(playersTurn)).GreenOccupied -1;
				}
				if(r1.Color == 1 && r1.TenantNotMatch == 2)
				{
					((character)playerChars.get(playersTurn)).BlueOccupied = ((character)playerChars.get(playersTurn)).BlueOccupied -1;
				}
				if(r1.Color == 1 && r1.TenantNotMatch == 3)
				{
					((character)playerChars.get(playersTurn)).GreenOccupied = ((character)playerChars.get(playersTurn)).GreenOccupied -1;
				}
				if(r1.Color == 2 && r1.TenantNotMatch == 0)
				{
					((character)playerChars.get(playersTurn)).RedOccupied = ((character)playerChars.get(playersTurn)).RedOccupied -1;
				}
				if(r1.Color == 2 && r1.TenantNotMatch == 2)
				{
					((character)playerChars.get(playersTurn)).GreenOccupied = ((character)playerChars.get(playersTurn)).GreenOccupied -1;
				}
				if(r1.Color == 3 && r1.TenantNotMatch == -1)
				{
					((character)playerChars.get(playersTurn)).RedOccupied = ((character)playerChars.get(playersTurn)).RedOccupied -1;
				}
				if(r1.Color == 3 && r1.TenantNotMatch == 0)
				{
					((character)playerChars.get(playersTurn)).BlueOccupied = ((character)playerChars.get(playersTurn)).BlueOccupied -1;
				}
				r1.TenantNotMatch = 1;
				if(r1.Damage == 0)
				{
					btnRedpropone.setHorizontalTextPosition(SwingConstants.CENTER);
					btnRedpropone.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
					btnRedpropone.setVerticalAlignment(SwingConstants.TOP);
					btnRedpropone.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 68));
					btnRedpropone.setText("*");
				}
				if(r1.Damage == 1)
				{
					btnRedpropone.setHorizontalTextPosition(SwingConstants.CENTER);
					btnRedpropone.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
					btnRedpropone.setVerticalAlignment(SwingConstants.CENTER);
					btnRedpropone.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 42));
					btnRedpropone.setText("X");
				}
				if(r1.Damage == 2)
				{
					btnRedpropone.setHorizontalTextPosition(SwingConstants.CENTER);
					btnRedpropone.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
					btnRedpropone.setVerticalAlignment(SwingConstants.CENTER);
					btnRedpropone.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 28));
					btnRedpropone.setText("XX");
				}
				if(r1.Damage == 3)
				{
					btnRedpropone.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 20));
					btnRedpropone.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
					btnRedpropone.setHorizontalTextPosition(SwingConstants.CENTER);
					btnRedpropone.setVerticalAlignment(SwingConstants.CENTER);
					btnRedpropone.setText("XXX");
				}
				if(currentRoll.Blue > 0)
				{
					btnDrawCard.setVisible(true);
				}
				else
				{
				btnNextPhase.setVisible(true);
				Turn.setText("<html>Click on Next Phase to Tally your Monthly Earnings and Move on to the Buying and Repairing Phase</html>");
				}
			}
		}
		//method for repairing
		public void Repairing(property r1, JButton btnRedpropone, ArrayList playerChars, int playersTurn, int phase, JLabel Turn, JLabel characterCard ){
			if(r1.OwnedBy.equals(((character)playerChars.get(playersTurn)).PlayerColor) && phase == 4)
			{
				//String resp = ((character) playerChars.get(playersTurn)).repair(r1);
				if( r1.Damage == 0)
				{
					Turn.setText("No Damage to Repair");
					
				}
				if(((character) playerChars.get(playersTurn)).SkillNum == 2)
				{
					if( ((character) playerChars.get(playersTurn)).Bank >= 100)
					{
						((character) playerChars.get(playersTurn)).Bank = ((character) playerChars.get(playersTurn)).Bank - 100;
						r1.Damage = r1.Damage - 1;
						Turn.setText("Damage Repaired");
					}
				}
				else if( ((character) playerChars.get(playersTurn)).Bank >= 200)
				{
					((character) playerChars.get(playersTurn)).Bank = ((character) playerChars.get(playersTurn)).Bank - 200;
					r1.Damage = r1.Damage - 1;
					Turn.setText("Damage Repaired");
				}
				if(this.Bank < 200)
				{
					Turn.setText("Not Enough Money to Repair");
				}
				characterCard.setText(((character) playerChars.get(playersTurn)).charCard() );
				if(r1.Occupied == true)
				{
					if(r1.Damage == 0)
					{
						btnRedpropone.setText("O");
						btnRedpropone.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 42));
						btnRedpropone.setVerticalAlignment(SwingConstants.CENTER);
						characterCard.setText(((character) playerChars.get(playersTurn)).charCard() );
					}
					if(r1.Damage == 1)
					{
						btnRedpropone.setHorizontalTextPosition(SwingConstants.CENTER);
						btnRedpropone.setVerticalAlignment(SwingConstants.CENTER);
						btnRedpropone.setFont(new Font("Copperplate Gothic Bold", Font.BOLD, 22));
						btnRedpropone.setText("<html>O<br>X</html>");
					}
					if(r1.Damage == 2)
					{
						btnRedpropone.setHorizontalTextPosition(SwingConstants.CENTER);
						btnRedpropone.setVerticalAlignment(SwingConstants.CENTER);
						btnRedpropone.setFont(new Font("Copperplate Gothic Bold", Font.BOLD, 22));
						btnRedpropone.setText("<html>&nbsp;O<br>XX</html>");
					}
					
				}
				if(r1.Occupied == false)
				{
					if(r1.Damage == 0)
					{
						btnRedpropone.setText("*");
						btnRedpropone.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 68));
						btnRedpropone.setVerticalAlignment(SwingConstants.TOP);
						characterCard.setText(((character) playerChars.get(playersTurn)).charCard() );
					}
					if(r1.Damage == 1)
					{
						btnRedpropone.setHorizontalTextPosition(SwingConstants.CENTER);
						btnRedpropone.setVerticalAlignment(SwingConstants.CENTER);
						btnRedpropone.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 42));
						btnRedpropone.setText("X");
					}
					if(r1.Damage == 2)
					{
						btnRedpropone.setHorizontalTextPosition(SwingConstants.CENTER);
						btnRedpropone.setVerticalAlignment(SwingConstants.CENTER);
						btnRedpropone.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 28));
						btnRedpropone.setText("XX");
					}
				}
				
			}
		}	
	
}
