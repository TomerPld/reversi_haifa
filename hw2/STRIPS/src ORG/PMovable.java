
public class PMovable extends Predicate {
	public Move direction;
	
	public PMovable(int id, boolean sign, Move dir)
	{
		super(id, sign);
		direction = dir;
	}
	
	public boolean equals(PMovable p)
	{
		if(	(getFurnId() == p.getFurnId()) && (getSign() == p.getSign()) &&
			(direction == p.direction))
			return true;
		return false;
	}
	
	public int getFurnId()
	{
		return super.furn_id;
	}
	
	public boolean getSign()
	{
		return super.sign;
	}
	
}
