package Slumlord;

import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JLabel;

public class eCard {
	
	protected String Description;
	protected int Damage;
	protected boolean LoseTenant;
	protected int WhichProperty;
	protected String Which;
	protected int Money;
	
	private static ArrayList<eCard> allE;
	private static ArrayList<eCard> usedE;
	
	public eCard( String Desc, int Damage, boolean LoseTenant,int Money, int WhichProp, String Which)
	{
		this.Description = Desc;
		this.Damage = Damage;
		this.LoseTenant = LoseTenant;
		this.WhichProperty = WhichProp;
		this.Which = Which;
		this.Money = Money;
	}
	
	public void TakeMoney(int amountLost, int playerSelected, int players, ArrayList playerChars, dice currentRoll, JLabel diceResults, JButton btnDrawCard, JButton btnNextPhase, JLabel Turn)
	{
		// Amount lost 1 equals $1000
		if(amountLost == 1)
		{	//Iterate over all players 
			for(int i = 0; i < players; i++)
			{	//get Players Starting player position 
				int n = (((character)playerChars.get(i)).PlayerNumber);
				//if player is "player selected"
				if( n == playerSelected)
				{
					//Remove 1000 from their bank
					((character)playerChars.get(i)).Bank = ((character)playerChars.get(i)).Bank - 1000;
				}
			}
			//Remove 1 from Blue Die/Card Counter, Update die display
			currentRoll.Blue = currentRoll.Blue - 1;
			diceResults.setText("<html>Dice Roll Results" + "<br>" + "Red:" + currentRoll.Red + "<br>Blue:" + currentRoll.Blue + "<br>Green:" + currentRoll.Green + "</html>");
			//if there are no more blue cards to draw, make next phase visible
			if(currentRoll.Blue > 0)
			{
				btnDrawCard.setVisible(true);
			}
			else
			{
				btnNextPhase.setVisible(true);
				Turn.setText("<html>Click on Next Phase to Continue.<html>");
			}
		}
		// Amount lost 1 equals $500
		if(amountLost == 2)
		{	//Iterate over all players 
			for(int i = 0; i < players; i++)
			{	//get Players Starting player position
				int n = (((character)playerChars.get(i)).PlayerNumber);
				//if player is "player selected"
				if( n == playerSelected)
				{
					//Remove 500 from their bank
					((character)playerChars.get(i)).Bank = ((character)playerChars.get(i)).Bank - 500;
				}
			}
			//Remove 1 from Blue Die/Card Counter, Update die display
			currentRoll.Blue = currentRoll.Blue - 1;
			diceResults.setText("<html>Dice Roll Results" + "<br>" + "Red:" + currentRoll.Red + "<br>Blue:" + currentRoll.Blue + "<br>Green:" + currentRoll.Green + "</html>");
			//if there are no more blue cards to draw, make next phase visible
			if(currentRoll.Blue > 0)
			{
				btnDrawCard.setVisible(true);
			}
			else
			{
				btnNextPhase.setVisible(true);
				Turn.setText("<html>Click on Next Phase to Continue.<html>");
			}
		}
		// Amount lost 1 equals $200
		if(amountLost == 3)
		{
			//Iterate over all players
			for(int i = 0; i < players; i++)
				//get Players Starting player position 
			{
				int n = (((character)playerChars.get(i)).PlayerNumber);
				//if player is "player selected"
				if( n == playerSelected)
				{
					//Remove 200 from their bank
					((character)playerChars.get(i)).Bank = ((character)playerChars.get(i)).Bank - 200;
				}
			}
			//Remove 1 from Blue Die/Card Counter, Update die display
			currentRoll.Blue = currentRoll.Blue - 1;
			diceResults.setText("<html>Dice Roll Results" + "<br>" + "Red:" + currentRoll.Red + "<br>Blue:" + currentRoll.Blue + "<br>Green:" + currentRoll.Green + "</html>");
			//if there are no more blue cards to draw, make next phase visible
			if(currentRoll.Blue > 0)
			{
				btnDrawCard.setVisible(true);
			}
			else
			{
				btnNextPhase.setVisible(true);
				Turn.setText("<html>Click on Next Phase to Continue.<html>");
			}
		}
	}
		
};
