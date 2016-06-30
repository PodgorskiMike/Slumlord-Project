package Slumlord;

import java.awt.Color;
import java.awt.Font;
import java.io.FileWriter;
import java.io.IOException;
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
	
	private static void generateCsvFile(String sFileName, String round, String player, String action, String outcome)
	   {

		boolean exists = true;
		try {
			FileWriter writer = new FileWriter(sFileName,exists);
			 writer.append(round);
			 writer.append(player);
			 writer.append(action);
			 writer.append(outcome);
			 writer.append('\n');
			 
			 writer.flush();
			 //writer.close();
		
		
		
		
		
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
}
	
	//handles the manipulation of the character object for buying a property
	public void buy(property a)
	{	
		//if a player can afford the color property and it is not owned by someone else
		if (a.Color == 1 && this.Bank >= this.BuyRed && a.Owned == false)
		{
			//subtract fund from bank
			this.Bank = this.Bank - this.BuyRed;
			//mark as owned (cannot be bought by anyone else)
			a.Owned = true;
			//change owned by to player color
			a.OwnedBy = this.PlayerColor;
			//add 1 to total of buildings owned
			this.RedOwned = this.RedOwned + 1;
			//add to arraylist for easy searching
			this.AllOwned.add(a);
		}

		//if a player can afford the color property and it is not owned by someone else
		if (a.Color == 2 && this.Bank >= this.BuyBlue && a.Owned == false)
		{
			//subtract fund from bank
			this.Bank = this.Bank - this.BuyBlue;
			//mark as owned (cannot be bought by anyone else)
			a.Owned = true;
			//change owned by to player color
			a.OwnedBy = this.PlayerColor;
			//add 1 to total of buildings owned
			this.BlueOwned = this.BlueOwned + 1;
			//add to arraylist for easy searching
			this.AllOwned.add(a);
		}
		//if a player can afford the color property and it is not owned by someone else
		if (a.Color == 3 && this.Bank >= this.BuyGreen && a.Owned == false)
		{
			//subtract fund from bank
			this.Bank = this.Bank - this.BuyGreen;
			//mark as owned (cannot be bought by anyone else)
			a.Owned = true;
			//change owned by to player color
			a.OwnedBy = this.PlayerColor;
			//add 1 to total of buildings owned
			this.GreenOwned = this.GreenOwned + 1;
			//add to arraylist for easy searching
			this.AllOwned.add(a);
		}
	}
	
	//handles the manipulation of the character object for upgrading a property
	public String upgrade(property a)
	{
		//checks to see if it can be upgraded
		if(a.Color == 3)
		{
			return "Cannot Upgrate a Green Property";
		}
		//checks to see that player can afford the upgrade
		if (this.Bank >= 500 )
		{
			//subtracts money from bank and adds upgrade to property
			this.Bank = this.Bank - 500;
			a.Upgrades = a.Upgrades + 1;
			return "Upgraded";
		}
		else
		{	
			return "Not Enough Money to Upgrade";
		}
	}
		
	  //handles the manipulation of the character object for repairing a property
		public String repair(property a)
		{	
			//checks to see if it needs repair
			if( a.Damage == 0)
			{
				return "No Damage to Repair";
				
			}
			//checks to see that player can afford the upgrade
			if( this.Bank >= 200)
			{
				//subtracts money from bank and removes damage from the property
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
		
		//String formatted for end game results
		public String finalScore()
		{
			return "<html>" + this.Name + " Red Buildings Owned " + this.RedOwned + " Blue Buildings Owned " + this.BlueOwned + " Green Building Owned " + this.GreenOwned + " Total Bank " + this.Bank;
		}
		
		//String formatted for character card
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
		
		//string for character selection list
		public String toString()
		{
			return this.Name +"  "+ this.Skill;
		}
		
		//Method for all move in red
		public dice MoveInRed(property r1, ArrayList playerChars, int playersTurn, int phase, int currentTenant, dice currentRoll, dice gameDice, JLabel diceResults, JLabel Turn, 
				JButton btnRedpropone, tenantTokens avaliable, JLabel avaliableTenants, int round, JButton btnDrawCard, JButton btnNextPhase, String roundS)
		{
			//checks that the property is owned by the current player, that we are not in damage mode, it is not already occupied, in phase 1(move in), and that a number of dice has been selected
			if(r1.OwnedBy.equals(((character)playerChars.get(playersTurn)).PlayerColor) && DamageVar == -1 && r1.Occupied == false && phase == 1 && numDice != 0)
			{
				//method for moving in red tenant
				if(currentTenant == 1)
				{
					
					dice LastRoll = new dice(0,0,0);
					//if this is a reroll add first roll
					if(reroll == 1)
					{
						LastRoll = new dice(currentRoll.Red, currentRoll.Blue, currentRoll.Green);
					}
					else
					{
						//if not a reroll (is a first roll) make sure previous roll is erased
						LastRoll.Red = 0;
						LastRoll.Blue = 0;
						LastRoll.Green = 0;
					}
					//call roll method and add results to current roll
					currentRoll = gameDice.Roll(numDice);
					currentRoll.Red = currentRoll.Red + LastRoll.Red;
					currentRoll.Blue = currentRoll.Blue + LastRoll.Blue;
					currentRoll.Green = currentRoll.Green + LastRoll.Green;
					//make results visible
					
					diceResults.setVisible(true);
					diceResults.setText("<html>Dice Roll Results" + "<br>" + "Red:" + currentRoll.Red + "<br>Blue:" + currentRoll.Blue + "<br>Green:" + currentRoll.Green + "</html>");
					//check if current green dice are enough to move in tenant
					if(currentRoll.Green >= ((character)playerChars.get(playersTurn)).LeaseNumRed)
					{	//success add one to occupied int
						generateCsvFile("C:\\Users\\salty\\Desktop\\Game.csv", roundS + "," ,(((character)playerChars.get(playersTurn)).Name), ",Rolled To Move In",",Success");
						Turn.setText("<html>You Succsfully Moved the Tenant In! You Rolled Some Red's Click on the Draw Cards Button");
						((character)playerChars.get(playersTurn)).RedOccupied = ((character)playerChars.get(playersTurn)).RedOccupied + 1;
						//change display depending on damage to property
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
						//change occupied to true
						r1.Occupied = true;
						//add deposit amount to players bank
						((character)playerChars.get(playersTurn)).Bank = ((character)playerChars.get(playersTurn)).Bank + ((character)playerChars.get(playersTurn)).DepRed;
						//change dice to 0 (no need to reroll)
						numDice = 0;
						//subtract tenant from available tenant list and display
						avaliable.RedTenant = avaliable.RedTenant - 1;
						avaliableTenants.setText("<html>Round" + round + "<br>" + "Tenants Avaliable<br>Red:" + avaliable.RedTenant + "<br>Blue:" + avaliable.BlueTenant + "<br>Green:" + avaliable.GreenTenant + "</html>");
						//if reds were rolled move on to drawing cards
						if(currentRoll.Red > 0)
						{
							btnDrawCard.setVisible(true);
							return currentRoll;
						}
						//if blues were rolled move on to drawing cards
						if(currentRoll.Blue > 0)
						{
							btnDrawCard.setVisible(true);
							return currentRoll;
						}
						//not reds or blues, move on to next phase
						else
						{
							btnNextPhase.setVisible(true);
							Turn.setText("<html>You Moved the tenant in and rolled no reds Click on Next Phase to Tally your Monthly Earnings and Move on to the Buying and Repairing Phase</html>");
							return currentRoll;
						}
						
					}
					//Failure
					//if its the first roll(reroll available)
					else if(reroll < 1)
					{
						generateCsvFile("C:\\Users\\salty\\Desktop\\Game.csv", roundS + "," ,(((character)playerChars.get(playersTurn)).Name), ",Rolled To Move In",",Fail");
						//red rolls are the only ones you can reroll
						//num dice now equals red
						numDice = currentRoll.Red;
						//increment reroll so player cant reroll again
						reroll++;
						//update text, if a player can reroll
						if(currentRoll.Red > 0)
						{
							Turn.setText("<html>Sorry the Dice Must Hate You!  You can Reroll your Red dice if you like");
							btnDrawCard.setVisible(true);
							return currentRoll;
						}
						//update text, if a player chooses not to reroll and just draw cards
						if(currentRoll.Blue > 0)
						{
							Turn.setText("<html>Nothing to Reroll, Click Draw Card to Draw for Your Blue Roll.");
							btnDrawCard.setVisible(true);
							return currentRoll;
						}
						//update text, cant reroll, no reds or blues to draw, move on to next phase
						else
						{
							btnNextPhase.setVisible(true);
							Turn.setText("<html>Couldnt Get it Done, And no Red Dice to Reroll, Click on Next Phase to Tally your Monthly Earnings and Move on to the Buying and Repairing Phase</html>");
							return currentRoll;
						}
					}
					else if(reroll >= 1)
					{
						//set text
						generateCsvFile("C:\\Users\\salty\\Desktop\\Game.csv", roundS + "," ,(((character)playerChars.get(playersTurn)).Name), ",Rolled To Move In",",Fail");
						Turn.setText("<html>Sorry the Dice Still Hate You! You Failed to Move the Tenant In Click on the Draw Card Button</html>");
						//clear dice and rerolls
						numDice = 0;
						reroll = 2;
						//if red was rolled draw card
						if(currentRoll.Red > 0)
						{
							btnDrawCard.setVisible(true);
							return currentRoll;
						}
						//if blue was rolled draw card
						if(currentRoll.Blue > 0)
						{
							btnDrawCard.setVisible(true);
							return currentRoll;
						}
						//update text, cant reroll, no reds or blues to draw, move on to next phase
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
					//if this is a reroll add first roll
					if(reroll == 1)
					{
						LastRoll = new dice(currentRoll.Red, currentRoll.Blue, currentRoll.Green);
					}
					else
					{
						//if not a reroll (is a first roll) make sure previous roll is erased
						LastRoll.Red = 0;
						LastRoll.Blue = 0;
						LastRoll.Green = 0;
					}
					//call roll method and add results to current roll
					currentRoll = gameDice.Roll(numDice);
					currentRoll.Red = currentRoll.Red + LastRoll.Red;
					currentRoll.Blue = currentRoll.Blue + LastRoll.Blue;
					currentRoll.Green = currentRoll.Green + LastRoll.Green;
					//make results visible
					
					diceResults.setVisible(true);
					diceResults.setText("<html>Dice Roll Results" + "<br>" + "Red:" + currentRoll.Red + "<br>Blue:" + currentRoll.Blue + "<br>Green:" + currentRoll.Green + "</html>");
					//check if current green dice are enough to move in tenant
					if(currentRoll.Green >= ((character)playerChars.get(playersTurn)).LeaseNumBlue + 1)
					{
						generateCsvFile("C:\\Users\\salty\\Desktop\\Game.csv", roundS + "," ,(((character)playerChars.get(playersTurn)).Name), ",Rolled To Move In",",Success");
						//success add one to occupied int
						Turn.setText("<html>You Succsfully Moved the Tenant In! You Rolled Some Red's Click on the Draw Cards Button");
						((character)playerChars.get(playersTurn)).BlueOccupied = ((character)playerChars.get(playersTurn)).BlueOccupied + 1;
						r1.TenantNotMatch = r1.TenantNotMatch + 1;
						//change display depending on damage to property
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
						//change occupied to true
						r1.Occupied = true;
						//add deposit amount to players bank
						((character)playerChars.get(playersTurn)).Bank = ((character)playerChars.get(playersTurn)).Bank + ((character)playerChars.get(playersTurn)).DepBlue;
						//change dice to 0 (no need to reroll)
						numDice = 0;
						//subtract tenant from available tenant list and display
						avaliable.BlueTenant = avaliable.BlueTenant - 1;
						avaliableTenants.setText("<html>Round" + round + "<br>" + "Tenants Avaliable<br>Red:" + avaliable.RedTenant + "<br>Blue:" + avaliable.BlueTenant + "<br>Green:" + avaliable.GreenTenant + "</html>");
						//if reds were rolled move on to drawing cards
						if(currentRoll.Red > 0)
						{
							btnDrawCard.setVisible(true);
							return currentRoll;
						}
						//if blues were rolled move on to drawing cards
						if(currentRoll.Blue > 0)
						{
							btnDrawCard.setVisible(true);
							return currentRoll;
						}
						//not reds or blues, move on to next phase
						else
						{
							btnNextPhase.setVisible(true);
							Turn.setText("<html>You Moved the tenant in and rolled no reds! Click on Next Phase to Tally your Monthly Earnings and Move on to the Buying and Repairing Phase</html>");
							return currentRoll;
						}
					}
					//Failure
					//if its the first roll(reroll available)
					else if(reroll < 1)
					{
						generateCsvFile("C:\\Users\\salty\\Desktop\\Game.csv", roundS + "," ,(((character)playerChars.get(playersTurn)).Name), ",Rolled To Move In",",Fail");
						//red rolls are the only ones you can reroll
						//num dice now equals red
						numDice = currentRoll.Red;
						//increment reroll so player cant reroll again
						reroll++;
						//update text, if a player can reroll
						if(currentRoll.Red > 0)
						{
							Turn.setText("<html>Sorry the Dice Must Hate You! You can Reroll your Red dice if you like</html>");
							btnDrawCard.setVisible(true);
							return currentRoll;
						}
						//update text, if a player chooses not to reroll and just draw cards
						if(currentRoll.Blue > 0)
						{
							Turn.setText("<html>Nothing to Reroll, Click Draw Card to Draw for Your Blue Roll.");
							btnDrawCard.setVisible(true);
							return currentRoll;
						}
						//update text, cant reroll, no reds or blues to draw, move on to next phase
						else
						{
							btnNextPhase.setVisible(true);
							Turn.setText("<html>Couldnt Get it Done, And no Red Dice to Reroll, Click on Next Phase to Tally your Monthly Earnings and Move on to the Buying and Repairing Phase</html>");
							return currentRoll;
						}
					}
					else if(reroll >= 1)
					{
						generateCsvFile("C:\\Users\\salty\\Desktop\\Game.csv", roundS + "," ,(((character)playerChars.get(playersTurn)).Name), ",Rolled To Move In",",Fail");
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
				//if this is a reroll add first roll
				if(reroll == 1)
				{
					LastRoll = new dice(currentRoll.Red, currentRoll.Blue, currentRoll.Green);
				}
				else
				{
					//if not a reroll (is a first roll) make sure previous roll is erased
					LastRoll.Red = 0;
					LastRoll.Blue = 0;
					LastRoll.Green = 0;
				}
				//call roll method and add results to current roll
				currentRoll = gameDice.Roll(numDice);
				currentRoll.Red = currentRoll.Red + LastRoll.Red;
				currentRoll.Blue = currentRoll.Blue + LastRoll.Blue;
				currentRoll.Green = currentRoll.Green + LastRoll.Green;
				//make results visible
				
				diceResults.setVisible(true);
				diceResults.setText("<html>Dice Roll Results" + "<br>" + "Red:" + currentRoll.Red + "<br>Blue:" + currentRoll.Blue + "<br>Green:" + currentRoll.Green + "</html>");
				//check if current green dice are enough to move in tenant
				if(currentRoll.Green >= ((character)playerChars.get(playersTurn)).LeaseNumGreen + 3)
				{
					generateCsvFile("C:\\Users\\salty\\Desktop\\Game.csv", roundS + "," ,(((character)playerChars.get(playersTurn)).Name), ",Rolled To Move In",",Success");
					//success add one to occupied int
					Turn.setText("<html>You Succsfully Moved the Tenant In!  You Rolled Some Red's. Click on the Draw Cards Button");
					((character)playerChars.get(playersTurn)).GreenOccupied = ((character)playerChars.get(playersTurn)).GreenOccupied + 1;
					r1.TenantNotMatch = r1.TenantNotMatch + 2;
					//change display depending on damage to property
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
					//change occupied to true
					r1.Occupied = true;
					//add deposit amount to players bank
					((character)playerChars.get(playersTurn)).Bank = ((character)playerChars.get(playersTurn)).Bank + ((character)playerChars.get(playersTurn)).DepGreen;
					//change dice to 0 (no need to reroll)
					numDice = 0;
					//subtract tenant from available tenant list and display
					avaliable.GreenTenant = avaliable.GreenTenant - 1;
					avaliableTenants.setText("<html>Round" + round + "<br>" + "Tenants Avaliable<br>Red:" + avaliable.RedTenant + "<br>Blue:" + avaliable.BlueTenant + "<br>Green:" + avaliable.GreenTenant + "</html>");
					//if reds were rolled move on to drawing cards
					if(currentRoll.Red > 0)
					{
						btnDrawCard.setVisible(true);
						return currentRoll;
					}
					//if blues were rolled move on to drawing cards
					if(currentRoll.Blue > 0)
					{
						btnDrawCard.setVisible(true);
						return currentRoll;
					}
					//not reds or blues, move on to next phase
					else
					{
						btnNextPhase.setVisible(true);
						Turn.setText("<html>Click on Next Phase to Tally your Monthly Earnings and Move on to the Buying and Repairing Phase</html>");
						return currentRoll;
					}
				}
				//Failure
				//if its the first roll(reroll available)
				else if(reroll < 1)
					{
					generateCsvFile("C:\\Users\\salty\\Desktop\\Game.csv", roundS + "," ,(((character)playerChars.get(playersTurn)).Name), ",Rolled To Move In",",Fail");
					//red rolls are the only ones you can reroll
					//num dice now equals red
					numDice = currentRoll.Red;
					//increment reroll so player cant reroll again
					reroll++;
					//update text, if a player can reroll
					if(currentRoll.Red > 0)
						{
							Turn.setText("<html>Sorry the Dice Must Hate You! You can Reroll your Red dice if you like");
							btnDrawCard.setVisible(true);
							return currentRoll;
						}
					//update text, if a player chooses not to reroll and just draw cards
					if(currentRoll.Blue > 0)
					{
						Turn.setText("<html>Nothing to Reroll, Click Draw Card to Draw for Your Blue Roll.");
						btnDrawCard.setVisible(true);
						return currentRoll;
					}
					//update text, cant reroll, no reds or blues to draw, move on to next phase
						else
						{
							btnNextPhase.setVisible(true);
							Turn.setText("<html>Couldnt Get it Done, And no Red Dice to Reroll, Click on Next Phase to Tally your Monthly Earnings and Move on to the Buying and Repairing Phase</html>");
							return currentRoll;
						}
					}
				else if(reroll >= 1)
					{
					generateCsvFile("C:\\Users\\salty\\Desktop\\Game.csv", roundS + "," ,(((character)playerChars.get(playersTurn)).Name), ",Rolled To Move In",",Fail");
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
						JButton btnRedpropone, tenantTokens avaliable, JLabel avaliableTenants, int round, JButton btnDrawCard, JButton btnNextPhase, String roundS)
				{
					//checks that the property is owned by the current player, that we are not in damage mode, it is not already occupied, in phase 1(move in), and that a number of dice has been selected
					if(r1.OwnedBy.equals(((character)playerChars.get(playersTurn)).PlayerColor) && DamageVar == -1 && r1.Occupied == false && phase == 1 && numDice != 0)
					{
						//method for moving in red tenant
						if(currentTenant == 1)
						{
							dice LastRoll = new dice(0,0,0);
							//if this is a reroll add first roll
							if(reroll == 1)
							{
								LastRoll = new dice(currentRoll.Red, currentRoll.Blue, currentRoll.Green);
							}
							else
							{
								//if not a reroll (is a first roll) make sure previous roll is erased
								LastRoll.Red = 0;
								LastRoll.Blue = 0;
								LastRoll.Green = 0;
							}
							//call roll method and add results to current roll
							currentRoll = gameDice.Roll(numDice);
							currentRoll.Red = currentRoll.Red + LastRoll.Red;
							currentRoll.Blue = currentRoll.Blue + LastRoll.Blue;
							currentRoll.Green = currentRoll.Green + LastRoll.Green;
							//make results visible
							
							diceResults.setVisible(true);
							diceResults.setText("<html>Dice Roll Results" + "<br>" + "Red:" + currentRoll.Red + "<br>Blue:" + currentRoll.Blue + "<br>Green:" + currentRoll.Green + "</html>");
							//check if current green dice are enough to move in tenant
							if(currentRoll.Green >= ((character)playerChars.get(playersTurn)).LeaseNumRed +1)
							{
								generateCsvFile("C:\\Users\\salty\\Desktop\\Game.csv", roundS + "," ,(((character)playerChars.get(playersTurn)).Name), ",Rolled To Move In",",Success");
								//success add one to occupied int
								Turn.setText("<html>You Succsfully Moved the Tenant In! You Rolled Some Red's. Click on the Draw Cards Button");
								((character)playerChars.get(playersTurn)).RedOccupied = ((character)playerChars.get(playersTurn)).RedOccupied + 1;
								r1.TenantNotMatch = r1.TenantNotMatch - 1;
								//change display depending on damage to property
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
								//change occupied to true
								r1.Occupied = true;
								//add deposit amount to players bank
								((character)playerChars.get(playersTurn)).Bank = ((character)playerChars.get(playersTurn)).Bank + ((character)playerChars.get(playersTurn)).DepRed;
								//change dice to 0 (no need to reroll)
								numDice = 0;
								//subtract tenant from available tenant list and display
								avaliable.RedTenant = avaliable.RedTenant - 1;
								avaliableTenants.setText("<html>Round" + round + "<br>" + "Tenants Avaliable<br>Red:" + avaliable.RedTenant + "<br>Blue:" + avaliable.BlueTenant + "<br>Green:" + avaliable.GreenTenant + "</html>");
								//if reds were rolled move on to drawing cards
								if(currentRoll.Red > 0)
								{
									btnDrawCard.setVisible(true);
									return currentRoll;
								}
								//if Blues were rolled move on to drawing cards
								if(currentRoll.Blue > 0)
								{
									btnDrawCard.setVisible(true);
									return currentRoll;
								}
								//not reds or blues, move on to next phase
								else
								{
									btnNextPhase.setVisible(true);
									Turn.setText("<html>You Moved the tenant in and rolled no reds! Click on Next Phase to Tally your Monthly Earnings and Move on to the Buying and Repairing Phase</html>");
									return currentRoll;
								}
								
							}
							//Failure
							//if its the first roll(reroll available)
							else if(reroll < 1)
							{
								generateCsvFile("C:\\Users\\salty\\Desktop\\Game.csv", roundS + "," ,(((character)playerChars.get(playersTurn)).Name), ",Rolled To Move In",",Fail");
								//red rolls are the only ones you can reroll
								//num dice now equals red
								numDice = currentRoll.Red;
								//increment reroll so player cant reroll again
								reroll++;
								//update text, if a player can reroll
								if(currentRoll.Red > 0)
								{
									Turn.setText("<html>Sorry the Dice Must Hate You!  You can Reroll your Red dice if you like");
									btnDrawCard.setVisible(true);
									return currentRoll;
								}
								//update text, if a player chooses not to reroll and just draw cards
								if(currentRoll.Blue > 0)
								{
									Turn.setText("<html>Nothing to Reroll, Click Draw Card to Draw for Your Blue Roll.");
									btnDrawCard.setVisible(true);
									return currentRoll;
								}
								//update text, cant reroll, no reds or blues to draw, move on to next phase
								else
								{
									btnNextPhase.setVisible(true);
									Turn.setText("<html>Couldnt Get it Done, And no Red Dice to Reroll, Click on Next Phase to Tally your Monthly Earnings and Move on to the Buying and Repairing Phase</html>");
									return currentRoll;
								}
							}
							else if(reroll >= 1)
							{
								generateCsvFile("C:\\Users\\salty\\Desktop\\Game.csv", roundS + "," ,(((character)playerChars.get(playersTurn)).Name), ",Rolled To Move In",",Fail");
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
							//if this is a reroll add first roll
							if(reroll == 1)
							{
								LastRoll = new dice(currentRoll.Red, currentRoll.Blue, currentRoll.Green);
							}
							else
							{
								//if not a reroll (is a first roll) make sure previous roll is erased
								LastRoll.Red = 0;
								LastRoll.Blue = 0;
								LastRoll.Green = 0;
							}
							//call roll method and add results to current roll
							currentRoll = gameDice.Roll(numDice);
							currentRoll.Red = currentRoll.Red + LastRoll.Red;
							currentRoll.Blue = currentRoll.Blue + LastRoll.Blue;
							currentRoll.Green = currentRoll.Green + LastRoll.Green;
							//make results visible
							
							diceResults.setVisible(true);
							diceResults.setText("<html>Dice Roll Results" + "<br>" + "Red:" + currentRoll.Red + "<br>Blue:" + currentRoll.Blue + "<br>Green:" + currentRoll.Green + "</html>");
							//check if current green dice are enough to move in tenant
							if(currentRoll.Green >= ((character)playerChars.get(playersTurn)).LeaseNumBlue)
							{
								generateCsvFile("C:\\Users\\salty\\Desktop\\Game.csv", roundS + "," ,(((character)playerChars.get(playersTurn)).Name), ",Rolled To Move In",",Success");
								//success add one to occupied int
								Turn.setText("<html>You Succsfully Moved the Tenant In! You Rolled Some Red's. Click on the Draw Cards Button");
								((character)playerChars.get(playersTurn)).BlueOccupied = ((character)playerChars.get(playersTurn)).BlueOccupied;
								//change display depending on damage to property
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
								//change occupied to true
								r1.Occupied = true;
								//add deposit amount to players bank
								((character)playerChars.get(playersTurn)).Bank = ((character)playerChars.get(playersTurn)).Bank + ((character)playerChars.get(playersTurn)).DepBlue;
								//change dice to 0 (no need to reroll)
								numDice = 0;
								//subtract tenant from available tenant list and display
								avaliable.BlueTenant = avaliable.BlueTenant - 1;
								avaliableTenants.setText("<html>Round" + round + "<br>" + "Tenants Avaliable<br>Red:" + avaliable.RedTenant + "<br>Blue:" + avaliable.BlueTenant + "<br>Green:" + avaliable.GreenTenant + "</html>");
								//if reds were rolled move on to drawing cards
								if(currentRoll.Red > 0)
								{
									btnDrawCard.setVisible(true);
									return currentRoll;
								}
								//if blues were rolled move on to drawing cards
								if(currentRoll.Blue > 0)
								{
									btnDrawCard.setVisible(true);
									return currentRoll;
								}
								//not reds or blues, move on to next phase
								else
								{
									btnNextPhase.setVisible(true);
									Turn.setText("<html>You Moved the tenant in and rolled no reds! Click on Next Phase to Tally your Monthly Earnings and Move on to the Buying and Repairing Phase</html>");
									return currentRoll;
								}
							}
							//Failure
							//if its the first roll(reroll available)
							else if(reroll < 1)
							{
								generateCsvFile("C:\\Users\\salty\\Desktop\\Game.csv", roundS + "," ,(((character)playerChars.get(playersTurn)).Name), ",Rolled To Move In",",Fail");
								//red rolls are the only ones you can reroll
								//num dice now equals red
								numDice = currentRoll.Red;
								//increment reroll so player cant reroll again
								reroll++;
								//update text, if a player can reroll
								if(currentRoll.Red > 0)
								{
									Turn.setText("<html>Sorry the Dice Must Hate You! You can Reroll your Red dice if you like");
									btnDrawCard.setVisible(true);
									return currentRoll;
								}
								//update text, if a player chooses not to reroll and just draw cards
								if(currentRoll.Blue > 0)
								{
									Turn.setText("<html>Nothing to Reroll, Click Draw Card to Draw for Your Blue Roll.");
									btnDrawCard.setVisible(true);
									return currentRoll;
								}
								//update text, cant reroll, no reds or blues to draw, move on to next phase
								else
								{
									btnNextPhase.setVisible(true);
									Turn.setText("<html>Couldnt Get it Done, And no Red Dice to Reroll, Click on Next Phase to Tally your Monthly Earnings and Move on to the Buying and Repairing Phase</html>");
									return currentRoll;
								}
							}
							else if(reroll >= 1)
							{
								generateCsvFile("C:\\Users\\salty\\Desktop\\Game.csv", roundS + "," ,(((character)playerChars.get(playersTurn)).Name), ",Rolled To Move In",",Fail");
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
						//if this is a reroll add first roll
						if(reroll == 1)
						{
							LastRoll = new dice(currentRoll.Red, currentRoll.Blue, currentRoll.Green);
						}
						else
						{
							//if not a reroll (is a first roll) make sure previous roll is erased
							LastRoll.Red = 0;
							LastRoll.Blue = 0;
							LastRoll.Green = 0;
						}
						//call roll method and add results to current roll
						currentRoll = gameDice.Roll(numDice);
						currentRoll.Red = currentRoll.Red + LastRoll.Red;
						currentRoll.Blue = currentRoll.Blue + LastRoll.Blue;
						currentRoll.Green = currentRoll.Green + LastRoll.Green;
						//make results visible
						
						diceResults.setVisible(true);
						diceResults.setText("<html>Dice Roll Results" + "<br>" + "Red:" + currentRoll.Red + "<br>Blue:" + currentRoll.Blue + "<br>Green:" + currentRoll.Green + "</html>");
						//check if current green dice are enough to move in tenant
						if(currentRoll.Green >= ((character)playerChars.get(playersTurn)).LeaseNumGreen + 1)
						{
							generateCsvFile("C:\\Users\\salty\\Desktop\\Game.csv", roundS + "," ,(((character)playerChars.get(playersTurn)).Name), ",Rolled To Move In",",Success");
							//success add one to occupied int
							Turn.setText("<html>You Succsfully Moved the Tenant In! You Rolled Some Red's. Click on the Draw Cards Button");
							((character)playerChars.get(playersTurn)).GreenOccupied = ((character)playerChars.get(playersTurn)).GreenOccupied + 1;
							r1.TenantNotMatch = r1.TenantNotMatch + 1;
							//change display depending on damage to property
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
							//change occupied to true
							r1.Occupied = true;
							//add deposit amount to players bank
							((character)playerChars.get(playersTurn)).Bank = ((character)playerChars.get(playersTurn)).Bank + ((character)playerChars.get(playersTurn)).DepGreen;
							//change dice to 0 (no need to reroll)
							numDice = 0;
							//subtract tenant from available tenant list and display
							avaliable.GreenTenant = avaliable.GreenTenant - 1;
							avaliableTenants.setText("<html>Round" + round + "<br>" + "Tenants Avaliable<br>Red:" + avaliable.RedTenant + "<br>Blue:" + avaliable.BlueTenant + "<br>Green:" + avaliable.GreenTenant + "</html>");
							//if reds were rolled move on to drawing cards
							if(currentRoll.Red > 0)
							{
								btnDrawCard.setVisible(true);
								return currentRoll;
							}
							//if blues were rolled move on to drawing cards
							if(currentRoll.Blue > 0)
							{
								btnDrawCard.setVisible(true);
								return currentRoll;
							}
							//not reds or blues, move on to next phase
							else
							{
								btnNextPhase.setVisible(true);
								Turn.setText("<html>Click on Next Phase to Tally your Monthly Earnings and Move on to the Buying and Repairing Phase</html>");
								return currentRoll;
							}
						}
						//Failure
						//if its the first roll(reroll available)
						else if(reroll < 1)
							{
							generateCsvFile("C:\\Users\\salty\\Desktop\\Game.csv", roundS + "," ,(((character)playerChars.get(playersTurn)).Name), ",Rolled To Move In",",Fail");
							//red rolls are the only ones you can reroll
							//num dice now equals red
							numDice = currentRoll.Red;
							//increment reroll so player cant reroll again
							reroll++;
							//update text, if a player can reroll
							if(currentRoll.Red > 0)
								{
									Turn.setText("<html>Sorry the Dice Must Hate You!  You can Reroll your Red dice if you like");
									btnDrawCard.setVisible(true);
									return currentRoll;
								}
								//update text, if a player chooses not to reroll and just draw cards
								if(currentRoll.Blue > 0)
								{
									Turn.setText("<html>Nothing to Reroll, Click Draw Card to Draw for Your Blue Roll.");
									btnDrawCard.setVisible(true);
									return currentRoll;
								}
							//update text, cant reroll, no reds or blues to draw, move on to next phase
								else
								{
									btnNextPhase.setVisible(true);
									Turn.setText("<html>Couldnt Get it Done, And no Red Dice to Reroll, Click on Next Phase to Tally your Monthly Earnings and Move on to the Buying and Repairing Phase</html>");
									return currentRoll;
								}
							}
						else if(reroll >= 1)
							{
							generateCsvFile("C:\\Users\\salty\\Desktop\\Game.csv", roundS + "," ,(((character)playerChars.get(playersTurn)).Name), ",Rolled To Move In",",Fail");
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
						JButton btnRedpropone, tenantTokens avaliable, JLabel avaliableTenants, int round, JButton btnDrawCard, JButton btnNextPhase, String roundS)
				{
					//checks that the property is owned by the current player, that we are not in damage mode, it is not already occupied, in phase 1(move in), and that a number of dice has been selected
					if(r1.OwnedBy.equals(((character)playerChars.get(playersTurn)).PlayerColor) && DamageVar == -1 && r1.Occupied == false && phase == 1 && numDice != 0)
					{
						//method for moving in red tenant
						if(currentTenant == 1)
						{
							dice LastRoll = new dice(0,0,0);
							//if this is a reroll add first roll
							if(reroll == 1)
							{
								LastRoll = new dice(currentRoll.Red, currentRoll.Blue, currentRoll.Green);
							}
							else
							{
								//if not a reroll (is a first roll) make sure previous roll is erased
								LastRoll.Red = 0;
								LastRoll.Blue = 0;
								LastRoll.Green = 0;
							}
							//call roll method and add results to current roll
							currentRoll = gameDice.Roll(numDice);
							currentRoll.Red = currentRoll.Red + LastRoll.Red;
							currentRoll.Blue = currentRoll.Blue + LastRoll.Blue;
							currentRoll.Green = currentRoll.Green + LastRoll.Green;
							//make results visible
							
							diceResults.setVisible(true);
							diceResults.setText("<html>Dice Roll Results" + "<br>" + "Red:" + currentRoll.Red + "<br>Blue:" + currentRoll.Blue + "<br>Green:" + currentRoll.Green + "</html>");
							//check if current green dice are enough to move in tenant
							if(currentRoll.Green >= ((character)playerChars.get(playersTurn)).LeaseNumRed +3)
							{
								generateCsvFile("C:\\Users\\salty\\Desktop\\Game.csv", roundS + "," ,(((character)playerChars.get(playersTurn)).Name), ",Rolled To Move In",",Success");
								//success add one to occupied int
								Turn.setText("<html>You Succsfully Moved the Tenant In! You Rolled Some Red's. Click on the Draw Cards Button");
								((character)playerChars.get(playersTurn)).RedOccupied = ((character)playerChars.get(playersTurn)).RedOccupied + 1;
								r1.TenantNotMatch = r1.TenantNotMatch - 2;
								//change display depending on damage to property
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
								//change occupied to true
								r1.Occupied = true;
								//add deposit amount to players bank
								((character)playerChars.get(playersTurn)).Bank = ((character)playerChars.get(playersTurn)).Bank + ((character)playerChars.get(playersTurn)).DepRed;
								//change dice to 0 (no need to reroll)
								numDice = 0;
								//subtract tenant from available tenant list and display
								avaliable.RedTenant = avaliable.RedTenant - 1;
								avaliableTenants.setText("<html>Round" + round + "<br>" + "Tenants Avaliable<br>Red:" + avaliable.RedTenant + "<br>Blue:" + avaliable.BlueTenant + "<br>Green:" + avaliable.GreenTenant + "</html>");
								//if reds were rolled move on to drawing cards
								if(currentRoll.Red > 0)
								{
									btnDrawCard.setVisible(true);
									return currentRoll;
								}
								//if Blues were rolled move on to drawing cards
								if(currentRoll.Blue > 0)
								{
									btnDrawCard.setVisible(true);
									return currentRoll;
								}
								//not reds or blues, move on to next phase
								else
								{
									btnNextPhase.setVisible(true);
									Turn.setText("<html>You Moved the tenant in and rolled no reds! Click on Next Phase to Tally your Monthly Earnings and Move on to the Buying and Repairing Phase</html>");
									return currentRoll;
								}
								
							}
							//Failure
							//if its the first roll(reroll available)
							else if(reroll < 1)
							{
								generateCsvFile("C:\\Users\\salty\\Desktop\\Game.csv", roundS + "," ,(((character)playerChars.get(playersTurn)).Name), ",Rolled To Move In",",Fail");
								//red rolls are the only ones you can reroll
								//num dice now equals red
								numDice = currentRoll.Red;
								//increment reroll so player cant reroll again
								reroll++;
								//update text, if a player can reroll
								if(currentRoll.Red > 0)
								{
									Turn.setText("<html>Sorry the Dice Must Hate You! You can Reroll your Red dice if you like");
									btnDrawCard.setVisible(true);
									return currentRoll;
								}
								//update text, if a player chooses not to reroll and just draw cards
								if(currentRoll.Blue > 0)
								{
									Turn.setText("<html>Nothing to Reroll, Click Draw Card to Draw for Your Blue Roll.");
									btnDrawCard.setVisible(true);
									return currentRoll;
								}
								//update text, cant reroll, no reds or blues to draw, move on to next phase
								else
								{
									btnNextPhase.setVisible(true);
									Turn.setText("<html>Couldnt Get it Done, And no Red Dice to Reroll, Click on Next Phase to Tally your Monthly Earnings and Move on to the Buying and Repairing Phase</html>");
									return currentRoll;
								}
							}
							else if(reroll >= 1)
							{
								generateCsvFile("C:\\Users\\salty\\Desktop\\Game.csv", roundS + "," ,(((character)playerChars.get(playersTurn)).Name), ",Rolled To Move In",",Fail");
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
							//if this is a reroll add first roll
							if(reroll == 1)
							{
								LastRoll = new dice(currentRoll.Red, currentRoll.Blue, currentRoll.Green);
							}
							else
							{
								//if not a reroll (is a first roll) make sure previous roll is erased
								LastRoll.Red = 0;
								LastRoll.Blue = 0;
								LastRoll.Green = 0;
							}
							//call roll method and add results to current roll
							currentRoll = gameDice.Roll(numDice);
							currentRoll.Red = currentRoll.Red + LastRoll.Red;
							currentRoll.Blue = currentRoll.Blue + LastRoll.Blue;
							currentRoll.Green = currentRoll.Green + LastRoll.Green;
							//make results visible
							
							diceResults.setVisible(true);
							diceResults.setText("<html>Dice Roll Results" + "<br>" + "Red:" + currentRoll.Red + "<br>Blue:" + currentRoll.Blue + "<br>Green:" + currentRoll.Green + "</html>");
							//check if current green dice are enough to move in tenant
							if(currentRoll.Green >= ((character)playerChars.get(playersTurn)).LeaseNumBlue + 1)
							{
								generateCsvFile("C:\\Users\\salty\\Desktop\\Game.csv", roundS + "," ,(((character)playerChars.get(playersTurn)).Name), ",Rolled To Move In",",Success");
								//success add one to occupied int
								Turn.setText("<html>You Succsfully Moved the Tenant In! You Rolled Some Red's. Click on the Draw Cards Button");
								((character)playerChars.get(playersTurn)).BlueOccupied = ((character)playerChars.get(playersTurn)).BlueOccupied + 1;
								r1.TenantNotMatch = r1.TenantNotMatch - 1;
								//change display depending on damage to property
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
								//change occupied to true
								r1.Occupied = true;
								//add deposit amount to players bank
								((character)playerChars.get(playersTurn)).Bank = ((character)playerChars.get(playersTurn)).Bank + ((character)playerChars.get(playersTurn)).DepBlue;
								//change dice to 0 (no need to reroll)
								numDice = 0;
								//subtract tenant from available tenant list and display
								avaliable.BlueTenant = avaliable.BlueTenant - 1;
								avaliableTenants.setText("<html>Round" + round + "<br>" + "Tenants Avaliable<br>Red:" + avaliable.RedTenant + "<br>Blue:" + avaliable.BlueTenant + "<br>Green:" + avaliable.GreenTenant + "</html>");
								//if reds were rolled move on to drawing cards
								if(currentRoll.Red > 0)
								{
									btnDrawCard.setVisible(true);
									return currentRoll;
								}
								//if blues were rolled move on to drawing cards
								if(currentRoll.Blue > 0)
								{
									btnDrawCard.setVisible(true);
									return currentRoll;
								}
								//not reds or blues, move on to next phase
								else
								{
									btnNextPhase.setVisible(true);
									Turn.setText("<html>You Moved the tenant in and rolled no reds! Click on Next Phase to Tally your Monthly Earnings and Move on to the Buying and Repairing Phase</html>");
									return currentRoll;
								}
							}
							//Failure
							//if its the first roll(reroll available)
							else if(reroll < 1)
							{
								generateCsvFile("C:\\Users\\salty\\Desktop\\Game.csv", roundS + "," ,(((character)playerChars.get(playersTurn)).Name), ",Rolled To Move In",",Fail");
								//red rolls are the only ones you can reroll
								//num dice now equals red
								numDice = currentRoll.Red;
								//increment reroll so player cant reroll again
								reroll++;
								//update text, if a player can reroll
								if(currentRoll.Red > 0)
								{
									Turn.setText("<html>Sorry the Dice Must Hate You! You can Reroll your Red dice if you like");
									btnDrawCard.setVisible(true);
									return currentRoll;
								}
								//update text, if a player chooses not to reroll and just draw cards
								if(currentRoll.Blue > 0)
								{
									Turn.setText("<html>Nothing to Reroll, Click Draw Card to Draw for Your Blue Roll.");
									btnDrawCard.setVisible(true);
									return currentRoll;
								}
								//update text, cant reroll, no reds or blues to draw, move on to next phase
								else
								{
									btnNextPhase.setVisible(true);
									Turn.setText("<html>Couldnt Get it Done, And no Red Dice to Reroll, Click on Next Phase to Tally your Monthly Earnings and Move on to the Buying and Repairing Phase</html>");
									return currentRoll;
								}
							}
							else if(reroll >= 1)
							{
								generateCsvFile("C:\\Users\\salty\\Desktop\\Game.csv", roundS + "," ,(((character)playerChars.get(playersTurn)).Name), ",Rolled To Move In",",Fail");
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
						//if this is a reroll add first roll
						if(reroll == 1)
						{
							LastRoll = new dice(currentRoll.Red, currentRoll.Blue, currentRoll.Green);
						}
						else
						{
							//if not a reroll (is a first roll) make sure previous roll is erased
							LastRoll.Red = 0;
							LastRoll.Blue = 0;
							LastRoll.Green = 0;
						}
						//call roll method and add results to current roll
						currentRoll = gameDice.Roll(numDice);
						currentRoll.Red = currentRoll.Red + LastRoll.Red;
						currentRoll.Blue = currentRoll.Blue + LastRoll.Blue;
						currentRoll.Green = currentRoll.Green + LastRoll.Green;
						//make results visible
						
						diceResults.setVisible(true);
						diceResults.setText("<html>Dice Roll Results" + "<br>" + "Red:" + currentRoll.Red + "<br>Blue:" + currentRoll.Blue + "<br>Green:" + currentRoll.Green + "</html>");
						//check if current green dice are enough to move in tenant
						if(currentRoll.Green >= ((character)playerChars.get(playersTurn)).LeaseNumGreen)
						{
							generateCsvFile("C:\\Users\\salty\\Desktop\\Game.csv", roundS + "," ,(((character)playerChars.get(playersTurn)).Name), ",Rolled To Move In",",Success");
							//success add one to occupied int
							Turn.setText("<html>You Succsfully Moved the Tenant In! You Rolled Some Red's. Click on the Draw Cards Button");
							((character)playerChars.get(playersTurn)).GreenOccupied = ((character)playerChars.get(playersTurn)).GreenOccupied + 1;
							//change display depending on damage to property
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
							//change occupied to true
							r1.Occupied = true;
							//add deposit amount to players bank
							((character)playerChars.get(playersTurn)).Bank = ((character)playerChars.get(playersTurn)).Bank + ((character)playerChars.get(playersTurn)).DepGreen;
							//change dice to 0 (no need to reroll)
							numDice = 0;
							//subtract tenant from available tenant list and display
							avaliable.GreenTenant = avaliable.GreenTenant - 1;
							avaliableTenants.setText("<html>Round" + round + "<br>" + "Tenants Avaliable<br>Red:" + avaliable.RedTenant + "<br>Blue:" + avaliable.BlueTenant + "<br>Green:" + avaliable.GreenTenant + "</html>");
							//if reds were rolled move on to drawing cards
							if(currentRoll.Red > 0)
							{
								btnDrawCard.setVisible(true);
								return currentRoll;
							}
							//if blues were rolled move on to drawing cards
							if(currentRoll.Blue > 0)
							{
								btnDrawCard.setVisible(true);
								return currentRoll;
							}
							//not reds or blues, move on to next phase
							else
							{
								btnNextPhase.setVisible(true);
								Turn.setText("<html>Click on Next Phase to Tally your Monthly Earnings and Move on to the Buying and Repairing Phase</html>");
								return currentRoll;
							}
						}
						//Failure
						//if its the first roll(reroll available)
						else if(reroll < 1)
							{
							generateCsvFile("C:\\Users\\salty\\Desktop\\Game.csv", roundS + "," ,(((character)playerChars.get(playersTurn)).Name), ",Rolled To Move In",",Fail");
							//red rolls are the only ones you can reroll
							//num dice now equals red
							numDice = currentRoll.Red;
							//increment reroll so player cant reroll again
							reroll++;
							//update text, if a player can reroll
							if(currentRoll.Red > 0)
								{
									Turn.setText("<html>Sorry the Dice Must Hate You! You can Reroll your Red dice if you like");
									btnDrawCard.setVisible(true);
									return currentRoll;
								}
							//update text, if a player chooses not to reroll and just draw cards
							if(currentRoll.Blue > 0)
							{
								Turn.setText("<html>Nothing to Reroll, Click Draw Card to Draw for Your Blue Roll.");
								btnDrawCard.setVisible(true);
								return currentRoll;
							}
							//update text, cant reroll, no reds or blues to draw, move on to next phase
								else
								{
									btnNextPhase.setVisible(true);
									Turn.setText("<html>Couldnt Get it Done, And no Red Dice to Reroll, Click on Next Phase to Tally your Monthly Earnings and Move on to the Buying and Repairing Phase</html>");
									return currentRoll;
								}
							}
						else if(reroll >= 1)
							{
							generateCsvFile("C:\\Users\\salty\\Desktop\\Game.csv", roundS + "," ,(((character)playerChars.get(playersTurn)).Name), ",Rolled To Move In",",Fail");
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
				JButton btnTakeBank, JButton btnDrawCard, JButton btnNextPhase, String roundS)
		{
			//checks that property is owned by player and repair card has been drawn or that it is phase 11(damage others)
		if(r1.OwnedBy.equals(((character)playerChars.get(playersTurn)).PlayerColor) && DamageVar != -1 && phase == 2 || phase == 11)
		{	//checks for amount of damage already on property
			if(DamageVar >= 0 && DamageVar < 4 || phase == 11)
			{
				//if damage is under three
				if(r1.Damage < 3)
				{
					generateCsvFile("C:\\Users\\salty\\Desktop\\Game.csv", roundS + "," ,(((character)playerChars.get(playersTurn)).Name), ",Damaged Property","");
					//remove red dice/card
					if(currentRoll.Red > 0)
					{
						currentRoll.Red = currentRoll.Red - 1;
					}
					//add damage to property and display new roll info
					r1.Damage = r1.Damage +1;
					diceResults.setText("<html>Dice Roll Results" + "<br>" + "Red:" + currentRoll.Red + "<br>Blue:" + currentRoll.Blue + "<br>Green:" + currentRoll.Green + "</html>");
				}
				//if already too damaged
				else
				{
					//inform player/ make take from bank possible
					Turn.setText("<html>Cannot take more Damage! Apply to another Property or you can click Take From Bank");
					btnTakeBank.setVisible(true);
					btnDrawCard.setVisible(false);
					
				}
				//if more cards to draw, make button visible
				if(currentRoll.Red > 0 || currentRoll.Blue > 0)
					{
						btnDrawCard.setVisible(true);
					}
				//if not move to next phase
				else
				{
					btnNextPhase.setVisible(true);
					Turn.setText("<html>Click on Next Phase to Tally your Monthly Earnings and Move on to the Buying and Repairing Phase</html>");
				}
				//reset damage variable and phase to default so no accidental damage is done
				this.DamageVar = -1;
				phase = 9;
				//change property display characters to match damage dealt
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
						//if there is a tenant in a property and the damage equals three, the tenant moves out
						//inform player
						Turn.setText("<html>Tenant Moved Out Because of all of the Damage!");
						//set occupied to false and adjust occupied int on character
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
				//change property display characters to match damage dealt
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
		// checks that the property isnt owned and it is phase 3
			if(r1.OwnedBy.equals("")&& phase == 3)
			{
			//call buy method	
			((character) playerChars.get(playersTurn)).buy(r1);
			//set random string to players color
			String Col = ((character)playerChars.get(playersTurn)).PlayerColor;
			characterCard.setVisible(true);
			//update character card to reflect money spent
			characterCard.setText(((character) playerChars.get(playersTurn)).charCard());
			//sets property visible color to character color
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
		public void Upgrading(property r1,JButton btnRedpropone, ArrayList playerChars, int playersTurn, int phase, JLabel Turn, JLabel characterCard, String roundS){
			//checks that player owns the property and the game is in phase 5
		if(r1.OwnedBy.equals(((character)playerChars.get(playersTurn)).PlayerColor) && phase == 5)
		{
			//checks that the property is upgrade able
			if(r1.Color == 3)
			{
				Turn.setText("Cannot Upgrate a Green Property");
				return;
			}
			//cheks if the character has an upgrade skill, if so only charges half price
			if(((character) playerChars.get(playersTurn)).SkillNum == 4)
			{
				//checks for enought money
				if (((character) playerChars.get(playersTurn)).Bank >= 250 )
				{
					//subtracts the money
					((character) playerChars.get(playersTurn)).Bank = ((character) playerChars.get(playersTurn)).Bank - 250;
					//marks the upgrade on the property
					generateCsvFile("C:\\Users\\salty\\Desktop\\Game.csv", roundS + "," ,(((character)playerChars.get(playersTurn)).Name), ",Upgrade","");
					r1.Upgrades = r1.Upgrades + 1;
					Turn.setText("Upgraded");
				}
			}
			// makes sure player acan afford it
			else if (((character) playerChars.get(playersTurn)).Bank >= 500 )
			{	
				//subtracts the money
				((character) playerChars.get(playersTurn)).Bank = ((character) playerChars.get(playersTurn)).Bank - 500;
				//marks the upgrade on the property
				generateCsvFile("C:\\Users\\salty\\Desktop\\Game.csv", roundS + "," ,(((character)playerChars.get(playersTurn)).Name), ",Upgrade","");
				r1.Upgrades = r1.Upgrades + 1;
				Turn.setText("Upgraded");
			}
			else
			{	
				Turn.setText("Not Enough Money to Upgrade");
			}
			//displays the updated character card
			characterCard.setText(((character) playerChars.get(playersTurn)).charCard() );
			//adds upgrade border depending on how many upgrades
			if(r1.Upgrades == 1)
			{
				btnRedpropone.setBorder(new LineBorder(Color.ORANGE, 3));
			}
			if(r1.Upgrades == 2)
			{
				btnRedpropone.setBorder(new LineBorder(Color.ORANGE, 6));
			}
			//when upgrades equal 3 the property goes from red to blue
			if(r1.Upgrades == 3)
			{
				//changes property numerical color
				r1.Color = 2;
				//changes property physical color
				btnRedpropone.setBackground(Color.BLUE);
				//erases upgrade borders
				btnRedpropone.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
				//removes one red owned and replaces it with a blue owned
				((character)playerChars.get(playersTurn)).RedOwned = ((character)playerChars.get(playersTurn)).RedOwned -1;
				((character)playerChars.get(playersTurn)).BlueOwned = ((character)playerChars.get(playersTurn)).BlueOwned +1;
				//removes one red occupied and replaces it with a blue occupied if needed
				if(r1.Occupied == true)
				{
					((character)playerChars.get(playersTurn)).RedOccupied = ((character)playerChars.get(playersTurn)).RedOccupied -1;
					((character)playerChars.get(playersTurn)).BlueOccupied = ((character)playerChars.get(playersTurn)).BlueOccupied +1;
				}
			}
			//adds upgrade border depending on how many upgrades
			if(r1.Upgrades == 4)
			{
				btnRedpropone.setBorder(new LineBorder(Color.ORANGE, 3));
			}
			if(r1.Upgrades == 5)
			{
				btnRedpropone.setBorder(new LineBorder(Color.ORANGE, 6));
			}
			//when upgrades equal 6 the property goes from blue to green
			if(r1.Upgrades == 6)
			{
				//changes property numerical color
				r1.Color = 3;
				//changes property physical color
				btnRedpropone.setBackground(new Color(0, 128, 0));
				//erased upgrade borders
				btnRedpropone.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
				//removes one blue owned and replaces it with a green owned
				((character)playerChars.get(playersTurn)).BlueOwned = ((character)playerChars.get(playersTurn)).BlueOwned -1;
				((character)playerChars.get(playersTurn)).GreenOwned = ((character)playerChars.get(playersTurn)).GreenOwned +1;
			}
			//removes one blue occupied and replaces it with a green occupied if needed
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
			//checks that you own the property and that it is phase 6(tenant removal)
			if(r1.OwnedBy.equals(((character)playerChars.get(playersTurn)).PlayerColor) && phase == 6)
			{
				//sets occupied to false
				r1.Occupied = false;
				//removes the blue card/die and redisplays
				currentRoll.Blue = currentRoll.Blue - 1;
				diceResults.setText("<html>Dice Roll Results" + "<br>" + "Red:" + currentRoll.Red + "<br>Blue:" + currentRoll.Blue + "<br>Green:" + currentRoll.Green + "</html>");
				//this accounts for tenants of different colors being removed from any building
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
				//change display depending on damage to property and occupied status
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
				//if no blues left move on to next phase
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
		public void Repairing(property r1, JButton btnRedpropone, ArrayList playerChars, int playersTurn, int phase, JLabel Turn, JLabel characterCard, String roundS){
			//checks to be sure player owns building, it is phase 4(repair) and there is damage to be repaired
			if(r1.OwnedBy.equals(((character)playerChars.get(playersTurn)).PlayerColor) && phase == 4 && r1.Damage < 3  && r1.Damage > 0)
			{
				//no damage
				if( r1.Damage == 0)
				{
					Turn.setText("No Damage to Repair");
					
				}
				//accounts for the character with discounted repairs
				if(((character) playerChars.get(playersTurn)).SkillNum == 2)
				{
					//checks for enough money
					if( ((character) playerChars.get(playersTurn)).Bank >= 100)
					{
						generateCsvFile("C:\\Users\\salty\\Desktop\\Game.csv", roundS + "," ,(((character)playerChars.get(playersTurn)).Name), ",Repaired","");
						//removes the damage and the money from bank
						((character) playerChars.get(playersTurn)).Bank = ((character) playerChars.get(playersTurn)).Bank - 100;
						r1.Damage = r1.Damage - 1;
						Turn.setText("Damage Repaired");
					}
				}
				//checks for enough money
				else if( ((character) playerChars.get(playersTurn)).Bank >= 200)
				{
					generateCsvFile("C:\\Users\\salty\\Desktop\\Game.csv", roundS + "," ,(((character)playerChars.get(playersTurn)).Name), ",Repaired","");
					//removes the damage and the money from bank
					((character) playerChars.get(playersTurn)).Bank = ((character) playerChars.get(playersTurn)).Bank - 200;
					r1.Damage = r1.Damage - 1;
					Turn.setText("Damage Repaired");
				}
				//if no any moneys, inform player
				if(this.Bank < 200)
				{
					Turn.setText("Not Enough Money to Repair");
				}
				//display updated player card
				characterCard.setText(((character) playerChars.get(playersTurn)).charCard() );
				if(r1.Occupied == true)
				{
					//change display depending on damage to property and occupied status
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
					//change display depending on damage to property and occupied status
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
