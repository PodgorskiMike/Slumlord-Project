package Slumlord;

public class property {
	
	private String Hood;
	protected int Color;
	private boolean Occupied;
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

}
