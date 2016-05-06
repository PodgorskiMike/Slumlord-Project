package encapsulation;

public class AttackRobot extends Robot{
	
	private String weapon;
	private int damage;
	
	public AttackRobot(String name, int positionX, int positionY, int speed, char orientation, String weapon)
	{
		super(name, positionX, positionY, speed, orientation);
		this.damage = 11;
		this.weapon = weapon;	
	}
	public boolean attack(Robot a)
	{
		if(Math.abs(this.distance(a)) <= 10)
		{
			a.health = a.health - this.damage;
			if(a.health <= 0)
			{
				System.out.println("The Robot called " + a.name + " was destroyed");
			}
			return true;
		}
		else
			return false;
	}
	
	public String toString()
	{
		return "Name: " + this.name + " Position X: " + this.positionX + " Position Y: " + this.positionY + " Orientation: " + this.orientation + " Speed: " + this.speed
				+ " Weapon: " + this.weapon + " Damage: "+ this.damage + " Health: "+ this.health;
	}
	public static void main(String args[])
	{
		AttackRobot Killer = new AttackRobot("Killer", 0, 0, 10, 'N', "Blaster");
		Robot Vic = new Robot("Vic", 4,5,10,'E');
		Killer.attack(Vic);
		System.out.println(Vic);
	}
}
