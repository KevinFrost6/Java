/* Kevin Frost and Kevin Kusion
 * CSIS 4461 Programming Assignment 5/6
 * Last edit 10/21/2015
 */
import java.awt.*;
import java.awt.event.*;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.geom.*;
import java.io.IOException;

public class Display extends JApplet
{
	public static void main(String s[]) 
	{
	    JFrame frame = new JFrame();
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.setTitle("YinYang");
	    JApplet applet = new Display();
	    applet.init();
	    frame.getContentPane().add(applet);
	    frame.pack();
	    frame.setVisible(true);
  }
  
  public void init() 
  {
    JPanel panel = new YYPanel();
    getContentPane().add(panel);
  }
}

class YYPanel extends JPanel implements ActionListener
{  
	// Constants
	public static final int PANEL_WIDTH = 1000;
	public static final int PANEL_HEIGHT = 600;
	public static final int SLEEP_TIME = 15;

	// Objects
	private Yinyang a;
	private Yinyang b;
	
	// Colors
	private Color colorA;
	private Color colorB;
	
	// Game timer
	private Timer timer;
	
	// Score/lives
	private double score = 10;
	private int lives = 10;

	// Needed
	private AffineTransform at = new AffineTransform();

	// Variables

	// Interval by which YYB moves
	private int iXb = 1;
	private int iYb = 1;
	
	
	// Interval by which YYA moves
	private int iXa = -1;
	private int iYa = -1;
	
	// Scale for Yinyang A and B
	private double sa = 1.0;
	private double sb = 1.0;
	
	// First run boolean. NEEDED.
	private boolean firstRun = true;
	
	// Speed of a and b
	private int speedA = 1;
	private int speedB = 1;
	
	// Individual points for a and b
	private int pointsA = 10;
	private int pointsB = 10;

	public YYPanel()  
	{
		// Set the size of the panel
		setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));

		// White BG
		setBackground(Color.white);
		
		colorA = Color.BLUE;
		colorB = Color.BLACK;
		
		class MyMouseListener implements MouseListener 
		{

	    	@Override
	    	public void mouseClicked(MouseEvent event) 
	    	{
	    	}
	    	
	    	@Override
	    	public void mouseEntered(MouseEvent arg0) 
			{
	    	}
	
	    	@Override
	    	public void mouseExited(MouseEvent arg0) 
			{
	    	}
	
	    	@Override
	    	public void mousePressed(MouseEvent arg0) 
	    	{
    		
	    	}
	
	    	@Override
	    	public void mouseReleased(MouseEvent event) 
	    	{
	    		int zone = 50; // Set the detection zone to "fair"
	    		
	    		// Mouse detection
	    		if (event.getX() <= (a.getX() + zone) &&
	    				event.getX() >= (a.getX() - zone))
	    		{
		    		if (event.getY() <= (a.getY() + zone) &&
		    				event.getY() >= (a.getY() - zone))
		    		{
		    			score = score + (pointsA * speedA);
		    			speedA = speedA + 1;
		    			
		    			speedUpdate();
		    		}
	    		}
	    		else if (event.getX() <= (b.getX() + zone) &&
	    				event.getX() >= (b.getX() - zone))
	    		{
		    		if (event.getY() <= (b.getY() + zone) &&
		    				event.getY() >= (b.getY() - zone))
		    		{
		    			score = score + (pointsB * speedB);
		    			speedB = speedB + 1;
		    			
		    			speedUpdate();
		    		}
	    		}
	    		else
	    		{
	    			lives--;
	    		}
	    	}
	    	
	    }
    	addMouseListener(new MyMouseListener());

    	Timer timer = new Timer(SLEEP_TIME, this);
    	timer.start();
	}

	/**
	 * Generates a random color
	 * @return A random color.
	 */
	public Color randomColor()
  	{
  		int r, g, b;
  		Color randomC;
  		
  		r = (int)(Math.random() * 255);
  		g = (int)(Math.random() * 255);
  		b = (int)(Math.random() * 255);
  		
  		randomC = new Color(r, g, b);
  		
  		return randomC;
  	}

  	// YinYang B's wall collision detection
  	public void checkForWallB()
  	{
  		if (b.getX() >= YYPanel.PANEL_WIDTH)
  		{
  			iXb = -speedB;
  			colorB = randomColor();
  		}
  		
  		else if (b.getX() <= 0)
  		{
  			iXb = speedB;
  			colorB = randomColor();
  		}
  		
  		if (b.getY() >= YYPanel.PANEL_HEIGHT)
  		{
  			iYb = -speedB;
  			colorB = randomColor();
  		}
  		
  		else if (b.getY() <= 0)
  		{
  			iYb = speedB;
  			colorB = randomColor();
  		}
  	}
  	
  	// YinYang A's wall collision detection
  	public void checkForWallA()
  	{
  		if (a.getX() >= YYPanel.PANEL_WIDTH)
  		{
  			iXa = -speedA;
  			colorA = randomColor();
  		}
  		else if (a.getX() <= 0)
  		{
  			iXa = speedA;
  			colorA = randomColor();
  		}
  		
  		if (a.getY() >= YYPanel.PANEL_HEIGHT)
  		{
  			iYa = -speedA;
  			colorA = randomColor();
  		}
  		else if (a.getY() <= 0)
  		{
  			iYa = speedA;
  			colorA = randomColor();
  		}
  	}
  	
  	// Checks for collision with another ellipse
  	public void checkForCollision()
  	{
  		int zone = 45;
  		// Essentially the long way to compute YYs bounding rectangle
  		if ((b.getX() <= (a.getX() + zone)) && (b.getX() >= (a.getX() - zone)))
  		{
  			if ((b.getY() <= (a.getY() + zone)) && (b.getY() >= (a.getY() - zone)))
  			{
  				// IF they collide, bounce...
  				// B takes A's direction, A takes B's direction, etc.
  				int temp;
  				temp = iXa;
  				iXa = iXb;
  				iXb = temp;
  				
  				temp = iYa;
  				iYa = iYb;
  				iYb = temp;
  			}
  		}
  	}
  	
  	// Updates speeds when there is a collision
  	public void speedUpdate()
  	{
  		// Code for A
  		if (iXa < 0)
  		{
  			iXa = -speedA;
  			repaint();
  		}
  		else 
  		{
  			iXa = speedA;
  			repaint();
  		}
  		
  		if (iYa < 0)
  		{
  			iYa = -speedA;
  			repaint();
  		}
  		else 
  		{
  			iYa = speedA;
  			repaint();
  		}
  		
  	
  		// Code for B
  		if (iXb < 0)
  		{
  			iXb = -speedB;
  			repaint();
  		}
  		else 
  		{
  			iXb = speedB;
  			repaint();
  		}
  		
  		if (iYb < 0)
  		{
  			iYb = -speedB;
  			repaint();
  		}
  		else 
  		{
  			iYb = speedB;
  			repaint();
  		}
  	}
  	
  	
  	// The smaller one
  	public void randomScaleA()
  	{
  		sa = Math.random() * 1.0;
  	}
  	
  	// The bigger one
  	public void randomScaleB()
  	{
  		sb = Math.random() * 2.0;
  	}  	

  	public void paintComponent(Graphics g) 
	{
		super.paintComponent(g);
	    Graphics2D g2 = (Graphics2D)g;
	    
	    g2.drawString("Score = " + score, 20, 20);
	    g2.drawString("Lives = " + lives, 20, 30);
	    
	    if (lives == 0)
	    {
	    	g2.drawString("GAME OVER", YYPanel.PANEL_WIDTH / 2, YYPanel.PANEL_HEIGHT / 2);
	    	g2.drawString("click to exit", YYPanel.PANEL_WIDTH / 2, (YYPanel.PANEL_HEIGHT / 2) + 20);
	    	return;
	    }
	    else if (lives < 0) System.exit(0);
	    	
	    else
	    {
	    
	    	if (firstRun)
	    	{
	    		a = new Yinyang(0, 0, 30);
	    		b = new Yinyang(0, 0, 30);
	    		
	    		b.setDx(300);
		  	    b.setDy(300);
		    
			    g2.setColor(colorB);
			    g2.translate(b.getDx(), b.getDy());
			    checkForWallB();
			    
			    g2.draw(b);
			    g2.fill(b);
			    
			    
			    b.setX(b.getDx());
			    b.setY(b.getDy());
			    g2.translate(-b.getDx(), -b.getDy());
			    
			    // -----------------------------------------
			    
			    a.setDx(300);
		  	    a.setDy(500);
		  	    
			    g2.setColor(colorA);
			    g2.translate(a.getDx(), a.getDy());
			    checkForWallA();
			   
			    g2.draw(a);
			    g2.fill(a);
			    a.setX(a.getDx());
			    a.setY(a.getDy());
			    g2.translate(-a.getDx(), -a.getDy());
		    	
		    	iXa = speedA;
		    	iYa = -speedA;
		    	
		    	iXb = -speedB;
		    	iYb = speedB;
		    	
			    firstRun = false;
		    }
	
		    else
		    {
		    	checkForCollision();
	
			    // B
			    b.setDx(b.getX() + iXb);
			    b.setDy(b.getY() + iYb);
			    
			    g2.setColor(colorB);
			    g2.translate(b.getDx(), b.getDy());
			    checkForWallB();
			    
			    g2.draw(b);
			    g2.fill(b);
			    
			    b.setX(b.getDx());
			    b.setY(b.getDy());
			    g2.translate(-b.getDx(), -b.getDy());
			    // B - END ----------------------------
			    

			    // A
			    a.setDx(a.getX() + iXa);
			    a.setDy(a.getY() + iYa);
			    
			    g2.setColor(colorA);
			    g2.translate(a.getDx(), a.getDy());
			    checkForWallA();
			    
			    g2.draw(a);
			    g2.fill(a);
			    
			    a.setX(a.getDx());
			    a.setY(a.getDy());
			    g2.translate(-a.getDx(), -a.getDy());
			    // A - END ----------------------------
		    }
	    }
	}

	@Override
	public void actionPerformed(ActionEvent arg0) 
	{
    	repaint();
    	if (lives > 0) score = score - .001;
    }
}

