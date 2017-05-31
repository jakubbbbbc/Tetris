import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.Timer;


public class ViewBoard  extends JPanel{

	
	private final int blockSize= 30;
	
	private final int boardWidth= 5, boardHeight= 5, nextX=90, nextY=5, holdX=0, holdY=5;
	
	private boolean isHold;
	
	private static Shape holdShape;
	private static Shape[] shapes;
	
	private Timer timer;
	private final int FPS=60;
	private final int delay= 1000/FPS;
	
	public ViewBoard(Shape[] s, boolean ih)
	{	
		isHold=ih;
		shapes= s;
		holdShape=null;
		
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
		
		Graphics2D g2 =(Graphics2D)g;
		
		int thickness = 3;
		//Stroke oldStroke = g2.getStroke();
		g2.setStroke(new BasicStroke(thickness));
		//g2.setStroke(oldStroke);
		
		if (isHold) {
			// draw the grid
			for (int i=0; i<=boardWidth; i++)
				if (i==0 || i==boardWidth)	// only borders
					g.drawLine(blockSize*i+holdX, 0+holdY, blockSize*i+holdX, blockSize*boardHeight+holdY);
			for (int i=0; i<=boardHeight; i++)
				if (i==0 || i==boardHeight)		// only borders
					g.drawLine(0+holdX, blockSize*i+holdY, blockSize*boardWidth+holdX, blockSize*i+holdY);
		
			// draw the shape
			if (holdShape!=null)
				for (int i=0; i<holdShape.getCoords().length; i++)
					for (int j=0; j<holdShape.getCoords()[i].length; j++)
						if (holdShape.getCoords()[i][j]==1)
							if (holdShape.getCoords().length==2)
								g.drawImage(holdShape.getBlock(), (j+1)*blockSize+holdX+15, (i+1)*blockSize+holdY+15, null);
							else if (holdShape.getCoords().length==3)
								g.drawImage(holdShape.getBlock(), (j+1)*blockSize+holdX, (i+2)*blockSize+holdY-15, null);
							else if (holdShape.getCoords().length==4)
								g.drawImage(holdShape.getBlock(), (j+1)*blockSize+holdX-15, (i+1)*blockSize+holdY, null);
			
			// draw the frame
			/*		g.fillRect(0, 0+shiftX, shiftX, blockSize*boardHeight);
					g.fillRect(blockSize*boardWidth+shiftX, 0+shiftX, shiftX, blockSize*boardHeight);
					g.fillRect(0, 0, blockSize*boardWidth+2*shiftX, shiftX);
					g.fillRect(0, blockSize*boardHeight+shiftX, blockSize*boardWidth+2*shiftX, shiftX);
			*/	
		}
		else {
			// draw the grid
				for (int i=0; i<=boardWidth; i++)
					if (i==0 || i==boardWidth)	// only borders
						g.drawLine(blockSize*i+nextX, 0+nextY, blockSize*i+nextX, blockSize*shapes.length*3+nextY);
				for (int i=0; i<=shapes.length*3; i++)
					if (i==0 || i==shapes.length*3)		// only borders
						g.drawLine(0+nextX, blockSize*i+nextY, blockSize*boardWidth+nextX, blockSize*i+nextY);
		
				// draw the shape
				for (int n=0; n<shapes.length; n++)
					for (int i=0; i<shapes[n].getCoords().length; i++)
						for (int j=0; j<shapes[n].getCoords()[i].length; j++)
							if (shapes[n].getCoords()[i][j]==1)
								if (shapes[n].getCoords().length==2)
									g.drawImage(shapes[n].getBlock(), (j+1)*blockSize+nextX+15, (i+n*3)*blockSize+15+nextY, null);
								else if (shapes[n].getCoords().length==3)
									g.drawImage(shapes[n].getBlock(), (j+1)*blockSize+nextX, (i+n*3)*blockSize+15+nextY, null);
								else if (shapes[n].getCoords().length==4)
									g.drawImage(shapes[n].getBlock(), (j+1)*blockSize+nextX-15, (i+n*3)*blockSize+nextY, null);
				
				// draw the frame
				/*				g.fillRect(0, 0+shiftX, shiftX, blockSize*boardHeight);
								g.fillRect(blockSize*boardWidth+shiftX, 0+shiftX, shiftX, blockSize*boardHeight);
								g.fillRect(0, 0, blockSize*boardWidth+2*shiftX, shiftX);
								g.fillRect(0, blockSize*boardHeight+shiftX, blockSize*boardWidth+2*shiftX, shiftX);
				*/	
							
		}
		
			
			
		
	}
	

	public static Shape getShape() {
		return holdShape;
	}
	
	public static void setShape(Shape s) {
		holdShape=s;
	}
	public static void setShapes(Shape[] s) {
		shapes=s;
	}
}
