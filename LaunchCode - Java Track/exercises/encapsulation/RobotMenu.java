package encapsulation;

import java.util.ArrayList;
import java.util.Scanner;

public class RobotMenu {

	private ArrayList<Robot> robots;
	private Scanner scan;
	
	public static void main(String[] args)
	{
		int x = 0;
		RobotMenu rm = new RobotMenu();
		do {
			x = rm.startMenu();
			rm.processInput(x);
		} while (x != 6);
	}
	
	public RobotMenu()
	{
		scan = new Scanner(System.in);
		robots = new ArrayList<Robot>();
	}
	
	public int startMenu()
	{
		System.out.println("Welcome to Fun With Robots!");
		System.out.println("1. Create a Robot");
		System.out.println("2. Display all Robots");
		System.out.println("3. Move a Robot");
		System.out.println("4. Rotate a Robot");
		System.out.println("5. Compute distance between 2 Robots");
		System.out.println("6. Exit");
		System.out.println("Please select an option:");
		int selection = scan.nextInt();
		while(selection < 0 || selection > 6)
		{
			System.out.println("Invalid Selection, Try Again:");
			selection = scan.nextInt();			
		}
		return selection;
	}
	
	public void processInput(int selection)
	{
		if(selection == 1)
		{
			createRobot();
		}
		else if(selection == 2)
		{
			displayRobots();
		}
		else if(selection == 3)
		{
			displayRobots();
			Robot a = selectRobot();
			a.move(true);
		}
		else if(selection == 4)
		{
			displayRobots();
			Robot a = selectRobot();
			System.out.println("Right ot Left?");
			String ans = scan.next();
			String right = "Right";
			String left = "Left";
			if(ans.equals(right))
			{
				a.rotate(true);
			}
			else if (ans.equals(left))
			
			{
				a.rotate(false);
			}
			else
			{
				System.out.println("Right or Left only");
				ans = scan.next();
			}
		}
		else if (selection == 5)
		{
			displayRobots();
			Robot a = selectRobot();
			Robot b = selectRobot();
			a.distance(b);
		}
		
	}
	
	
	private void displayRobots()
	{
		for(int i = 0; i < robots.size(); i++)
		{
			System.out.println((i+1) + ".)" + robots.get(i));
		}
	}
	
	private Robot selectRobot() {
		System.out.println("Choose a Robot:");
		int selection = scan.nextInt();
		while(selection < 1 || selection > robots.size())
		{
			System.out.println("Invalid, try again");
			selection = scan.nextInt();
		}
		return robots.get(selection - 1);
	}
	private void createRobot()
	{
		System.out.println("Enter the name of the new Robot:");
		String name = scan.next();
		System.out.println("Position on the X axis:");
		int x = scan.nextInt();
		System.out.println("Position on the Y axis:");
		int y = scan.nextInt();
		System.out.println("How fast is this Robot:(1-20):");
		int s = scan.nextInt();
		while(s < 1 || s > 20)
		{
			System.out.println("That wasnt between 1 and 20! Try again:");
			s = scan.nextInt();
		}
		System.out.println("What ditection is this Robot Facing? (N,E,S,W):");
		char d = scan.next().charAt(0);
		//if(d != 'N' || d != 'E' || d != 'S' || d != 'W')
		//{
		//	System.out.println("N,E,S or W only, Try Again:");
		//	d = scan.next().charAt(0);
		//}
		robots.add(new Robot(name, x,y,s,d));
	}
	
	
}
