package a8adept;
import java.util.Observable;
import java.util.Observer;

import a8adept.PlateEvent.EventType;
import comp401.sushi.RedPlate;
import comp401.sushi.GreenPlate;
import comp401.sushi.Plate;
import comp401.sushi.BluePlate;
import comp401.sushi.GoldPlate;


public class ProfitCounter implements java.util.Observer {
	
	private Belt b;
	private double profit = 0.0;
	private int plateCount = 0;

	public ProfitCounter(Belt b)  {
		if (b == null) {
			throw new IllegalArgumentException();
		}
		this.b = b;
		
		b.addObserver(this);
		for (int i = 0; i < b.getSize(); i++) {
			if (!(b.getPlateAtPosition(i) == null)) {
				profit += b.getPlateAtPosition(i).getProfit();
				plateCount++;
			}	
		}
		
	}
	
	// Update method that changes the fields in the observer based on what happened in the observable
	// Ouput: changes the fields in the observer 
	public void update(Observable o, Object arg) {
		b = (Belt) o;
		
		PlateEvent whatHappened = (PlateEvent) arg;
		
		if (whatHappened.getType().equals(EventType.PLATE_PLACED)) {
			profit += whatHappened.getPlate().getProfit();
			plateCount++;
		} else {
			profit -= whatHappened.getPlate().getProfit();
			plateCount--;
		}
	}
		
	// Simple getter for total belt profit
	// Output: double, the total belt profit
	public double getTotalBeltProfit() {
		return profit;
	}
	
	// Getter for the calculated average belt profit
	// Output: double, the average belt profit
	public double getAverageBeltProfit() {
		if (b == null ) {
			return 0.0;
		}
		if (plateCount == 0) {
			return 0.0;
		}
		return profit / plateCount;
	}
	}
