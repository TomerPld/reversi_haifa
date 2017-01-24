package core;

public abstract class Predicate {
	private int id;
	// TODO check if readable
	private int listSize;

	public Predicate(){}

	public int getListSize() {
		return this.listSize;
	}

	public void setListSize(int listSize) {
		this.listSize = listSize;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
