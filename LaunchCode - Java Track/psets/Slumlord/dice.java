package Slumlord;

import java.util.Random;

public class dice {

	protected int Green;
	protected int Red;
	protected int Blue;
	
	
	public dice(int red, int blue, int green)
	{
		this.Green = green;
		this.Red = red;
		this.Blue = blue;
		
	}
		
		public dice Roll(int Numdice)
		{
			dice Results = new dice(0,0,0);
			this.Red = 0;
			this.Blue = 0;
			this.Green = 0;
			//dice Results2 = new dice()
			
			Random generator = new Random();
			for(int i = 0; i < Numdice; i++)
			{
				int j = generator.nextInt(6)+1;
				if(j == 1)
					this.Blue = this.Blue + 1;
				if(j == 2)
					this.Red = this.Red + 1;
				if(j == 3)
					this.Green = this.Green + 1;
				if(j == 4)
					this.Red = this.Red + 1;
				if(j == 5)
					this.Green = this.Green + 1;
				if(j == 6)
					this.Green = this.Green + 2;
			}
			
			Results = this;
		return Results;
		}
		
		public String toString()
		{
			return this.Red + " " + this.Blue + " " + this.Green;
		}
		
		public static void main(String args[])
		{
			
			
		}
		
	}


