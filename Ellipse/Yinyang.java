/* Kevin Kusion and Kevin Frost
 * CSIS 4461 Programming Assignment 3
 * Last edit 9/30/2015
 */

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public class Yinyang implements Shape 
{
	private Area shape;
	private Area shape2;
	private double  dx, dy, x, y;
	/**
	 * Constructs a YinYang like shape.
	 * 
	 * @param x0 x coordinate of the center of main sphere
	 * @param y0 y coordinate of the center of main sphere
	 * @param radius radius of main sphere
	 */
	public Yinyang(double x0, double y0, double radius) {
		shape = new Area(new MyEllipse(x0, y0, radius, radius));
		shape2 = new Area(new MyEllipse(x0, y0, radius, radius));
		MyEllipse cutter = new MyEllipse(x0, y0, radius-1, radius-1);
		Area rekt = new Area(new Rectangle2D.Double(x0, radius, radius, y0));
		MyEllipse topOne = new MyEllipse(x0+radius/radius, y0-radius/2, radius/2, radius/2);
		MyEllipse topCutter = new MyEllipse(x0+radius/radius, y0-radius/2, radius/2, radius/2);
		MyEllipse topCenter = new MyEllipse(x0+radius/radius, y0-radius/2, radius/20, radius/20);
		MyEllipse bottomOne = new MyEllipse(x0-radius/radius, y0+radius/2, radius/2, radius/2);
		MyEllipse bottomCenter = new MyEllipse(x0-radius/radius, y0+radius/2, radius/20, radius/20);
		shape.subtract(new Area(cutter));
		shape2.subtract(new Area(cutter));
		shape.exclusiveOr(rekt);
			shape.intersect(new Area(cutter));
			shape.add(shape2);
		shape.add(new Area(topOne));
		shape.subtract(new Area(topCutter));
		shape.add(new Area(bottomOne));
		shape.add(new Area(topCenter));
		shape.subtract(new Area(bottomCenter));
	}
	
	/**
	 * 
	 * @return x coord (double).
	 */
	public double getX()
	{
		return x;
	}
	
	/**
	 * 
	 * @return y coord (double).
	 */
	public double getY()
	{
		return y;
	}
	
	/**
	 * 
	 * @return dx coord (double).
	 */
	public double getDx()
	{
		return dx;
	}
	
	/**
	 * 
	 * @return dy coord (double).
	 */
	public double getDy()
	{
		return dy;
	}
	
	/**
	 * 
	 * @param x0 Sets the x coord (double).
	 */
	public void setX(double x0)
	{
		x = x0;
	}
	
	/**
	 * 
	 * @param y0 Sets the y coord (double).
	 */
	public void setY(double y0)
	{
		y = y0;
	}
	
	/**
	 * 
	 * @param x0 Sets the dx coord (double).
	 */
	public void setDx(double x0)
	{
		dx = x0;
	}
	
	/**
	 * 
	 * @param y0 Sets the dy coord (double).
	 */
	public void setDy(double y0)
	{
		dy = y0;
	}
	
	
	@Override
	// Checks whether a point is inside the bounds of this object.
	public boolean contains(Point2D arg0) {
		// TODO Auto-generated method stub
		return shape.contains(arg0);
	}

	@Override
	// Checks whether a point is inside the bounds of this object.
	public boolean contains(Rectangle2D arg0) {
		// TODO Auto-generated method stub
		return shape.contains(arg0);
	}

	@Override
	// Checks whether a point is inside the bounds of this object.
	public boolean contains(double arg0, double arg1) {
		// TODO Auto-generated method stub
		return shape.contains(arg0,arg1);
	}

	@Override
	// Checks whether a point is inside the bounds of this object.
	public boolean contains(double arg0, double arg1, double arg2, double arg3) {
		// TODO Auto-generated method stub
		return shape.contains(arg0,arg1,arg2,arg3);
	}

	@Override
	/**
	 * @return Returns a Rectangle that is the outside of this shape.
	 */
	public Rectangle getBounds() {
		// TODO Auto-generated method stub
		return shape.getBounds();
	}

	@Override
	/**
	 * @return Returns a Rectangle that is the outside of this shape. 2D.
	 */
	public Rectangle2D getBounds2D() {
		// TODO Auto-generated method stub
		return shape.getBounds2D();
	}

	@Override
	/**
	 * @param arg0 - an affine transform
	 * @return A PathIterator
	 */
	public PathIterator getPathIterator(AffineTransform arg0) {
		// TODO Auto-generated method stub
		return shape.getPathIterator(arg0);
	}

	@Override
	/**
	 * @param arg0 - an affine transform
	 * @return A PathIterator
	 */
	public PathIterator getPathIterator(AffineTransform arg0, double arg1) {
		// TODO Auto-generated method stub
		return shape.getPathIterator(arg0, arg1);
	}

	@Override
	/**
	 * 	Checks for intersection
	 * @param Rectangle to check
	 * @return True if it intersects, false if not.
	 */
	public boolean intersects(Rectangle2D arg0) {
		// TODO Auto-generated method stub
		return shape.intersects(arg0);
	}

	@Override
	/**
	 * 	Checks for intersection
	 * @param Respective points to check.
	 * @return True if it intersects, false if not.
	 */
	public boolean intersects(double arg0, double arg1, double arg2, double arg3) {
		// TODO Auto-generated method stub
		return shape.intersects(arg0, arg1, arg2, arg3);
	}

}
