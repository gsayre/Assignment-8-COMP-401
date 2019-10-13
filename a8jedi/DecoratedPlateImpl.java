package a8jedi;

import comp401.sushi.Plate;
import comp401.sushi.PlatePriceException;
import comp401.sushi.Sushi;
import comp401.sushi.Plate.Color;

public class DecoratedPlateImpl implements DecoratedPlate {
	
	private Sushi contents;
	private double price;
	private Plate.Color color;
	private int age;
	private Belt belt;
	private Plate plate;
	private boolean spoiled;
	
	public DecoratedPlateImpl(Belt belt, Plate plate) {
		contents = null;
		this.age = 0;
		this.belt = belt;
		this.plate = plate;
	}
	
	public DecoratedPlateImpl(Sushi s, double price, Plate.Color color, Belt belt, Plate plate) throws PlatePriceException {
		this(belt, plate);
		setContents(s);
	}

	@Override
	public Sushi getContents() {
		return contents;
	}

	@Override
	public void setContents(Sushi s) throws PlatePriceException {
		if (s == null) {
			throw new IllegalArgumentException();
		}
		
		if (s.getCost() > getPrice()) {
			throw new PlatePriceException(this, s);
		}
		contents = s;
	}

	@Override
	public Sushi removeContents() {
		Sushi s = contents;
		contents = null;
		return s;
	}

	@Override
	public boolean hasContents() {
		return (contents != null);
	}

	@Override
	public double getPrice() {
		return price;
	}

	@Override
	public Color getColor() {
		return color;
	}

	@Override
	public double getProfit() {
		if (!hasContents()) {
			throw new UnsupportedOperationException("Profit undefined for empty plate");
		}
		return getPrice() - contents.getCost();
	}
	
	@Override
	public int getAge() {
		return age;
	}
	
	@Override
	public void ageUp() {
		age++;
	}
	
	@Override
	public Plate unwrapPlate() {
		return plate;
	}
	
	@Override
	public void isSpoiled() {
		if (plate.getContents().getHasShellfish()) {
			if (getAge() >= belt.getSize()) {
				spoiled = true;
			}
		} else if (!(plate.getContents().getIsVegetarian())) {
			if (getAge() >= (2 * (belt.getSize()))) {
				spoiled = true;
			}
		}
		if (plate.getContents().getIsVegetarian()) {
			if (getAge() >= (3 * (belt.getSize()))) {
				spoiled = true;
			}
		}
		spoiled = false;
	}
	
	@Override
	public boolean getSpoiled() {
		return spoiled;
	}

}
