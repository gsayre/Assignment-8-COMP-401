package a8adept;

import a8adept.PlateEvent.EventType;
import comp401.sushi.Plate;

public class PlateEvent {
	public enum EventType {PLATE_PLACED, PLATE_REMOVED};
	
	private EventType type;
	private Plate plate;
	private int position;
	
	public PlateEvent(EventType type, Plate plate, int position) {
		this.type = type;
		this.plate = plate;
		this.position = position;
	}
	
	// Simple getter for the EventType object
	// Output: EventType object
	public EventType getType() {
		return type;
	}
	
	// Simple getter for the Plate object
	// Output: Plate object
	public  Plate getPlate() {
		return plate;
	}
	
	// Simple getter for the position
	// Output: int position
	public int getPosition() {
		return position;
	}
}