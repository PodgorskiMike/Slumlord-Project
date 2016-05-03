import static org.junit.Assert.*;

import org.junit.Test;

public class RectangleTest {

	@Test
	public void testarea() 
	{
		Rectangle a = new Rectangle(4,4);
		assertTrue(a.area() == 16);
	}
	@Test
	public void testperimeter() 
	{
		Rectangle a = new Rectangle(4,4);
		assertTrue(a.perimeter() == 16);
	}

	@Test
	public void testbigger() 
	{
		Rectangle a = new Rectangle(4,4);
		assertTrue(a.bigger(5,5) == false);
	}
}
