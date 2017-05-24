import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JPanel;

import java.awt.GridLayout;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Tetris extends JFrame implements ActionListener
{
	private static final int WIDTH= 700, HEIGHT= 621;
	
	private static Board board;
	private static JLabel score, highScore, paused, next;
	private JButton newGame;
	
	public Tetris()
	{
		setTitle("Tetris Game");
		setSize(WIDTH, HEIGHT);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setLocationRelativeTo(null);
		
		score = new JLabel ("Score: 0");
		highScore= new JLabel ("High Score: 0");
		paused= new JLabel (" ", JLabel.CENTER);
		next= new JLabel("Next Shape", JLabel.CENTER);
		newGame= new JButton ("New Game");
		board= new Board();
		
		
		// set the panel
		JPanel right= new JPanel();
		//right.setSize(200, 621);
		right.setLayout(new GridLayout(5,1));
		right.add(next);
		right.add(paused);
		right.add(newGame);
		right.add(highScore);
		right.add(score);
		
		// set the whole layout
		setLayout(new GridLayout(1,2));
		
		add(board);
		
		add(right);
		
		newGame.addActionListener(this);
		board.addKeyListener(board);
		board.setFocusable(true);
		setVisible(true);
	}
	
	
	public void actionPerformed (ActionEvent e)
	{
		String action= e.getActionCommand();
		if (action.equals("New Game")) {
			board.setBoard();
			board.nextShape();
			board.startTimer();
			//board.setFocusable(true);
			board.requestFocusInWindow();
			System.out.println("start the game");
		}
		
	}
	
	public static JLabel getPaused() {
		return paused;
	}
	
	public static void main(String[]args)
	{
		new Tetris();
		for (int i=0; i<board.getBoard().length; i++) {
			for (int j=0; j<board.getBoard()[i].length; j++) 
				System.out.print(board.getBoard()[i][j]+"\t");
			System.out.println("\n");
			}
	}
}
