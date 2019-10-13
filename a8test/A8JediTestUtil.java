package a8test;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.Observable;
import java.util.Observer;

import org.junit.Before;

import a8jedi.*;
import comp401.sushi.*;

public class A8JediTestUtil {

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
	public void testShellfishSpoil() throws PlatePriceException, BeltPlateException {
		b.setPlateAtPosition(crab_plate, 0);
		SpoiledEventObserver test_observer = new SpoiledEventObserver(b);
		full_rotate_belt(b);

		assertEquals(1, test_observer.getSpoiledEventCount());		
	}


	@Test
	public void testNonShellfishSeafoodSpoil() throws PlatePriceException, BeltPlateException {
		b.setPlateAtPosition(salmon_plate, 0);
		SpoiledEventObserver test_observer = new SpoiledEventObserver(b);
		full_rotate_belt(b);
		assertEquals(0, test_observer.getSpoiledEventCount());		
		full_rotate_belt(b);
		assertEquals(1, test_observer.getSpoiledEventCount());		
	}

	@Test
	public void testVegSpoil() throws PlatePriceException, BeltPlateException {
		b.setPlateAtPosition(veggie_plate, 0);
		SpoiledEventObserver test_observer = new SpoiledEventObserver(b);
		full_rotate_belt(b);
		assertEquals(0, test_observer.getSpoiledEventCount());		
		full_rotate_belt(b);
		assertEquals(0, test_observer.getSpoiledEventCount());		
		full_rotate_belt(b);
		assertEquals(1, test_observer.getSpoiledEventCount());		
	}

	@Test
	public void testDeliversMultipleSpoiledEvents() throws PlatePriceException, BeltPlateException {
		SpoiledEventObserver test_observer = new SpoiledEventObserver(b);

		b.setPlateAtPosition(crab_plate, 0);
		b.setPlateAtPosition(crab_plate2, 1);
		full_rotate_belt(b);
		assertEquals(2, test_observer.getSpoiledEventCount());
	}
	
	@Test
	public void testDeliversSpoiledEventsMultipleTimes() throws PlatePriceException, BeltPlateException {
		SpoiledEventObserver test_observer = new SpoiledEventObserver(b);
		
		b.setPlateAtPosition(crab_plate, 0);
		full_rotate_belt(b);
		assertEquals(1, test_observer.getSpoiledEventCount());
		b.rotate();
		assertEquals(2, test_observer.getSpoiledEventCount());
		b.clearPlateAtPosition(1);
		b.rotate();
		assertEquals(2, test_observer.getSpoiledEventCount());		
	}
	
	
	@Test
	public void testDeliveredPlateIsOriginal() throws PlatePriceException, BeltPlateException {
		SpoiledEventObserver test_observer = new SpoiledEventObserver(b);
		
		b.setPlateAtPosition(crab_plate, 0);
		full_rotate_belt(b);
		assertEquals(crab_plate, test_observer.getLastPlate());
	}
}

class SpoiledEventObserver implements Observer {
	private int spoiled_event_count;
	private Plate last_plate;
	
	public SpoiledEventObserver(Belt b) {
		spoiled_event_count = 0;
		last_plate = null;
		b.addObserver(this);
	}

	public Object getLastPlate() {
		return last_plate;
	}

	@Override
	public void update(Observable o, Object arg) {
		PlateEvent plate_event = (PlateEvent) arg;
		if (plate_event.getType() == PlateEvent.EventType.PLATE_SPOILED) {
			spoiled_event_count++;
			last_plate = plate_event.getPlate();
		}
	}

	public int getSpoiledEventCount() {
		return spoiled_event_count;
	}
}
