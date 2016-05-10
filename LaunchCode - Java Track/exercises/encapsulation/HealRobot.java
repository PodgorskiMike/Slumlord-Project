package encapsulation;

import java.util.Random;

public class HealRobot extends Robot implements RobotBehavior{
	
	private int range;
	private int healAmount;

	public HealRobot(String name, int positionX, int positionY, int speed, char orientation)
	{
		super(name, positionX, positionY, speed, orientation);
		this.range = 20;
		this.healAmount = 20;	
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
	
	@Override
	public boolean doNextMove(Robot a, Robot b) {
		int q = Math.abs(this.distance(a));
		int w = Math.abs(this.distance(b));
		if(q > w)
		{
			if(this.range >= Math.abs(this.distance(b)))
			{
				System.out.println (Math.abs(this.distance(b)));
				this.heal(b);
				System.out.println(this.name + " Healed " + b.name + " for " + this.healAmount);
				return true;
			}
		}
		if(w > q)
		{
			if(this.range >= Math.abs(this.distance(a)))
			{
				System.out.println (Math.abs(this.distance(a)));
				this.heal(a);
				System.out.println(this.name + " Healed " + a.name + " for " + this.healAmount);
				return true;
			}
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
	return false;
	}
}
