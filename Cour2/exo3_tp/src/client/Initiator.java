package client;

import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.*;

import manager.AgentImplem;
import manager.MyHostname;
import manager.MySecurity;

import service.Agent;
import service.Host;


/**
 * this class implements the entity that will
 * 		create an agent, 
 * 		send it across the network to find the minimum price, 
 * 		and wait for its return.
 * this class also implements the Host interface, 
 * 		so that the agent makes no difference between the Hosts it visits 
 * 		(i.e. HostImplem or Initiator) 
 */
public class Initiator implements Host
	{
	
	static private boolean wait = true;

	public Initiator() throws RemoteException
	{
		super();
	}

	@Override
	//when this method is called on an Initiator object, 
	//		it means that the agent has returned to the starting point 
	// 		and carries the result.
	//=> simply display the agent's result (minimum price)
	public void migrate(Agent agent) 
	{
		agent.displayResult();
		wait = false;
    }

	public static void main(String args[]) 
	{
		//if a SecurityManager is not availabe then create one
		if (System.getSecurityManager() == null) 
			System.setSecurityManager(new MySecurity());

		//check input parameters
		if (args.length < 3)
		{
			System.out.println("At least three parameters: " +
					"1) rmi-registry port; 2) product; 3) host; {host} ... ");
			System.exit(1);
		}

		try 
		{
			//create an Initiator to be provided as a remote service
			Host myInitiator = new Initiator();
			
			//register the initiator instance with the rmiregistry
			//get the port of the local rmi-registry
			int rmiregistryPort = new Integer(args[0]).intValue();
			
			//TODO 		
			//register the initiator service with the rmi-registry
			//	1. get the initiator's stub; 
			Host stub = (Host)UnicastRemote.exportObject(myInitiator,0);
			//	2. get the local rmi-registry; 
			Registry registry = LocateRegistry.getRegistry(rmiregistryPort);
		
			//	3. rebind the stub with the Host.initiatorName in the rmi-registry
			registry.rebind(Host.initiatorName,stub);

			System.out.println("Initiator registered: " + Host.initiatorName + 
					"; in local rmiregistry listening on port: " + rmiregistryPort);

			//create the table of remote hosts that the agent should visit
			//at the end of the list add the local-host (on which the initiator service executes) 
			//			so that the agent can return to the starting point (host).
			String[] hostsToVisit = new String[args.length-1];//since the localhost is also added at the end
			int j = 0;
			for(int i = 2; i < args.length; i++){
				MyHostname remoteHost = new MyHostname(args[i]);
				hostsToVisit[j] = remoteHost.getNonQualifiedHost();//use getQualifiedHost if the domain name is also needed
				j = j + 1;
			}
			//add the local host name to the end of the list
			MyHostname localHost = new MyHostname();
			hostsToVisit[j]= localHost.getNonQualifiedHost();

			//instantiate the agent, setting its 
			//	rmi-registry port, list of hosts to visit and ingredient to search for. 
			AgentImplem agent = new AgentImplem(
					Integer.parseInt(args[0]), hostsToVisit, args[1]);

			//get the name of the first host the agent should visit
			String remoteHostName = hostsToVisit[0];
			//get a reference to the rmi-registry on the remote host
			registry = LocateRegistry.getRegistry(remoteHostName, rmiregistryPort);
			//lookup the Host service executing on the remote host
			Host remoteHost = (Host)registry.lookup(Host.hostServiceName);
			
			System.out.println("Initiator: the agent is migrating towards host: " + remoteHostName + 
					", using service: " + hostServiceName);

			//migrate the agent to the remote host
			remoteHost.migrate(agent);

			//wait for the agent to return 
			while(wait)
			{	
				//print messages while waiting..
				System.out.println("Waiting for the result ...");
				try {Thread.sleep(100) ;} catch(Exception e) {};
			}

			//the agent has returned with the result
			//(the result is returned to the user via the Initiator's migrate method)
			System.out.println("The agent has returned and the result received.");
			System.exit(0);	

		} 
		catch (Exception e) 
		{
			System.err.println("migration exception: " + e.getMessage());
			e.printStackTrace();
		}
	}    
}


