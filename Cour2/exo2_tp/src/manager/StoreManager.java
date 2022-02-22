package manager;

import java.io.*;
import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.*;

import store.Store;

/**
 * class of the remote Store objects/services
 * instances of this class can be registered with the rmi-registry and called remotely
 */
public class StoreManager implements Store  {

	//each StoreManager object reads the ingrendients and their prices from a MagX file 
	//and stroe them in the ingredientNames and ingredientPrices tables, respectively
	String[] ingredientNames = new String[100] ;
	Float[] ingredientPrices = new Float [100];
	
	//counts the lines read from the MagX file
	int lineCounter = 0 ;
	 
	//service name for the StoreManager object to register and make available remotely
	static final String serviceName = "StoreService";

	/**
	 * reads the ingredients and prices from the given file and populates the ingredientNames and ingredientPrices tables 
	 * @param fileName: the name of the file from which to read ingredients and their prices (e.g. Mag1, Mag2, Mag3)  
	 * @throws RemoteException
	 * @throws IOException
	 */
	public StoreManager (String fileName) throws RemoteException, IOException {
		
		BufferedReader ingredients = null;
		String line;
		int i = 0;
      
		//open the file
		try {
			ingredients = new BufferedReader(new FileReader(fileName));
		}
		catch(FileNotFoundException exception) {
			System.out.println("Error opening file " + fileName);
			exception.printStackTrace();
			System.exit(0);
		}

		//read the file line by line to extract the ingredient names and prices 
		while ((line = ingredients.readLine()) != null) {
			
			System.out.println("Ingredient number " + i + ": " +  line);
			
			//even lines contain ingredient names 
			//and odd lines contain ingredient prices
			if ( (lineCounter%2) == 0){ 
				ingredientNames[i] = line;
			}
			else{
				ingredientPrices[i] = Float.valueOf(line);
				i = i + 1;
			}
			
			lineCounter = lineCounter + 1;
		}

		//close the file
		ingredients.close();
	}
  
	/* (non-Javadoc)
	 * @see store.Store#getPrice(java.lang.String)
	 */
	@Override
	public float getPrice(String ingredient) throws java.rmi.RemoteException {
		
		System.out.println("the StoreManager is looking for the price of: " + ingredient );
		
		for (int i = 0; i < lineCounter/2; i++){
			if (ingredientNames[i].equals(ingredient)){
				return ingredientPrices[i].floatValue();
			}
		}
	  
		return -1;
	}
  

	public static void main(String args[]) throws IOException {
		StoreManager storeServer = null;
     
		//check input parameters
		if (args.length != 2) {
			System.out.println("Two parameters: 1) rmi-registry port; 2) file name");
			System.exit(1);
		}

		//if a SecurityManager does not already exist then create one
		if (System.getSecurityManager() == null){ 
			System.setSecurityManager(new RMISecurityManager());
		}
 
		//create a StoreManager instance and
		//register it with the local rmi-registry
		try {
			//create a StoreManager object
			//this store will manage the products in the MagX file given as parameter (args[1]) 
			storeServer = new StoreManager(args[1]);
			
			//the rmiregistry port
			int rmiPort = new Integer(args[0]).intValue();
			
			//export the Store instance to the RMI runtime, so that it can receive remote method calls
			//the export method returns the exported object's stub, to be registered with the rmi-registry 
            Store stub = (Store)UnicastRemoteObject.exportObject(storeServer, 0);
            
            //Obtain a reference to a rmiregistry on the local host on the specified port (default port 1099)
            Registry registry = LocateRegistry.getRegistry(rmiPort);
            
            //register the stub in the rmiregistry
            registry.rebind(StoreManager.serviceName, stub);

			System.out.println("StoreManager server bound: " + serviceName);

		} 
		catch (RemoteException e) {
			System.out.println("Store Manager error: " + e.getMessage());
			e.printStackTrace();
		}
	}
}


