import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font; //the star means import everything in that library
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

/**
 * The sliding game for Exercise 3.02 - ArraysOfObjects.
 * 
 * @author Leo Yoon
 * @since Thursday, December 4th of 2014 
 */

public class SlidingGame extends JFrame implements ActionListener
{
	//array of JButtons, size 16
	private JButton[] buttons = new JButton[16];
	private JButton start, reset; 
	private int moves, emptyIndex;
	private JPanel layout, menu, overall;	
	private JLabel counter;
		
	public SlidingGame()
	{	
		//this sets the title of the GUI
		super("The Sliding Game");
		
		//this is to ensure that the program shuts down once the window has been closed
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//sets the size of the GUI
		setSize(825, 825);
		
		//Font object for each square
		Font f = new Font("Arial", Font.BOLD, 32);
		
		//Font object for the menu bar
		Font g = new Font("Arial", Font.PLAIN, 16);
		
		//Color array
		Color[] colours = {Color.ORANGE, Color.WHITE, Color.BLUE};
		
		//every JFrame has a frame, then a contentPane
		//Container c = getContentPane();
		
		//creates a grid layout with empty spaces between it
		//c.setLayout(new GridLayout(4, 4, 5, 5));
									
		//start game button
		start = new JButton();
		start.addActionListener(this);
		start.setFont(g);
		start.setText("Start Game");

		//reset game button
		reset = new JButton();
		reset.addActionListener(this);
		reset.setFont(g);
		reset.setText("Reset Game");
										
		//creates an empty border?
		layout = new JPanel();
		layout.setBorder(new EmptyBorder(5, 5, 5, 5));
		layout.setLayout(new GridLayout(4, 4, 5, 5));
				
		//the counter for moves
		//this constructor has (text of Label, alignment)
		counter = new JLabel("Moves: " + moves, JLabel.CENTER);
		counter.setFont(g);
				
		//menu bar?
		menu = new JPanel();
		menu.setLayout(new GridLayout (1, 3, 5, 5));
		menu.add(counter);
		menu.add(start);
		menu.add(reset);
						
		//the overall JPanel that stores the two sub JPanels
		overall = new JPanel();
		overall.setLayout(new BorderLayout());
		overall.add(layout, BorderLayout.CENTER);
		overall.add(menu, BorderLayout.PAGE_END);		
		
		for (int i = 0; i < buttons.length; i++)
		{
			buttons[i] = new JButton("" + i);
			
			//no need for x, y coordinates
			buttons[i].setSize(200, 200);
						
			if (i / 4 % 2 == 0)
			{
				if ((i + 1) % 2 == 0)
					buttons[i].setBackground(colours[1]); //sets background colour
				else
					buttons[i].setBackground(colours[0]);	
			}
			else
			{
				if ((i + 1) % 2 == 1)
					buttons[i].setBackground(colours[1]); 
				else
					buttons[i].setBackground(colours[0]);	
			}
			
			buttons[i].setForeground(colours[2]);	//sets text colour
			buttons[i].setFont(f);
			
			//now each button gets an ActionListener - (this) means this class
			buttons[i].addActionListener(this);
			layout.add(buttons[i]);
		}
	
		//this button is empty & invisible to start
		buttons[15].setVisible(false);
		emptyIndex = 15;

		add(overall);
	
		//this ensures that the window is visible: should be at the end of the code
		setVisible(true);
	}
	
	public static void main(String[] args)
	{
		new SlidingGame();
	}

	private void scramble()
	{
		int random;
				
		for (int i = 0; i < 100; i ++)
		{
			random = (int)(Math.random()* 15) + 1;
		
			if (isNextToEmpty(random))
				swapPieces(random);
		}		
	}
	
	private boolean isNextToEmpty(int clicked)
	{
		//if empty space is left
		if (clicked - 1 == emptyIndex && clicked % 4 != 0)
			return true;
		
		//if empty space is right
		else if (clicked + 1 == emptyIndex && clicked % 4 != 3)
			return true;
		
		//if empty space is up
		else if (clicked - 4 == emptyIndex)
			return true;
		
		//if empty space is down
		else if (clicked + 4 == emptyIndex)
			return true;
		
		else
			return false;
	}
	
	private void swapPieces(int clicked)
	{			
		//store the text of the clicked button
		String textOfClicked = buttons[clicked].getText();
	
		//switch text from button clicked to empty button
		buttons[emptyIndex].setText(textOfClicked);

		//make the button (now updated) visible
		buttons[emptyIndex].setVisible(true);
		
		//make new empty button not visible
		buttons[clicked].setVisible(false); 
		
		//update empty index
		emptyIndex = clicked;
		
		moves++;
		counter.setText("Moves: " + moves);			
	}
	
	private boolean isVictory()
	{
		//if at any point, if the text is not equal to the index number
		for (int i = 0; i < buttons.length - 1; i++)
		{
			if (!buttons[i].getText().equals("" + i))
				return false;
		}
		
		//if user wins
		return true;
	}
	
	public void actionPerformed(ActionEvent e)
	{
		for (int i = 0; i < buttons.length; i++)
		{
			//check for the button that is the source of the click
			if (e.getSource() == buttons[i])
			{
				if (isNextToEmpty(i) == true)
					swapPieces(i);
				
				if(isVictory())
				{
					buttons[15].setFont(new Font("Arial", Font.BOLD, 32));
					buttons[15].setText("VICTORY!");
					buttons[15].setVisible(true);
					
					emptyIndex = -1;
				}				
			}
		}
		
		if (e.getSource() == start)
		{
			scramble();
			moves = 0;
			counter.setText("Moves: " + moves);
		}
		
		if (e.getSource() == reset)
		{
			moves = 0;
			counter.setText("Moves: " + moves);
			
			for (int i = 0; i < buttons.length; i++)
			{
				buttons[15].setFont(new Font("Arial", Font.BOLD, 32));				
				buttons[i].setText("" + i);
				buttons[i].setVisible(true);
			}
			
			//this button is empty & invisible to start 
			buttons[15].setVisible(false);
			emptyIndex = 15;
		}
	}
	
}


// move / swap tiles
// specific button (which is clicked)
// is the button valid to move?
// count the # of moves
// set grid size (?)
// scramble
// way to win & check for win