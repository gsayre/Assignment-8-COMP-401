package a8jedi;

import java.util.Observable;
import java.util.Observer;
import a8jedi.PlateEvent.EventType;
import comp401.sushi.IngredientPortion;


public class SpoilageCollector implements java.util.Observer {
	
	private Belt b;
	private double spoiledCost;
	private double spoiledShellfish;
	private double spoiledSeafood;
	private double spoiledFood;
	
	public SpoilageCollector(Belt b) {
		if (b == null) {
			throw new IllegalArgumentException();
		}
		this.b = b;
		
		b.addObserver(this);
	}

	@Override
	public void update(Observable o, Object arg) {
		b = (Belt) o;
		PlateEvent plateEvent = (PlateEvent) arg;
		IngredientPortion[] ingredients = plateEvent.getPlate().getContents().getIngredients();

		if (plateEvent.getType() == EventType.PLATE_SPOILED) {
			b.removePlateAtPosition(plateEvent.getPosition());
			spoiledCost += plateEvent.getPlate().getContents().getCost();
			for (int i = 0; i < ingredients.length; i++) {
				if (ingredients[i].getName().equals("crab") || ingredients[i].getName().equals("shrimp")) {
					spoiledShellfish += ingredients[i].getAmount();
					spoiledSeafood += ingredients[i].getAmount();
					spoiledFood += ingredients[i].getAmount();
				}
				if (ingredients[i].getName().equals("salmon") || ingredients[i].getName().equals("tuna") || ingredients[i].getName().equals("eel")) {
					spoiledSeafood += ingredients[i].getAmount();
					spoiledFood += ingredients[i].getAmount();
				}
				if (ingredients[i].getName().equals("avocado") ) {
					spoiledFood += ingredients[i].getAmount();
				}
			}

		}
	}

	public double getTotalSpoiledCost() {
		return spoiledCost;
	}
	
	public double getTotalSpoiledShellfish() {
		return spoiledShellfish;
	}
	
	public double getTotalSpoiledSeafood() {
		return spoiledSeafood;
	}
	
	public double getTotalSpoiledFood() {
		return spoiledFood;
	}
}
