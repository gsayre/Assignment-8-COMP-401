package a8test;

import org.junit.Test;
import static org.junit.Assert.*;

import comp401.observable.Observable;
import comp401.observable.Observer;

import org.junit.Before;

import a8jedi.*;
import comp401.sushi.*;

public class A8JediTestCommon {

	private static void full_rotate_belt(Belt b) {
		for (int i=0; i<b.getSize(); i++) {
			b.rotate();
		}
	}

	private Belt b;
	private Sushi crab_sashimi;
	private Plate crab_plate;
	private Sushi salmon_sashimi;
	private Plate salmon_plate;
	private Sushi veggie_roll;
	private Plate veggie_plate;
	private Plate crab_plate2;
	
	@Before
	public void setup() throws PlatePriceException {
		b = new Belt(10);
		crab_sashimi = new Sashimi(Sashimi.SashimiType.CRAB);
		crab_plate = new BluePlate(crab_sashimi);		
		crab_plate2 = new BluePlate(crab_sashimi);		
		salmon_sashimi = new Sashimi(Sashimi.SashimiType.SALMON);
		salmon_plate = new BluePlate(salmon_sashimi);
		veggie_roll = new Roll("veggie_roll",
				new IngredientPortion[] {
						new RicePortion(0.25),
						new AvocadoPortion(0.25),
						new SeaweedPortion(0.25)
		});

		veggie_plate = new BluePlate(veggie_roll);
	}
	
	
	@Test
	public void testSpoilageCollectorRemovesPlate() throws PlatePriceException, BeltPlateException {
		SpoilageCollector sc = new SpoilageCollector(b);
		b.setPlateAtPosition(crab_plate, 0);
		full_rotate_belt(b);
		assertNull(b.getPlateAtPosition(0));
	}
	
	@Test
	public void testSpoilageCollectorMultiplePlatesDifferentExpirations() throws PlatePriceException, BeltPlateException {
		SpoilageCollector sc = new SpoilageCollector(b);
		b.setPlateAtPosition(crab_plate, 0);
		b.setPlateAtPosition(salmon_plate, 1);
		b.setPlateAtPosition(veggie_plate, 2);
		full_rotate_belt(b);
		assertEquals(0.75,sc.getTotalSpoiledShellfish(), 0.01);
		assertEquals(0.75, sc.getTotalSpoiledSeafood(), 0.01);
		assertEquals(0.75, sc.getTotalSpoiledFood(), 0.01);
		assertEquals(0.5625, sc.getTotalSpoiledCost(), 0.01);
		
		full_rotate_belt(b);
		assertEquals(0.75,sc.getTotalSpoiledShellfish(), 0.01);
		assertEquals(1.5, sc.getTotalSpoiledSeafood(), 0.01);
		assertEquals(1.5, sc.getTotalSpoiledFood(), 0.01);
		assertEquals(1.1025, sc.getTotalSpoiledCost(), 0.01);

		full_rotate_belt(b);
		assertEquals(0.75,sc.getTotalSpoiledShellfish(), 0.01);
		assertEquals(1.5, sc.getTotalSpoiledSeafood(), 0.01);
		assertEquals(2.25, sc.getTotalSpoiledFood(), 0.01);		
		assertEquals(1.925, sc.getTotalSpoiledCost(), 0.01);
	}
	
	@Test
	public void testSpoilageCollectorMultiplePlatesSameExpiration() throws PlatePriceException, BeltPlateException {
		SpoilageCollector sc = new SpoilageCollector(b);

		b.setPlateAtPosition(veggie_plate, 0);
		full_rotate_belt(b);
		assertEquals(0, sc.getTotalSpoiledCost(), 0.01);
		assertEquals(0,sc.getTotalSpoiledShellfish(), 0.01);
		assertEquals(0, sc.getTotalSpoiledSeafood(), 0.01);
		assertEquals(0, sc.getTotalSpoiledFood(), 0.01);

		b.setPlateAtPosition(salmon_plate, 1);
		full_rotate_belt(b);
		assertEquals(0, sc.getTotalSpoiledCost(), 0.01);
		assertEquals(0,sc.getTotalSpoiledShellfish(), 0.01);
		assertEquals(0, sc.getTotalSpoiledSeafood(), 0.01);
		assertEquals(0, sc.getTotalSpoiledFood(), 0.01);
		
		b.setPlateAtPosition(crab_plate, 2);
		full_rotate_belt(b);
		assertEquals(1.925, sc.getTotalSpoiledCost(), 0.01);
		assertEquals(0.75,sc.getTotalSpoiledShellfish(), 0.01);
		assertEquals(1.5, sc.getTotalSpoiledSeafood(), 0.01);
		assertEquals(2.25, sc.getTotalSpoiledFood(), 0.01);
	}
}

