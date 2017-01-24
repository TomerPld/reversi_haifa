package core;
import java.util.LinkedList;

public class State {
	public int identifier;
	public LinkedList<Predicate> pList;
	
	// Constructor - initialize list to be empty
	public State()
	{
		pList = new LinkedList<Predicate>();
	}
	
	// Copy constructor
	public State(State s)
	{
		pList = new LinkedList<Predicate>(s.pList);
	}
	
	// Constructor, gets: l - a list of predicates
	public State(LinkedList<Predicate> l)
	{
		pList = new LinkedList<Predicate>(l);
	}
	
	// Removes predicate p from its list
	public void removePredicate(Predicate p)
	{
		for(int i = 0; i < pList.size(); i++)
			if(pList.get(i).getClass() == p.getClass())
				if(p.getClass() == PPosition.class) {
					PPosition tmp = (PPosition) pList.get(i);
					if(tmp.equals((PPosition)p))
					{
						pList.remove(i);
						return;
					}
				} else if(p.getClass() == PEmpty.class) {
					PEmpty tmp = (PEmpty) pList.get(i);
					if(tmp.equals((PEmpty)p))
					{
						pList.remove(i);
						return;
					}
				}
	}

	// Adds predicate p to its list
	public void addPredicate(Predicate p) {
		pList.add(p);
	}

	// Subtracts (Removes) the given list of predicates from its own
	public State subtract(State s) {
		for(int i = 0; i < s.pList.size(); i++)
			removePredicate(s.pList.get(i));
		return this;
	}
	
	// Appends the given list of predicates to its own
	public State addState(State s) {
		for(int i = 0; i < s.pList.size(); i++)
			addPredicate(s.pList.get(i));
		return this;
	}
	
	// Returns the index in which a Predicate is found which equals the given predicate p (if not found - returns (-1))
	public int contains(Predicate p) {
		Predicate pr;
		for(int i = 0; i < pList.size(); i++)
			if((pr = pList.get(i)).getClass() == p.getClass()) {
				if(p.getClass() == PPosition.class)
					if(((PPosition)p).equals((PPosition)pr))
						return i;
				if(p.getClass() == PEmpty.class)
					if(((PEmpty)p).equals((PEmpty)pr))
						return i;
			}
		return -1;
	}
	
	public int contains(int furnitureId) {
		Predicate pr;
		for(int i = 0; i < pList.size(); i++) {
			if(((pr = pList.get(i)).getClass() == PPosition.class) && (((PPosition)pr).getFurniture().id == furnitureId)) {
				return i;
			}
		}
		return -1;
	}
	
	// Returns the state's string represntation (as a list of predicates)
	@Override
	public String toString()
	{
		String str = new String("State: ");
		int i = 0;
		while(i < pList.size() - 1)
			str = str + pList.get(i++).toString() + " & ";
		
		if(!pList.isEmpty())
			str = str + pList.get(i).toString();
		return str;
	}
}
