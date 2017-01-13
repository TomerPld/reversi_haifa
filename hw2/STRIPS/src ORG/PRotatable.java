
public class PRotatable extends Predicate {
	public boolean clockwise;
	
	public PRotatable(int id, boolean sign, boolean cw)
	{
		super(id, sign);
		clockwise = cw;
	}
	
	public boolean equals(PRotatable p)
	{
		if(	(getFurnId() == p.getFurnId()) && (getSign() == p.getSign()) &&
			(clockwise == p.clockwise))
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
