package core;
import java.util.LinkedList;

public class ARotate extends Action {
	private RotationDirection rotationDirection;

	// Constructor, gets: furn - the furn on which the action is applied, cw - the rotation direction
	public ARotate(Furniture furn, RotationDirection rotationDirection) {
		super(furn);
		this.rotationDirection = rotationDirection;

		LinkedList<Predicate> list1, list2;
		PPosition pp = new PPosition(furn);
		
		Furniture tmp = new Furniture(furn);
		tmp.rotate(rotationDirection);
		int min_x, max_x;
		int min_y, max_y;
		int min_length, max_length;
		int min_width, max_width;
		boolean cond;
		
		min_x		= Math.min(furn.cornerx, tmp.cornerx);
		max_x		= Math.max(furn.cornerx, tmp.cornerx);
		min_y		= Math.min(furn.cornery, tmp.cornery);
		max_y		= Math.max(furn.cornery, tmp.cornery);
		min_length	= Math.min(furn.length , tmp.length);
		max_length	= Math.max(furn.length , tmp.length);
		min_width	= Math.min(furn.width  , tmp.width);
		max_width	= Math.max(furn.width  , tmp.width);
		
		cond = (furn.length > furn.width);
		

		// list1 is dedicated to the action's preconditions list
		list1 = new LinkedList<Predicate>();
		list1.add(pp);
				
		if(RotationDirection.CW.equals(rotationDirection))	// rotate clockwise
			if(cond)	// upper right and lower left
			{
				// upper right
				for(int i = min_y; i < max_y + min_length; i++)
					for(int j = max_x + min_width; j < min_x + max_width; j++)
						list1.add(new PEmpty(i,j));
				
				// lower left
				for(int i = max_y; i < min_y + max_length; i++)
					for(int j = min_x; j < max_x; j++)
						list1.add(new PEmpty(i,j));
			}
			else		// upper left and lower right
			{
				// upper left
				for(int i = min_y; i < max_y; i++)
					for(int j = min_x; j < max_x + min_width; j++)
						list1.add(new PEmpty(i,j));
				
				// lower right
				for(int i = max_y + min_length; i < min_y + max_length; i++)
					for(int j = max_x; j < min_x + max_width; j++)
						list1.add(new PEmpty(i,j));
			}
		else			// rotate counter-clockwise
			if(!cond)	// upper right and lower left
			{
				// upper right
				for(int i = min_y; i < max_y; i++)
					for(int j = max_x; j < min_x + max_width; j++)
						list1.add(new PEmpty(i,j));
				
				// lower left
				for(int i = max_y + min_length; i < min_y + max_length; i++)
					for(int j = min_x; j < max_x + min_width; j++)
						list1.add(new PEmpty(i,j));
			}
			else
			{
				// upper left
				for(int i = min_y; i < max_y + min_length; i++)
					for(int j = min_x; j < max_x; j++)
						list1.add(new PEmpty(i,j));
				
				// lower right
				for(int i = max_y; i < min_y + max_length; i++)
					for(int j = max_x + min_width; j < min_x + max_width; j++)
						list1.add(new PEmpty(i,j));
			}
		super.setPreCond(new State(list1));

		
		// list1 is now dedicated to the action's delete list
		list1 = new LinkedList<Predicate>();
		list1.add(pp);

		// list2 is now dedicated to the action's add list
		list2 = new LinkedList<Predicate>();
		list2.add(new PPosition(tmp));
		
		for(int j = max_x; j < max_x + min_width; j++)
		{
			// top is no longer empty
			for(int i = min_y; i < max_y; i++)
				if(cond)
					list2.add(new PEmpty(i,j));
				else
					list1.add(new PEmpty(i,j));
					
			// bottom is no longer empty
			for(int i = max_y + min_length; i < min_y + max_length; i++)
				if(cond)
					list2.add(new PEmpty(i,j));
				else
					list1.add(new PEmpty(i,j));
		}
		
		for(int i = max_y; i < max_y + min_length; i++)
		{
			// left is no longer empty
			for(int j = min_x; j < max_x; j++)
				if(cond)
					list1.add(new PEmpty(i,j));
				else
					list2.add(new PEmpty(i,j));
			
			// right is no longer empty
			for(int j = max_x + min_width; j < min_x + max_width; j++)
				if(cond)
					list1.add(new PEmpty(i,j));
				else
					list2.add(new PEmpty(i,j));
		}
		
		super.setDelList(new State(list1));
		super.setAddList(new State(list2));
	}

	public RotationDirection getRotationDirection() {
		return this.rotationDirection;
	}

	@Override
	public String toString()
	{
		return "Rotate " + (RotationDirection.CW.equals(rotationDirection) ? "" : "C") + "CW " + this.getFurniture(); 
	}	

	// Returns whether this (rotating action) equals to ar (given rotating action)
	public boolean equals(ARotate ar)
	{
		if((this.getFurniture().id == ar.getFurniture().id) && (rotationDirection == ar.getRotationDirection()))
			return true;
		return false;
	}

}
