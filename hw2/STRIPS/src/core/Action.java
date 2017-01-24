package core;

public abstract class Action {
	private Furniture furniture;
	private State preconditions;
	private State deleteList;
	private State addList;
	
    /**
     * Action object consturcotr, based on a given furniture.
     * @param furniture
     */
	public Action(Furniture furniture) {
		this.furniture = furniture;
	}
	
	public Furniture getFurniture() {
		return this.furniture;
	}

	// Returns the action's precondition list
	public State getPreCond() {
		return preconditions;
	}

	// Set a new precondition list to the action
	public void setPreCond(State preconditions) {
		this.preconditions = preconditions;
	}
	
	// Returns the action's delete list
	public State getDelList() {
		return deleteList;
	}

	// Set a new delete list to the action
	public void setDelList(State deleteList) {
		this.deleteList = deleteList;
	}
	
	// Returns the action's add list
	public State getAddList() {
		return addList;
	}

	// Set a new add list to the action
	public void setAddList(State addList) {
		this.addList = addList;
	}
}
