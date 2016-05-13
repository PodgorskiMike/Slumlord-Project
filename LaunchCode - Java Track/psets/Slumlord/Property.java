package Slumlord;

public class Property {
	
	private String Hood;
	protected int Color;
	private boolean Occupied;
	protected boolean Owned;
	protected String OwnedBy;
	protected int Upgrades;
	protected int Damage;
	
	
	public Property(String Hood, int Color)
	{
		this.Hood = Hood;
		this.Color = Color;
		this.Occupied = false;
		this.Owned = false;
		this.OwnedBy = "";
		this.Upgrades = 0;
		this.Damage = 0;
	}

}
