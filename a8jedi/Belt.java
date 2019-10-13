package a8jedi;

import java.util.NoSuchElementException;

import a8jedi.PlateEvent.EventType;
import comp401.sushi.Plate;

public class Belt extends java.util.Observable{

	private DecoratedPlate belt[];
	
	public Belt(int size) {
		if (size < 1) {
			throw new IllegalArgumentException("Belt size must be greater than zero.");
		}

		belt = new DecoratedPlate[size];
	}

	// Simple getter for the size parameter
	// Output: int size
	public int getSize() {
		return belt.length;
	}

	// Sets the given plate at the given position
	// Output: void (but changes the array)
	public void setPlateAtPosition(Plate plate, int position) throws BeltPlateException {
		position = normalizePosition(position);

		if (plate == null) {
			throw new IllegalArgumentException("Plate is null");
		}

		if (belt[position] != null) {
			throw new BeltPlateException(position, plate, this);
		}
		setChanged();
		PlateEvent plateEvent = new PlateEvent(EventType.PLATE_PLACED, plate, position);
		notifyObservers(plateEvent);
		belt[position] = new DecoratedPlateImpl(this, plate);
	}

	// Returns the plate at a certain given position on the belt
	// Output: Plate object
	public Plate getPlateAtPosition(int position) {
		position = normalizePosition(position);
		
		return  ((DecoratedPlate) belt[position]).unwrapPlate();
	}

	// Turns the plate at the given position to a null value
	// Output: void (but changes the array)
	public void clearPlateAtPosition(int position) {
		position = normalizePosition(position);
		belt[position] = null;
	}

	// Removes the plate at the given position and then returns it 
	// Output: the removed plate
	public Plate removePlateAtPosition(int position) {
		Plate plate =  getPlateAtPosition(position);
		if (plate == null) {
			throw new NoSuchElementException();
		}
		clearPlateAtPosition(position);
		setChanged();
		PlateEvent plateEvent = new PlateEvent(EventType.PLATE_REMOVED, plate, position);
		notifyObservers(plateEvent);
		return plate;
	}

	// Sets the plate to the position nearest to given position if it is not taken
	// Output: int, the position at which the plate was set
	public int setPlateNearestToPosition(Plate plate, int position) throws BeltFullException {
		for (int i=0; i < getSize(); i++) {
			try {
				setPlateAtPosition(plate, position);
				setChanged();
				PlateEvent plateEvent = new PlateEvent(EventType.PLATE_PLACED, plate, position);
				notifyObservers(plateEvent);
				return normalizePosition(position);
			} catch (BeltPlateException e) {
				position += 1;
			}
		}
		throw new BeltFullException(this);
	}
	
	// Getter for the age field of the decorated plate object
	// Output: int, age of the plate
	public int getAgeOfPlateAtPosition(int position) {
		if (belt[position] == null) {
			return -1;
		}
		
		return belt[position].getAge();
	}

	// Makes sure belt indexes that are out of bounds are normalized in the booundaries
	// Output: int within the bounds of the array
	private int normalizePosition(int position) {
		int normalized_position = position % getSize();

		if (position < 0) {
			normalized_position += getSize();
		}

		return normalized_position;
	}

	// Rotates the belt and notifies the observers
	// Output: void (new rotated belt)
	public void rotate() {
		DecoratedPlate last_plate = belt[getSize()-1];
		for (int i = getSize()-1; i > 0; i--) {
			belt[i] = belt[i-1];
		}
		belt[0] = last_plate;
		
		for (int j = 0; j < getSize(); j++) {
			if (belt[j] == null) {
				continue;
			}
			belt[j].ageUp();
			
		}
		for (int k = 0; k < getSize(); k++) {
			if (belt[k] == null) {
				continue;
			}
			if (belt[k].getSpoiled()) {
				setChanged();
				PlateEvent plateEvent = new PlateEvent(EventType.PLATE_SPOILED, belt[k].unwrapPlate(), k);
				notifyObservers(plateEvent);	
			}
		}
		
	}
}
