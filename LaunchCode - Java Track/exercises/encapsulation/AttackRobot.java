package encapsulation;

import java.util.Random;

public class AttackRobot extends Robot implements RobotBehavior{
	
	private String weapon;
	private int damage;
	private int wRange;
	
	public AttackRobot(String name, int positionX, int positionY, int speed, char orientation, String weapon, int damage, int wRange)
	{
		super(name, positionX, positionY, speed, orientation);
		this.damage = damage;
		this.weapon = weapon;
		this.wRange = wRange;
	}
	public boolean attack(Robot a)
	{
		if(Math.abs(this.distance(a)) <= this.wRange)
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
	
	@Override
	public boolean doNextMove(Robot a, Robot b) {
		if(this.wRange >= Math.abs(this.distance(a)))
		{
			System.out.println (Math.abs(this.distance(a)));
			this.attack(a);
			System.out.println(this.name + " Attacked for " + this.damage);
			return true;
		}
		else if(this.wRange >Math.abs(this.distance(a))- this.speed)
		{
			System.out.println(Math.abs(this.distance(a)));
			this.move(true);
			this.attack(a);
			System.out.println(this.name + " Moved Forward and attacks");
			return true;
		}
		else
		{
			Random num = new Random();
			boolean RorL = num.nextBoolean();
			this.rotate(RorL);
			this.move(true);
			System.out.println(this.name + " Rotated and Moved");
			return true;
		}
	
	}
	public static void main(String args[])
	{
		AttackRobot Killer = new AttackRobot("Killer", -30, -30, 10, 'N', "Blaster", 7, 15);
		AttackRobot Vic = new AttackRobot("Vic", 30,30,10,'S',"Ray Gun", 10, 8);
		HealRobot Sue= new HealRobot("Sue", 0, 0, 5, 'W');
		while(Vic.health > 0 && Killer.health > 0)
		{
			System.out.println(Killer.name + "s Move");
			Killer.doNextMove(Vic, Killer);
			System.out.println(Killer);
			System.out.println(Vic);
			System.out.println(Vic.name + "s Move");
			Vic.doNextMove(Killer, Vic);
			System.out.println(Killer);
			System.out.println(Vic);
			System.out.println(Sue.name + "s Move");
			Sue.doNextMove(Killer, Vic);
			System.out.println(Killer);
			System.out.println(Vic);
			
		}
	}
}
