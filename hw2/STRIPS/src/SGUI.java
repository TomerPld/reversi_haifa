//import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;


@SuppressWarnings("serial")
public class SGUI extends JPanel {

	Game game;
	Color transparent;
	JTextArea txtStack;
	Font font;
	
	JScrollPane scpane;
	
	
	public SGUI(Game instance)
	{
		this.setLayout(null);
		
		game = instance;
		transparent = new Color((float)0, (float)0, (float)0, (float)0);
		font = new Font("Calibri", Font.BOLD, 20);
		
		// txtStack - the text area used to display the stack / listAction
		txtStack = new JTextArea("");
		txtStack.setEditable(false);
		txtStack.setBackground(Color.white);
		txtStack.setForeground(Color.black);
		txtStack.setFont(new Font("Calibri", Font.BOLD | Font.ITALIC, 20));
		txtStack.setFocusable(false);
		
		// scpane - the scroll pane which holds txtStack
		scpane = new JScrollPane(txtStack);
		scpane.setBounds(0, 0, 400, 400);
		scpane.setPreferredSize(new Dimension(400, 400));
		scpane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scpane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		
		
		this.add(scpane);
		this.setComponentZOrder(scpane, 0);
	}
	
	// Paints STRIPS' stack or listAction
	public void paint(Graphics g)
	{
		String str = new String();
		
		g.setColor(Color.white);
		g.fillRect(0, 0, (int)getSize().getWidth(), (int)getSize().getHeight());
		
		if(!game.s.isEmpty())	// display stack
		{
			str += "STRIPS Stack:\n";
			for(int i = 0; i < game.s.size(); i++)
				str += game.s.get(i).toString() + "\n";
		}
		else if(game.listAction != null)	// display listAction
		{	
			str += "List of Actions:\n";
			for(int i = 0; i < game.listAction.size(); i++)
				str += game.listAction.get(i).toString() + "\n";
		}
		
		txtStack.setText(str);
				
		this.paintComponents(g);
	}
}
