
public abstract class Action {
	public Furniture furn;
//	public int preference;
	private State preconditions;
	private State deleteList;
	private State addList;
	
	public Action(Furniture f)
	{
		furn = f;
	}
	
	public State getPreCond()
	{
		return preconditions;
	}
	
	public State getDelList()
	{
		return deleteList;
	}
	
	public State getAddList()
	{
		return addList;
	}

	public void setPreCond(State pcond) {
		preconditions = pcond;
	}

	public void setDelList(State dlist) {
		deleteList = dlist;
	}

	public void setAddList(State alist) {
		addList = alist;
	}
}
