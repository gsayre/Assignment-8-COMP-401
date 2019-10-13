package a8jedi;

import comp401.sushi.Plate;

public class BeltPlateException extends Exception {

	private int position;
	private Plate plate;
	private Belt belt;
	
	public BeltPlateException(int position, Plate plate_to_be_set, Belt belt) {
		this.position = position;
		this.plate = plate;
		this.belt = belt;
	}
	
	// Simple getter for the position field
	// Output: int position
	public int getPosition() {
		return position;
	}

	// Simple getter for the plate objed
	// Output: plate object
	public Plate getPlateToSet() {
		return plate;
	}
	
	// Simple getter for the belt object
	// Output: belt object
	public Belt getBelt() {
		return belt;
	}

}
