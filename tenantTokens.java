package Slumlord;

import java.util.Random;

public class tenantTokens {
	
	protected int RedTenant; 
	protected int BlueTenant;
	protected int GreenTenant;

	public tenantTokens()
	{
		//create finite number of tenant tokens for each game
		this.RedTenant = 35;
		this.BlueTenant = 28;
		this.GreenTenant = 20;
	}
	
	public tenantTokens RandomDraw(tenantTokens a, int round, int players)
	{
		//set the amount of tenant tokens to be drawn for each round
		int i = 0;
		if (players == 2)
			{
			if(round == 1)
				i = 4;
			if(round == 2)
				i = 4;
			if(round == 3)
				i = 4;
			if(round == 4)
				i = 6;
			if(round == 5)
				i = 6;
			if(round == 6)
				i = 6;
			if(round == 7)
				i = 8;
			if(round == 8)
				i = 8;
			if(round == 9)
				i = 6;
			if(round == 10)
				i = 4;
			if(round == 11)
				i = 2;
			if(round == 12)
				i = 2;
			}
		if (players > 2)
		{
			i = 0;
			if(round == 1)
				i = 6;
			if(round == 2)
				i = 6;
			if(round == 3)
				i = 6;
			if(round == 4)
				i = 8;
			if(round == 5)
				i = 8;
			if(round == 6)
				i = 8;
			if(round == 7)
				i = 10;
			if(round == 8)
				i = 10;
			if(round == 9)
				i = 8;
			if(round == 10)
				i = 6;
			if(round == 11)
				i = 4;
			if(round == 12)
				i = 3;
		}
		
		//create blank slate for tenant tokens each round
		tenantTokens avaliable = new tenantTokens();
		avaliable.RedTenant = 0;
		avaliable.BlueTenant = 0;
		avaliable.GreenTenant = 0;
		
		//loop over random generator as may times as called for by above table
		while (i > 0)
		{
			//create random number generator between 1 and 3
			Random generator = new Random();
			int j = generator.nextInt(3)+1;
			if( j == 1 && a.RedTenant > 0)
			{
				//if selected, add one red to available, take one from total
				avaliable.RedTenant = avaliable.RedTenant + 1;
				a.RedTenant = a.RedTenant -1;
				i--;
			}
			if( j == 2 && a.BlueTenant > 0)
			{
				//if selected, add one Blue to available, take one from total
				avaliable.BlueTenant = avaliable.BlueTenant + 1;
				a.BlueTenant = a.BlueTenant -1;
				i--;
			}
			if( j == 3 && GreenTenant > 0)
			{
				//if selected, add one Blue to available, take one from total
				avaliable.GreenTenant = avaliable.GreenTenant + 1;
				a.GreenTenant = a.GreenTenant -1;
				i--;
			}
		}
		//return available for that round
		return avaliable;	
	}
}

