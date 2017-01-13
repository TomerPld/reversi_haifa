import java.util.LinkedList;

public class ARotate extends Action {
	public boolean clockwise;

	public ARotate(Furniture furn, boolean cw) {
		super(furn);
		LinkedList<Predicate> list;

		list = new LinkedList<Predicate>();
		list.add(new PRotatable(furn.id, false, cw));
		super.setPreCond(new State(list));

		list = new LinkedList<Predicate>();
		list.add(new PPosition(furn.id, false, furn.cornerx, furn.cornery,
				furn.width, furn.length, furn.state));
		super.setDelList(new State(list));

		list = new LinkedList<Predicate>();
		Furniture temp = new Furniture(furn);
		temp.rotate(cw);
		list.add(new PPosition(temp.id, false, temp.cornerx, temp.cornery,
				temp.width, temp.length, temp.state));
		super.setAddList(new State(list));

		clockwise = cw;
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
