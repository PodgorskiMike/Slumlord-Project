package Slumlord;

public class property {
	
	protected String Hood;
	protected int Color;
	protected boolean Occupied;
	protected boolean Owned;
	protected String OwnedBy;
	protected int Upgrades;
	protected int Damage;
	
	
	public property(String Hood, int Color)
	{
		this.Hood = Hood;
		this.Color = Color;
		this.Occupied = false;
		this.Owned = false;
		this.OwnedBy = "";
		this.Upgrades = 0;
		this.Damage = 0;
	}

	public String toString()
	{
		return "" + this.Damage + "";
	}
	
	public static void main(String args[])
	{
	}
}
