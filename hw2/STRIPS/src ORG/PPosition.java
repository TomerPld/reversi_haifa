public class PPosition extends Predicate {
	public int x;
	public int y;
	public int width;
	public int length;
	public Move state;

	public PPosition(int id, boolean sign, int x, int y, int width, int length,	Move state) {
		super(id, sign);
		this.x = x;
		this.y = y;
		this.width = width;
		this.length = length;
		this.state = state;
	}

	public int getFurnId()
	{
		return super.furn_id;
	}
	
	public boolean getSign()
	{
		return super.sign;
	}
	
	
	public boolean equals(PPosition p)
	{
		if(	(getFurnId() == p.getFurnId()) && (getSign() == p.getSign()) &&
			(x == p.x) && (y == p.y) && (width == p.width) && (length == p.length) &&
			(state == p.state))
			return true;
		return false;
	}
	
//	public void printf()
//	{
//		System.out.println("id" + super.furn_id + "sign: " + super.sign + "x: " + x);
//	}
}
