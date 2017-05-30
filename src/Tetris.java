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
	private static final int WIDTH= 700, HEIGHT= 622+2*borderWidth; //700, 622
	
	private static Board board;
	private ViewBoard nextBoard, holdBoard;
	private static JLabel scoreLabel, highScoreLabel, gameOverLabel, pausedLabel, next, levelLabel, linesClearedLabel;
	private JButton newGame;
	
	public int newGameCount=0;
	public GridBagConstraints c;
	
	public Tetris()
	{
		setTitle("Tetris Game");
		setSize(WIDTH, HEIGHT);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setLocationRelativeTo(null);
		
		// initialize stuff
		board= new Board();
		nextBoard= new ViewBoard(board.getCurrentShape(), board.getNextShape(), false);
		scoreLabel = new JLabel ("Score: 0");
		highScoreLabel= new JLabel ("High Score: "+board.getHighScore());
		gameOverLabel= new JLabel ("Press \"New Game\" to start a new game.");
		pausedLabel= new JLabel (" ", JLabel.CENTER);
		next= new JLabel("Next Shape", JLabel.CENTER);
		levelLabel= new JLabel ("Level: 1");
		linesClearedLabel= new JLabel("Lines made: 0");
		newGame= new JButton ("New Game");
		
		
		// set the panel
		JPanel right= new JPanel();
		//right.setSize(200, 621);
		//right.setLayout(new GridLayout(5,1));
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
	//	c.insets = new Insets(0, 92, 0, 0);
		right.add(nextBoard, c);
		
		c.gridy = 2;
		c.weightx = .1;
		c.weighty = .1;
	//	c.insets = new Insets(0, 0, 0, 0);
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
		
		// set the whole layout
		setLayout(new GridLayout(1,2));
		
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
