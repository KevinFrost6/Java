import java.io.FileReader;
import java.util.Scanner;

// Kevin Frost
// 05/13/2015
public class LevelLoader 
{
	private Character[][] level;
	private int sizeX, sizeY;
	
	public LevelLoader() 
	{
		sizeX = 0;
		sizeY = 0;
	}
	
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
	
	public int getSizeX()
	{
		return sizeX;
	}
	
	public int getSizeY()
	{
		return sizeY;
	}

	public Character[][] getLevel()
	{
		return level;
	}
	
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
