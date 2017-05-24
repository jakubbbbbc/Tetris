/////3, 14:24
/// start moving down. Change moving sideways to update method???


import javax.swing.JFrame;

public class Tetris
{
	private JFrame window;
	private static final int WIDTH= 310, HEIGHT= 600;
	private Board board;
	
	public Tetris()
	{
		window=new JFrame("Tetris Game");
		window.setSize(WIDTH, HEIGHT);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.setLocationRelativeTo(null);
		
		board= new Board();
		
		window.add(board);
		window.addKeyListener(board);
		
		window.setVisible(true);
	}
	
	
	public static void main(String[]args)
	{
		new Tetris();
	}
}
