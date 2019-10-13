package a8jedi;

public class BeltFullException extends Exception {

	private Belt belt;

	public BeltFullException(Belt belt) {
		this.belt = belt;
	}
	
	// Simple getter for the belt object
	// Output: Belt object
	public Belt getBelt() {
		return belt;
	}
}
