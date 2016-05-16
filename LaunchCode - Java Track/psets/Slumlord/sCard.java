package Slumlord;

import java.util.ArrayList;
import java.util.Random;

public class sCard {
	
	protected String Description;
	protected int Damage;
	protected boolean LoseTenant;
	protected int WhichProperty;
	
	private static ArrayList<sCard> all;
	private static ArrayList<sCard> used;
	
	public sCard( String Desc, int Damage, boolean LoseTenant, int Which)
	{
		this.Description = Desc;
		this.Damage = Damage;
		this.LoseTenant = LoseTenant;
		this.WhichProperty = Which;
	}
	
	public sCard Draw()
	{
		if(all.size() == 0)
		{
			all = used;
		}
		Random generator = new Random();
		sCard Drawn = new sCard("",0,false,0);
		int j = generator.nextInt(all.size())+1;
		Drawn = all.get(j);
		all.remove(j);
		used.add(all.get(j));
		
		return Drawn;
	}

	public static void main(String args[])
	{
		sCard a = new sCard("Tenant Arrested", 0,true, 0);
		all.add(a);
		sCard b = new sCard("Meth Lab Explosion", 0,true, 1);
		all.add(b);
		
	}
	
}