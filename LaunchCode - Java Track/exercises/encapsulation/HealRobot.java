package encapsulation;

public class HealRobot extends Robot{
	
	private int range;
	private int healAmount;

	public HealRobot(String name, int positionX, int positionY, int speed, char orientation)
	{
		super(name, positionX, positionY, speed, orientation);
		this.range = 15;
		this.healAmount = 7;	
	}
	
	public boolean heal(Robot a)
	{
		if(Math.abs(this.distance(a)) <= this.range)
		{
			a.health = a.health + this.healAmount;
			return true;
		}
		else
			return false;
	}
	public String toString()
	{
		return "Name: " + this.name + " Position X: " + this.positionX + " Position Y: " + this.positionY + " Orientation: " + this.orientation + " Speed: " + this.speed
				+ " Healing Amount: " + this.healAmount + " Healing Range: "+ this.range + " Health: "+ this.health;
	}
	public static void main(String args[])
	{
		AttackRobot Killer = new AttackRobot("Killer", 0, 0, 10, 'N', "Blaster");
		Robot Vic = new Robot("Vic", 4,5,10,'E');
		HealRobot Nurse = new HealRobot("Nurse", 10, 10 ,20, 'S');
		System.out.println(Vic);
		Killer.attack(Vic);
		System.out.println(Vic);
		Nurse.heal(Vic);
		System.out.println(Vic);
	}
}
