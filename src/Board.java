import java.awt.Graphics;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.Timer;


public class Board extends JPanel implements KeyListener
{
	private BufferedImage blocks;
	
	private final int blockSize= 30;
	
	private final int boardWidth= 10, boardHeight= 20, borderWidth=Tetris.getBorderWidth();
	
	private int[][] board;
	
	public boolean gameOver=true;
	
	private Shape shapes[]=new Shape[7];
	private Shape currentShape;
	private Shape newShape;
	
	private Timer timer;
	private final int FPS=60;
	private final int delay= 1000/FPS;
	
	private int score=0, highScore;
	
	public Board()
	{
		setBoard();
		
		highScore=getHighScore();
		
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
		
		//timer.start();
		
		
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
		
		int shapeNum = (int)(Math.random()*7); 
		currentShape= new Shape (shapes[shapeNum].getColMul(), shapes[shapeNum].getBlock(), shapes[shapeNum].getCoords(), this);
		shapeNum = (int)(Math.random()*7); 
		newShape= new Shape (shapes[shapeNum].getColMul(), shapes[shapeNum].getBlock(), shapes[shapeNum].getCoords(), this);
		
	}
	
	public void update()
	{
		if(!gameOver)
			currentShape.update();
		else {
			Tetris.getGameOverLabel().setText("Game over. Press \"New Game\" to start a new game.");
			timer.stop();
		}
	}
	
	public void checkLine()  {
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
			else {
				score++;
				Tetris.getScoreLabel().setText("Score: "+ String.valueOf(score));
				checkScore();
			}
		}
	}
	
	public void checkScore() {
		if (score>highScore) {
			highScore=score;
			Tetris.getHighScoreLabel().setText("High Score: "+ String.valueOf(highScore));
			
			// create file with the high score
			File file = new File ("HighScore.dat");
			if (!file.exists())
				try {
					file.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
			// update the high score file
			FileWriter writeFile=null;
			BufferedWriter out=null;
			try {
				writeFile=new FileWriter(file);
				out = new BufferedWriter(writeFile);
				out.write(String.valueOf(highScore));
				System.out.println("highscore written"); 
			} catch (IOException e) {
				e.printStackTrace();
			}
			finally {
				try {
					if (out!=null) {
						out.close();
						System.out.println("Writer closed");
					}
				}
				catch (IOException e) {
						e.printStackTrace();
				}
			}
			System.out.printf("File is located at %s%n", file.getAbsolutePath());
		}
	}
	public int getHighScore() {
		
		File file = new File ("HighScore.dat");
		
		FileReader readFile=null;
		BufferedReader in=null;
		try {
			readFile= new FileReader(file);
			in= new BufferedReader(readFile);
			return Integer.parseInt(in.readLine());
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			try {
				if (in!=null) {
					in.close();
					System.out.println("Reader closed");
				}
			}
			catch (IOException e) {
					e.printStackTrace();
			}
		}
		return 0;
	}
	
	
	public void nextShape() {
		
		if (currentShape.collisionY()) {
			for (int i=0; i<currentShape.getCoords().length; i++)
				for (int j=0; j<currentShape.getCoords()[i].length; j++)
					if (currentShape.getCoords()[i][j]==1)
						board[currentShape.getY()+i][currentShape.getX()+j]=currentShape.getColMul();
			
			// print the board
			System.out.println("\n");
			for (int i=0; i<board.length; i++) {
				for (int j=0; j<board[i].length; j++) 
					System.out.print(board[i][j]+"\t");
				System.out.println("\n");
			}
		}
		
		currentShape=newShape;
		int shapeNum = (int)(Math.random()*7); 
		newShape= new Shape (shapes[shapeNum].getColMul(), shapes[shapeNum].getBlock(), shapes[shapeNum].getCoords(), this);
		
// Display new Shape!!!		
		
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		currentShape.render(g);
		g.setColor(Color.black); // not needed; black automatically
		
		// draw the grid
		for (int i=1; i<boardWidth; i++)
			g.drawLine(blockSize*i+borderWidth, 0+borderWidth, blockSize*i+borderWidth, blockSize*boardHeight+borderWidth);
		for (int i=1; i<boardHeight; i++)
			g.drawLine(0+borderWidth, blockSize*i+borderWidth, blockSize*boardWidth+borderWidth, blockSize*i+borderWidth);
	
		// draw blocks on the bottom
		for (int i=0; i<board.length; i++) 
			for (int j=0; j<board[i].length; j++) 
				if (board[i][j]!=0 && board[i][j]!=8)
					g.drawImage(blocks.getSubimage(blockSize*(board[i][j]-1), 0, blockSize, blockSize), (j-1)*blockSize+borderWidth, (i-2)*blockSize+borderWidth, null);
		
		// draw the frame
		g.fillRect(0, 0+borderWidth, borderWidth, blockSize*boardHeight);
		g.fillRect(blockSize*boardWidth+borderWidth, 0+borderWidth, borderWidth, blockSize*boardHeight);
		g.fillRect(0, 0, blockSize*boardWidth+2*borderWidth, borderWidth);
		g.fillRect(0, blockSize*boardHeight+borderWidth, blockSize*boardWidth+2*borderWidth, borderWidth);
	
	}
	
	public void pause() {
		if (timer.isRunning()) {
			timer.stop();
			Tetris.getPausedLabel().setText("Paused");
		}
		else {
			timer.start();
			Tetris.getPausedLabel().setText(" ");
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
	public int getBorderWidth() {
		return borderWidth;
	}
	public int[][] getBoard() {
		return board;
	}
	public void setGameOver(boolean go) {
		gameOver=go;
	}
	public void setScore(int s) {
		score=s;
	}
	
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode()== KeyEvent.VK_LEFT)
			currentShape.setDeltaX(-1);
		if (e.getKeyCode()== KeyEvent.VK_RIGHT)
			currentShape.setDeltaX(1);
		if (e.getKeyCode()== KeyEvent.VK_DOWN)
			currentShape.setSpeed(true);
		if (e.getKeyCode()== KeyEvent.VK_SPACE)
			currentShape.hardDrop();
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
	
	public void restartTimer() {
		timer.restart();
	}

	public void setBoard() {
		board= new int[boardHeight+3][boardWidth+2];
		for (int i=0; i<board.length; i++)
			for (int j=0; j<board[0].length; j++)
				if (i==board.length-1 || j==0 || j==board[i].length-1)
					board[i][j]=8;
	}
}
