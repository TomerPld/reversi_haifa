package core;

public class Furniture {
	public int cornerx;
	public int cornery;
	public int length;
	public int width;
	public int id;
	public Direction state;
	
	// Constructor - gets: x - distance from left, y - distance from top, wid - width, len - length, identifier - used in Hashtable, s - state(RIGHT/DOWN/LEFT/UP)
	public Furniture(int x, int y, int wid, int len, int identifier, Direction s) {
		cornerx = x;
		cornery = y;
		length	= len;
		width	= wid;
		id		= identifier;
		state	= s;
	}
	
	// Copy constructor
	public Furniture(Furniture f) {
		cornerx = f.cornerx;
		cornery = f.cornery;
		length	= f.length;
		width	= f.width;
		id		= f.id;
		state	= f.state;
	}
	
	// Rotates the furniture cw/ccw and updates its fields
	public void rotate(RotationDirection clockwise) {
		double x,y;
		int temp;
		
		x = cornerx + (width/2.0) - (length/2.0);
		y = cornery + (length/2.0) - (width/2.0);
		
		if((width + length)%2 == 1) {
			switch (state) {
			case RIGHT:
				if(RotationDirection.CW.equals(clockwise)) {
					x -= 0.5;
					y += 0.5;
				}
				else {
					x -= 0.5;
					y -= 0.5;
				}
				break;
			case DOWN:
				if(RotationDirection.CW.equals(clockwise)) {
					x -= 0.5;
					y -= 0.5;
				}
				else {
					x += 0.5;
					y -= 0.5;
				}
				break;
			case LEFT:
				if(RotationDirection.CW.equals(clockwise)) {
					x += 0.5;
					y -= 0.5;
				}
				else {
					x += 0.5;
					y += 0.5;
				}
				break;
			case UP:
				if(RotationDirection.CW.equals(clockwise)) {
					x += 0.5;
					y += 0.5;
				}
				else {
					x -= 0.5;
					y += 0.5;
				}
				break;
			}
		}
		
		switch (state) {
			case RIGHT:
				state = RotationDirection.CW.equals(clockwise) ? Direction.DOWN : Direction.UP;
				break;
			case DOWN:
				state = RotationDirection.CW.equals(clockwise) ? Direction.LEFT : Direction.RIGHT;
				break;
			case LEFT:
				state = RotationDirection.CW.equals(clockwise) ? Direction.UP : Direction.DOWN;
				break;
			case UP:
				state = RotationDirection.CW.equals(clockwise) ? Direction.RIGHT : Direction.LEFT;
				break;
		}

		cornerx = (int) x;
		cornery = (int) y;
		
		temp = width;
		width = length;
		length = temp;
	}
	
	// Returns whether this (furniture) equals furn (given furniture)
	public boolean equals(Furniture furn) {
		if(	(cornerx != furn.cornerx) || (cornery != furn.cornery) || (width != furn.width)	||
			(length	 != furn.length)  || (id	  != furn.id)	   || (state != furn.state)    )
			return false;
		return true;
	}
	
	// Returns the furniture's string representation
	@Override
	public String toString()
	{
		return "Furniture(" + id + ": " + cornerx + ", " + cornery + ", " + width + ", " + length + ", " + state + ")";
	}
}
