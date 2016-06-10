import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public class MyEllipse implements Shape 
{
	private GeneralPath path;
	
	// Constructor
	public MyEllipse(double x0, double y0, double a, double b) 
	{
		path = new GeneralPath();
		double tMin = 0;
	    double tMax = 2*Math.PI;
	    int numSegments = 30;
	    double x1 = getX(x0,a,tMin);
	    double y1 = getY(y0,b,tMin);
	    path.moveTo(x1, y1);
	   
	    double tIncrement = (tMax-tMin) / numSegments;
	    for (int i = 1; i <= numSegments; i++) {
	    	double x2 = getX(x0,a,tMin+tIncrement*i);
	    	double y2 = getY(y0,b,tMin+tIncrement*i);
	    	path.lineTo(x2, y2);
	    }
	}
	
	/*
	 * NOTE: x = a cos t
	 *		 y = b sin t  
	 */
	
	/**
	 * @param x0 x0 coord
	 * @param a Radius
	 * @param Ranges from 0 to 2pi radians 
	 * @return Returns x coord (double).
	 */
	private double getX(double x0, double a, double t) {
		return x0 + a * Math.cos(t);
	}

	/**
	 * @param x0 coord
	 * @param b Radius
	 * @param Ranges from 0 to 2pi radians 
	 * @return Returns y coord (double).
	 */
	private double getY(double y0, double b, double t) {
		return y0 + b * Math.sin(t);
	}
	
	@Override
	/**
	 * @param 2D point.
	 * @return True if contains point, false if not.
	 */
	public boolean contains(Point2D arg0) {
		// TODO Auto-generated method stub
		return path.contains(arg0);
	}

	@Override
	/**
	 * @param 2D Rectangle
	 * @return True if contained, false if not.
	 */
	public boolean contains(Rectangle2D arg0) {
		// TODO Auto-generated method stub
		return path.contains(arg0);
	}

	@Override
	/**
	 * @param arg0 Point x.
	 * @param arg1 Point y.
	 * @return True if contained, false if not.
	 */
	public boolean contains(double arg0, double arg1) {
		// TODO Auto-generated method stub
		return path.contains(arg0,arg1);
	}

	@Override
	/**
	 * Manual set of points to check
	 * @return True if contained, false if not.
	 */
	public boolean contains(double arg0, double arg1, double arg2, double arg3) {
		// TODO Auto-generated method stub
		return path.contains(arg0,arg1,arg2,arg3);
	}

	@Override
	/**
	 * @return A rectangle that is the bounds of this shape.
	 */
	public Rectangle getBounds() {
		// TODO Auto-generated method stub
		return path.getBounds();
	}

	@Override
	/**
	 * @return A 2D rectangle that is the bounds of this shape.
	 */
	public Rectangle2D getBounds2D() {
		// TODO Auto-generated method stub
		return path.getBounds2D();
	}

	@Override
	/**
	 * @param An AffineTransform.
	 * @return A PathIterator.
	 */
	public PathIterator getPathIterator(AffineTransform arg0) {
		// TODO Auto-generated method stub
		return path.getPathIterator(arg0);
	}

	@Override
	/**
	 * @param arg0 - an AffineTransform.
	 * @return A PathIterator.
	 */
	public PathIterator getPathIterator(AffineTransform arg0, double arg1) {
		// TODO Auto-generated method stub
		return path.getPathIterator(arg0, arg1);
	}

	@Override
	/**
	 * @param 2D Rectangle to check.
	 * @return Boolean. True if intersects, false if not.
	 */
	public boolean intersects(Rectangle2D arg0) {
		// TODO Auto-generated method stub
		return path.intersects(arg0);
	}

	@Override
	/**
	 * 	Checks for intersection
	 * @param Respective points to check.
	 * @return True if it intersects, false if not.
	 */
	public boolean intersects(double arg0, double arg1, double arg2, double arg3) {
		// TODO Auto-generated method stub
		return path.intersects(arg0, arg1, arg2, arg3);
	}

}
