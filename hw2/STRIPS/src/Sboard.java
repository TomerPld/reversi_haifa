import java.util.*;

enum Move {UP,RIGHT,DOWN,LEFT};

public class Sboard {
	
	private int [][]a = new int [13][25];
	public LinkedList<Integer> listId = new LinkedList<Integer>();
	public Hashtable<Integer, Furniture> tblFurniture = new Hashtable<Integer, Furniture>();
	
	// Constructor - initializes rooms
	public Sboard()
	{
		int i,j;
		for (i=0;i<13;i++)
			for(j=0;j<25;j++)
			{
				if((i==0) || (j==0) || (i==12) || (j==24) || (i>5 && j>11) || (j==12 && i==1) || (j==12 && i==5))
					a[i][j] = -1;
				else
					a[i][j] = 0;
			}		
	}
		
	// Returns whether a furniture with given dimensions can be placed on board (with an exception for excp_id)
	public boolean canPlace(int x, int y, int width, int length, int excp_id)
	{
		int i,j,flag=0;
		for(i=0;i<length;i++)
			for(j=0;j<width;j++)
			{
				if((a[i+y][j+x] != 0) && (a[i+y][j+x] != excp_id))
					flag = 1;	
			}
		if(flag == 0)
			return true;
		return false;
	}
	
	// Adds the given furniture to the array (used in GUI) 
	public void addFurnitureToArray(Furniture furn)
	{
		int i,j;
		for(i=0;i<furn.length;i++)
			for(j=0;j<furn.width;j++)
				a[furn.cornery+i][furn.cornerx+j] = furn.id;
	}
	
	// Adds the furniture to the data structures (given its dimensions) and updates the array accordingly
	public int addFurniture(int x, int y, int width, int length)
	{
		Furniture furn;
		if(tblFurniture.isEmpty())
			listId.add(1);
		else
			listId.add(listId.getLast() + 1);
			
		if(!canPlace(x,y,width,length,0))
			return -1;
		
		furn = new Furniture(x,y,width,length,listId.getLast(),Move.UP);
		tblFurniture.put(furn.id, furn);
		addFurnitureToArray(furn);
		return furn.id;
	}
	
	// Returns whether the given furniture can move in the given direction
	public boolean canMove(Furniture furn, Move direction)
	{
		if(furn == null)			
			return false;
		
		int i;
				
		switch (direction) {
		case RIGHT:
			for(i=0;i<furn.length;i++)
				if(a[furn.cornery+i][furn.cornerx+furn.width] != 0)
					return false;
			break;
		case DOWN:
			for(i=0;i<furn.width;i++)
				if(a[furn.cornery+furn.length][furn.cornerx+i] != 0)
					return false;
			break;
		case LEFT:
			for(i=0;i<furn.length;i++)
				if(a[furn.cornery+i][furn.cornerx-1] != 0)
					return false;
			break;
		case UP:
			for(i=0;i<furn.width;i++)
				if(a[furn.cornery-1][furn.cornerx+i] != 0)
					return false;
			break;
		default:
			break;
		}		
		return true;
	}
	
	// Moves the furniture with the given identifier in the given direction (if possible)
	public boolean doMove(int furn_id, Move direction)
	{
		int i;
		Furniture furn = getFurnitureById(furn_id);
		if(canMove(furn,direction) == false)
			return false;
		
		switch (direction) {
		case RIGHT:
			for(i=0;i<furn.length;i++)
			{
				a[furn.cornery+i][furn.cornerx+furn.width] = furn.id;
				a[furn.cornery+i][furn.cornerx] = 0;
			}
			++furn.cornerx;
			break;
			
		case DOWN:
			for(i=0;i<furn.width;i++)
			{
				a[furn.cornery+furn.length][furn.cornerx+i] = furn.id;
				a[furn.cornery][furn.cornerx+i] = 0;
			}
			++furn.cornery;
			break;
			
		case LEFT:
			for(i=0;i<furn.length;i++)
			{
				a[furn.cornery+i][furn.cornerx-1] = furn.id;
				a[furn.cornery+i][furn.cornerx+furn.width-1] = 0;
			}
			--furn.cornerx;
			break;
			
		case UP:
			for(i=0;i<furn.width;i++)
			{
				a[furn.cornery-1][furn.cornerx+i] = furn.id;
				a[furn.cornery+furn.length-1][furn.cornerx+i] = 0;
			}
			--furn.cornery;
			break;
			
		default:
			break;
		}
		return true;
	}
	
	// Deletes the given furniture from the array
	private void deleteFurnitureFromArray(Furniture furn)
	{
		int i,j;
		for(i=0;i<furn.length;i++)
			for(j=0;j<furn.width;j++)
				a[furn.cornery+i][furn.cornerx+j]=0;
	}
	
	// Deletes the furniture with the given identifier from the data structures and updates the array accordingly
	public void deleteFurniture(int id)
	{
		Furniture furn = tblFurniture.get(id);
		if(furn == null)
			return;
		deleteFurnitureFromArray(furn);
		listId.remove((Integer)furn.id);
		tblFurniture.remove(furn.id);
		
	}
	
	// Returns whether the given furniture can be rotated in the given direction (cw/ccw)
	public boolean canRotate(Furniture furn, boolean clockwise)
	{
		if(furn == null)
			return false;
		Furniture tmp = new Furniture(furn);
		tmp.rotate(clockwise);
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
		
		if((clockwise && cond) || (!clockwise && !cond))	// upper right and lower left
		{
			for(int i = min_y; i < max_y + min_length; i++)
			{
				for(int j = max_x + (i < max_y ? 0 : min_width); j < min_x + max_width; j++)
				{
					if((a[i][j] != 0) && (a[i][j] != furn.id))
						return false;
				}
			}
			for(int i = max_y; i < min_y + max_length; i++)
			{
				for(int j = min_x; j < max_x + (i < (max_y + min_length) ? 0 : min_width); j++)
				{
					if((a[i][j] != 0) && (a[i][j] != furn.id))
						return false;
				}
			}
		}
		else												// upper left and lower right
		{
			for(int i = min_y; i < max_y + min_length; i++)
			{
				for(int j = min_x; j < max_x + (i < max_y ? min_width : 0); j++)
				{
					if((a[i][j] != 0) && (a[i][j] != furn.id))
						return false;
				}
			}
			for(int i = max_y; i < min_y + max_length; i++)
			{
				for(int j = max_x + (i < (max_y + min_length) ? min_width : 0); j < min_x + max_width; j++)
				{
					if((a[i][j] != 0) && (a[i][j] != furn.id))
						return false;
				}
			}
		}
		return true;
	}
	
	// Rotates the furniture with the given identifier in the given direction (cw/ccw)
	public boolean doRotate(int furn_id, boolean clockwise)
	{
		Furniture furn = getFurnitureById(furn_id);
		if(canRotate(furn, clockwise))
		{
			deleteFurnitureFromArray(furn);
			furn.rotate(clockwise);
			addFurnitureToArray(furn);
			return true;
		}
		
		return false;
	}
	
	// Returns the array content at the given coordinates (if coordinates are illegal, returns -1)
	public int getContent(int i, int j) {
		if((i < 0) || (i > 12) || (j < 0) || (j > 24))
			return -1;
		return a[i][j];
	}

	
	// Updates given furniture on array (adds frame and furn on top)
	public void updateFurnitureOnArray(Furniture furn, Furniture frame)
	{
		addFurnitureToArray(frame);
		addFurnitureToArray(furn);
	}

	// Updates the furniure with the given identifier based on given dimensions and updates the array accordingly
	public void updateFurniture(int id, int x, int y, int width, int length, Furniture frame) {
		Furniture furn = getFurnitureById(id);
		furn.cornerx = x;
		furn.cornery = y;
		furn.width = width;
		furn.length = length;
		updateFurnitureOnArray(furn, frame);
	}
	
	public int getLastId(){
		return tblFurniture.isEmpty()? -1: listId.getLast();
	}
		
	public Furniture getFurnitureById(int id)
	{
		return tblFurniture.get(id);
	}
		
	public int getListSize(){
		return listId.size();
	}
	
	public int getListElement(int index){
		return listId.get(index);
	}
	
}

