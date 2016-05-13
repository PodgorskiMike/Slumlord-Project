package Slumlord;

import java.util.Random;

public class TenantTokens {
	
	protected int RedTenant;
	protected int BlueTenant;
	protected int GreenTenant;

	public TenantTokens()
	{
		this.RedTenant = 35;
		this.BlueTenant = 28;
		this.GreenTenant = 20;
	}
	
	public TenantTokens RandomDraw(TenantTokens a, int round)
	{
		int i = 0;
		if(round == 1)
			i = 8;
		if(round == 2)
			i = 8;
		if(round == 3)
			i = 8;
		if(round == 4)
			i = 8;
		if(round == 5)
			i = 8;
		if(round == 6)
			i = 8;
		
		TenantTokens avaliable = new TenantTokens();
		avaliable.RedTenant = 0;
		avaliable.BlueTenant = 0;
		avaliable.GreenTenant = 0;
		while (i >= 0)
		{
			Random generator = new Random();
			int j = generator.nextInt(3)+1;
			if( j == 1 && a.RedTenant > 0)
			{
				avaliable.RedTenant = avaliable.RedTenant + 1;
				a.RedTenant = a.RedTenant -1;
				i--;
			}
			if( j == 2 && a.BlueTenant > 0)
			{
				avaliable.BlueTenant = avaliable.BlueTenant + 1;
				a.BlueTenant = a.BlueTenant -1;
				i--;
			}
			if( j == 3 && GreenTenant > 0)
			{
				avaliable.GreenTenant = avaliable.GreenTenant + 1;
				a.GreenTenant = a.GreenTenant -1;
				i--;
			}
		}
		return avaliable;	
	}
}

