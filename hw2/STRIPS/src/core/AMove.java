package core;
import java.util.LinkedList;


public class AMove extends Action {
	private Direction direction;
	
	// Constructor, gets: furn - the furn on which the action is applied, dir - the move direction
	public AMove(Furniture furniture, Direction direction) {
		super(furniture);
		this.direction = direction;
		LinkedList<Predicate> list1, list2;
		Furniture furn = this.getFurniture();
		PPosition pp = new PPosition(furn);

		list1 = new LinkedList<Predicate>();
		list1.add(new PPosition(furn));
				
		list2 = new LinkedList<Predicate>();

		switch (direction) {
		case RIGHT:
			pp.getFurniture().cornerx++;
			for(int i = 0; i < furn.length; i++)
			{
				list1.add(new PEmpty(furn.cornery + i, furn.cornerx + furn.width));
				list2.add(new PEmpty(furn.cornery + i, furn.cornerx));
			}
			break;
		case DOWN:
			pp.getFurniture().cornery++;
			for(int i = 0; i < furn.width; i++)
			{
				list1.add(new PEmpty(furn.cornery + furn.length, furn.cornerx + i));
				list2.add(new PEmpty(furn.cornery, furn.cornerx + i));
			}
			break;
		case LEFT:
			pp.getFurniture().cornerx--;
			for(int i = 0; i < furn.length; i++)
			{
				list1.add(new PEmpty(furn.cornery + i, furn.cornerx - 1));
				list2.add(new PEmpty(furn.cornery + i, furn.cornerx + furn.width - 1));
			}
			break;
		case UP:
			pp.getFurniture().cornery--;
			for(int i = 0; i < furn.width; i++)
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
	}

	public Direction getDirection() {
		return this.direction;
	}

	@Override
	public String toString() {
		return "Move " + direction + " " + getFurniture().toString(); 
	}
	
	// Returns whether this (moving action) equals to am (given moving action)
	public boolean equals(AMove am) {
		if((getFurniture().id == am.getFurniture().id) && (direction == am.direction))
			return true;
		return false;
	}

}
