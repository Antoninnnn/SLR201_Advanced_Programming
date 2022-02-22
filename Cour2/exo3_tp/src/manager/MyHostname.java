package manager;

import java.net.*;


//
// Cette classe permet d'obtenir les nom DNS soit de facon qualifie
// soit de facon non qualifie ; et ce qq soit la forme avec laquelle
// la classe est instanciee
//
/**
 *	this class helps obtaining DNS names (either qualified or non-qualified)
 *	note: please check that the domain name corresponds to the current network configuration.
 */
public class MyHostname 
{
	private String hostname;
	private final String domain = ".enst.fr";


	public MyHostname(String host)  
	{
		if(host.equals(""))
			this.local();
		else{
			if (host.indexOf(".")==-1)
				hostname = host;
			else	
				hostname = host.substring(0,host.indexOf("."));
		}
	}


	public MyHostname() 
	{
		this.local();
	}


	private void local() 
	{
		try 
		{
			InetAddress machine = InetAddress.getLocalHost();
                	hostname = machine.getHostName();
		
			if (hostname.indexOf(".") != -1)
				hostname = hostname.substring(0, hostname.indexOf("."));

		}
		catch(UnknownHostException e) 
		{
			System.out.println ("Hello exception: " + e.getMessage ());
			e.printStackTrace ();
		}
	}


	public String getNonQualifiedHost() 
	{
		return (hostname);
	}


	public String getQualifiedHost() 
	{
		return (hostname + domain);
	}

}


