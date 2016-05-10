import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class Question {
	private String Q;
	private String A;
	private String W;
	private String X;
	private String Y;
	private String Z;
	
	
	private static Scanner scan;
	private static ArrayList<Question> all;
	
	
	public Question(String q, String a, String w, String x, String y, String z)
	{
		this.Q = q;
		this.A = a;
		this.W = w;
		this.X = x;
		this.Y = y;
		this.Z = z;
	}
	
	public static boolean ask()
	{
		scan = new Scanner(System.in);
		String m = "";
		
		int r = ThreadLocalRandom.current().nextInt(0, 3);
		Question n = all.get(r);
		System.out.println(n.Q);
		if(n.X != m)
		{
			System.out.println(n.W);
			System.out.println(n.X);
			System.out.println(n.Y);
			System.out.println(n.Z);
		}
		System.out.println("Your Answer:");
		String input = scan.nextLine();
		if(input.equals(n.A))
		{
			System.out.println("Correct!");
			return true;
		}
		else
		{
			System.out.println("Wrong!");
			return false;
			
		}
	}
	public static void main(String args[])
	{
		all = new ArrayList<Question>();
		Question i = new Question("What is my name","Mike", "","","","");
		all.add(i);
		Question j = new Question("What game am I playing", "Dark Souls 3", "","","","");
		all.add(j);
		Question k = new Question("Do I like DS3", "Yes", "No", "Too Hard", "Yes", "Havent Played it");
		all.add(k);
		
		boolean right = ask();
		
	}
	
	
}
