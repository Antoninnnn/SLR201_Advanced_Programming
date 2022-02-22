package manager;

import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.*;

import service.Agent;
import service.Host;
import client.AgentThread;

//manages the Store at a given location (host)
public class HostImplem implements Host{
	
	//the local Store (with its ingredients and prices) 
	public Store myStore;

	/**
	 * instantiates the local host manager:
	 * 		creates a local store and keeps its reference.
	 * @param fileName: the name of the file from which the Store is initialized (Mag1, Mag2, Mag3, ...)
	 * @throws RemoteException
	 */
	public HostImplem(String fileName) throws RemoteException
	{
		super();
		myStore = new Store(fileName);
	}

	/* (non-Javadoc)
	 * @see service.Host#migrate(service.Agent)
	 */
	@Override
	//this method is called remotely, 
	//		in order to transport the agent to the local host.
	//it starts a new Thread in which the agent can execute;
	//this unblocks the remote client immediately, before the agent finishes to execute.
	public void migrate(Agent agent) 
	{
		AgentThread myThread = new AgentThread(agent, myStore);
		myThread.start();
	}


	public static void main(String[] args) 
	{
		//if a Security Manager does not already exist then create one.
		if (System.getSecurityManager() == null) 
			System.setSecurityManager(new MySecurity());
		
		//check the input parameters
		if (args.length != 2){
			System.out.println("Two parameters: 1) rmiregistry port; " +
					"2) the Store's file name (Mag1 or Mag2 or Mag3 or ...)");
			System.exit(1);
		}

		try{

			//create the HostImplem object to be made available remotely
			Host myHost = new HostImplem(args[1]);
			
			//rmi-registry port
			int rmiregistryPort = new Integer(args[0]).intValue();
			
			//TODO
			//register the HostImplem instance with the rmi-registry, using the Host.hostServiceName
			myStore = (Store)UnicastRemoteObject.exportObject(myHost,0);
			Registry registry = LocateRegistry.getRegistry(rmiregistryPort);
			register.rebind(Host.hostServiceName,myStore)

			System.out.println("HostImplem registered: " + Host.hostServiceName);
		} 
		catch (Exception e) 
		{
			System.err.println("Hote_implem exception: " + e.getMessage());
			e.printStackTrace();
		}
	}
}


