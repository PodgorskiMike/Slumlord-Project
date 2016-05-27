package Slumlord;

import java.util.ArrayList;
import java.util.Random;

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
	
	/**public sCard Draw()
	{
		if(allE.size() == 0)
		{
			allE = usedE;
		}
		Random generator = new Random();
		eCard Drawn = new eCard("",0,false,100,0,"");
		int j = generator.nextInt(allE.size())+1;
		Drawn = allE.get(j);
		allE.remove(j);
		usedE.add(allE.get(j));
		
		//return Drawn;
	}
	*/
	public static void main(String args[])
	{
		
	}
	
}