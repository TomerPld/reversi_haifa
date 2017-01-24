package core;

public class PEmpty extends Predicate {
	private int x;
	private int y;
	
	public PEmpty(int y, int x)	{
		super();
		this.x = x;
		this.y = y;
	}
	
	public int getX() {
		return this.x;
	}

	public int getY() {
		return this.y;
	}

	public boolean equals(PEmpty p) {
		if(x == p.x && y == p.y)
			return true;
		return false;
	}
	
	@Override
	public String toString() {
		return "Empty(" + y + ", " + x + ")";
	}
}
