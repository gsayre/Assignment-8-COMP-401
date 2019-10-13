package a8test;

import org.junit.Test;
import static org.junit.Assert.*;

import a8adept.*;
import comp401.sushi.BluePlate;
import comp401.sushi.Plate;
import comp401.sushi.PlatePriceException;
import comp401.sushi.Sashimi;
import comp401.sushi.Sashimi.SashimiType;

public class A8AdeptTest {

	@Test
	public void testOneRotation() throws PlatePriceException, BeltPlateException {
		Belt b = new Belt(10);
		
		Plate p = new BluePlate(new Sashimi(Sashimi.SashimiType.CRAB));
		
		b.setPlateAtPosition(p,  0);
		
		assertEquals(0, b.getAgeOfPlateAtPosition(0));
		b.rotate();
		assertEquals(-1, b.getAgeOfPlateAtPosition(0));
		assertEquals(1, b.getAgeOfPlateAtPosition(1));
	}

	@Test
	public void testManyRotations() throws PlatePriceException, BeltPlateException {
		Belt b = new Belt(10);
		
		Plate p = new BluePlate(new Sashimi(Sashimi.SashimiType.CRAB));
		
		b.setPlateAtPosition(p,  0);
		
		int large_num_of_rotations = 239;
		for (int i=0; i<large_num_of_rotations; i++) {
			b.rotate();
		}
		assertEquals(239, b.getAgeOfPlateAtPosition(9));		
	}
	
	@Test
	public void testGetSamePlateBack() throws PlatePriceException, BeltPlateException {
		Belt b = new Belt(10);
		Plate p = new BluePlate(new Sashimi(Sashimi.SashimiType.CRAB));

		b.setPlateAtPosition(p,  0);
		b.rotate();
		assertEquals(p, b.getPlateAtPosition(1));		
	}
	
	@Test
	public void testReuseSamePlate() throws PlatePriceException, BeltPlateException {
		Belt b = new Belt(10);
		Plate p = new BluePlate(new Sashimi(Sashimi.SashimiType.CRAB));
		
		b.setPlateAtPosition(p, 0);
		b.rotate();
		p = b.getPlateAtPosition(1);
		b.clearPlateAtPosition(1);
		b.setPlateAtPosition(p, 5);
		b.rotate();
		assertEquals(1, b.getAgeOfPlateAtPosition(6));
	}
	
	@Test
	public void testManyPlatesDifferentAges() throws PlatePriceException, BeltPlateException {
		Belt b = new Belt(10);
		
		// Load belt up one plate at a time
		for (int i=0; i<10; i++) {
			Plate p = new BluePlate(new Sashimi(SashimiType.CRAB));
			b.setPlateAtPosition(p, 0);
			b.rotate();
		}
		
		// Check ages
		for (int i=1; i<10; i++) {
			assertEquals(i, b.getAgeOfPlateAtPosition(i));
		}
		assertEquals(10, b.getAgeOfPlateAtPosition(0));
	}
}
