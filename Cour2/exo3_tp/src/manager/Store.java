package manager;

import java.io.*;

/**
 * one Store instance is available on each host, 
 * 		and holds a different list of ingredients and prices 
 * 		(initiated from a different MagX file)
 */
public class Store 
{

	//each Store object reads the ingredients and their prices from a MagX file 
	//and stores them in the ingredientNames and ingredientPrices tables, respectively
	public String[] ingredientNames = new String[100] ;
	public Float[] ingredientPrices = new Float[100];
 
	//the store's name - given by the name of the MagX file that the store will be associated with
	public String storeName = null;


	/**
	 * instantiates and customizes a local Store:
	 * reads the ingredients and prices from the given file 
	 * 					and populates the ingredientNames and ingredientPrices tables
	 * @param fileName: the name of the file from which 
	 * 					to read ingredients and their prices (e.g. Mag1, Mag2, Mag3)  
	 */
	public Store(String fileName) 
	{
		this.storeName = fileName;
		
		BufferedReader ingredients = null;
		String line;
		int i = 0;
		
		//open the file
		try 
		{
			ingredients = new BufferedReader(new FileReader(fileName));
			
			int lineCounter = 0 ;
	
			//read the file line by line to extract the ingredient names and prices 
			while ((line = ingredients.readLine()) != null)
			{
				System.out.println("Ingredient number " + i + ": " +  line);
				if ( (lineCounter%2) == 0){ 
					ingredientNames[i] = line;
				}
				else {
					ingredientPrices[i] = Float.valueOf(line);
					i = i+1;
				}
				lineCounter++;
			}
			
			//close the file
			ingredients.close();
	
		}
		catch (Exception e) 
		{
			System.err.println("Store exception: " + e.getMessage());
			e.printStackTrace();
		}
	}
}
