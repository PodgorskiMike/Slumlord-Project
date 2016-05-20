package Slumlord;

public class Trial {

	public Trial(property r1, ArrayList playerChars, int playersTurn, int phase)
	{
		if(r1.OwnedBy.equals(playerChars.get(playersTurn).Color)&& phase == 4)
		{
			String resp = playerChars.get(playersTurn).repair(r1);
			Turn.setText(resp);
			if(r1.Damage == 0)
			{
				btnRedpropone.setText("<html>  <br> O <br> </html>");
				characterCard.setText(playerChars.get(playersTurn).charCard() );
			}
			if(r1.Damage == 1)
			{
				btnRedpropone.setText("<html> X <br> O <br> </html>");
				characterCard.setText(playerChars.get(playersTurn).charCard() );
			}
			if(r1.Damage == 2)
			{
				btnRedpropone.setText("<html> XX <br> O <br> </html>");
				characterCard.setText(playerChars.get(playersTurn).charCard() );
			}
			if(r1.Damage == 3)
			{
				btnRedpropone.setText("<html> XXX <br> O <br> </html>");
				characterCard.setText(playerChars.get(playersTurn).charCard() );
			}
		}
	}
}
