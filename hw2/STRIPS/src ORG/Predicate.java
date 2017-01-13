
public abstract class Predicate {
	public int furn_id;
	public boolean sign;
	
	public Predicate(int id, boolean s){
		furn_id = id;
		sign = s;
	}
	
	public boolean equals(Predicate p)
	{
		if(furn_id == p.furn_id && sign == p.sign)
			return true;
		return false;
	}


}
