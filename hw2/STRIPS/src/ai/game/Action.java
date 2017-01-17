package ai.game;

public abstract class Action {
	public Furniture furn;
	private State preconditions;
	private State deleteList;
	private State addList;
	
	// Constructor, gets: f - the furniture on which the action is applied
	public Action(Furniture f)
	{
		furn = f;
	}
	
	// Returns the action's precondition list
	public State getPreCond()
	{
		return preconditions;
	}
	
	// Returns the action's delete list
	public State getDelList()
	{
		return deleteList;
	}
	
	// Returns the action's add list
	public State getAddList()
	{
		return addList;
	}

	// Set a new precondition list to the action
	public void setPreCond(State pcond) {
		preconditions = pcond;
	}

	// Set a new delete list to the action
	public void setDelList(State dlist) {
		deleteList = dlist;
	}

	// Set a new add list to the action
	public void setAddList(State alist) {
		addList = alist;
	}
}
