import java.util.LinkedList;


public class AMove extends Action {
	public Move direction;
	
	// Constructor, gets: furn - the furn on which the action is applied, dir - the move direction
	public AMove(Furniture furn, Move dir)
	{
		super(furn);
		int i;
		LinkedList<Predicate> list1, list2;
		PPosition pp = new PPosition(furn.id, furn.cornerx, furn.cornery, furn.width, furn.length, furn.state);

		list1 = new LinkedList<Predicate>();
		list1.add(new PPosition(furn.id, furn.cornerx, furn.cornery, furn.width, furn.length, furn.state));
				
		list2 = new LinkedList<Predicate>();

		switch (dir) {
		case RIGHT:
			pp.x++;
			for(i = 0; i < furn.length; i++)
			{
				list1.add(new PEmpty(furn.cornery + i, furn.cornerx + furn.width));
				list2.add(new PEmpty(furn.cornery + i, furn.cornerx));
			}
			break;
		case DOWN:
			pp.y++;
			for(i = 0; i < furn.width; i++)
			{
				list1.add(new PEmpty(furn.cornery + furn.length, furn.cornerx + i));
				list2.add(new PEmpty(furn.cornery, furn.cornerx + i));
			}
			break;
		case LEFT:
			pp.x--;
			for(i = 0; i < furn.length; i++)
			{
				list1.add(new PEmpty(furn.cornery + i, furn.cornerx - 1));
				list2.add(new PEmpty(furn.cornery + i, furn.cornerx + furn.width - 1));
			}
			break;
		case UP:
			pp.y--;
			for(i = 0; i < furn.width; i++)
			{
				list1.add(new PEmpty(furn.cornery - 1, furn.cornerx + i));
				list2.add(new PEmpty(furn.cornery + furn.length - 1, furn.cornerx + i));
			}
			break;
		}
			
		super.setPreCond(new State(list1));
		super.setDelList(new State(list1));
		
		list2.add(pp);
		super.setAddList(new State(list2));
				
		direction = dir;
	}
	
	// Returns the action's string representation
	@Override
	public String toString()
	{
		return "Move " + direction + " " + super.furn; 
	}
	
	// Returns whether this (moving action) equals to am (given moving action)
	public boolean equals(AMove am)
	{
		if((furn.id == am.furn.id) && (direction == am.direction))
			return true;
		return false;
	}

//	@Override
//	public int compareTo(Action a) {
//		if(super.preference < a.preference)
//			return -1;
//		else if(super.preference > a.preference)
//			return 1;
//		return 0;
//	}
//	
//	public void setPreference(int pref)
//	{
//		super.preference = pref;
//	}
//	
//	public int getPreference()
//	{
//		return super.preference;
//	}
}
