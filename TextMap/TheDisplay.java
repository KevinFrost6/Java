// Kevin Frost
// 5/6/2015
import java.applet.Applet;
import java.awt.*;
//import java.awt.event.KeyEvent;
//import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class TheDisplay extends Applet implements Runnable//, KeyListener
{
	// This is a basic sprite map with potential for loop, threads, and a key listener
	private static final long serialVersionUID = 1L;
	
	// Size in pixels of the tiles
	private static final int TILE_X = 32;
	private static final int TILE_Y = 32;
	
	// Number of tiles to be displayed
	private static final int TILE_X_NUMBER = 20;
	private static final int TILE_Y_NUMBER = 20;
	
	// Resolution of the window
	// Currently it's equal to the number of tiles times the pixel
	// size of the tile
	private static final int RES_X = (TILE_X * TILE_X_NUMBER) + 2;
	private static final int RES_Y = (TILE_Y * TILE_Y_NUMBER) + 2;
	
	// This is the image that is the entire screen
	private BufferedImage backBuffer;
	 
	// This is the image array that holds all of the tile images
	private BufferedImage img[];
	
	// Graphics...
	private Graphics2D g2d;

	// The game loop thread
	private Thread theLoop;
	
	// The level
	private Character[][] level;
	
	//private int winSizeX, winSizeY, winLocationX, winLocationY, lvlSizeX, lvlSizeY;
	private int lvlSizeX, lvlSizeY;
	
	private int imageCount;
	// THE FOLLOWING TILES HAVE BEEN DRAWN:
	// Grass - 0
	// Fence - 1
	// Door  - 2
	// Bush  - 3	

	public void init()
	{
		backBuffer = new BufferedImage(RES_X, RES_Y, BufferedImage.TYPE_INT_RGB);
		g2d = backBuffer.createGraphics();
		
		LevelLoader lvlLoad = new LevelLoader();
		
		Frame c = (Frame)this.getParent().getParent();
		c.setTitle("RESOLUTION: " + RES_X + "x" + RES_Y);
		
		System.out.println("Setting size to " + RES_X + "x" + RES_Y);
		setSize(RES_X, RES_Y);

		g2d.setPaint(Color.BLUE);
		g2d.setColor(Color.CYAN);
		
		lvlLoad.load("/home/minauros/Basic.txt");
		
		lvlSizeX = lvlLoad.getSizeX();
		lvlSizeY = lvlLoad.getSizeY();
		
		level = new Character[lvlSizeY][lvlSizeY];
		
		level = lvlLoad.getLevel();

		imageCount = 4;
		
		// Load the Images
		try 
		{
			img = new BufferedImage[imageCount];
			
			// Images to load with full path name
			img[0] = ImageIO.read(new File("~/Pictures/Grass32x32.jpg"));
			img[1] = ImageIO.read(new File("~/Pictures/Fence.jpg"));
			img[2] = ImageIO.read(new File("~/Pictures/Door.jpg"));
			img[3] = ImageIO.read(new File("~/Pictures/Bush.jpg"));
		} 
		catch (IOException e)
		{
			System.out.println("Caught exception: " + e.toString());
		}
		
		// Draws the level
		for (int yy = 0; yy < lvlSizeY; yy++)
		{
			for (int xx = 0; xx < lvlSizeX; xx++)
			{
				if (level[yy][xx] == '0') g2d.drawImage(img[0], xx * TILE_X, yy * TILE_Y, TILE_X, TILE_Y, null);
				if (level[yy][xx] == '1') g2d.drawImage(img[1], xx * TILE_X, yy * TILE_Y, TILE_X, TILE_Y, null);
				if (level[yy][xx] == '2') g2d.drawImage(img[2], xx * TILE_X, yy * TILE_Y, TILE_X, TILE_Y, null);
				if (level[yy][xx] == '3') g2d.drawImage(img[3], xx * TILE_X, yy * TILE_Y, TILE_X, TILE_Y, null);
			}
		}
		
		// Draws the grid over the level. Optional.
		drawGrid();
		
		//addKeyListener(this);
	}
	
	// Draws a grid over the map
	public void drawGrid()
	{
		for (int y = 0; y <= RES_Y; y = y + TILE_Y)
		{
			for (int x = 0; x <= RES_X; x = x + TILE_X)
			{
				g2d.setColor(Color.WHITE);
				g2d.drawRect(x, y, TILE_X, TILE_Y);
			}
		}
	}
	
// Paint code -----------------------------------------------------------------------------
	public void paint(Graphics g)
	{
		g.drawImage(backBuffer, 0, 0, this);
	}

	public void update(Graphics g)
	{
		paint(g);
	}
	
	public void blackout()
	{
		g2d.setPaint(Color.BLACK);
		g2d.fillRect(0, 0, RES_X, RES_Y);
	}
// ---------------------------------------------------------------------------------------

// Thread code ---------------------------------------------------------------------------
	@Override
	public void run() 
	{
		Thread t = Thread.currentThread();
		
		while (t == theLoop)
		{
			try
			{
				// Put runnable code here
				//randomSquare();
				drawGrid();
				Thread.sleep(100);
			}
			catch(InterruptedException e)
			{
				e.printStackTrace();
			}
			repaint();
		}
	}
	
	public void start()
	{
		theLoop = new Thread(this);
		theLoop.start();
	}
	
	public void stop()
	{
		theLoop = null;
	}
}
