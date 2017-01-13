public class PPosition extends Predicate {
	public int furn_id;
	public int x;
	public int y;
	public int width;
	public int length;
	public Move state;

	// Constructor - gets: furn_id - represented furniture's identifier, x - distance from left, y - distance from top, width - width, length - length, state - RIGHT/DOWN/LEFT/UP
	public PPosition(int furn_id, int x, int y, int width, int length, Move state) {
		super();
		this.furn_id = furn_id;
		this.x = x;
		this.y = y;
		this.width = width;
		this.length = length;
		this.state = state;
	}

	// Returns whether this (position predicate) equals to p (given position predicate)
	public boolean equals(PPosition p)
	{
		if(	(furn_id == p.furn_id) && (x == p.x) && (y == p.y) &&
			(width == p.width) && (length == p.length) && (state == p.state))
			return true;
		return false;
	}
	
	// Returns the predicate's string representation
	@Override
	public String toString()
	{
		return "Position(" + furn_id + ": " + x + ", " + y + ", " + width + ", " + length + ", " + state + ")";
	}
	
//	public void printf()
//	{
//		System.out.println("id" + super.furn_id + "sign: " + super.sign + "x: " + x);
//	}
}
