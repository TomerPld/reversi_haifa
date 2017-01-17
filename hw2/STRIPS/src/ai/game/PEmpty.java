package ai.game;

public class PEmpty extends Predicate {
	public int x;
	public int y;
	
	// Constructor, gets: x - distance from left, y - distance from top
	public PEmpty(int y, int x)
	{
		super();
		this.x = x;
		this.y = y;
	}
	
	// Returns whether this (empty spot predicate) equals to p (given empty spot predicate)
	public boolean equals(PEmpty p)
	{
		if(x == p.x && y == p.y)
			return true;
		return false;
	}
	
	// Returns the predicate's string representation
	@Override
	public String toString()
	{
		return "Empty(" + y + ", " + x + ")";
	}
}
