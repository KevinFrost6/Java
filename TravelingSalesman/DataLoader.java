// Kevin Frost
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class DataLoader 
{
    // Variables
	private Scanner file;
    private String fileInfo;
    private int cityNumber;
    
    private City[] city;
    
    // Imports data from chosen file
    /**
     * @param inFN name of file to load (String)
     * @throws FileNotFoundException
     */
    public void load(String inFN) throws FileNotFoundException
    {
        String check = " ";
    	file = new Scanner(new FileReader(inFN));
        
    	// Rip apart the string and find the number of cities
    	// Save it to a misc string named fileInfo
    	while (!check.equals("NODE_COORD_SECTION"))
        {
        	check = file.next();
        	// Get the number of cities from the file
        	if (check.equals("DIMENSION"))
        	{
        		// Removes the character ':'
        		check = file.next();
        		fileInfo = fileInfo + check + " ";
        		
        		// Extracts the number of cities
        		cityNumber = file.nextInt();
        		fileInfo = fileInfo + cityNumber + " ";
        	}
        	
        	// Dumps the fileinfo into one string
        	fileInfo = fileInfo + check + " ";
        }

    	// Now that we have the number of cities, use it to
    	// initialize an array
    	city = new City[cityNumber];
    	
    	// Loads the city data from the file into the city array
    	for (int i = 0; i < cityNumber; i++)
        {
    		file.nextInt();
        	city[i] = new City(i, file.nextDouble(), file.nextDouble());
    	}
    }
    
    /**
     * 
     * @return Returns an array of cities
     */
    public City[] getCities()
    {
    	return city;
    }
    
    // Dumps all the city data being held
    public void showCities()
    {
    	System.out.println("CITY LIST");
    	
    	for (int i = 0; i < cityNumber; i++)
    	{
    		System.out.println(city[i]);
    	}
    }
    


}
