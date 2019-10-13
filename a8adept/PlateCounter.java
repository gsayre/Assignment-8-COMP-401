package a8adept;
import java.util.Observable;
import java.util.Observer;

import a8adept.PlateEvent.EventType;
import comp401.sushi.RedPlate;
import comp401.sushi.GreenPlate;
import comp401.sushi.Plate;
import comp401.sushi.BluePlate;
import comp401.sushi.GoldPlate;


public class PlateCounter implements java.util.Observer {
	
	private Belt b;
	private int redPlateCount = 0;
	private int greenPlateCount = 0;
	private int bluePlateCount = 0;
	private int goldPlateCount = 0;

	public PlateCounter(Belt b)  {
		if (b == null) {
			throw new IllegalArgumentException();
		}
		this.b = b;
		
		b.addObserver(this);
		for (int i = 0; i < b.getSize(); i++) {
			if (!(b.getPlateAtPosition(i) == null)) {
				if(b.getPlateAtPosition(i).getColor().equals(Plate.Color.RED)) {
					redPlateCount++;
				}
				if(b.getPlateAtPosition(i).getColor().equals(Plate.Color.GREEN)) {
					greenPlateCount++;
				}
				if(b.getPlateAtPosition(i).getColor().equals(Plate.Color.BLUE)) {
					bluePlateCount++;
				}
				if(b.getPlateAtPosition(i).getColor().equals(Plate.Color.GOLD)) {
					goldPlateCount++;
				}
			}
		}
	}
	
	// Update method that whenever a change occurs in the observable it notifies the observer
	// Output: updates the observers
	public void update(Observable o, Object arg) {
		b = (Belt) o;
		
		PlateEvent whatHappened = (PlateEvent) arg;
		
		if (whatHappened.getType().equals(EventType.PLATE_PLACED)) {
			if(whatHappened.getPlate().getColor().equals(Plate.Color.RED)) {
				redPlateCount++;
			}
			if(whatHappened.getPlate().getColor().equals(Plate.Color.GREEN)) {
				greenPlateCount++;
			}
			if(whatHappened.getPlate().getColor().equals(Plate.Color.BLUE)) {
				bluePlateCount++;
			}
			if(whatHappened.getPlate().getColor().equals(Plate.Color.GOLD)) {
				goldPlateCount++;
			}
		} else {
			if(whatHappened.getPlate().getColor().equals(Plate.Color.RED)) {
				redPlateCount--;
			}
			if(whatHappened.getPlate().getColor().equals(Plate.Color.GREEN)) {
				greenPlateCount--;
			}
			if(whatHappened.getPlate().getColor().equals(Plate.Color.BLUE)) {
				bluePlateCount--;
			}
			if(whatHappened.getPlate().getColor().equals(Plate.Color.GOLD)) {
				goldPlateCount--;
			}
		}
		
	}
	
	// Simple getter for the red plate count int
	// Output: int red plate count
	public int getRedPlateCount() {
		return redPlateCount;
	}
	
	// Simple getter for the green plate count int
	// Output: int green plate count
	public int getGreenPlateCount() {
		return greenPlateCount;
	}
	
	// Simple getter for the blue plate count int
	// Output: int blue plate count
	public int getBluePlateCount() {
		return bluePlateCount;
	}
	
	// Simple getter for the gold plate count int
	// Output: gold red plate count
	public int getGoldPlateCount() {
		return goldPlateCount;
	}
}
