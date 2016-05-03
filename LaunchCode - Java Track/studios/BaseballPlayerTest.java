import static org.junit.Assert.*;

import org.junit.Test;

public class BaseballPlayerTest {

	@Test
	public void testconstructor() 
	{
		BaseballPlayer a = new BaseballPlayer(47, "righty", 0,0,0,0,0,0,0);
		assertTrue(a.getgames() == 0);
		assertTrue(a.gethits() == 0);
		assertTrue(a.getruns() == 0);
		assertTrue(a.getrbi() == 0);
		assertTrue(a.gethitsToday() == 0);
		assertTrue(a.getrunsToday() == 0);
		assertTrue(a.getrbiToday() == 0);
	}
	@Test
	public void testtodayStatsNew() 
	{
		BaseballPlayer a = new BaseballPlayer(47, "righty", 0,0,0,0,0,0,0);
		a.todayStats(2,2,2);
		assertTrue(a.getgames() == 1);
		assertTrue(a.gethits() == 2);
		assertTrue(a.getruns() == 2);
		assertTrue(a.getrbi() == 2);
		assertTrue(a.gethitsToday() == 2);
		assertTrue(a.getrunsToday() == 2);
		assertTrue(a.getrbiToday() == 2);
	}
	@Test
	public void testtodayStatsAdd() 
	{
		BaseballPlayer a = new BaseballPlayer(47, "righty", 1,3,3,3,2,2,2);
		a.todayStats(2,2,2);
		assertTrue(a.getgames() == 2);
		assertTrue(a.gethits() == 5);
		assertTrue(a.getruns() == 5);
		assertTrue(a.getrbi() == 5);
		assertTrue(a.gethitsToday() == 2);
		assertTrue(a.getrunsToday() == 2);
		assertTrue(a.getrbiToday() == 2);
	}

}
