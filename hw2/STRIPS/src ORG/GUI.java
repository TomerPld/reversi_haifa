import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Random;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;


public class GUI extends JPanel implements MouseListener, MouseMotionListener, ActionListener
{	
	Game game;

	boolean fur_create = false;
	int prs_x, prs_y;
	int lst_clk_x, lst_clk_y;
	int prv_clk_x, prv_clk_y;
	int offset_x, offset_y;
	int tmp_id;
	boolean isStack;
	Random rnd;

	
	JTextArea sstack;
	
	JTextArea title;
//	JTextArea boardShown;
	JButton btnCreate;
	JButton btnDelete;
	JButton btnRotateCW;
	JButton btnRotateCCW;
	JButton btnMoveRight;
	JButton btnMoveDown;
	JButton btnMoveLeft;
	JButton btnMoveUp;
	JButton btnSwitchDisp;
	JButton btnShowSolution;
	
	Color furn_bg, furn_bg_trans;
	Color furn_sel, furn_sel_trans;
	Color transparent;
	Font f_title;
	Font id_font;
	Font subtitle;
	
//	String stack_content;
	
	public GUI(Game instance)
	{
		isStack = false;
		this.setLayout(null);
		addMouseListener(this);
		addMouseMotionListener(this);
		
//		bg = new BoardGUI(this);
//		mg = new MenuGUI(this);
//		bg.setIgnoreRepaint(false);
//		mg.setIgnoreRepaint(false);
//
//		this.setComponentZOrder(bg, 0);
//		this.setComponentZOrder(mg, 1);
		
		game = instance;
		
		furn_bg			= new Color((float)0, (float)1, (float)1, (float)0.5);
		furn_bg_trans	= new Color((float)0, (float)1, (float)1, (float)0.1);
		furn_sel		= new Color((float)1, (float)1, (float)0, (float)0.5);
		furn_sel_trans	= new Color((float)1, (float)1, (float)0, (float)0.1);
		transparent 	= new Color((float)0, (float)0, (float)0, (float)0  );
		
		f_title = new Font("Calibri", Font.BOLD | Font.ITALIC, 50);
		id_font = new Font("Calibri", Font.BOLD, 20);
		subtitle = new Font("Calibri", Font.ITALIC, 20);
		
		// title - STRIPS
		title = new JTextArea("STRIPS");
		title.setBounds(655, 350, 140, 50);
		title.setEditable(false);
		title.setBackground(transparent);
		title.setForeground(Color.white);
		title.setFont(f_title);
		title.setFocusable(false);

		// boardShown - indicates whether normal or goal board are currently shown
//		boardShown = new JTextArea("normal board");
//		boardShown.setBounds(815, 375, 120, 50);
//		boardShown.setEditable(false);
//		boardShown.setBackground(transparent);
//		boardShown.setForeground(Color.white);
//		boardShown.setFont(subtitle);
//		boardShown.setFocusable(false);
		
		// btnCreate - New furniture button
		btnCreate = new JButton("<html><center>Create Furniture</center></html>");
		btnCreate.setBounds(655, 405, 90, 40);
		btnCreate.addActionListener(this);
		btnCreate.setFocusable(false);
		
		// btnDelete - Delete furniture button
		btnDelete = new JButton("<html><center>Delete Furniture</center></html>");
		btnDelete.setBounds(755, 405, 90, 40);
		btnDelete.addActionListener(this);
		btnDelete.setFocusable(false);
		
		// btnRotateCW - Rotate clockwise furniture button
		btnRotateCW = new JButton("<html><center>Rotate CW</center></html>");
		btnRotateCW.setBounds(855, 405, 90, 40);
		btnRotateCW.addActionListener(this);
		btnRotateCW.setFocusable(false);
		
		// btnRotateCCW - Rotate counter-clockwise furniture button
		btnRotateCCW = new JButton("<html><center>Rotate CCW</center></html>");
		btnRotateCCW.setBounds(855, 455, 90, 40);
		btnRotateCCW.addActionListener(this);
		btnRotateCCW.setFocusable(false);
		
		// btnMoveRight - Move right furniture button
		btnMoveRight = new JButton("<html><center> &gt; </center></html>");
		btnMoveRight.setBounds(1055, 455, 40, 40);
		btnMoveRight.addActionListener(this);
		btnMoveRight.setFocusable(false);

		// btnMoveDown - Move down furniture button
		btnMoveDown = new JButton("<html><center> \\/ </center></html>");
		btnMoveDown.setBounds(1005, 455, 40, 40);
		btnMoveDown.addActionListener(this);
		btnMoveDown.setFocusable(false);

		// btnMoveLeft - Move left furniture button
		btnMoveLeft = new JButton("<html><center> &lt; </center></html>");
		btnMoveLeft.setBounds(955, 455, 40, 40);
		btnMoveLeft.addActionListener(this);
		btnMoveLeft.setFocusable(false);

		// btnMoveUp - Move right furniture button
		btnMoveUp = new JButton("<html><center> /\\ </center></html>");
		btnMoveUp.setBounds(1005, 405, 40, 40);
		btnMoveUp.addActionListener(this);
		btnMoveUp.setFocusable(false);
		
		// btnSwitchDisp - Switch board/goal_board display button
		btnSwitchDisp = new JButton("<html><center>Switch Display</center></html>");
		btnSwitchDisp.setBounds(655, 455, 90, 40);
		btnSwitchDisp.addActionListener(this);
		btnSwitchDisp.setFocusable(false);
	
		// btnShowSolution - Presents the stack of the execution actions
		btnShowSolution = new JButton("<html><center>Show Solution</center></html>");
		btnShowSolution.setBounds(755, 455, 90, 40);
		btnShowSolution.addActionListener(this);
		btnShowSolution.setFocusable(false);
	
		
//        this.setLayout(new BorderLayout());
//        this.add(bg, BorderLayout.CENTER);
//        this.add(mg, BorderLayout.EAST);
        
	}
	
	
	public GUI(String stextarea)
	{
		this.setLayout(null);
		isStack = true;
//		stack_content = stextarea;
		sstack = new JTextArea(stextarea);
		sstack.setBounds(5, 5, 290, 590);
		sstack.setEditable(false);
		sstack.setBackground(transparent);
		sstack.setForeground(Color.black);
		sstack.setFont(new Font("Calibri", Font.BOLD | Font.ITALIC, 20));
		sstack.setFocusable(false);
		//stextarea = "Noam " + '\n' + "Hammer";
//		sstack.setText(stextarea);
		//sstack.setText("4334");
		
	}
	
	
	// Paints furniture
	public void paint(Graphics g)
	{
		if(isStack)
		{
			this.add(sstack);
			this.paintComponents(g);
//			g.setColor(Color.black);
//			g.setFont(id_font);
//			g.drawString("<html>" + stack_content + "<br/>" + "yeah </html>", 5, 15);
			return;
		}
			
		Furniture furn;
		paintStatic(g);
		rnd = new Random();
		
//		int lastind = f.lastIndexOf(f.lastElement());
//		System.out.println("Last index = " + lastind);
		
		for(int i = 0; i<getListSize(); i++)
//		for(int i = 0; i<getVectorSize(); i++)
		{
			furn = getFurnitureById(getListElement(i));
			if(furn != null)
			{
				if(furn.id == tmp_id)
					g.setColor(furn_sel);
				else
					g.setColor(furn_bg);
							
				g.fillRect(furn.cornerx * 50, furn.cornery * 50, furn.width * 50, furn.length * 50);
				
				g.setColor(Color.black);
				g.setFont(id_font);
				g.drawString(Integer.toString(furn.id), furn.cornerx * 50 + furn.width * 25 - 5 * (int)(Math.log10(furn.id) + 1), furn.cornery * 50 + furn.length * 25 + 7);
//				furn.text.setBounds(furn.cornerx * 50 + furn.width * 25 - 5 * (int)(Math.log10(furn.id) + 1), furn.cornery * 50 + furn.length * 25 - 10, 30, 20);
								
				g.setColor(Color.black);			
				g.drawRect(furn.cornerx*50, furn.cornery*50, furn.width*50, furn.length*50);
			}
		}
		
		game.goal = game.goal ^ true;
		
		for(int i = 0; i<getListSize(); i++)
		{
			furn = getFurnitureById(getListElement(i));
			if(furn != null)
			{
				if(furn.id == tmp_id)
					g.setColor(furn_sel_trans);
				else
					g.setColor(furn_bg_trans);
							
				g.fillRect(furn.cornerx * 50, furn.cornery * 50, furn.width * 50, furn.length * 50);
				
				g.setColor(new Color((float)0,(float)0,(float)0,(float)0.4));
				g.setFont(id_font);
				g.drawString(Integer.toString(furn.id), furn.cornerx * 50 + furn.width * 25 - 5 * (int)(Math.log10(furn.id) + 1), furn.cornery * 50 + furn.length * 25 + 7);
								
				g.setColor(new Color((float)0,(float)0,(float)0,(float)0.4));			
				g.drawRect(furn.cornerx*50, furn.cornery*50, furn.width*50, furn.length*50);
			}
		}

		game.goal = game.goal ^ true;
		
		this.paintComponents(g);
		
		// paint temporary furniture (during creation)
//		g.setColor(Color.red);
//		g.fillRect(tmp_furn.cornerx*50, tmp_furn.cornery*50, tmp_furn.width*50, tmp_furn.length*50);
//		g.setColor(Color.black);			
//		g.drawRect(tmp_furn.cornerx*50, tmp_furn.cornery*50, tmp_furn.width*50, tmp_furn.length*50);
		
		/** print board as text TODO **/
//		for(int i=0; i<13; i++)
//		{
//			for(int j=0; j<24; j++)
//			{
//				if(getContent(i, j)>=0)
//					System.out.print(" ");
//				System.out.print(getContent(i, j) + ", ");
//			}
//			System.out.println(getContent(i, 24));
//		}
//		System.out.println();
	}
	
	// Paints menu and static board objects
	public void paintStatic(Graphics g)
	{
		/** display board **/
		g.setColor(Color.white);
		g.fillRect(0, 0, (int)getSize().getWidth(), (int)getSize().getHeight());
		for(int j = 0; j<25; j++)
			for(int i = 0; i<13; i++)
			{
				g.setColor(Color.lightGray);
				g.drawRect(j*50, i*50, 50, 50);
			}
				
		// display tiles
		for(int j = 0; j<25; j++)
			for(int i = 0; i<13; i++)
				if(getContent(i,j)==-1)	// Walls
				{
					g.setColor(Color.gray);
					g.fillRect(j*50, i*50, 50, 50);
					g.setColor(Color.black);
					g.drawRect(j*50, i*50, 50, 50);
				}
		
		/** display menu **/
		g.setColor(Color.darkGray);
		g.fillRect(650, 350, 550, 250);
		
		// subtitle - indicates whether normal or goal board are currently shown
		g.setColor(Color.white);		
		g.setFont(subtitle);
		g.drawString(game.goal ? "goal board" : "initial board", 815, 395);
		
		
		this.add(title);
		this.add(btnCreate);
		this.add(btnDelete);
		this.add(btnRotateCW);
		this.add(btnRotateCCW);
		this.add(btnMoveRight);
		this.add(btnMoveDown);
		this.add(btnMoveLeft);
		this.add(btnMoveUp);
		this.add(btnSwitchDisp);
		this.add(btnShowSolution);

//		this.paintComponents(g);
	}
	
	private int getContent(int i, int j) {
		return game.getContent(i,j);
	}
	
	private int addFurniture(int x, int y, int width, int length) {
		return game.addFurniture(x, y, width, length);
	}
	
//	private Furniture getFurnitureAt(int index) {
//		return game.getFurnitureAt(index);
//	}
	
	private void deleteFurniture(int id) {
		game.deleteFurniture(id);
	}
	
//	public int getVectorSize()
//	{
//		return game.getVectorSize();
//	}
	
//	public int getLastId(){
//		return game.getLastId();
//	}
	
	public int getListSize(){
		return game.getListSize();
	}
	
	public int getListElement(int index){
		return game.getListElement(index);
	}
	
//	public int getLastVectorIndex() {
//		return game.getLastVectorIndex();
//	}
	
	public void updateFurniture(int id, int x, int y, int width, int length, Furniture frame, boolean both)
	{
		game.updateFurniture(id, x, y, width, length, frame, both);
	}

	public boolean canPlace(int x, int y, int width, int length, int excp_id) {
		return game.canPlace(x, y, width, length, excp_id);
	}
	
//	public boolean canMove(Furniture furn, Move direction) {
//		return game.canMove(furn, direction);
//	}
	
	public boolean doMove(int furn_id, Move direction) {
		return game.doMove(furn_id, direction);
	}
	
	public boolean canRotate(Furniture furn, boolean clockwise) {
		return game.canRotate(furn, clockwise);
	}

	public boolean doRotate(int furn_id, boolean cw) {
		return game.doRotate(furn_id,cw);
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON3)	// MOUSE2: "Right-click" was made
		{
			System.out.println("MOUSE3");
			tmp_id = -1;
			fur_create = false;
		}
		
		prs_x = e.getX()/50;
		prs_y = e.getY()/50;
		
		lst_clk_x = prs_x;
		lst_clk_y = prs_y;
		
		tmp_id = getContent(prs_y, prs_x);
				
		if(fur_create)	// creating a new furniture at pressed location
			if(tmp_id == 0)	// and location is empty
				tmp_id = addFurniture(prs_x, prs_y, 1, 1);
			else
				tmp_id = -1;
		else
			if(tmp_id > 0)
			{
				Furniture furn = getFurnitureById(tmp_id);
				offset_x = prs_x - furn.cornerx;
				offset_y = prs_y - furn.cornery;
				System.out.println("offset_x = " + offset_x + ", offset_y = " + offset_y);
			}
		
		System.out.println("tmp_id = " + tmp_id);
//		if(fur_create)	// creating a new furniture at pressed location
//			tmp_id = addFurniture(prs_x, prs_y, 1, 1);
		paint(getGraphics());
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		paint(getGraphics());
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		if((tmp_id < 1) || (e.getX()/50 > 24) || (e.getY()/50 > 12))
			return;

		Furniture frame;
		int content;
		
		prv_clk_x = lst_clk_x;
		prv_clk_y = lst_clk_y;
		
		lst_clk_x = e.getX()/50;
		lst_clk_y = e.getY()/50;
		
		if((lst_clk_x == prv_clk_x) && (lst_clk_y == prv_clk_y))	// repaint conditions
			return;
		
		if((content = getContent(lst_clk_y, lst_clk_x)) != 0 && content != tmp_id)
		{
			lst_clk_x = prv_clk_x;
			lst_clk_y = prv_clk_y;
			return;
		}
		
		if(fur_create)
		{
			int prv_diff_x = Math.abs(prv_clk_x - prs_x);
			int prv_diff_y = Math.abs(prv_clk_y - prs_y);
			
			int lst_diff_x = Math.abs(lst_clk_x - prs_x);
			int lst_diff_y = Math.abs(lst_clk_y - prs_y);
			
			//			if((lst_diff_x > prv_diff_x) || (lst_diff_y > prv_diff_y))	// last click has affected a larger area
//			{
//				// validating selection
//				if(!canPlace(Math.min(lst_clk_x, prs_x), Math.min(lst_clk_y, prs_y), lst_diff_x + 1, lst_diff_y + 1, furn.id))
//				{
//					System.out.println("Can't place id " + furn.id + " at:");
//					System.out.println("X=" + Math.min(lst_clk_x, prs_x) + ", Y=" + Math.min(lst_clk_y, prs_y) + ", Width=" + (lst_diff_x + 1) + ", Length=" + (lst_diff_y + 1));
//					return;
//				}
//				
//				// if valid, update furniture
//				furn.cornerx = Math.min(lst_clk_x, prs_x);
//				furn.cornery = Math.min(lst_clk_y, prs_y);
//				furn.width = lst_diff_x + 1;
//				furn.length = lst_diff_y + 1;
//			}
//			else														// previous click has affected a larger area
//			{
//				// validating selection
//				if(!canPlace(Math.min(prv_clk_x, prs_x), Math.min(prv_clk_y, prs_y), prv_diff_x + 1, prv_diff_y + 1, furn.id))
//				{
//					System.out.println("Can't place id " + furn.id + " at:");
//					System.out.println("X=" + Math.min(lst_clk_x, prs_x) + ", Y=" + Math.min(lst_clk_y, prs_y) + ", Width=" + (lst_diff_x + 1) + ", Length=" + (lst_diff_y + 1));
//					return;
//				}
//				
//				furn.cornerx = Math.min(lst_clk_x, prs_x);
//				furn.cornery = Math.min(lst_clk_y, prs_y);
//				furn.width = lst_diff_x + 1;
//				furn.length = lst_diff_y + 1;
//			}
			
			// validating selection
			if(!canPlace(Math.min(lst_clk_x, prs_x), Math.min(lst_clk_y, prs_y), lst_diff_x + 1, lst_diff_y + 1, tmp_id))
			{
				System.out.println("Can't place id " + tmp_id + " at:");
				System.out.println("X=" + Math.min(lst_clk_x, prs_x) + ", Y=" + Math.min(lst_clk_y, prs_y) + ", Width=" + (lst_diff_x + 1) + ", Length=" + (lst_diff_y + 1));
				lst_clk_x = prv_clk_x;
				lst_clk_y = prv_clk_y;
				return;
			}
			
			// if selection got smaller, must repaint background
			frame = new Furniture(Math.min(prv_clk_x, prs_x), Math.min(prv_clk_y, prs_y), prv_diff_x + 1, prv_diff_y + 1, 0, Move.UP);
			
			updateFurniture(tmp_id, Math.min(lst_clk_x, prs_x), Math.min(lst_clk_y, prs_y), lst_diff_x + 1, lst_diff_y + 1, frame, true);
		}
		else if(game.goal)
		{
			Furniture furn = getFurnitureById(tmp_id);
			frame = new Furniture(prv_clk_x - offset_x, prv_clk_y - offset_y, furn.width, furn.length, 0, Move.UP);
			if(canPlace(lst_clk_x - offset_x, lst_clk_y - offset_y, furn.width, furn.length, tmp_id))
			{
				System.out.println("Can place at: " + (lst_clk_x - offset_x) + ", " + (lst_clk_y - offset_y) + ", " + furn.width + ", " + furn.length + ", " + tmp_id);
				updateFurniture(tmp_id, lst_clk_x - offset_x, lst_clk_y - offset_y, furn.width, furn.length, frame, false);
			}
			else
			{
				lst_clk_x = prv_clk_x;
				lst_clk_y = prv_clk_y;
			}
		}
		
		paint(getGraphics());
		System.out.println("mouseDragged: X = " + lst_clk_x + ", Y = " + lst_clk_y);
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		fur_create = false;
		if(e.getSource() == btnCreate) {
			fur_create = true;
			System.out.println("actionPerformed: fur_create = " + fur_create);
		} else if(e.getSource() == btnSwitchDisp){
			game.goal = game.goal ^ true;
			
			if(game.goal){
				btnCreate.setVisible(false);
				btnDelete.setVisible(false);
			} else {
				btnCreate.setVisible(true);
				btnDelete.setVisible(true);		
			}	
		} else if (e.getSource() == btnShowSolution){
			drawStack();
		} else if(e.getSource() == btnDelete) {
			deleteFurniture(tmp_id);
		} else if(e.getSource() == btnRotateCW){
			doRotate(tmp_id, true);
		} else if(e.getSource() == btnRotateCCW){
			doRotate(tmp_id, false);
		} else if(e.getSource() == btnMoveRight){
			doMove(tmp_id, Move.RIGHT);
		} else if(e.getSource() == btnMoveDown){
			doMove(tmp_id, Move.DOWN);
		} else if(e.getSource() == btnMoveLeft){
			doMove(tmp_id, Move.LEFT);
		} else if(e.getSource() == btnMoveUp){
			doMove(tmp_id, Move.UP);
		} 
		
		paint(getGraphics());
	}

	private void drawStack() {
		game.drawStack();
	}


	private Furniture getFurnitureById(int id) {
		return game.getFurnitureById(id);
	}

}









