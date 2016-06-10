// Kevin Frost

import java.util.concurrent.ThreadLocalRandom;

public class TSP 
{
	// Variables
	private int count = 0;
	private City[] city;
	
	private int[] route, route2;
	
	
	// Constructor 
	public TSP(City[] inCity)
	{
		city = inCity;
		
		route = new int[city.length];
	}
	

	/*
	 * The distance function between two cities is the Euclidean distance 
	 * rounded to the nearest integer.  So, if city 1 is at (x1, y1), 
	 * and city 2 is at (x2, y2), the distance between them can be computed 
	 * (in Java syntax) as: 
	 * (int)Math.round( Math.sqrt((x1-x2)*(x1-x2) + (y1-y2)*(y1-y2)) )
	 * 
	 */

	/**
	 * 
	 * @param c1 City 1
	 * @param c2 City 2
	 * @return The distance (int) between c1 and c2
	 */
	private int getDistance(City c1, City c2)
	{
		return (int)Math.round(Math.sqrt((c1.getX() - c2.getX())
				* (c1.getX() - c2.getX()) + (c1.getY() - c2.getY()) 
				* (c1.getY() - c2.getY())));
	}
	
	/**
	 * Calculates the distance of an entire route
	 * @param inRoute An already calculated route.
	 * @param inMap A "map" the route is taking.
	 * @return Total distance of route (int).
	 */
	public int calcRouteDistance(City[] inRoute, int[] inMap)
	{
		int distance = 0;
		
		for (int i = 0; i < inRoute.length; i++)
		{
			if (i != inRoute.length - 1)
			{
				distance +=getDistance(inRoute[inMap[i]], inRoute[inMap[i + 1]]);
			}
		}
		
		distance += getDistance(inRoute[inMap[city.length - 1]], inRoute[inMap[0]]);
		
		return distance;
	}

	/**
	 * 
	 * @param x City name (int).
	 * @return True if it is in the current route, false if not.
	 */
	private boolean check(int x)
	{
		boolean result = false;
		
		for (int i = 0; i < count; i++)
		{
			if (x == route[i]) result = true;
		}
		
		return result;
	}
	
	/**
	 * 
	 * @param inCity Target city.
	 * @return Int value of the city shortest distance away.
	 */
	private int findShortestDist(City inCity)
	{
		int temp = 999999999;
		int outCityN = 0;
		
		for (int i = 0; i < city.length; i++)
		{
			if ((getDistance(inCity, city[i]) < temp) 
					&& (getDistance(inCity, city[i]) != 0) && !check(i))
			{
				outCityN = i;
				temp = getDistance(inCity, city[i]);
			}
		}
		
		return outCityN;
	}

	/**
	 * 
	 * @return An integer array that is a random route.
	 */
	private int[] generateRandomRoute()
	{
		int temp = 0;
		
		count = 1;
		route[0] = 0; 
		
		for (int i = 1; i < city.length; i++)
		{
			// Generates a random number based on the number of cities.
			temp = ThreadLocalRandom.current().nextInt(city.length);
			if (check(temp))
			{
				while (check(temp))
				{
					temp = ThreadLocalRandom.current().nextInt(city.length);
				}
			
				route[i] = temp;
			}
			else route[i] = temp;
			
			count++;
		}
		
		return route;
	}

	/*
	 * CALCULATE SHORTEST DISTANCE
	 * 1. Starting with the first city, find the next shortest distance
	 * 2. Add the city to the route
	 * 3. Find the next shortest distance from the city you just got to
	 *    NOTE: Scan using the check function
	 * 4. Continue and repeat until you have a full route
	 */
	// Sequentially goes from point to next shortest point
	public void calcShortRoute()
	{
		count = 0;
		route[0] = 0;
		count++;
		
		for (int i = 1; i < city.length; i++)
		{
			route[i] = findShortestDist(city[route[i - 1]]);
			count++;
		}

		System.out.println("TOTAL DISTANCE: " + calcRouteDistance(city, route));
		showRoute();
	}
	
	// 1. Start with a route
	// 2. Randomly swap two elements
	// 3. Did the path improve? 
	//		YES - Save it, go to 2
	//		NO  - Ignore it, go to 2
	/**
	 * 
	 * @param inSeconds Time in seconds (int) to try to improve the route.
	 */
	public void rhc(int inSeconds)
	{
		int r1 = 0, r2 = 0, t = 0;
		int temp = 0;

		route2 = route;
		
		long start = System.currentTimeMillis();
		long end = start + inSeconds*1000; // 60 seconds * 1000 ms/sec
		
		// 1
		route = generateRandomRoute();
		route2 = route;
		
		temp = calcRouteDistance(city, route);
		
		while (System.currentTimeMillis() < end)
		{
			// 2
			r1 = ThreadLocalRandom.current().nextInt(city.length);
			r2 = ThreadLocalRandom.current().nextInt(city.length);

			// Make sure nothing is zero...TSP always starts from 1
			while (r1 == 0 || r2 == 0) 
			{
				r1 = ThreadLocalRandom.current().nextInt(city.length);
				r2 = ThreadLocalRandom.current().nextInt(city.length);
			}
			
			t = route2[r1];
			route2[r1] = route2[r2];
			route2[r2] = t;
			
			if (calcRouteDistance(city, route2) < temp)
			{
				route = route2;
				temp = calcRouteDistance(city, route);
			}
		}

		System.out.println(calcRouteDistance(city, route));
		showRoute();
	}
	
	/**
	 * Prints the current route.
	 */
	public void showRoute()
	{
		System.out.println("Route:");
		for (int i = 0; i < city.length; i++)
		{
			System.out.printf("%8d  %8.4f  %8.4f", (route[i] + 1),
					city[route[i]].getX(), city[route[i]].getY());
			System.out.println();
		}
	}
}