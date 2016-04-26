package encapsulation;

public class Robot {
	private String name;
	private int positionX;
	private int positionY;
	private int speed;
	private char orientation;
	
	public Robot(String name, int positionX, int positionY, int speed, char orientation)
	{
		this.name = name;
		this.positionX = positionX;
		this.positionY = positionY;
		this.speed = speed;
		this.orientation = orientation;
		
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
		public void rotate(boolean r)
		{
			if(this.orientation == 'N')
			{
				if(r == true)
					orientation = 'E';
				if(r == false)
					orientation = 'W';
			}
			if(this.orientation == 'E')
			{
				if(r == true)
					orientation = 'S';
				if(r == false)
					orientation = 'N';
			}
			if(this.orientation == 'S')
			{
				if(r == true)
					orientation = 'W';
				if(r == false)
					orientation = 'E';
			}
			if(this.orientation == 'W')
			{
				if(r == true)
					orientation = 'N';
				if(r == false)
					orientation = 'S';
			}	
		}
		
		public int distance(Robot other)
		{
			int xdistance = this.positionX - other.positionX;
			int ydistance = this.positionY - other.positionY;
			int total = xdistance + ydistance;
			return total;
		}
		
		public String toString()
		{
			return "Name: " + this.name + " Position X: " + this.positionX + " Position Y: " + this.positionY + " Orientation: " + this.orientation + " Speed: " + this.speed;
		}

		public static void main(String args[])
		{
			Robot myRobot = new Robot("Vikki", 0,0,5,'N');
			System.out.println(myRobot);
			Robot yourRobot = new Robot("Sam", 5, 5, 5, 'S');
			System.out.println(yourRobot);
		}
}

