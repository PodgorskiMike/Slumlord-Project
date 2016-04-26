
public class BaseballPlayer {
	private int jersey;
	private String stance;
	private int games;
	private int hits;
	private int runs;
	private int rbi;
	private int hitsToday;
	private int runsToday;
	private int rbiToday;
	
	public BaseballPlayer(int jersey, String stance, int games, int hits, int runs, int rbi, int hitsToday, int runsToday, int rbiToday)
	{
		this.jersey = jersey;
		this.stance = stance;
		this.games = games;
		this.hits = hits;
		this.runs = runs;
		this.rbi = rbi;
		this.hitsToday = hitsToday;
		this.runsToday = runsToday;
		this.rbiToday = rbiToday;
	}
	public void todayStats(int H, int R, int Rb)
	{
		this.hitsToday = H;
		this.runsToday = R;
		this.rbiToday = Rb;
		this.hits = this.hits + H;
		this.runs = this.runs + R;
		this.rbi = this.rbi + Rb;
		this.games = this.games + 1;
		System.out.println("Player #" + this.jersey + " had " + this.hitsToday + " hits, " + this.runsToday + " runs, and " + this.rbiToday + " Rbi's in todays game");
		System.out.println("Their career stats in " + this.games + " games as a " + this.stance + " are " + this.hits + " hits, " + this.runs + " runs, and " + this.rbi + " Rbi's");
	}
	
	
	public static void main(String args[])
	{
		BaseballPlayer player1 = new BaseballPlayer(47, "righty", 0,0,0,0,0,0,0);
		player1.todayStats(2,2,2);
		player1.todayStats(1,2,1);
		player1.todayStats(3,2,3);
		
	}
	
}
