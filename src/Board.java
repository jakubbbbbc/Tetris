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
	
	public boolean gameOver=false;
	
	private Shape shapes[]=new Shape[7];
	private Shape currentShape;
	private Shape newShape;
	
	private Timer timer;
	private final int FPS=60;
	private final int delay= 1000/FPS;
	
	public Board()
	{
		setBoard();
		
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
		
		shapes[0]= new Shape (1, blocks.getSubimage(0, 0, blockSize, blockSize), new int[][] {
			{0, 0, 0, 0},
			{1, 1, 1, 1},
			{0, 0, 0, 0},
			{0, 0, 0, 0}	// I-shape
		}, this);
		shapes[1]= new Shape (2, blocks.getSubimage(blockSize, 0, blockSize, blockSize), new int[][] {
			{1, 1, 0},
			{0, 1, 1},
			{0, 0, 0}	// Z-shape
		}, this);
		shapes[2]= new Shape (3, blocks.getSubimage(blockSize*2, 0, blockSize, blockSize), new int[][] {
			{0, 1, 1},
			{1, 1, 0},
			{0, 0, 0}	// S-shape
		}, this);
		shapes[3]= new Shape (4, blocks.getSubimage(blockSize*3, 0, blockSize, blockSize), new int[][] {
			{0, 1, 0},
			{1, 1, 1},
			{0, 0, 0}	// T-shape
		}, this);
		shapes[4]= new Shape (5, blocks.getSubimage(blockSize*4, 0, blockSize, blockSize), new int[][] {
			{0, 0, 1},
			{1, 1, 1},
			{0, 0, 0}	// L-shape
		}, this);
		shapes[5]= new Shape (6, blocks.getSubimage(blockSize*5, 0, blockSize, blockSize), new int[][] {
			{1, 0, 0},
			{1, 1, 1},
			{0, 0, 0}	// J-shape
		}, this);
		shapes[6]= new Shape (7, blocks.getSubimage(blockSize*6, 0, blockSize, blockSize), new int[][] {
			{1, 1},
			{1, 1}		// square-shape
		}, this);
		
		currentShape= shapes[(int)(Math.random()*7)];
		newShape= shapes[(int)(Math.random()*7)];
	}
	
	public void update()
	{
		if(!gameOver)
			currentShape.update();
		else {
			System.out.println("\nGame Over!!! You Loser!!!");
			timer.stop();
		}
	}
	
	public void checkLine() {
		int height= board.length-2;
		
		for (int i=height; i>0; i--) {
			int count=0;
			for (int j=1; j<board[i].length-1; j++) {
				if (board[i][j]!=0)
					count++;
				board[height][j]=board[i][j];
			}
			if (count<board[i].length-2)
				height--;
		}
	}
	
	public void nextShape() {
		
		if (currentShape.collisionY()) {
			for (int i=0; i<currentShape.getCoords().length; i++)
				for (int j=0; j<currentShape.getCoords()[i].length; j++)
					if (currentShape.getCoords()[i][j]==1)
						board[currentShape.getY()-1+i][currentShape.getX()+j]=currentShape.getColMul();
		
			// print the board
			System.out.println("\n");
			for (int i=0; i<board.length; i++) {
				for (int j=0; j<board[i].length; j++) 
					System.out.print(board[i][j]+"\t");
				System.out.println("\n");
			}
		}

		currentShape.setX(5);
		currentShape.setY(1);
		
		currentShape=newShape;
		newShape= shapes[(int)(Math.random()*7)];
		
		for (int i=0; i<currentShape.getCoords().length; i++)
			for (int j=0; j<currentShape.getCoords()[i].length; j++)
				if (currentShape.getCoords()[i][j]==1)
					if (board[currentShape.getY()+i][currentShape.getX()+j]!=0)
						gameOver=true;
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
		
		for (int i=0; i<board.length; i++) 
			for (int j=0; j<board[i].length; j++) 
				if (board[i][j]!=0)
					g.drawImage(blocks.getSubimage(blockSize*(board[i][j]-1), 0, blockSize, blockSize), (j-1)*blockSize, (i-1)*blockSize, null);
		
	}
	
	public void pause() {
		if (timer.isRunning()) {
			timer.stop();
			Tetris.getPaused().setText("Paused");
		}
		else {
			timer.start();
			Tetris.getPaused().setText(" ");
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
	public int[][] getBoard() {
		return board;
	}

	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode()== KeyEvent.VK_LEFT)
			currentShape.setDeltaX(-1);
		if (e.getKeyCode()== KeyEvent.VK_RIGHT)
			currentShape.setDeltaX(1);
		if (e.getKeyCode()== KeyEvent.VK_DOWN)
			currentShape.setSpeed(true);
		if (e.getKeyCode()== KeyEvent.VK_P)
			pause();
	}
	
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode()== KeyEvent.VK_DOWN)
			currentShape.setSpeed(false);
		if (e.getKeyCode()== KeyEvent.VK_X)
			currentShape.rotate(true);
		if (e.getKeyCode()== KeyEvent.VK_Z)
			currentShape.rotate(false);
	}
	
	public void keyTyped(KeyEvent e) {
		
		
	}
	
	public void startTimer() {
		timer.start();
	}

	public void setBoard() {
		board= new int[boardHeight+2][boardWidth+2];
		for (int i=0; i<board.length; i++)
			for (int j=0; j<board[0].length; j++)
				if (i==0 || i==board.length-1 || j==0 || j==board[i].length-1)
					board[i][j]=1;
		Tetris.getPaused().setText(" ");
	}
}