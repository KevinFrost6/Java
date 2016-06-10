// Kevin Frost

public class City 
{
	private double x;
	private double y;
	private int name;
	
	// Constructor
	public City (int inName, double inX, double inY)
	{
		name = inName;
		x = inX;
		y = inY;
	}
	
	/**
	 * 
	 * @return Returns the int name of this city.
	 */
	public int getN()
	{
		return name;
	}
	
	/**
	 * 
	 * @return Returns the x coord of this city (double).
	 */
	public double getX()
	{
		return x;
	}
	
	/**
	 * 
	 * @return Returns the y coord of this city (double).
	 */
	public double getY()
	{
		return y;
	}
	
	/**
	 * @return Returns a string of all values this city is holding. 
	 */
	public String toString()
	{
		String out = "Name: " + name + " x: " + x + " y: " + y;  
		return out;
	}
}
