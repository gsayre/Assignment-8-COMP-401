package a8adept;

import comp401.sushi.Plate;

public interface DecoratedPlate extends Plate {
	public int getAge();
	public void ageUp();
	public Plate unwrapPlate();
}
