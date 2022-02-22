package manager;

import java.rmi.*;



/**
 * defines a SecurityManager that does very little, to say the least.
 */
public class MySecurity extends RMISecurityManager 
{
	public void checkConnect(String host, int port){}

	public void checkConnect(String host, int port, Object context){}
}


