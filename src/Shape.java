import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Shape 
{
	private BufferedImage block;
	private int[][]coords;
	private int[] dis;
	Board board;
	private int x, y;
	private int deltaX;
	
	private long time, lastTime;
	private static int normalSpeed=500, fastSpeed=50;
	private boolean goFast;
	
	public Shape(BufferedImage b, int[][] c, int[] d, Board bo) // 0=left, 1=right, 2=up, 3=down
	{
		block=b;
		coords=c;
		dis=d;
		board=bo;
		x=3;
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
			if(time>normalSpeed) 
				if (y+dis[3]<board.getBoardHeight()) {
					y++;
					time=0;
				}
		if (goFast==true)	
			if (time>fastSpeed) 
				if (y+dis[3]<board.getBoardHeight()) {
					y++;
					time=0;
				}
		
		// X direction
		if (x-dis[0]+deltaX>=0 && x+dis[1]+deltaX<=board.getBoardWidth())
			x+=deltaX;
		deltaX=0;
		
	}

	public void rotateLeft() {
		if (coords.length!=2&&coords[0].length!=2) {
			int[]d= new int[] {dis[2], dis[3], dis[1], dis[0]};
			dis=d;
			int r=coords.length;
			int c=coords[0].length;
			int ar[][] = new int[c][r];
			for (int i=0; i<r; i++)
				for (int j=0; j<c; j++)
					ar[c-1-j][i]=coords[i][j];
			if (x+coords.length<=board.getBoardWidth()) //right side only
				coords=ar;
		}
	}
	
	public void rotateRight() {
		if (coords.length!=2&&coords[0].length!=2) {
			int[]d= new int[] {dis[3], dis[2], dis[0], dis[1]};
			dis=d;
			int r=coords.length;
			int c=coords[0].length;
			int ar[][] = new int[c][r];
			for (int i=0; i<r; i++)
				for (int j=0; j<c; j++)
					ar[j][r-1-i]=coords[i][j];
			if (x+coords.length<=board.getBoardWidth()) // right side only
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
	
}