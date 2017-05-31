import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class Tetris extends JFrame implements ActionListener
{	
	private static final int borderWidth=2;
	private static final int WIDTH= 1000, HEIGHT= 622+2*borderWidth; //700, 622
	
	private static Board board;
	private ViewBoard nextBoard, holdBoard;
	private static JLabel scoreLabel, highScoreLabel, gameOverLabel, pausedLabel, next, levelLabel, linesClearedLabel, held, holdError;
	private JButton newGame;
	
	public int newGameCount=0;
	public GridBagConstraints c, c1;
	
	public Tetris()
	{
		setTitle("Tetris Game");
		setSize(WIDTH, HEIGHT);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setLocationRelativeTo(null);
		
		// initialize stuff
		board= new Board();
		
		nextBoard= new ViewBoard(board.getNextShapes(), false);
		holdBoard= new ViewBoard(board.getNextShapes(), true);
		
		next= new JLabel("Next Shapes", JLabel.CENTER);
		scoreLabel = new JLabel ("Score: 0");
		highScoreLabel= new JLabel ("High Score: "+board.getHighScore());
		levelLabel= new JLabel ("Level: 1");
		linesClearedLabel= new JLabel("Lines made: 0");
		
		pausedLabel= new JLabel (" ", JLabel.CENTER);
		gameOverLabel= new JLabel ("Press \"New Game\" to start a new game.");
		
		held = new JLabel("Held Shape", JLabel.CENTER);
		holdError= new JLabel(" ", JLabel.CENTER);
		
		newGame= new JButton ("New Game");
		
		
	// right side
		JPanel right= new JPanel();
		right.setLayout(new GridBagLayout());
		c = new GridBagConstraints();
		
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 2;
		c.weighty = .1;
		c.fill = GridBagConstraints.BOTH;
		right.add(next, c);
		
		c.gridy = 1;
		c.weighty = 1;
		//c.insets = new Insets(0, 0, 0, 0);
		right.add(nextBoard, c);
		
		c.gridy = 2;
		c.weightx = .1;
		c.weighty = .1;
		//c.insets = new Insets(0, 0, 0, 0);
		c.fill = GridBagConstraints.NONE;
		right.add(pausedLabel, c);
		
		c.gridy = 3;
		c.ipadx = 40;
		c.ipady = 40;
		right.add(newGame, c);
		
		c.gridy = 4;
		c.ipadx = 0;
		c.ipady = 0;
		right.add(gameOverLabel, c);
		
		c.gridy = 5;
		c.gridwidth = 1;
		right.add(levelLabel, c);
		
		c.gridy = 6;
		right.add(linesClearedLabel, c);
		
		c.gridx = 1;
		c.gridy = 5;
		right.add(scoreLabel, c);
		
		c.gridy = 6;
		right.add(highScoreLabel, c);
		
	// left side
		JPanel left= new JPanel();
		//left.setLayout(new GridLayout(3,1));
		left.setLayout(new GridBagLayout());
		c1 = new GridBagConstraints();
		
		c1.gridx = 0;
		c1.gridy = 0;
		c1.gridwidth = 2;
		c1.weighty = .1;
		//c1.fill = GridBagConstraints.BOTH;
		right.add(held, c1);
		
		//c1.gridy = 1;
		//c1.weighty = 1;
		//c1.gridwidth=2;
		//left.add(holdBoard, c1);
		//left.add(holdError, c1);
	
	// set the whole layout
		setLayout(new GridLayout(1,3));
		add(left);
		add(board);
		add(right);
		
		newGame.addActionListener(this);
		board.setFocusable(true);
		setVisible(true);
	}
	
	
	public void actionPerformed (ActionEvent e)
	{
		String action= e.getActionCommand();
		
		if (action.equals("New Game")) {
			if (newGameCount==0) {
				restart();
				newGameCount++;
			}
			else {
				restart();
				restart();
				restart();
				restart();
			}
		}
		
	}
	
	public static int getBorderWidth() {
		return borderWidth;
	}
	public static JLabel getPausedLabel() {
		return pausedLabel;
	}
	public static JLabel getScoreLabel() {
		return scoreLabel;
	}
	public static JLabel getHighScoreLabel() {
		return highScoreLabel;
	}
	public static JLabel getGameOverLabel() {
		return gameOverLabel;
	}
	public static JLabel getLevelLabel() {
		return levelLabel;
	}
	public static JLabel getLinesClearedLabel() {
		return linesClearedLabel;
	}
	
	public void restart() {
		board.setBoard();
		board.addKeyListener(board);
		
		board.setScore(0);
		scoreLabel.setText("Score: 0");
		
		board.setLinesMade(0);
		linesClearedLabel.setText("Lines cleared: 0");
		
		Shape.setLevel(1);
		levelLabel.setText("Level: 1");
		
		pausedLabel.setText(" ");
		gameOverLabel.setText(" ");
		ViewBoard.setShape(null);
		
		//board.setFocusable(true);
		board.requestFocusInWindow();
		
		board.setGameOver(false);
		if (newGameCount!=0) 
			board.nextShape();
		else 
			newGameCount++;
		board.restartTimer();
		System.out.println("start the game");
	}
	
	public static void main(String[]args)
	{
		new Tetris();
		for (int i=0; i<board.getBoard().length; i++) {
			for (int j=0; j<board.getBoard()[i].length; j++) 
				System.out.print(board.getBoard()[i][j]+"\t");
			System.out.println("\n");
			}
		System.out.println(scoreLabel.getText());
	}
}
