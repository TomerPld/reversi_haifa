import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;


@SuppressWarnings("serial")
public class GUI extends JPanel implements MouseListener, MouseMotionListener, ActionListener
{	
	Game game;

	boolean fur_create = false;
	int prs_x, prs_y;
	int lst_clk_x, lst_clk_y;
	int prv_clk_x, prv_clk_y;
	int offset_x, offset_y;
	int tmp_id;

	
	JTextArea title;
	JButton btnCreate;
	JButton btnDelete;
	JButton btnRotateCW;
	JButton btnRotateCCW;
	JButton btnMoveRight;
	JButton btnMoveDown;
	JButton btnMoveLeft;
	JButton btnMoveUp;
	JButton btnSwitchDisp;
	JButton btnFindSolution;
	JButton btnNextStep;
	JButton btnExecute;

	JRadioButton radioPerform;
	JRadioButton radioAvoid;
	ButtonGroup perform_group;
	
	Icon arrow_cw, arrow_ccw;
	
	Color furn_bg, furn_bg_trans;
	Color furn_sel, furn_sel_trans;
	Color transparent;
	Font f_title;
	Font id_font;
	Font subtitle;
	
	
	// Constructor - initializes and adds components to the GUI
	public GUI(Game instance)
	{
		this.setLayout(null);
		addMouseListener(this);
		addMouseMotionListener(this);
				
		game = instance;
		
		furn_bg			= new Color((float)0, (float)1, (float)1, (float)0.5);
		furn_bg_trans	= new Color((float)0, (float)1, (float)1, (float)0.1);
		furn_sel		= new Color((float)1, (float)1, (float)0, (float)0.5);
		furn_sel_trans	= new Color((float)1, (float)1, (float)0, (float)0.1);
		transparent 	= new Color((float)0, (float)0, (float)0, (float)0  );
		
		f_title = new Font("Calibri", Font.BOLD | Font.ITALIC, 50);
		id_font = new Font("Calibri", Font.BOLD, 20);
		subtitle = new Font("Calibri", Font.ITALIC, 20);
		
		perform_group = new ButtonGroup();
		
		// title - STRIPS
		title = new JTextArea("STRIPS");
		title.setBounds(1005, 0, 140, 50);
		title.setEditable(false);
		title.setBackground(transparent);
		title.setForeground(Color.black);
		title.setFont(f_title);
		title.setFocusable(false);
		
		// btnCreate - New furniture button
		btnCreate = new JButton("<html><center>Create Furniture</center></html>");
		btnCreate.setBounds(1005, 55, 90, 40);
		btnCreate.addActionListener(this);
		btnCreate.setFocusable(false);
		
		// btnDelete - Delete furniture button
		btnDelete = new JButton("<html><center>Delete Furniture</center></html>");
		btnDelete.setBounds(1105, 55, 90, 40);
		btnDelete.addActionListener(this);
		btnDelete.setFocusable(false);
		
		// btnRotateCW - Rotate clockwise furniture button
		arrow_cw = new ImageIcon("images/CW.png");
		btnRotateCW = new JButton();
		btnRotateCW.setBounds(1105, 205, 40, 40);
		btnRotateCW.setIcon(arrow_cw);
		btnRotateCW.setOpaque(false);
		btnRotateCW.setContentAreaFilled(false);
		btnRotateCW.setBorderPainted(false);
		btnRotateCW.addActionListener(this);
		btnRotateCW.setFocusable(false);
		
		// btnRotateCCW - Rotate counter-clockwise furniture button
		arrow_ccw = new ImageIcon("images/CCW.png");
		btnRotateCCW = new JButton();
		btnRotateCCW.setBounds(1005, 205, 40, 40);
		btnRotateCCW.setIcon(arrow_ccw);
		btnRotateCCW.setOpaque(false);
		btnRotateCCW.setContentAreaFilled(false);
		btnRotateCCW.setBorderPainted(false);
		btnRotateCCW.addActionListener(this);
		btnRotateCCW.setFocusable(false);
		
		// btnMoveRight - Move right furniture button
		btnMoveRight = new JButton("<html><center> &gt; </center></html>");
		btnMoveRight.setBounds(1105, 255, 40, 40);
		btnMoveRight.addActionListener(this);
		btnMoveRight.setFocusable(false);

		// btnMoveDown - Move down furniture button
		btnMoveDown = new JButton("<html><center> \\/ </center></html>");
		btnMoveDown.setBounds(1055, 255, 40, 40);
		btnMoveDown.addActionListener(this);
		btnMoveDown.setFocusable(false);

		// btnMoveLeft - Move left furniture button
		btnMoveLeft = new JButton("<html><center> &lt; </center></html>");
		btnMoveLeft.setBounds(1005, 255, 40, 40);
		btnMoveLeft.addActionListener(this);
		btnMoveLeft.setFocusable(false);

		// btnMoveUp - Move right furniture button
		btnMoveUp = new JButton("<html><center> /\\ </center></html>");
		btnMoveUp.setBounds(1055, 205, 40, 40);
		btnMoveUp.addActionListener(this);
		btnMoveUp.setFocusable(false);
		
		// btnSwitchDisp - Switch board/goal_board display button
		btnSwitchDisp = new JButton("<html><center>Switch Display</center></html>");
		btnSwitchDisp.setBounds(1005, 305, 140, 40);
		btnSwitchDisp.addActionListener(this);
		btnSwitchDisp.setFocusable(false);
	
		// btnFindSolution - Presents the stack of the execution actions
		btnFindSolution = new JButton("<html><center>Find Solution</center></html>");
		btnFindSolution.setBounds(1105, 105, 90, 40);
		btnFindSolution.addActionListener(this);
		btnFindSolution.setFocusable(false);

		// btnNextStep - Perform the next step in the algorithm
		btnNextStep = new JButton("<html><center>Next Step</center></html>");
		btnNextStep.setBounds(1005, 105, 90, 40);
		btnNextStep.addActionListener(this);
		btnNextStep.setFocusable(false);

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
		this.add(btnFindSolution);
		this.add(btnNextStep);
	}
		
	
	// Paints furniture
	public void paint(Graphics g)
	{			
		Furniture furn;
		paintStatic(g);
		
		// paint current board's furniture (initial's/goal's) 
		for(int i = 0; i<getListSize(); i++)
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
				g.setColor(Color.black);			
				g.drawRect(furn.cornerx*50, furn.cornery*50, furn.width*50, furn.length*50);
			}
		}
		
		game.goal = game.goal ^ true;
		
		// paint other board's furniture (goal's/initial's)
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
	}
	

	// Paints menu and static board objects
	public void paintStatic(Graphics g)
	{
		/** display board **/
		g.setColor(Color.white);
		g.fillRect(0, 0, (int)getSize().getWidth(), (int)getSize().getHeight());
		for(int j = 0; j<20; j++)
			for(int i = 0; i<12; i++)
			{
				g.setColor(Color.lightGray);
				g.drawRect(j*50, i*50, 50, 50);
			}
				
		//display walls
		g.setColor(Color.black);
		g.drawLine(0, 0, 0*50, 12*50);
		g.drawLine(0, 0, 20*50, 0*50);
		g.drawLine(0, 12*50, 20*50, 12*50);
		g.drawLine(20*50, 0, 20*50, 12*50);

		g.drawLine(8*50, 0, 8*50, 1*50);
		g.drawLine(8*50, 4*50, 8*50, 6*50);
		g.drawLine(0, 5*50, 2*50, 5*50);
		g.drawLine(6*50, 5*50, 8*50, 5*50);
		g.drawLine(8*50, 11*50, 8*50, 12*50);

		/** display menu **/
		g.setColor(Color.darkGray);
		g.fillRect(1001, 0, 205, 12*50);
		
		// subtitle - indicates whether normal or goal board are currently shown
		g.setColor(Color.black);
		g.setFont(subtitle);
		g.drawString(game.goal ? "goal board" : "initial board", 1015, 195);
		
	}
	
	private int getContent(int i, int j) {
		return game.getContent(i,j);
	}
	
	private int addFurniture(int x, int y, int width, int length) {
		return game.addFurniture(x, y, width, length);
	}

	private void deleteFurniture(int id) {
		game.deleteFurniture(id);
	}
	
	public int getListSize(){
		return game.getListSize();
	}
	
	public int getListElement(int index){
		return game.getListElement(index);
	}
		
	public void updateFurniture(int id, int x, int y, int width, int length, Furniture frame, boolean both)
	{
		game.updateFurniture(id, x, y, width, length, frame, both);
	}

	public boolean canPlace(int x, int y, int width, int length, int excp_id) {
		return game.canPlace(x, y, width, length, excp_id);
	}
		
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
		if(e.getButton() == MouseEvent.BUTTON3)	// MOUSE3: "Right-click" was made
		{
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
		else			// remember offset of clicked location from the furniture's upper left corner 
			if(tmp_id > 0)
			{
				Furniture furn = getFurnitureById(tmp_id);
				offset_x = prs_x - furn.cornerx;
				offset_y = prs_y - furn.cornery;
			}
		paint(getGraphics());
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		paint(getGraphics());
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		if((tmp_id < 1) || (e.getX()/50 > 24) || (e.getY()/50 > 12))	// illegal drag
			return;

		Furniture frame;
		int content;
		
		prv_clk_x = lst_clk_x;
		prv_clk_y = lst_clk_y;
		
		lst_clk_x = e.getX()/50;
		lst_clk_y = e.getY()/50;
		
		if((lst_clk_x == prv_clk_x) && (lst_clk_y == prv_clk_y))	// repaint conditions are unmet
			return;
		
		if((content = getContent(lst_clk_y, lst_clk_x)) != 0 && content != tmp_id)	// can't drag furniture to an illegal location
		{
			lst_clk_x = prv_clk_x;
			lst_clk_y = prv_clk_y;
			return;
		}
		
		if(fur_create)		// upon creation, update furniture's dimentions (if drag was legal)
		{
			int prv_diff_x = Math.abs(prv_clk_x - prs_x);
			int prv_diff_y = Math.abs(prv_clk_y - prs_y);
			
			int lst_diff_x = Math.abs(lst_clk_x - prs_x);
			int lst_diff_y = Math.abs(lst_clk_y - prs_y);

			// validating selection
			if(!canPlace(Math.min(lst_clk_x, prs_x), Math.min(lst_clk_y, prs_y), lst_diff_x + 1, lst_diff_y + 1, tmp_id))
			{
				lst_clk_x = prv_clk_x;
				lst_clk_y = prv_clk_y;
				return;
			}
			
			// if selection got smaller, must repaint background
			frame = new Furniture(Math.min(prv_clk_x, prs_x), Math.min(prv_clk_y, prs_y), prv_diff_x + 1, prv_diff_y + 1, 0, Move.UP);
			
			updateFurniture(tmp_id, Math.min(lst_clk_x, prs_x), Math.min(lst_clk_y, prs_y), lst_diff_x + 1, lst_diff_y + 1, frame, true);
		}
		else if(game.goal)	// setting a new goal location 
		{
			Furniture furn = getFurnitureById(tmp_id);
			frame = new Furniture(prv_clk_x - offset_x, prv_clk_y - offset_y, furn.width, furn.length, 0, Move.UP);
			if(canPlace(lst_clk_x - offset_x, lst_clk_y - offset_y, furn.width, furn.length, tmp_id))
			{
				updateFurniture(tmp_id, lst_clk_x - offset_x, lst_clk_y - offset_y, furn.width, furn.length, frame, false);
			}
			else	// collision with another furniture
			{
				lst_clk_x = prv_clk_x;
				lst_clk_y = prv_clk_y;
			}
		}
		
		paint(getGraphics());
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
		} else if(e.getSource() == btnSwitchDisp){
			game.goal = !game.goal;
			if(game.goal){
				btnCreate.setVisible(false);
				btnDelete.setVisible(false);
				btnFindSolution.setVisible(false);
				btnNextStep.setVisible(false);
			} else {
				btnCreate.setVisible(true);
				btnDelete.setVisible(true);		
				btnFindSolution.setVisible(true);
				btnNextStep.setVisible(true);
			}
		} else if(e.getSource() == btnFindSolution){
			executeSTRIPS();
		} else if(e.getSource() == btnNextStep){
			stepByStep();
			if(game.firstStep) {
				btnCreate.setVisible(true);
				btnDelete.setVisible(true);
			} else {
				btnCreate.setVisible(false);
				btnDelete.setVisible(false);				
			}
		} else if(e.getSource() == radioPerform){
			game.perform = true;
		} else if(e.getSource() == radioAvoid){
			game.perform = false;
		} else if(e.getSource() == btnExecute){
			executeActions();
		}
					
		paint(getGraphics());
	}

	private void executeActions() {
		game.executeActions();
	}

	private void stepByStep() {
		game.stepByStep();
	}

	private void executeSTRIPS() {
		game.strips();
	}

	private Furniture getFurnitureById(int id) {
		return game.getFurnitureById(id);
	}
}