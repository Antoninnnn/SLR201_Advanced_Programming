package service;

import java.io.Serializable;

/**
 * interface of all agents performing operations on Stores
 */
public interface Agent extends Serializable 
{
	/**
	 * the agent executes this method on each host, where:
	 * it gets the minimum price of the given ingredient 
	 * 			based on the minimum price found so far (from previous hosts) 
	 * 			and the price found on the current host.  
	 * @param storeIngredients: the ingredients of the current store, which the agent is inquiring 
	 * @param storePrices: the prices of the current store, which the agent is inquiring
	 */
	void getMinPrice(String[] storeIngredients, Float[] storePrices);

	/**
	 * display the result - the minimum price of the ingredient -
	 * when the agent arrives back at its starting point (the Initiator host)
	 */
	void displayResult();

	/**
	 * @return: the next host that the agent should visit
	 */
	String getNextHostName();
	
	/**
	 * @return: indicates if the next host to migrate to 
	 * 			is executing a Magazine (Store) service 
	 * 			or an Initiator service
	 */
	public boolean isNextInitiator();
	 
	/**
	 * @return: the port on which listens the local rmi-registry
	 * note: in the current setting all rmi-registries on all hosts 
	 * 			should be configured to listen on the same port
	 */
	int getRmiRegistryPort();

}
