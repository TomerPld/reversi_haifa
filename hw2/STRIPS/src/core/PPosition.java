package core;
public class PPosition extends Predicate {
	private Furniture furniture;

	// Constructor - gets: furn_id - represented furniture's identifier, x - distance from left, y - distance from top, width - width, length - length, state - RIGHT/DOWN/LEFT/UP
	public PPosition(Furniture furniture) {
		super();
		this.furniture = new Furniture(furniture);
	}

	public Furniture getFurniture() {
		return this.furniture;
	}

	public boolean equals(PPosition p) {
		return this.furniture.equals(p.getFurniture());
	}
	
	@Override
	public String toString()
	{
		return "Position(" + furniture.id + ": " + furniture.cornerx + ", " + furniture.cornery + ", " + furniture.width + ", " + furniture.length + ", " + furniture.state + ")";
	}
}
