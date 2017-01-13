import javax.swing.JTextArea;



public class Furniture {
	public int cornerx;
	public int cornery;
	public int length;
	public int width;
	public int id;
	public Move state;
	
	public Furniture(int x, int y, int wid, int len, int identifier, Move s)
	{
		cornerx = x;
		cornery = y;
		length	= len;
		width	= wid;
		id		= identifier;
		state	= s;
	}
	
	public Furniture(Furniture f)
	{
		cornerx = f.cornerx;
		cornery = f.cornery;
		length	= f.length;
		width	= f.width;
		id		= f.id;
		state	= f.state;
	}
	
	public void rotate(boolean clockwise)
	{
		double x,y;
		int temp;
		
		x = cornerx + (width/2.0) - (length/2.0);
		y = cornery + (length/2.0) - (width/2.0);
		
//		System.out.println("cornerx = " + cornerx + ", cornery = " + cornery + ", width = " + width + ", length = " + length);
//		System.out.println("(B-rotate) X= " + x + " Y= " + y);
		
		if((width + length)%2 == 1)
		{
			switch (state) {
			case RIGHT:
				if(clockwise)
				{
					x -= 0.5;
					y += 0.5;
					state = Move.DOWN;
				}
				else
				{
					x -= 0.5;
					y -= 0.5;
					state = Move.UP;
				}
//				x -= factor;
//				y += factor;
//				state = clockwise ? Move.LEFT : Move.RIGHT;
				break;
			case DOWN:
				if(clockwise)
				{
					x -= 0.5;
					y -= 0.5;
					state = Move.LEFT;
				}
				else
				{
					x += 0.5;
					y -= 0.5;
					state = Move.RIGHT;
				}
//				x -= factor;
//				y -= factor;
//				state = clockwise ? Move.LEFT : Move.RIGHT;
				break;
			case LEFT:
				if(clockwise)
				{
					x += 0.5;
					y -= 0.5;
					state = Move.UP;
				}
				else
				{
					x += 0.5;
					y += 0.5;
					state = Move.DOWN;
				}
//				x += factor;
//				y -= factor;
//				state = clockwise ? Move.UP : Move.DOWN;
				break;
			case UP:
				if(clockwise)
				{
					x += 0.5;
					y += 0.5;
					state = Move.RIGHT;
				}
				else
				{
					x -= 0.5;
					y += 0.5;
					state = Move.LEFT;
				}
//				x += factor;
//				y += factor;
//				state = clockwise ? Move.RIGHT : Move.LEFT;
				break;
			default:
				break;
			}
		}
		
//		System.out.println("(A-rotate) X= " + x + " Y= " + y);
		
		cornerx = (int) x;
		cornery = (int) y;
		
		temp = width;
		width = length;
		length = temp;
	}
	
	public boolean equals(Furniture furn)
	{
		if(	(cornerx != furn.cornerx) || (cornery != furn.cornery) || (width != furn.width)	||
			(length	 != furn.length)  || (id	  != furn.id)	   || (state != furn.state)    )
			return false;
		return true;
	}
}
