package Slumlord;

import java.util.Random;

public class dice {

	protected int Blue;
	protected int Red;
	protected int Yellow;
	protected int Blue2;
	
	public dice()
	{
		this.Blue = 0;
		this.Red = 0;
		this.Yellow = 0;
		this.Blue2 = 0;
	}
		
		public dice Roll(int Numdice)
		{
			dice Results = new dice();
			//dice Results2 = new dice()
			
			Random generator = new Random();
			for(int i = Numdice; i > 0; i++)
			{
				int j = generator.nextInt(6)+1;
				if(j == 1)
					this.Yellow = this.Yellow + 1;
				if(j == 2)
					this.Red = this.Red + 1;
				if(j == 3)
					this.Blue = this.Blue + 1;
				if(j == 4)
					this.Red = this.Red + 1;
				if(j == 5)
					this.Blue = this.Blue + 1;
				if(j == 6)
					this.Blue = this.Blue + 2;
			}
			//Not sure how reroll will be implemeted
			/**if(this.Yellow < Numdice)
			{
				int k = Numdice - this.Yellow;
				System.out.println("You can reroll up to " + k + " dice");
				System.out.println("Would you like to reroll?");
				//Get input
				System.out.println("How Many");
				//Get input Q
				Results2 = this.Roll(int Q);
				
			}
		Results.Yellow = Results.Yellow + Results2.Yellow;
		**/
		return Results;
		}
				
	}
