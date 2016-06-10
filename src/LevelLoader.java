import java.io.FileReader;
import java.util.Scanner;

// Kevin Frost
// 05/13/2015

// Loads/manages a text file of a map
public class LevelLoader 
{
	// Variables
	private Character[][] level;
	private int sizeX, sizeY;
	
	// Constructor
	public LevelLoader() 
	{
		sizeX = 0;
		sizeY = 0;
	}
	
	/**
	 * Loads a text file
	 * @param fileName String. The file to be loaded.
	 * @return Boolean. True if the file loaded, false if not.
	 */
	public boolean load(String fileName)
	{
		boolean result = true;
		String temp;
		try
		{
			@SuppressWarnings("resource")
			Scanner readLevel = new Scanner(new FileReader(fileName));	
			
			sizeX = readLevel.nextInt();
			sizeY = readLevel.nextInt();
			
			level = new Character[sizeY][sizeX];
			
			for (int yy = 0; yy < sizeY; yy++)
			{
				for (int xx = 0; xx < sizeX; xx++)
				{
					temp = readLevel.next();
					
					level[yy][xx] = temp.charAt(0);
				}
			}
		
		}
		catch (Exception e)
		{
			System.out.println("EXCEPTION CAUGHT:");
			System.out.println(e.toString());
			result = false;
		}
		
		return result;
	}
	
	/**
	 * 
	 * @return The x size of the map (int)
	 */
	public int getSizeX()
	{
		return sizeX;
	}
	
	/**
	 * 
	 * @return The y size of the map (int)
	 */
	public int getSizeY()
	{
		return sizeY;
	}

	/**
	 * 
	 * @return A character array of the map
	 */
	public Character[][] getLevel()
	{
		return level;
	}
	
	/**
	 * Outputs the map
	 */
	public void printLevel()
	{
		for (int yy = 0; yy < sizeY; yy++)
		{
			for (int xx = 0; xx < sizeX; xx++)
			{
				System.out.print(level[yy][xx] + " ");
			}
			System.out.println();
		}
	}
	
}
