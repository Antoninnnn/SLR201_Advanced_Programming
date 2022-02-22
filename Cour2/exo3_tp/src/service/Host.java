package service;

import java.rmi.Remote;
import java.rmi.RemoteException;


public interface Host extends Remote 
{	
	//the HostImplem service name
	public static final String hostServiceName = "HostService";
	
	//the Initiator service name
	public static String initiatorName = "InitiatorService";

    /**
     * migrates the agent to the next host
     * @param agent: the agent to migrate
     * @throws RemoteException
     */
    void migrate(Agent agent) throws RemoteException;

}


