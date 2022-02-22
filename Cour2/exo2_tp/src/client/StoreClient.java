package client;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import store.Store;

/**
 * This client will search through the list of remote hosts given as parameter,
 * 		inquiring each of them for their ingredient price 
 * 		and returning the minimum price.
 * Each of the hosts is inquired separately, based on a client-server request-reply model.
 */
public class StoreClient 
{

	//the result message to be displayed to the user 
	static String message = "";

	//displays the result of the search for the minimum ingredient price
	public void displayResult() {
		System.out.println(message);
	}
  
	public static void main(String args[]) {
		
		float price = 0; //stores the price inquiry result from each store
		float leastExpensive = Float.MAX_VALUE;//stores the lowest price (found thus far) 
		String leastExpensiveSite = "no site";//the name of the least expensive store (found thus far)
		
		//check input arguments
		if (args.length < 3) {
			System.out.println("At least three parameters: " +
					"1) rmiregistry-port; 2) product; 3) host-name; {host-name}...  ");
			System.exit(1);
		}
		
		//if there is no security manager then create one
		if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }
		
	
		//create a client instance
		StoreClient client = new StoreClient();

		//the name of the remote host on which runs the Store server to be inquired about its prices
		String hostName;
		
		//the rmiregistry port
		int rmiPort = new Integer(args[0]).intValue();

		//TODO
		//the name of the remote service 
		String name = "StoreService";
		
		//the product to look for
		String product = args[1];

		//TODO
		//Inquire successively all stores (running on remote hosts)
		//	and keep the minimal price and the store that proposes it 
		for (int i = 2; i < length(args) ; i++) {

			//get the reference to the remote Store service to inquire
			//then inquire the service about the price of the product
			try {
				
				//the name of the remote host on which run the rmiregistry and store service to inquire 
				hostName = args[i];
				
				System.out.println("Connection to service: " + name + "; on host: " + hostName);
				
				//TODO
				//get a reference to the remote rmi-registry on the specified host and port
				Registry registry = LocateRegistry.getRegistry(hostName,rmiPort);
				
				//lookup the store service
				Store store = (Store)registry.lookup(name);
				
				//TODO 
				//inquire the store about the price of the product
				price = store.getPrice(product);
			} 
			catch (Exception e) {
				System.out.println("Store exception: " + e.getMessage());
				e.printStackTrace();
			}
	
			//determine the minimum price considering the new price from the last inquiry
			if (leastExpensive > price) {
				leastExpensive = price;
				leastExpensiveSite = args[i];
			}
		}//end for
       
		//display the message announcing the store with the cheapest product 
		message = leastExpensiveSite + ": the minimum price of " + args[1] + " is " + leastExpensive;
		client.displayResult();
	}
}
