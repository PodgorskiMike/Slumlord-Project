package encapsulation;

import static org.junit.Assert.*;

import org.junit.Test;

public class RobotTester {

	@Test
	public void testMoveN() {
		Robot a = new Robot("A",0,0,5,'N');
		assertTrue(a.getpositionX() == 0);
		assertTrue(a.getpositionY() == 0);
		a.move(true);
		assertTrue(a.getpositionX() == 5);
		assertTrue(a.getpositionY() == 0);
		
	}
	@Test
	public void testMoveE() {
		Robot a = new Robot("A",0,0,5,'E');
		assertTrue(a.getpositionX() == 0);
		assertTrue(a.getpositionY() == 0);
		a.move(true);
		assertTrue(a.getpositionX() == 0);
		assertTrue(a.getpositionY() == 5);
		
	}
	@Test
	public void testMoveS() {
		Robot a = new Robot("A",0,0,5,'S');
		assertTrue(a.getpositionX() == 0);
		assertTrue(a.getpositionY() == 0);
		a.move(true);
		assertTrue(a.getpositionX() == -5);
		assertTrue(a.getpositionY() == 0);
		
	}
	@Test
	public void testMoveW() {
		Robot a = new Robot("A",0,0,5,'W');
		assertTrue(a.getpositionX() == 0);
		assertTrue(a.getpositionY() == 0);
		a.move(true);
		assertTrue(a.getpositionX() == 0);
		assertTrue(a.getpositionY() == -5);
		
	}
	
	@Test
	public void testRotateN() {
		Robot a = new Robot("A",0,0,5,'N');
		assertTrue("Not Set",a.getorientation() == 'N');
		a.rotate(false);
		assertTrue("Not proper rotate",a.getorientation() == 'W');
		
	}
	@Test
	public void testRotateS() {
		Robot a = new Robot("A",0,0,5,'S');
		assertTrue("Not Set",a.getorientation() == 'S');
		a.rotate(true);
		assertTrue("Not proper rotate",a.getorientation() == 'W');
		
	}
	@Test
	public void testDistanceX() {
		Robot a = new Robot("A",0,0,5,'S');
		Robot b = new Robot("B",5,0,5,'N');
		int d = a.distance(b);
		System.out.println(d);
		assertTrue(d == 5);
			
	}
	@Test
	public void testDistanceY() {
		Robot a = new Robot("A",0,10,5,'S');
		Robot b = new Robot("B",0,0,5,'N');
		int d = a.distance(b);
		System.out.println(d);
		assertTrue(d == -10);
			
	}

}
