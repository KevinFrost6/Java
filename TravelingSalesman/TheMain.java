// Kevin Frost
import java.io.FileNotFoundException;
import java.util.Scanner;

public class TheMain 
{
	public static void main(String[] args) throws FileNotFoundException 
	{
		// File name
		String file = "Small.tsp";
		
		DataLoader dl = new DataLoader();

		// Loads and parses file
		dl.load(file);

		TSP tsp2 = new TSP(dl.getCities());
		
		// Calculates short route
		tsp2.calcShortRoute();
	}
}