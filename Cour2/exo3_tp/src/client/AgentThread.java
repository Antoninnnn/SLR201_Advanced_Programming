package client;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import manager.Store;
import service.Agent;
import service.Host;

/**
 * defines the Thread that will be launched 
 * 		each time an agent arrives on a Host.
 * the thread allows the agent to perform its work on the host 
 * 		and then to move to the next host.
 */
public class AgentThread extends Thread 
{
	private Agent theAgent;//the agent with which this Thread will work
	private Store theLocalStore;//the store with which this Thread will work

	public AgentThread(Agent theAgent, Store theLocalStore) 
	{
		this.theAgent = theAgent;
		this.theLocalStore = theLocalStore;
	}

	public void run() 
	{
		System.out.println("The agent is collecting price on store " + 
				theLocalStore.storeName + " ...");
		
		try 
		{
			//execute the agent's method that re-calculates the minimum price 
			//	based on its current minimum price 
			//	and the price of the local Store	
			theAgent.getMinPrice(theLocalStore.ingredientNames, 
				theLocalStore.ingredientPrices); 
			
			//TODO
			//ask the agent for the next host it must visit
			String nextHostName = theAgent.getNextHostName();
			
			//ask the agent for the port on which listens the rmi-registry of the next host 
			//note: all rmi-registries listen to the same port on all hosts 
			int rmiregistryPort = theAgent.getRmiRegistryPort();
			
			System.out.println("The agent is migrating towards " + nextHostName);
			
			//get a reference to the remote Host service (HostImplem or Initiator) on the next host 
			String nextServiceName = null;
			//determine the type of the service running on the next host: HostImplem or Initiator;
			//the service name to look for will vary accordingly.
			if(theAgent.isNextInitiator()){
				nextServiceName = Host.initiatorName;
			}
			else{
				nextServiceName = Host.hostServiceName;
			}
			

			//TODO
			//get a reference to the remote rmi-registry
			Registry registry = LocateRegistry.getRegistry(rmiregistryPort);
			
			//TODO
			//lookup the remote service executing on the next host
			Host nextHost = registry.lookup(nextServiceName);


			//migrate the agent towards the next host
			System.out.println("AgentThread: the agent is migrating towards host: " + nextHostName + 
					", using service: " + nextServiceName);
			nextHost.migrate(theAgent);
		} 
		catch (Exception e) 
		{
			System.err.println("migre exception: " + e.getMessage());
			e.printStackTrace();
		}

	}

}
