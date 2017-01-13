import java.util.LinkedList;


public class AMove extends Action {
	public Move direction;
	
	public AMove(Furniture furn, Move dir)
	{
		super(furn);
		LinkedList<Predicate> list;
		PPosition p;
		
		list = new LinkedList<Predicate>();
		list.add(new PMovable(furn.id, false, dir));
		super.setPreCond(new State(list));
		
		list = new LinkedList<Predicate>();
		list.add(new PPosition(furn.id, false, furn.cornerx, furn.cornery, furn.width, furn.length, furn.state));
		super.setDelList(new State(list));
				
		list = new LinkedList<Predicate>();
		p = new PPosition(furn.id, false, furn.cornerx, furn.cornery, furn.width, furn.length, furn.state);
		switch (dir) {
		case RIGHT:
			p.x++;
			break;
		case DOWN:
			p.y++;
			break;
		case LEFT:
			p.x--;
			break;
		case UP:
			p.y--;
			break;
		}
		list.add(p);
		super.setAddList(new State(list));
				
		direction = dir;
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
