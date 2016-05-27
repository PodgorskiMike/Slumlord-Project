package Slumlord;

import java.util.ArrayList;
import java.util.Random;

public class repairCard {
	
	protected String Description;
	protected int Damage;
	protected int WhichProperty;
	protected String Which;
	
	private static ArrayList<repairCard> all;
	private static ArrayList<repairCard> used;
	
	public repairCard( String Desc, int Damage, int Whichprop, String which)
	{
		this.Description = Desc;
		this.Damage = Damage;
		this.WhichProperty = Whichprop;
		this.Which = which;
		
	}
	
	/**public repairCard Draw()
	{
		if(all.size() == 0)
		{
			all = used;
		}
		Random generator = new Random();
		repairCard Drawn = new repairCard("",0,0,"");
		int j = generator.nextInt(all.size())+1;
		Drawn = all.get(j);
		all.remove(j);
		used.add(all.get(j));
		
		return Drawn;
	}
*/
	public static void main(String args[])
	{
		repairCard a = new repairCard("Test", 1, 0,"");
		all.add(a);
		repairCard b = new repairCard("Test2", 2, 1,"");
		all.add(b);
		
	}
	
}
