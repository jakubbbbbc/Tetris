import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Shape 
{
	private BufferedImage block;
	private int[][]coords;
	Board board;
	private int colMul;
	private int x, y;
	private int deltaX;
	
	private long time, lastTime;
	private static int normalSpeed=500, fastSpeed=50;
	private boolean goFast;
	
	public Shape( int cm, BufferedImage b, int[][] c, Board bo) // 0=left, 1=right, 2=up, 3=down
	{
		colMul=cm;
		block=b;
		coords=c;
		board=bo;
		
		x=5;
		y=1;
		
		deltaX=0;
		time=0;
		lastTime=System.currentTimeMillis();
		goFast=false;
	}
	
	public void update() {
		// Y direction
		time+=System.currentTimeMillis()-lastTime;
		lastTime=System.currentTimeMillis();
		
		if (goFast==false)
			if(time>normalSpeed) { 
				y++;
				if (collisionY()) {
					board.nextShape();
					goFast=false;
					board.checkLine();
				}
				time=0;
			} else {}
		else 
			if (time>fastSpeed)  { 
				y++;
				if (collisionY()) {
					board.nextShape();
					goFast=false;
					board.checkLine();
				}
				time=0;
			}
		
		// X direction
		x+=deltaX;
		if (collisionX())
			x-=deltaX;
		deltaX=0;
		
	}
	
 	public void rotate (boolean right) {
		if (coords.length!=2&&coords[0].length!=2) {
			int r=coords.length;
			int c=coords[0].length;
			int ar[][] = new int[c][r];
			for (int i=0; i<r; i++)
				for (int j=0; j<c; j++)
					if (right)
						ar[j][r-1-i]=coords[i][j];
					else
						ar[c-1-j][i]=coords[i][j];
			boolean turn=true;
			for (int i=0; i<ar.length; i++)
				for (int j=0; j<ar[0].length; j++)
					if (ar[i][j]!=0)
						if (board.getBoard()[y+i][x+j]!=0)
							turn=false;
			if (turn)
			coords=ar;
		}
	}
	
	public void render(Graphics g)
	{
		for (int i=0; i<coords.length; i++)
			for (int j=0; j<coords[i].length; j++)
				if (coords[i][j]==1)
					g.drawImage(block, (x+j-1)*board.getBlockSize(), (y+i-1)*board.getBlockSize(), null);
	}
	
	public void setDeltaX(int i){
		deltaX=i;
	}
	
	public void setSpeed(boolean b) {
		goFast=b;
	}
	
	public boolean collisionX() {
		for (int i=0; i<coords.length; i++)
			for (int j=0; j<coords[i].length; j++)
				if (coords[i][j]==1) {
					if (board.getBoard()[y+i][x+j]!=0)
						return true;
					j=coords[i].length;	
				}
		for (int i=0; i<coords.length; i++)
			for (int j=coords[i].length-1; j>=0; j--)
				if (coords[i][j]==1) {
					if (board.getBoard()[y+i][x+j]!=0)
						return true;
					j=-1;	
				}
		return false;
	} 
	
	public boolean collisionY() {
		for (int j=0; j<coords[0].length; j++)
			for (int i=coords.length-1; i>=0; i--)
				if (coords[i][j]==1) {
					if (board.getBoard()[y+i][x+j]!=0)
						return true;
					i=-1;	
				}
		return false;
	} 
	
	public int[][] getCoords() {
		return coords;
	}
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public void setX(int x) {
		this.x=x;
	}
	public void setY(int y) {
		this.y=y;
	}
	public BufferedImage getBlock() {
		return block;
	}
	public int getColMul() {
		return colMul;
	}
}