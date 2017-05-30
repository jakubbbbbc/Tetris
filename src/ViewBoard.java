import java.awt.Graphics;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.Timer;


public class ViewBoard  extends JPanel{

	
	private final int blockSize= 30;
	
	private final int boardWidth= 5, boardHeight= 5, shiftX=100;
	
	private boolean isHold;
	
	private static Shape viewShape;
	private static Shape[] shapes;
	
	private Timer timer;
	private final int FPS=60;
	private final int delay= 1000/FPS;
	
	public ViewBoard(Shape s, Shape[] sh, boolean ih)
	{	
		isHold=ih;
		if (isHold)
			viewShape= s;
		else
			shapes= sh;
		
		timer = new Timer(delay, new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				repaint();	
			}
			
		});
		timer.start();
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
	
		g.setColor(Color.black); // not needed; black automatically
		
		
		if (isHold) {
			// draw the grid
	/*		for (int i=0; i<=boardWidth; i++)
				if (i==0 || i==boardWidth)	// only borders
					g.drawLine(blockSize*i+shiftX, 0+shiftX, blockSize*i+shiftX, blockSize*boardHeight+shiftX);
			for (int i=0; i<=boardHeight; i++)
				if (i==0 || i==boardHeight)		// only borders
					g.drawLine(0+shiftX, blockSize*i+shiftX, blockSize*boardWidth+shiftX, blockSize*i+shiftX);
		*/
			
			// draw the frame
			g.fillRect(0, 0+shiftX, shiftX, blockSize*boardHeight);
			g.fillRect(blockSize*boardWidth+shiftX, 0+shiftX, shiftX, blockSize*boardHeight);
			g.fillRect(0, 0, blockSize*boardWidth+2*shiftX, shiftX);
			g.fillRect(0, blockSize*boardHeight+shiftX, blockSize*boardWidth+2*shiftX, shiftX);
		
			// draw the shape
			for (int i=0; i<viewShape.getCoords().length; i++)
				for (int j=0; j<viewShape.getCoords()[i].length; j++)
					if (viewShape.getCoords()[i][j]==1)
						if (viewShape.getCoords().length==2)
							g.drawImage(viewShape.getBlock(), (j+1)*blockSize+shiftX+15, (i+1)*blockSize+shiftX+15, null);
						else if (viewShape.getCoords().length==3)
							g.drawImage(viewShape.getBlock(), (j+1)*blockSize+shiftX, (i+2)*blockSize+shiftX-15, null);
						else if (viewShape.getCoords().length==4)
							g.drawImage(viewShape.getBlock(), (j+1)*blockSize+shiftX-15, (i+1)*blockSize+shiftX, null);
		}
		else {
			// draw the grid
					for (int i=0; i<=boardWidth; i++)
						if (i==0 || i==boardWidth)	// only borders
							g.drawLine(blockSize*i+shiftX, 0, blockSize*i+shiftX, blockSize*shapes.length*3);
					for (int i=0; i<=shapes.length*3; i++)
						if (i==0 || i==shapes.length*3)		// only borders
							g.drawLine(0+shiftX, blockSize*i, blockSize*boardWidth+shiftX, blockSize*i);
				
					
					// draw the frame
				/*	g.fillRect(0, 0+shiftX, shiftX, blockSize*boardHeight);
					g.fillRect(blockSize*boardWidth+shiftX, 0+shiftX, shiftX, blockSize*boardHeight);
					g.fillRect(0, 0, blockSize*boardWidth+2*shiftX, shiftX);
					g.fillRect(0, blockSize*boardHeight+shiftX, blockSize*boardWidth+2*shiftX, shiftX);
			*/	
					// draw the shape
					for (int n=0; n<shapes.length; n++)
						for (int i=0; i<shapes[n].getCoords().length; i++)
							for (int j=0; j<shapes[n].getCoords()[i].length; j++)
								if (shapes[n].getCoords()[i][j]==1)
									if (shapes[n].getCoords().length==2)
										g.drawImage(shapes[n].getBlock(), (j+1)*blockSize+shiftX+15, (i+n*3)*blockSize+15, null);
									else if (shapes[n].getCoords().length==3)
										g.drawImage(shapes[n].getBlock(), (j+1)*blockSize+shiftX, (i+n*3)*blockSize+15, null);
									else if (shapes[n].getCoords().length==4)
										g.drawImage(shapes[n].getBlock(), (j+1)*blockSize+shiftX-15, (i+n*3)*blockSize, null);
							
		}
		
			
			
		
	}
	

	public Shape getShape() {
		return viewShape;
	}
	
	public static void setShape(Shape s) {
		viewShape=s;
	}
	public static void setShapes(Shape[] s) {
		shapes=s;
	}
}
