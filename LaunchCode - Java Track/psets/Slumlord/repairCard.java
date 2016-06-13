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

}
