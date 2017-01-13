import java.util.LinkedList;

public class State {
	public LinkedList<Predicate> pList;
	
	public State()
	{
		pList = new LinkedList<Predicate>();
	}
	
	public State(State s)
	{
		pList = new LinkedList<Predicate>(s.pList);
	}
	
	public State(LinkedList<Predicate> l)
	{
		pList = new LinkedList<Predicate>(l);
	}
	
	public void removePredicate(Predicate p)
	{
		for(int i = 0; i < pList.size(); i++)
//			System.out.println("pList.get("+i+").getClass = " + pList.get(i).getClass());
			if(pList.get(i).getClass() == p.getClass())
				if(p.getClass() == PPosition.class) {
					PPosition tmp = (PPosition) pList.get(i);
					if(tmp.equals((PPosition)p))
					{
						pList.remove(i);
						return;
					}
				} else if(p.getClass() == PMovable.class) {
					PMovable tmp = (PMovable) pList.get(i);
					if(tmp.equals((PMovable)p))
					{
						pList.remove(i);
						return;
					}
				} else if(p.getClass() == PRotatable.class) {
					PRotatable tmp = (PRotatable) pList.get(i);
					if(tmp.equals((PRotatable)p))
					{
						pList.remove(i);
						return;
					}
				}
	}

	public void addPredicate(Predicate p) {
		pList.add(p);
	}

	public void subtract(State s) {
		for(int i=0; i < s.pList.size(); i++)
		{
			removePredicate(s.pList.get(i));
		}
	}
	
	public void addState(State s) {
		for(int i=0; i < s.pList.size(); i++)
		{
			addPredicate(s.pList.get(i));
		}
	}
}
