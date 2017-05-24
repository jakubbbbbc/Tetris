import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.Timer;


public class Board extends JPanel implements KeyListener
{
	private BufferedImage blocks;
	
	private final int blockSize= 30;
	
	private final int boardWidth= 10, boardHeight= 20;
	
	private int[][] board;
	
	private Shape shapes[]=new Shape[7];
	private Shape currentShape;
	
	private Timer timer;
	private final int FPS=60;
	private final int delay= 1000/FPS;
	
	public Board()
	{
		board= new int[boardWidth][boardHeight];
		try
		{
			blocks= ImageIO.read(Board.class.getResource("/blocks.png"));
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		timer = new Timer(delay, new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) 
			{
				update();
				repaint();	
			}
		});
		
		timer.start();
		
		
		//shapes
		
		shapes[0]= new Shape (blocks.getSubimage(0, 0, blockSize, blockSize), new int[][] {
			{0, 0, 0, 0},
			{1, 1, 1, 1},
			{0, 0, 0, 0},
			{0, 0, 0, 0}	// I-shape
		}, new int[] {1,3,0,1}, this);
		shapes[1]= new Shape (blocks.getSubimage(blockSize, 0, blockSize, blockSize), new int[][] {
			{1, 1, 0},
			{0, 1, 1},
			{0, 0, 0}	// Z-shape
		}, new int[] {1,2,1,1}, this);
		shapes[2]= new Shape (blocks.getSubimage(blockSize*2, 0, blockSize, blockSize), new int[][] {
			{0, 1, 1},
			{1, 1, 0},
			{0, 0, 0}	// S-shape
		}, new int[] {1,2,1,1}, this);
		shapes[3]= new Shape (blocks.getSubimage(blockSize*3, 0, blockSize, blockSize), new int[][] {
			{0, 1, 0},
			{1, 1, 1},
			{0, 0, 0}	// T-shape
		}, new int[] {1,2,1,1}, this);
		shapes[4]= new Shape (blocks.getSubimage(blockSize*4, 0, blockSize, blockSize), new int[][] {
			{0, 0, 1},
			{1, 1, 1},
			{0, 0, 0}	// L-shape
		}, new int[] {1,2,1,1}, this);
		shapes[5]= new Shape (blocks.getSubimage(blockSize*5, 0, blockSize, blockSize), new int[][] {
			{1, 0, 0},
			{1, 1, 1},
			{0, 0, 0}	// J-shape
		}, new int[] {1,2,1,1}, this);
		shapes[6]= new Shape (blocks.getSubimage(blockSize*6, 0, blockSize, blockSize), new int[][] {
			{1, 1},
			{1, 1}		// square-shape
		}, new int[] {1,0,1,0}, this);
		
		currentShape= shapes[2];
	}
	
	
	public void update()
	{
		currentShape.update();
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		currentShape.render(g);
		
		for (int i=0; i<boardWidth; i++)
		{
			g.drawLine(blockSize*i, 0, blockSize*i, blockSize*boardHeight);
		}
		for (int i=0; i<boardHeight; i++)
		{
			g.drawLine(0, blockSize*i, blockSize*boardWidth, blockSize*i);
		}
		
	}
	public int getBlockSize()
		{
			return blockSize;
		}
	public int getBoardWidth() {
		return boardWidth;
	}
	public int getBoardHeight() {
		return boardHeight;
	}


	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode()== KeyEvent.VK_LEFT)
			currentShape.setDeltaX(-1);
		if (e.getKeyCode()== KeyEvent.VK_RIGHT)
			currentShape.setDeltaX(1);
		if (e.getKeyCode()== KeyEvent.VK_DOWN)
			currentShape.setSpeed(true);
		
	}


	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode()== KeyEvent.VK_DOWN)
			currentShape.setSpeed(false);
		if (e.getKeyCode()== KeyEvent.VK_X)
			currentShape.rotateRight();
		if (e.getKeyCode()== KeyEvent.VK_Z)
			currentShape.rotateLeft();
	}


	public void keyTyped(KeyEvent e) {
		
		
	}
	
}