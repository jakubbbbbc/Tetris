import java.awt.Graphics;
import java.awt.Color;
import java.awt.image.BufferedImage;

import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;



public class ViewBoard  extends JPanel{

	private BufferedImage blocks;
	
	private final int blockSize= 30;
	
	private final int boardWidth= 5, boardHeight= 5, borderWidth=Tetris.getBorderWidth();
	
	//private Shape viewShape;
	
	
	public ViewBoard()
	{
				
		try
		{
			blocks= ImageIO.read(Board.class.getResource("/blocks.png"));
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
	
		g.setColor(Color.black); // not needed; black automatically
		
		// draw the grid
	/*	for (int i=0; i<=boardWidth; i++)
			g.drawLine(blockSize*i+borderWidth, 0+borderWidth, blockSize*i+borderWidth, blockSize*boardHeight+borderWidth);
		for (int i=0; i<=boardHeight; i++)
			g.drawLine(0+borderWidth, blockSize*i+borderWidth, blockSize*boardWidth+borderWidth, blockSize*i+borderWidth);
	*/
		
		// draw the frame
		g.fillRect(0, 0+borderWidth, borderWidth, blockSize*boardHeight);
		g.fillRect(blockSize*boardWidth+borderWidth, 0+borderWidth, borderWidth, blockSize*boardHeight);
		g.fillRect(0, 0, blockSize*boardWidth+2*borderWidth, borderWidth);
		g.fillRect(0, blockSize*boardHeight+borderWidth, blockSize*boardWidth+2*borderWidth, borderWidth);
	
	}
	
	
}
