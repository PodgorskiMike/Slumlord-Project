import static org.junit.Assert.*;

import org.junit.Test;

public class FractionTest {

	@Test
	public void testadd() 
	{
		Fraction a = new Fraction(7.77,7.0, 0.77);
		assertTrue(a.add(2.33) == 10.10);
	}

}
