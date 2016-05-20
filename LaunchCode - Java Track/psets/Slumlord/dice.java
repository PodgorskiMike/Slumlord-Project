package Slumlord;

import java.util.Random;

public class dice {

	protected int Green;
	protected int Red;
	protected int Blue;
	
	
	public dice()
	{
		this.Green = 0;
		this.Red = 0;
		this.Blue = 0;
		
	}
		
		public dice Roll(int Numdice)
		{
			dice Results = new dice();
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
			//Not sure how reroll will be implemeted
			/**if(this.Blue < Numdice)
			{
				int k = Numdice - this.Blue;
				System.out.println("You can reroll up to " + k + " dice");
				System.out.println("Would you like to reroll?");
				//Get input
				System.out.println("How Many");
				//Get input Q
				Results2 = this.Roll(int Q);
				
			}
		Results.Blue = Results.Blue + Results2.Blue;
		**/
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


