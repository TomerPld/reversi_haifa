 import java.awt.Dimension;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Random;
import java.util.Stack;

import javax.swing.JFrame;


public class Game
{
	GUI gui;
	SGUI sgui;
	LinkedList<Action> listAction = new LinkedList<Action>();
	LinkedList<Integer> listStateId = new LinkedList<Integer>();
	LinkedList<State> listState = new LinkedList<State>();
	LinkedList<State> listBannedState = new LinkedList<State>();
//	State bannedPredicates = new State();
	Hashtable<Integer, LinkedList<Action>> posActions = new Hashtable<Integer, LinkedList<Action>>();
	Sboard board;
	Sboard goal_board;
	State initial_state;
	State goal_state;
	State goal_state_copy;
	JFrame brd_frame = new JFrame("STRIPS");
	JFrame st_frame = new JFrame("STACK");
	
	public Stack<Object> s = new Stack<Object>();
	public boolean goal = false;
	public boolean firstStep = true;
	public boolean perform = true;
	
	
	// Resets all variables and paints the board
	public void execute()
	{   
        gui = new GUI(this);
		sgui = new SGUI(this);

        goal_board = new Sboard();
        board = new Sboard();
        
        initial_state = new State();
        goal_state = new State();
        
//        PPosition p1 = new PPosition(0,false,0,0,0,0,Move.UP);
//        PMovable p2 = new PMovable(0,false,Move.UP);
//        System.out.println("p1.equals(p2)? " + p1.equals(p2));
//        goal_state.pList.add(new PPosition(1,false,1,2,3,4,Move.DOWN));
        
        for(int i = 1; i < 12; i++)
        	for(int j = 1; j < 20; j++)
        	{
				initial_state.addPredicate(new PEmpty(i,j));
				goal_state.addPredicate(new PEmpty(i,j));
			}
        
//        addFurniture(3, 2, 1, 1);
//        addFurniture(2, 5, 1, 1);
//        addFurniture(5, 4, 1, 1);
//        addFurniture(2, 1, 1, 1);
        
                
//        for(int i = 0; i < 10; i++)
//        {
//        	AMove am = new AMove(getFurnitureById(1), Move.RIGHT);
//        	stackAction.push(am);
//        }
//        System.out.println("stack_action size is " + stackAction.size());
        
//        for(int i=0; i<getListSize(); i++)
//        {
//        	Furniture furn = getFurnitureById(getListElement(i));
//        	if(furn != null)
//        		System.out.println("(BEFORE) " + furn.id + ": X=" + furn.cornerx + ", Y=" + furn.cornery + ", Width=" + furn.width + ", Length=" + furn.length);
//        }
        
//        PPosition p3 = (PPosition) goal_state.pList.getFirst();
//        PPosition p4 = new PPosition(p3.furn_id, p3.sign, p3.x, p3.y, p3.width, p3.length, p3.state);
//        System.out.println("p3:[" + p3.toString() + "] equals p4:[" + p4.toString() + "]?" + p3.equals(p4));		
        
//        goal_state.pList.remove(goal_state.pList.indexOf(p4));
//        p4.printf();
//        goal_state.removePredicate(p4);
        
//        deleteFurniture(3);
//        deleteFurniture(4);
        
        
//        for(int i=0; i<getListSize(); i++)
//        {
//        	Furniture furn = getFurnitureById(getListElement(i));
//        	if(furn != null)
//        		System.out.println("(AFTER)  " + furn.id + ": X=" + furn.cornerx + ", Y=" + furn.cornery + ", Width=" + furn.width + ", Length=" + furn.length);
//        }
        
//        System.out.println("Equals: " + (board.equals(goal_board) ? "Yes" : "No"));
//        a.setText(arg0);
        
        // board frame window
        brd_frame.add(gui);
        brd_frame.setMinimumSize(new Dimension(1205, 630));
        brd_frame.setResizable(false);
        brd_frame.pack();
        brd_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        brd_frame.setVisible(true);

        // stack/listAction display frame window
        st_frame.add(sgui);
        st_frame.setMinimumSize(new Dimension(405, 415));
        st_frame.setResizable(false);
        st_frame.pack();
        st_frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        st_frame.setVisible(true);
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

//	// Restarts the game: resets all variables and repaints the board
//	public void restart()
//	{
//		brd_frame.remove(gui);
//		st_frame.remove(sgui);
//		execute();
//	}
	
	public int getContent(int i, int j) {
		if(goal)
			return goal_board.getContent(i, j);
		else
			return board.getContent(i, j);
	}

	// Adds furniture to both boards and updates the states accordingly
	public int addFurniture(int x, int y, int width, int length) 
	{
		int id = goal_board.addFurniture(x, y, width, length);
		board.addFurniture(x, y, width, length);
		 
		initial_state.addPredicate(new PPosition(id, x, y, width, length, Move.UP));
		goal_state.addPredicate(new PPosition(id, x, y, width, length, Move.UP));
		for(int i = y; i < y + length; i++)
			for(int j = x; j < x + width; j++)
			{
				initial_state.removePredicate(new PEmpty(i,j));
				goal_state.removePredicate(new PEmpty(i,j));
			}
		
//		displayStates();
		
		return id;
	}

	// Displays the states
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
	
//	public Furniture getFurnitureAt(int index) {
//		return board.getFurnitureAt(index);
//	}
	
	// Removes furniture from both boards and updates the states accordingly
	public void deleteFurniture(int id){
		Furniture furn = getFurnitureById(id);
		PPosition p = new PPosition(id, furn.cornerx, furn.cornery, furn.width, furn.length, furn.state);
		
		goal_state.removePredicate(p);
		initial_state.removePredicate(p);
		for(int i = furn.cornery; i < furn.cornery + furn.length; i++)
			for(int j = furn.cornerx; j < furn.cornerx + furn.width; j++)
			{
				initial_state.addPredicate(new PEmpty(i,j));
				goal_state.addPredicate(new PEmpty(i,j));
			}
		
		goal_board.deleteFurniture(id);
		board.deleteFurniture(id);
		
//		displayStates();
	}

	// Updates furniture in goal + initial(both==true) boards and updates the states accordingly
	public void updateFurniture(int id, int x, int y, int width, int length, Furniture frame, boolean both){
		Furniture furn = goal_board.getFurnitureById(id);
		PPosition p = new PPosition(id, furn.cornerx, furn.cornery, furn.width, furn.length, furn.state);
		goal_state.removePredicate(p);
		for(int i = furn.cornery; i < furn.cornery + furn.length; i++)
			for(int j = furn.cornerx; j < furn.cornerx + furn.width; j++)
				goal_state.addPredicate(new PEmpty(i,j));
		
		goal_board.updateFurniture(id, x, y, width, length, frame);
		
		goal_state.addPredicate(new PPosition(id, furn.cornerx, furn.cornery, furn.width, furn.length, furn.state));
		for(int i = furn.cornery; i < furn.cornery + furn.length; i++)
			for(int j = furn.cornerx; j < furn.cornerx + furn.width; j++)
				goal_state.removePredicate(new PEmpty(i,j));
		
		if(both)
		{
			furn = board.getFurnitureById(id);
			initial_state.removePredicate(p);
			for(int i = furn.cornery; i < furn.cornery + furn.length; i++)
				for(int j = furn.cornerx; j < furn.cornerx + furn.width; j++)
					initial_state.addPredicate(new PEmpty(i,j));
			
			board.updateFurniture(id, x, y, width, length, frame);
			
			initial_state.addPredicate(new PPosition(id, furn.cornerx, furn.cornery, furn.width, furn.length, furn.state));
			for(int i = furn.cornery; i < furn.cornery + furn.length; i++)
				for(int j = furn.cornerx; j < furn.cornerx + furn.width; j++)
					initial_state.removePredicate(new PEmpty(i,j));
		}
		
//		displayStates();
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
	
	// Move the furniture with the given identifier in the given direction (in the appropriate board) and update the state accordingly
	public boolean doMove(int furn_id, Move direction){
		boolean result;
		Furniture furn = getFurnitureById(furn_id);
		if(furn == null)
			return false;
		
		AMove am = new AMove(furn, direction);
		State pCondList = new State(am.getPreCond());

		if(goal)
		{
			pCondList.subtract(goal_state);
			if(!pCondList.pList.isEmpty())
				return false;
			goal_state.subtract(am.getDelList());
			result = goal_board.doMove(furn_id, direction);
			goal_state.addState(am.getAddList());
		}
		else
		{
			pCondList.subtract(initial_state);
			if(!pCondList.pList.isEmpty())
				return false;
			initial_state.subtract(am.getDelList());
			result = board.doMove(furn_id, direction);
			initial_state.addState(am.getAddList());
		}
		
//		displayStates();

		return result;
	}

	public boolean canRotate(Furniture furn, boolean clockwise){
		if(goal)
			return goal_board.canRotate(furn, clockwise);
		else
			return board.canRotate(furn, clockwise);
	}
	
	// Rotate the furniture with the given identifier in the given direction (in the appropriate board) and update the state accordingly
	public boolean doRotate(int furn_id, boolean cw) {
		boolean result;
		Furniture furn = getFurnitureById(furn_id);
		if(furn == null)
			return false;
		
		ARotate ar = new ARotate(furn, cw);
		State pCondList = new State(ar.getPreCond());
		
		if(goal)
		{
			pCondList.subtract(goal_state);
			if(!pCondList.pList.isEmpty())
				return false;
			goal_state.subtract(ar.getDelList());
			result = goal_board.doRotate(furn_id, cw);
			goal_state.addState(ar.getAddList());
		}
		else
		{
//			System.out.print("(BEFORE) pCondList: ");
//			for(int i = 0; i < pCondList.pList.size(); i++)
//				System.out.print(pCondList.pList.get(i).toString() + ", ");
//			System.out.println();
			
			pCondList.subtract(initial_state);

//			System.out.print("(AFTER) pCondList: ");
//			for(int i = 0; i < pCondList.pList.size(); i++)
//				System.out.print(pCondList.pList.get(i).toString() + ", ");
//			System.out.println();
			
			if(!pCondList.pList.isEmpty())
			{
//				System.out.println("preconditions not valid");
				return false;
			}
			
//			System.out.print("delList: ");
//			for(int i = 0; i < ar.getDelList().pList.size(); i++)
//				System.out.print(ar.getDelList().pList.get(i).toString() + ", ");
//			System.out.println();
//			System.out.print("addList: ");
//			for(int i = 0; i < ar.getAddList().pList.size(); i++)
//				System.out.print(ar.getAddList().pList.get(i).toString() + ", ");
//			System.out.println();
			
			initial_state.subtract(ar.getDelList());
			result = board.doRotate(furn_id, cw);
			initial_state.addState(ar.getAddList());
		}
		
//		displayStates();

		return result;
	}

	public Furniture getFurnitureById(int id) {
		if(goal)
			return goal_board.getFurnitureById(id);
		else
			return board.getFurnitureById(id);
	}
	
	// STRIPS algorithm - performed step by step (in a loop)
	public void strips()
	{
		if(firstStep)			// first step
			stepByStep();
		while(!s.isEmpty())		// major body
			stepByStep();
		stepByStep();			// data clean-up
		
		
		/**
		s.push(goal_state);
		Object item;
		while(!s.isEmpty())
		{			
			item = s.pop();
			if(item.getClass() == AMove.class || item.getClass() == ARotate.class)
			{
				System.out.println("handling action " + item.toString());
				Action a = (Action) item;
//				if(item.getClass() == AMove.class) {
//					AMove am = (AMove) a;
//					doMove(a.furn.id, am.direction);
//				} else if(item.getClass() == ARotate.class)	{
//					ARotate ar = (ARotate) a;
//					doRotate(a.furn.id, ar.clockwise);
//				}
				//TODO
				
//				System.out.print("a.getDelList: ");
//				for(int i = 0; i < a.getDelList().pList.size(); i++)
//					System.out.print(a.getDelList().pList.get(i).toString() + ", ");
//				System.out.println();
//				
//				System.out.print("a.getAddList: ");
//				for(int i = 0; i < a.getAddList().pList.size(); i++)
//					System.out.print(a.getAddList().pList.get(i).toString() + ", ");
//				System.out.println();
//
//				System.out.print("(BEFORE) initial_state: ");
//				for(int i = 0; i < initial_state.pList.size(); i++)
//					System.out.print(initial_state.pList.get(i).toString() + ", ");
//				System.out.println();
				
//				/*
//				 * TODO
				initial_state.subtract(a.getDelList());
				initial_state.addState(a.getAddList());
				 * /

//				System.out.print("(AFTER) initial_state: ");
//				for(int i = 0; i < initial_state.pList.size(); i++)
//					System.out.print(initial_state.pList.get(i).toString() + ", ");
//				System.out.println();
//				
//				if(!initial_state.contains(new PPosition(2,2,5,1,1,Move.UP)))
//					System.out.println("WTF MAN !?");
//				else
//					System.out.println("VERY GOOD");
				
				listAction.add(a);
			}
			if(item.getClass() == State.class)
			{
//				System.out.println("handling state: ");
//				for(int i = 0; i < ((State)item).pList.size(); i++)
//					System.out.print(((State)item).pList.get(i).toString() + ", ");
//				System.out.println();
				
				((State)item).subtract(initial_state);
				if(!(((State)item).pList.isEmpty()))
				{
					s.push(item);
					State tmp = new State((State) item);
															
					Random rand = new Random();
					int index;
					while(!(tmp.pList.isEmpty()))
					{
						index = rand.nextInt(tmp.pList.size());
//						System.out.println("tmp.pList.get(" + index + ")=" + tmp.pList.get(index));
						Predicate p = tmp.pList.remove(index);
//						System.out.println("p = " + p);
						s.push(p);
					}

//					System.out.print("stack s: ");
//					Stack<Object> s2 = new Stack<Object>();
//					while(!s.isEmpty())
//						s2.push(s.pop());
//					while(!s2.isEmpty())
//					{
//						Object o = s2.pop();
//						System.out.print(o + ", ");
//						s.push(o);
//					}
//					System.out.println();

				}
//				else
//					System.out.println("state is completely satisfied");
			}
			if(item.getClass() == PEmpty.class || item.getClass() == PPosition.class)
			{
//				System.out.println("handling predicate " + item.toString());
				if(!initial_state.contains((Predicate)item))
				{
//					System.out.println("predicate " + item.toString() + " calls heuristic");
					Action a = heuristic((Predicate)item, initial_state);
//					System.out.println("a = " + a);
					
//					System.out.print("a.getPreCond: ");
//					State preCond = new State(a.getPreCond());
//					for(int k = 0; k < preCond.pList.size(); k++)
//						System.out.print(preCond.pList.get(k).toString() + ", ");
//					System.out.println();
					
					s.push(a);
					s.push(a.getPreCond());
				}
			}
		}
		**/
	}
	
	// Returns the best action for the given state s, preferably one which contains p in its add-list		
	public Action heuristic(Predicate p, State s)
	{
		LinkedList<Action> act_list = new LinkedList<Action>();
		if(stateInList(s, listState) == -1)		// first time we meet this state
		{
			if(listStateId.isEmpty())
				listStateId.add(1);
			else
				listStateId.add(listStateId.getLast() + 1);
			s.identifier = listStateId.getLast();
			listState.add(new State(s));
			posActions.put(s.identifier, getActions(s));
		}
			
		act_list = posActions.get(s.identifier);
		Action a;
		
//		System.out.print("act_list = ");
//		for(int i=0; i<act_list.size(); i++)
//			System.out.print(act_list.get(i) + ", ");
//		System.out.println();
			
//		while(!(act_list.isEmpty()))
		for(int j = 0; j < act_list.size(); j++)
		{
			a = act_list.get(j);
			if(((a.getAddList()).contains(p) >= 0) && (dynamicState(s,a)))	// predicate is in add-list & state is not a dead-end
			{
				act_list.remove(j);
				if(act_list.isEmpty())	// once there are no possible actions, we need to ban that state 
				{
					listBannedState.add(new State(s));
					posActions.remove(s.identifier);
					removeStateFromList(s);
					listStateId.remove((Integer)s.identifier);
				}
				return a;
			}
		}
//		posActions.put(s, act_list);
		a = act_list.remove(0);
		if(act_list.isEmpty())	// once there are no possible actions, we need to ban that state 
		{
			listBannedState.add(new State(s));
			posActions.remove(s.identifier);
			removeStateFromList(s);
			listStateId.remove((Integer)s.identifier);
		}
		return a;
	}
	
	// Returns whether simulating a on s results in a dead-end state (no possible actions)
	private boolean dynamicState(State s, Action a) {
		State tmp = simulate(s, a);
		return (stateInList(tmp, listBannedState) == -1);
	}

	// Removes the given state s from the met state list
	private void removeStateFromList(State s) {
		State tmp;
		for(int i = 0; i < listState.size(); i++)
		{
			tmp = new State(s);
			tmp.subtract(listState.get(i));
			if(tmp.pList.isEmpty())
			{
				listState.remove(i);
				return;
			}
		}
	}

	// Returns a list of actions ordered from best to worst
	//  -  actions do not result in an already met state
	//  -  actions will not attempt to move a wall
	public LinkedList<Action> getActions(State s)
	{
		LinkedList<Action> action_list = new LinkedList<Action>();
		LinkedList<Integer> preference_list = new LinkedList<Integer>();
		State preCond;
		Predicate p;
		PPosition pp;
		
//		for(int i = 0; i < getListSize(); i++)
		
//		System.out.print("s: ");
//		for(int i = 0; i < s.pList.size(); i++)
//			System.out.print("[" + i + "]" + s.pList.get(i).toString() + ", ");
//		System.out.println();
//		System.out.println("174: " + s.pList.get(174) + ",   176: " + s.pList.get(176) + ",   178: " + s.pList.get(178));
		
		for(int i = 0; i < s.pList.size(); i++)
		{
			p = s.pList.get(i);
//			System.out.println("i = " + i + ", p = " + p);
			if(p.getClass() == PPosition.class)
			{
				int pref;
				pp = (PPosition)p;
//				Furniture furn = board.getFurnitureById(((PPosition)p).furn_id);
				Furniture furn = new Furniture(pp.x, pp.y, pp.width, pp.length, pp.furn_id, pp.state);
//				System.out.println("i = " + i + ", furn_id = " + ((PPosition)p).furn_id);
				Action[] act_array =    {new AMove	(furn, Move.RIGHT),
										 new AMove	(furn, Move.DOWN ),
										 new AMove	(furn, Move.LEFT ),
										 new AMove	(furn, Move.UP	 ),
										 new ARotate(furn, true		 ),
										 new ARotate(furn, false	 )};
				
				
				// transform act_array into a list of actions
				LinkedList<Action> tmp_list = new LinkedList<Action>();
				for(int j = 0; j < 6; j++)
					tmp_list.add(act_array[j]);
				
				// randomize list
				LinkedList<Action> rand_list = new LinkedList<Action>();
				Random rand  = new Random();
				for(int j = 0; j < 6; j++)
				{
					int index = rand.nextInt(tmp_list.size());
					rand_list.add(tmp_list.remove(index));
				}
				
				// transform list back to an array
				for(int j = 0; j < 6; j++)
					act_array[j] = rand_list.get(j);
				
				
				// add actions to main list according to met terms
				for(int j = 0; j < 6; j++)
				{
//					System.out.print("act_array[" + j + "]=" + act_array[j]);
					preCond = new State(act_array[j].getPreCond());
					
//					System.out.print("     , ");
//					
//					System.out.print("preCond: ");
//					for(int k = 0; k < preCond.pList.size(); k++)
//						System.out.print(preCond.pList.get(k).toString() + ", ");
//
//					System.out.print("     , ");
					
//					System.out.println("preCond for " + act_array[j]);
					boolean flag = true;
					for(int k = 0; k < preCond.pList.size() && flag; k++)
					{
						Predicate tmp_p = preCond.pList.get(k);
						if(tmp_p.getClass() == PEmpty.class)
						{
//							System.out.println("getContent(" + ((PEmpty)tmp_p).y + "," + ((PEmpty)tmp_p).x + ")=" + getContent(((PEmpty)tmp_p).y, ((PEmpty)tmp_p).x));
							if(getContent(((PEmpty)tmp_p).y, ((PEmpty)tmp_p).x) == -1)	// wall is not empty
								flag = false;
						}
					}
										
//					if(flag && (stateInList(simulate(s, act_array[j]), listState) == -1))
					if(flag)
					{
						pref = evaluate(s, act_array[j]);
						insertSorted(action_list, preference_list, act_array[j], pref);
					}
					
					/**
					preCond.subtract(s);
					
//					System.out.print("unmetCond: ");
//					for(int k = 0; k < preCond.pList.size(); k++)
//						System.out.print(preCond.pList.get(k).toString() + ", ");
//
//					System.out.print("     , ");
					
//					preCond = new State(act_array[j].getPreCond()).subtract(s);
					if(preCond.pList.isEmpty())	// action is legal
					{
						pref = evaluate(s, act_array[j]);
//						System.out.print("pref = " + pref);
						insertSorted(action_list, preference_list, act_array[j], pref);
					}
//					System.out.println();
					**/
				}
			}
		}
			
		return action_list;
	}
	
	// Simulates applying action a on state s and returns the result
	public State simulate(State s, Action a) {
		State new_state = new State(s);
		new_state.subtract(a.getDelList());
		new_state.addState(a.getAddList());
		return new_state;
	}
	
	// Returns a heuristic evaluation of applying an action a on state s (the lower the better)
	private int evaluate(State s, Action a) {
		int sum = 0;
		int diff;
		boolean flag;
		Predicate p1, p2 = null;
		PPosition pp1, pp2;
		State tmp_goal = new State(goal_state_copy);
//		State tmp_goal = goal_state_copy;
		
		State tmp_state = simulate(s, a);
		
//		if(tmp_goal.contains(a.furn.id) < 0)	// applying an action on a satisfied furniture
//		{
//			tmp_goal.addState(a.getDelList());
//			tmp_goal.subtract(a.getAddList());
//		}
		
		
		// unmet preconditions aren't preferred
		State unMetPreCond = new State(a.getPreCond());
		unMetPreCond.subtract(s);
		sum += 5 * unMetPreCond.pList.size();
		
		pp1 = (PPosition) s.pList.get(s.contains(a.furn.id));
		pp2 = (PPosition) tmp_goal.pList.get(tmp_goal.contains(a.furn.id));
				
		// touching an already perfectly positioned furniture might be a bad idea
		if(getPosDiff(pp1, pp2) + getStateDiff(pp1.state, pp2.state) == 0)
		{
//			System.out.println("I'm in goal ?! - " + a.furn.id);
			sum += 100;
		}
//		
//		pp1 = (PPosition) tmp_state.pList.get(tmp_state.contains(a.furn.id));
//		
//		sum += getPosDiff(pp1, pp2) + getStateDiff(pp1.state, pp2.state);
//		
//		System.out.println("sum = " + sum);
		
		
		
//		System.out.println();
//		System.out.print("tmp_state(" + tmp_state.pList.size() + "): ");
//		for(int k = 0; k < tmp_state.pList.size(); k++)
//			System.out.print(tmp_state.pList.get(k).toString() + ", ");
//		System.out.println();
		
		// calculate differences between each furniture's current position and its goal position 
		for(int i = 0; i < tmp_state.pList.size() ; i++)
		{
			int j = 0;
			p1 = tmp_state.pList.get(i);
			if(p1.getClass() == PPosition.class)	// diff is only relevant to PPosition
			{
				flag = true;
//				System.out.print("[" + i + "]" + p1 + ", ");
				while(j < tmp_goal.pList.size() && flag)
				{
					p2 = tmp_goal.pList.get(j);
//					System.out.print("[" + j + "]" + p2 + ", ");
					if((p1.getClass() == p2.getClass()) && (((PPosition)p1).furn_id == ((PPosition)p2).furn_id))
					{
//						System.out.print("[furn_id=" + ((PPosition)p1).furn_id + "]");
						flag = false;
					}
					else
						j++;
				}
				if(j < tmp_goal.pList.size()) // matching PPosition was found
				{
//					System.out.print("[furn_id=" + ((PPosition)p1).furn_id + "]");
//					System.out.print("[centerx1=" + (((PPosition)p1).x + (((PPosition)p1).width)/2.0) + ", centerx2=" + (((PPosition)p2).x + (((PPosition)p2).width)/2.0) + "]");
//					diff = (int) Math.pow( (((PPosition)p1).x + (((PPosition)p1).width)/2.0) - (((PPosition)p2).x + (((PPosition)p2).width)/2.0), 2);
//					System.out.print("[centery1=" + (((PPosition)p1).y + (((PPosition)p1).length)/2.0) + ", centery2=" + (((PPosition)p2).y + (((PPosition)p2).length)/2.0) + "]");
//					diff += (int) Math.pow( (((PPosition)p1).y + (((PPosition)p1).length)/2.0) - (((PPosition)p2).y + (((PPosition)p2).length)/2.0), 2);
					diff = getPosDiff((PPosition)p1, (PPosition)p2);
					diff += getStateDiff(((PPosition)p1).state,((PPosition)p2).state);
					if(diff == 0)	// a furniture is positioned perfectly
						diff = -10;
					sum += 10 * diff;
				}
			}
		}
		
		return sum;
	}

	// Returns the positional difference between two PPositions - based on dimensions 
	private int getPosDiff(PPosition p1, PPosition p2) {
		int diff_x, diff_y;
		
		double center_x1, center_x2;
		double center_y1, center_y2;
		
		center_x1 = p1.x + (p1.width  / 2.0);
		center_y1 = p1.y + (p1.length / 2.0);
		center_x2 = p2.x + (p2.width  / 2.0);
		center_y2 = p2.y + (p2.length / 2.0);
		
//		System.out.println("center_x1: " + center_x1 + ", " + "center_y1: " + center_y1 + ", " + "center_x2: " + center_x2 + ", " + "center_y2: " + center_y2);
		
		diff_x = (int) Math.abs(center_x1 - center_x2);
		diff_y = (int) Math.abs(center_y1 - center_y2);
		
		if((p1.x < 12 && p2.x >= 12) || (p1.x >= 12 && p2.x < 12))
//		if((p1.x - 12)*(p2.x - 12) < 0)		// different rooms
		{
			// add distance towards door
			if(center_y1 < 2 && center_y2 < 2)		// both above the door
				diff_y = (int) (Math.abs(center_y1 - 2) + Math.abs(center_y2 - 2)); 
			if(center_y1 > 4 && center_y2 > 4)		// both under the door
				diff_y = (int) (Math.abs(center_y1 - 4) + Math.abs(center_y2 - 4));
		}
			
		return diff_x + diff_y;
	}

	// Returns the directional difference between two PPositions - based on state 
	private int getStateDiff(Move s1, Move s2) {
		if(s1 == s2)
			return 0;
		switch (s1) {
		case RIGHT:
			if(s2 == Move.UP || s2 == Move.DOWN)
				return 1;
			break;
		case DOWN:
			if(s2 == Move.RIGHT || s2 == Move.LEFT)
				return 1;
			break;
		case LEFT:
			if(s2 == Move.DOWN || s2 == Move.UP)
				return 1;
			break;
		case UP:
			if(s2 == Move.LEFT || s2 == Move.RIGHT)
				return 1;
			break;
		}
		return 2;
	}

	// Inserts action act sorted into list a_list, based on its preference pref and the preference list p_list
	public void insertSorted(LinkedList<Action> a_list, LinkedList<Integer> p_list, Action act, int pref)
	{
		int i = 0;
		while(i < p_list.size() && p_list.get(i) <= pref)
			i++;
		
		p_list.add(i, pref);
		a_list.add(i, act);
		
//		System.out.println();
//		System.out.print("a_list: ");
//		for(i = 0; i < p_list.size(); i++)
//			System.out.print(i + ")" + a_list.get(i) + "[" + p_list.get(i) + "] ,   ");
//		System.out.println();
	}
	
	// Performs the next step of the STRIPS algorithm (after stack is empty, next step clears up data)
	public void stepByStep() {
		if(firstStep)
		{
			listAction.clear();
			goal_state_copy = new State(goal_state);
			s.push(goal_state_copy);
			firstStep = false;

			sgui.paint(sgui.getGraphics());
			return;
		}
		
		if(s.isEmpty())		// algorithm has ended with results
		{
			firstStep = true;	// make the algorithm runnable again
			
			// clear data
			listState.clear();
			listStateId.clear();
			listBannedState.clear();
			posActions.clear();
//			bannedPredicates.pList.clear();
			
			// remove unnecessary actions from listAction
			fixPlan();
		}

		Object item;
		if(!s.isEmpty())
		{
			item = s.pop();
			// handling an action
			if(item.getClass() == AMove.class || item.getClass() == ARotate.class)
			{
//				System.out.println("handling action " + item.toString());
				Action a = (Action) item;
				if(perform)		// if we wish to display changes in board on-the-run
					if(item.getClass() == AMove.class) {
						AMove am = (AMove) a;
						board.doMove(a.furn.id, am.direction);
					} else if(item.getClass() == ARotate.class)	{
						ARotate ar = (ARotate) a;
						board.doRotate(a.furn.id, ar.clockwise);
					}
				
				//TODO
				
//				System.out.print("a.getDelList: ");
//				for(int i = 0; i < a.getDelList().pList.size(); i++)
//					System.out.print(a.getDelList().pList.get(i).toString() + ", ");
//				System.out.println();
//				
//				System.out.print("a.getAddList: ");
//				for(int i = 0; i < a.getAddList().pList.size(); i++)
//					System.out.print(a.getAddList().pList.get(i).toString() + ", ");
//				System.out.println();3
				
//
//				System.out.print("(BEFORE) initial_state: ");
//				for(int i = 0; i < initial_state.pList.size(); i++)
//					System.out.print(initial_state.pList.get(i).toString() + ", ");
//				System.out.println();
				
				
				// apply the action to the current state
				initial_state.subtract(a.getDelList());
				initial_state.addState(a.getAddList());
												
//				goal_state_copy.subtract(a.getDelList());
//				goal_state_copy.addState(a.getAddList());
				
//				if(!goal_state_copy.contains(a.furn.id))	// applying an action on a satisfied furniture
//				{
//					goal_state_copy.addState(a.getDelList());
//					goal_state_copy.subtract(a.getAddList());
//				}
				

//				System.out.print("(AFTER) initial_state: ");
//				for(int i = 0; i < initial_state.pList.size(); i++)
//					System.out.print(initial_state.pList.get(i).toString() + ", ");
//				System.out.println();
//				
//				if(!initial_state.contains(new PPosition(2,2,5,1,1,Move.UP)))
//					System.out.println("WTF MAN !?");
//				else
//					System.out.println("VERY GOOD");
				
				listAction.add(a);		// record the action
			}
			// handling a state
			if(item.getClass() == State.class)
			{
//				System.out.println("handling state: ");
//				for(int i = 0; i < ((State)item).pList.size(); i++)
//					System.out.print(((State)item).pList.get(i).toString() + ", ");
//				System.out.println();
				
				State tmp;
				
				/** Hardcore abruptive cut **/
				// if we already reached our goal - don't entangle yourself with subgoals
				tmp = new State(initial_state);
				tmp.subtract(goal_state_copy);
				if(tmp.pList.isEmpty())
				{
					while(!s.isEmpty())
						s.pop();
					return;
				}
				
				/**
				// remove handled predicates that have been fully satisfied
				int i = 0;
				while(i < bannedPredicates.pList.size() && bannedPredicates.pList.get(i).p_id < s.size())
					i++;
				bannedPredicates.pList.subList(i, bannedPredicates.pList.size()).clear();
				**/
				
				tmp = new State((State)item);
				tmp.subtract(initial_state);
//				((State)item).subtract(initial_state);
				if(!tmp.pList.isEmpty())	// unmet conditions
//				if(!(((State)item).pList.isEmpty()))
				{
					s.push(item);			// push current goal to stack (met and unmet conditions)
					randomizedPush(tmp);	// push only unmet conditions as predicates
					
//					System.out.print("stack s: ");
//					Stack<Object> s2 = new Stack<Object>();
//					while(!s.isEmpty())
//						s2.push(s.pop());
//					while(!s2.isEmpty())
//					{
//						Object o = s2.pop();
//						System.out.print(o + ", ");
//						s.push(o);
//					}
//					System.out.println();

				}
//				else
//					System.out.println("state is completely satisfied");
			}
			// handling a predicate
			if(item.getClass() == PEmpty.class || item.getClass() == PPosition.class)
			{
				Predicate p = (Predicate)item;
//				System.out.println("handling predicate " + item.toString());
				if(initial_state.contains(p) < 0)	// predicate is not satisfied in the current state
				{
					/**
					if(bannedPredicates.contains(p) < 0)	// predicate hasn't been handled before
						bannedPredicates.addPredicate(p);
					else									// predicate has been handled before
					{
						// restore stack to the first occurrence of the handled predicate 
						while(s.size() > p.p_id)
							s.pop();
						
						// undo any applied actions ever since in order to restore initial_state
						while(listAction.size() > p.act_list_size)
						{
							Action a = getReverseAction(listAction.removeLast());
							if(perform)		// if we wish to display changes in board on-the-run
								if(item.getClass() == AMove.class) {
									AMove am = (AMove) a;
									board.doMove(a.furn.id, am.direction);
								} else if(item.getClass() == ARotate.class)	{
									ARotate ar = (ARotate) a;
									board.doRotate(a.furn.id, ar.clockwise);
								}
							initial_state.subtract(a.getDelList());
							initial_state.addState(a.getAddList());
						}

						// remove all states that have been removed from the stack
						int index = stateInList(initial_state, listState);
						if(index != -1)
						{
							listState.subList(index + 1, listState.size()).clear();
//							posActions.get(listState.get(index).identifier);
						}
						
						// technically we need to fix pos_actions and listBannedState as sell 
						//  we can do this by saving for each action its pref ...
					}
					**/
						
//					System.out.println("predicate " + item.toString() + " calls heuristic");
					Action a = heuristic((Predicate)item, initial_state);		// retreive the best action possible for this predicate
//					System.out.println("a = " + a);
					
//					System.out.print("a.getPreCond: ");
//					State preCond = new State(a.getPreCond());
//					for(int k = 0; k < preCond.pList.size(); k++)
//						System.out.print(preCond.pList.get(k).toString() + ", ");
//					System.out.println();
					
					s.push(a);						// push the action to the stack
					s.push(a.getPreCond());			// and its preconditions as well
				}
			}
		}

		// paint any necessary changes
		gui.paint(gui.getGraphics());
		sgui.paint(sgui.getGraphics());
		
//		Action a = listAction.remove(0);
//		if(a.getClass() == AMove.class)
//			doMove(a.furn.id, ((AMove)a).direction);
//		else if(a.getClass() == ARotate.class)
//			doRotate(a.furn.id, ((ARotate)a).clockwise);
	}
	
	// Fixes the plan in the manner of removing two adjacent actions which cancel each other
	public void fixPlan()
	{
		for(int i = 0; i < listAction.size(); i++)
			if(i + 1 < listAction.size())
			{
				Action a1 = listAction.get(i);
				Action a2 = listAction.get(i + 1);
				if(a1.getClass() == a2.getClass())
				{
//					System.out.println("same class is: " + a1.getClass());
//					System.out.println("a1 = " + a1);
//					System.out.println("reverseAction(a2) = " + getReverseAction(a2));
					if((a1.getClass() == AMove.class) && ((AMove)a1).equals((AMove)getReverseAction(a2)))
					{
//						System.out.println("equals");
						listAction.remove(i + 1);
						listAction.remove(i);
						i--;
					}
					if((a1.getClass() == ARotate.class) && ((ARotate)a1).equals((ARotate)getReverseAction(a2)))
					{
						listAction.remove(i + 1);
						listAction.remove(i);
						i--;
					}
				}
				
			}
	}

	// Returns the reverse action to a (an action which cancels it)
	private Action getReverseAction(Action a) {
		if(a.getClass() == AMove.class) {
			switch (((AMove)a).direction) {
			case RIGHT:
				return new AMove(a.furn, Move.LEFT);
			case DOWN:
				return new AMove(a.furn, Move.UP);
			case LEFT:
				return new AMove(a.furn, Move.RIGHT);
			case UP:
				return new AMove(a.furn, Move.DOWN);
			}
		} else if(a.getClass() == ARotate.class) {
			return new ARotate(a.furn, !((ARotate)a).clockwise);
		}
		return null;
	}
	
	// Pushes the predicates in state item into the stack in a randomized order
	private void randomizedPush(State item)
	{
		State tmp = new State(item);
		Random rand = new Random();
		int index;
		
		while(!(tmp.pList.isEmpty()))
		{
			index = rand.nextInt(tmp.pList.size());
			Predicate p = tmp.pList.remove(index);
			p.p_id = s.size();
			p.act_list_size = listAction.size();
			s.push(p);
		}
	}
	
	// Returns the index in which state s is found in the given state-list list (if not found returns (-1))
	public int stateInList(State s, LinkedList<State> list)
	{
		State tmp;
		for(int i = 0; i < list.size(); i++)
		{
			tmp = new State(s);
			tmp.subtract(list.get(i));
			if(tmp.pList.isEmpty())		// found a match
				return i;
		}
		return -1;
	}

	// If we didn't display any action on-the-run, then execute and display (on board) all actions in the list
	public void executeActions() {
		Action a;
		for(int i = 0; i < listAction.size(); i++)
		{
			a = listAction.get(i);
			if(a.getClass() == AMove.class)
				board.doMove(a.furn.id, ((AMove)a).direction);
			else if(a.getClass() == ARotate.class)
				board.doRotate(a.furn.id, ((ARotate)a).clockwise);
			
			gui.paint(gui.getGraphics());
		}
	}
}
