package Slumlord;

import javax.swing.JButton;

public class property {
	
	protected int Hood;
	protected int Color;
	protected boolean Occupied;
	protected boolean Owned;
	protected String OwnedBy;
	protected int Upgrades;
	protected int Damage;
	protected JButton ButtonName;
	protected int TenantNotMatch;
	
	
	public property(int Hood, int Color, int Upgrades)
	{
		this.Hood = Hood;
		this.Color = Color;
		this.Occupied = false;
		this.Owned = false;
		this.OwnedBy = "";
		this.Upgrades = Upgrades;
		this.Damage = 0;
		JButton btnDiscard = null;
		this.ButtonName = btnDiscard;
		this.TenantNotMatch = 1;
	}
	
	
	public String toString()
	{
		return "" + this.Damage + "";
	}
	
	public static void main(String args[])
	{
	}
}
