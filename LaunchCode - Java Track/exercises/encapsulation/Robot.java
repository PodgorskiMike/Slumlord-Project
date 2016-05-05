package encapsulation;

public class Robot {
	protected String name;
	protected int positionX;
	protected int positionY;
	protected int speed;
	protected char orientation;
	protected int health;
	
	public Robot(String name, int positionX, int positionY, int speed, char orientation)
	{
		this.name = name;
		this.positionX = positionX;
		this.positionY = positionY;
		this.speed = speed;
		this.orientation = orientation;
		this.health = 10;
		
	}
	public void move(boolean mover)
	{
		if (this.orientation == 'N'&& mover == true)
		{
			positionX = positionX + this.speed;
		}
		if (this.orientation == 'E'&& mover == true)
		{
			positionY = positionY + this.speed;
		} 
		if (this.orientation == 'S'&& mover == true)
		{
			positionX = positionX - this.speed;
		}
		if (this.orientation == 'W'&& mover == true)
		{
			positionY = positionY - this.speed;
		} 
		else
		{
			return;
		}
	}
	public int getpositionX()
	{
		return this.positionX;
	}
	public int getpositionY()
	{
		return this.positionY;
	}
	public int getorientation()
	{
		return this.orientation;
	}
	
		public void rotate(boolean r)
		{
			if(this.orientation == 'N')
			{
				if(r == true)
					orientation = 'E';
				if(r == false)
					orientation = 'W';
			}
			else if(this.orientation == 'E')
			{
				if(r == true)
					orientation = 'S';
				if(r == false)
					orientation = 'N';
			}
			else if(this.orientation == 'S')
			{
				if(r == true)
					orientation = 'W';
				if(r == false)
					orientation = 'E';
			}
			else if(this.orientation == 'W')
			{
				if(r == true)
					orientation = 'N';
				if(r == false)
					orientation = 'S';
			}	
		}
		
		public int distance(Robot other)
		{
			int xdistance = other.positionX - this.positionX;
			int ydistance = other.positionY - this.positionY;
			int total = xdistance + ydistance;
			System.out.println("The distance between the two Robots is " + total);
			return total;
		}
		
		public String toString()
		{
			return "Name: " + this.name + " Position X: " + this.positionX + " Position Y: " + this.positionY + " Orientation: " + this.orientation + " Speed: " + this.speed+ " Health: "+ this.health;
		}

		public static void main(String args[])
		{
			Robot myRobot = new Robot("Vikki", 0,0,5,'N');
			System.out.println(myRobot);
			//Robot yourRobot = new Robot("Sam", 5, 5, 5, 'S');
			//System.out.println(yourRobot);
			myRobot.rotate(false);
			System.out.println(myRobot);
			
		}
}

