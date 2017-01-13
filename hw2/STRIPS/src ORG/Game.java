import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Random;
import java.util.Stack;

import javax.swing.JFrame;
import javax.swing.JTextArea;

import sun.misc.Sort;


public class Game
{
	GUI gui;
	Stack<Action> stackAction = new Stack<Action>();
	Hashtable<State, LinkedList<Action>> posActions = new Hashtable<State, LinkedList<Action>>();
	Sboard board;
	Sboard goal_board;
	State initial_state;
	State goal_state;
	JFrame f = new JFrame("STRIPS");
	JFrame stack_gui = new JFrame("STACK");
	
	public boolean goal = false;
	
	
	// Resets all variables and paints the board
	public void execute()
	{   
        gui = new GUI(this);

        goal_board = new Sboard();
        board = new Sboard();
        
        initial_state = new State();
        goal_state = new State();
        
//        PPosition p1 = new PPosition(0,false,0,0,0,0,Move.UP);
//        PMovable p2 = new PMovable(0,false,Move.UP);
//        System.out.println("p1.equals(p2)? " + p1.equals(p2));
//        goal_state.pList.add(new PPosition(1,false,1,2,3,4,Move.DOWN));
        
        addFurniture(3, 2, 1, 1);
        addFurniture(2, 5, 1, 1);
        addFurniture(5, 4, 1, 1);
        addFurniture(2, 1, 1, 1);
        
        for(int i = 0; i < 10; i++)
        {
        	AMove am = new AMove(getFurnitureById(1), Move.RIGHT);
        	stackAction.push(am);
        }
        System.out.println("stack_action size is " + stackAction.size());
        
        for(int i=0; i<getListSize(); i++)
        {
        	Furniture furn = getFurnitureById(getListElement(i));
        	if(furn != null)
        		System.out.println("(BEFORE) " + furn.id + ": X=" + furn.cornerx + ", Y=" + furn.cornery + ", Width=" + furn.width + ", Length=" + furn.length);
        }
        
//        PPosition p3 = (PPosition) goal_state.pList.getFirst();
//        PPosition p4 = new PPosition(p3.furn_id, p3.sign, p3.x, p3.y, p3.width, p3.length, p3.state);
//        System.out.println("p3:[" + p3.toString() + "] equals p4:[" + p4.toString() + "]?" + p3.equals(p4));		
        
//        goal_state.pList.remove(goal_state.pList.indexOf(p4));
//        p4.printf();
//        goal_state.removePredicate(p4);
        
        deleteFurniture(3);
        deleteFurniture(4);
        
        
        for(int i=0; i<getListSize(); i++)
        {
        	Furniture furn = getFurnitureById(getListElement(i));
        	if(furn != null)
        		System.out.println("(AFTER)  " + furn.id + ": X=" + furn.cornerx + ", Y=" + furn.cornery + ", Width=" + furn.width + ", Length=" + furn.length);
        }
        
        System.out.println("Equals: " + (board.equals(goal_board) ? "Yes" : "No"));
//        a.setText(arg0);
        f.add(gui);
        f.setMinimumSize(new Dimension(1257, 682));
        f.setResizable(false);
        f.pack();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
        
//        drawStack();
	}
	
//	public int getVectorSize() {
//		return board.getVectorSize();
//	}
	
//	public int getLastId(){
//		if(goal)
//			return goal_board.getLastId();
//		else
//			return board.getLastId();
//	}
	
	public void drawStack()
	{
		strips();
		String stackstring = new String(stackToString(stackAction));
		GUI sgui = new GUI(stackstring);
        stack_gui.add(sgui);///////
        stack_gui.setMinimumSize(new Dimension(307, 682));
        stack_gui.setResizable(false);
        stack_gui.pack();
        stack_gui.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        stack_gui.setVisible(true);
        
		
	}
	
	public int getListSize(){
		if(goal)
			return goal_board.getListSize();
		else
			return board.getListSize();
	}
	
	public int getListElement(int index){
		if(goal)
			return goal_board.getListElement(index);
		else
			return board.getListElement(index);
	}
	
//	public int getLastVectorIndex() {
//		return board.getLastVectorIndex();
//	}

	// Restarts the game: resets all variables and repaints the board
	public void restart()
	{
		f.remove(gui);
		execute();
	}
	
	public int getContent(int i, int j) {
		if(goal)
			return goal_board.getContent(i, j);
		else
			return board.getContent(i, j);
	}

	public int addFurniture(int x, int y, int width, int length) 
	{
		int id = goal_board.addFurniture(x, y, width, length);
		board.addFurniture(x, y, width, length);
		 
		initial_state.addPredicate(new PPosition(id, false, x, y, width, length, Move.UP));
		goal_state.addPredicate(new PPosition(id, false, x, y, width, length, Move.UP));
		
		displayStates();
		
		return id;
	}
	
//	public Furniture getFurnitureAt(int index) {
//		return board.getFurnitureAt(index);
//	}
	
	public void deleteFurniture(int id){
		Furniture furn = getFurnitureById(id);
		PPosition p = new PPosition(id, false, furn.cornerx, furn.cornery, furn.width, furn.length, furn.state);
		
		goal_state.removePredicate(p);
		initial_state.removePredicate(p);
		
		goal_board.deleteFurniture(id);
		board.deleteFurniture(id);
		
		displayStates();
	}
		
	private void displayStates() {
		System.out.print("Initial state: ");
		for(int i = 0; i < initial_state.pList.size(); i++)
			System.out.print(initial_state.pList.get(i).toString() + ", ");

		System.out.println();
		
		System.out.print("Goal state: ");
		for(int i = 0; i < goal_state.pList.size(); i++)
			System.out.print(goal_state.pList.get(i).toString() + ", ");
		
		System.out.println();
	}

	public void updateFurniture(int id, int x, int y, int width, int length, Furniture frame, boolean both){
		Furniture furn = goal_board.getFurnitureById(id);
		PPosition p = new PPosition(id, false, furn.cornerx, furn.cornery, furn.width, furn.length, furn.state);
		goal_state.removePredicate(p);
		goal_board.updateFurniture(id, x, y, width, length, frame);
		goal_state.addPredicate(new PPosition(id, false, furn.cornerx, furn.cornery, furn.width, furn.length, furn.state));
		
		if(both)
		{
			furn = board.getFurnitureById(id);
			initial_state.removePredicate(p);
			board.updateFurniture(id, x, y, width, length, frame);
			initial_state.addPredicate(new PPosition(id, false, furn.cornerx, furn.cornery, furn.width, furn.length, furn.state));
		}
		
		displayStates();
	}
	
	public boolean canPlace(int x, int y, int width, int length, int excp_id){
		if(goal)
			return goal_board.canPlace(x, y, width, length, excp_id);
		else
			return board.canPlace(x, y, width, length, excp_id);
	}
	
	public boolean canMove(Furniture furn, Move direction){
		if(goal)
			return goal_board.canMove(furn, direction);
		else
			return board.canMove(furn, direction);
	}
	
	public boolean doMove(int furn_id, Move direction){
		boolean result;
		Furniture furn = getFurnitureById(furn_id);
		PPosition p = new PPosition(furn_id, false, furn.cornerx, furn.cornery, furn.width, furn.length, furn.state);
		if(goal)
		{
			goal_state.removePredicate(p);
			result = goal_board.doMove(furn_id, direction);
			goal_state.addPredicate(new PPosition(furn_id, false, furn.cornerx, furn.cornery, furn.width, furn.length, furn.state));
		}
		else
		{
			initial_state.removePredicate(p);
			result = board.doMove(furn_id, direction);
			initial_state.addPredicate(new PPosition(furn_id, false, furn.cornerx, furn.cornery, furn.width, furn.length, furn.state));
		}
		
		displayStates();

		return result;
	}

	public boolean canRotate(Furniture furn, boolean clockwise){
		if(goal)
			return goal_board.canRotate(furn, clockwise);
		else
			return board.canRotate(furn, clockwise);
	}
		
	public boolean doRotate(int furn_id, boolean cw) {
		boolean result;
		Furniture furn = getFurnitureById(furn_id);
		PPosition p = new PPosition(furn_id, false, furn.cornerx, furn.cornery, furn.width, furn.length, furn.state);
		if(goal)
		{
			goal_state.removePredicate(p);
			result = goal_board.doRotate(furn_id, cw);
			goal_state.addPredicate(new PPosition(furn_id, false, furn.cornerx, furn.cornery, furn.width, furn.length, furn.state));
		}
		else
		{
			initial_state.removePredicate(p);
			result = board.doRotate(furn_id, cw);
			initial_state.addPredicate(new PPosition(furn_id, false, furn.cornerx, furn.cornery, furn.width, furn.length, furn.state));
		}
		
		displayStates();

		return result;
	}

	public Furniture getFurnitureById(int id) {
		if(goal)
			return goal_board.getFurnitureById(id);
		else
			return board.getFurnitureById(id);
	}
	
	public void strips()
	{
		Stack<Object> s= new Stack<Object>();
		s.push(goal_state);
		Object item;
		Furniture furn;
		while(!s.isEmpty())
		{
			item = s.pop();
			if(item.getClass() == Action.class)
			{
				Action a = (Action) item;
				furn = a.furn;
				if(item.getClass() == AMove.class)
				{
					AMove am = (AMove) a;
					doMove(furn.id, am.direction);
				} 
				else 
					if(item.getClass() == ARotate.class)
					{
						ARotate ar = (ARotate) a;
						doRotate(furn.id, ar.clockwise);
					}
				initial_state.subtract(a.getDelList());
				initial_state.addState(a.getAddList());
				stackAction.push(a);
			}
			if(item.getClass() == State.class)
			{
				s.push(item);
				State tmp = new State((State) item);
				Random rand = new Random();
				int index;
				while(!(tmp.pList.isEmpty()))
				{
					index = rand.nextInt(tmp.pList.size());
					s.push(tmp.pList.remove(index));
				}
			}
			if(item.getClass()==Predicate.class)
			{
				s.push(heuristic((Predicate)item, initial_state).getPreCond());
			}
			
		}
		
	}
	
	public Action heuristic(Predicate p, State s)
	{
		LinkedList<Action> act_list = getActions(s);
		State add_list = new State();
		Predicate tmp_p;
//		while(!(act_list.isEmpty()))
		for(int j = 0; j < act_list.size(); j++)
		{
			add_list = act_list.get(j).getAddList();
			for(int i = 0; i < add_list.pList.size(); i++)
			{
				tmp_p = add_list.pList.get(i);
				if(p.getClass() == tmp_p.getClass())
				{
					if(p.getClass() == PPosition.class)
					{
						PPosition pp = (PPosition) p;
						if(pp.equals((PPosition) tmp_p))
							return act_list.get(j);
					}
					if(p.getClass() == PMovable.class)
					{
						PMovable pm = (PMovable) p;
						if(pm.equals((PMovable) tmp_p))
							return act_list.get(j);
					}
					if(p.getClass() == PRotatable.class)
					{
						PRotatable pm = (PRotatable) p;
						if(pm.equals((PRotatable) tmp_p))
							return act_list.get(j);
					}
				}
			}
		}
//		posActions.put(s, act_list);
		return null;
	}
	
	public LinkedList<Action> getActions(State s)
	{
		LinkedList<Action> action_list = new LinkedList<Action>();
		LinkedList<Integer> preference_list = new LinkedList<Integer>();
//		Action[] a = new Action[6 * getListSize()];
		for(int i = 0; i < getListSize(); i++)
		{
			int pref;
			Furniture furn = getFurnitureById(getListElement(i));
			if(board.canMove(furn, Move.UP))
			{
				AMove move_up = new AMove(furn, Move.UP);
				pref = evaluate(s, move_up);
				insertSorted(action_list, preference_list, move_up, pref);
			}
			if(board.canMove(furn, Move.DOWN))
			{
				AMove move_down = new AMove(furn, Move.DOWN);
				pref = evaluate(s, move_down);
				insertSorted(action_list, preference_list, move_down, pref);
			}
			if(board.canMove(furn, Move.RIGHT))
			{
				AMove move_right = new AMove(furn, Move.RIGHT);
				pref = evaluate(s, move_right);
				insertSorted(action_list, preference_list, move_right, pref);
			}
			if(board.canMove(furn, Move.LEFT))
			{
				AMove move_left = new AMove(furn, Move.LEFT);
				pref = evaluate(s, move_left);
				insertSorted(action_list, preference_list, move_left, pref);
			}
			if(board.canRotate(furn, true))
			{
				ARotate rotatecw = new ARotate(furn, true);
				pref = evaluate(s, rotatecw);
				insertSorted(action_list, preference_list, rotatecw, pref);
			}
			if(board.canRotate(furn, false))
			{
				ARotate rotateccw = new ARotate(furn, false);
				pref = evaluate(s, rotateccw);
				insertSorted(action_list, preference_list, rotateccw, pref);
			}
		}
		
		return action_list;
	}
	
	private int evaluate(State s, Action a) {
		int sum = 0;
		int diff;
		boolean flag;
		Predicate p1, p2 = null;
		
		s.subtract(a.getDelList());
		s.addState(a.getAddList());
		
		for(int i = 0; i < s.pList.size() ; i++)
		{
			int j = 0;
			p1 = s.pList.get(i);
			flag = true;
			while(j < goal_state.pList.size() && flag)
			{
				p2 = goal_state.pList.get(j);
				if((p1.getClass() == p2.getClass()) && (p1.getClass() == PPosition.class) && (p1.furn_id == p2.furn_id))
					flag = false;
				j++;
			}
			diff = (int) Math.pow( (((PPosition)p1).x + (((PPosition)p1).width)/2.0) - (((PPosition)p2).x + (((PPosition)p2).width)/2.0), 2);
			diff += (int) Math.pow( (((PPosition)p1).y + (((PPosition)p1).length)/2.0) - (((PPosition)p2).y + (((PPosition)p2).length)/2.0), 2);
			sum += diff;
		}
		
		return sum;
	}

	public void insertSorted(LinkedList<Action> a_list, LinkedList<Integer> p_list, Action act, int pref)
	{
		int i = 0;
		while(i < p_list.size() && p_list.get(i) >= pref)
			i++;
		
		p_list.add(i, pref);
		a_list.add(i, act);
	}
	
	public String stackToString(Stack<Action> opstack)
	{
		String str = new String();
		String tmp = new String();
		Action a;
		AMove am;
		ARotate ar;
		
		if(opstack.isEmpty())
			str = "Stack is empty";
		
		while(!opstack.isEmpty())
		{
			a = opstack.pop();
			if(a.getClass() == AMove.class)
			{
				am = (AMove) a;
				str = str + "Move " + am.direction + " - " + am.furn + "\n";
			}
			else if(a.getClass()==ARotate.class)
			{
				ar = (ARotate) a;
				if(ar.clockwise == true)
					tmp = "Clockwise";
				else
					tmp = "Counterclockwise";
				str = str + "Rotate " + tmp + " - " + ar.furn + "\n";
			}
		}
		
		return str;
	}
}
