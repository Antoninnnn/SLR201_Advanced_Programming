package manager;

import service.Agent;

/**
 * the implementation class of the agent 
 * 		to be sent across the remote hosts 
 * 		to search for the lowest ingredient price
 */
public class AgentImplem implements Agent 
{
	//version id for serializable classes
	private static final long serialVersionUID = 1L;
	
	//the hosts to visit sequentially
	private String[] hostNames;
	//the index of the current host in the hosts table (hostNames) 
	private int currentHostIndex = 0;
	//the port on which listen all rmi-registries executing on all hosts to visit 
	//		(by convention in the current TP setting)    
	private int rmiregistryPort;
	
	//the minimum ingredient price that the agent has found thus far
	//the agent takes this price value with it as it travels from host to host
	private Float myMinPrice = new Float(Float.MAX_VALUE);
	//the ingredient that this agent is looking for
	private String myIngredient="";
	//the name of the host on which is executing 
	//		the Store with the minimum price (found so far) 
	private String myMinHost = "";
	
	public AgentImplem(int rmiPort, String[] hostNames, String ingredient)
	{
		int numberOfHosts = hostNames.length;
		
		System.out.println("Agent created for vising hosts: ");
		for(int i=0; i < numberOfHosts; i++){
			System.out.println("\t>" + hostNames[i]);
		}
		
		this.rmiregistryPort = rmiPort;
		
		this.myIngredient = ingredient;
		
		this.hostNames = new String[numberOfHosts];
		for(int i=0; i < numberOfHosts; i++){
			this.hostNames[i] = hostNames[i];
		}
	}

	/* (non-Javadoc)
	 * @see service.Agent#getNextHostName()
	 */
	@Override
	public String getNextHostName()
	{
		//get the index of the next host
		currentHostIndex = currentHostIndex + 1;
		//get the name of the next host from the hosts table
		String nextHostName = hostNames[currentHostIndex];
		
		return nextHostName;
	}
	
	/* (non-Javadoc)
	 * @see service.Agent#isNextInitiator()
	 */
	@Override
	public boolean isNextInitiator(){
		return (currentHostIndex == hostNames.length-1);
	}
	
	/* (non-Javadoc)
	 * @see service.Agent#getRmiRegistryPort()
	 */
	@Override
	public int getRmiRegistryPort(){
		return this.rmiregistryPort;
	}

	/* (non-Javadoc)
	 * @see service.Agent#displayResult()
	 */
	@Override
	public void displayResult()
	{
		System.out.println(myMinHost + ": the minimal price of " + 
				myIngredient + " is " + myMinPrice );
	}

	/* (non-Javadoc)
	 * @see service.Agent#getMinPrice(java.lang.String[], java.lang.Float[])
	 */
	@Override
	public void getMinPrice(String[] ingredients, Float[] price)
	{
		//get the number of ingredients
		int size = 0;
		for(int i = 0; i<ingredients.length; i++){
			if(ingredients[i] != null)
				size++;
			else
				break;
		}

		//search for the agent's ingerdient in the current Store,
		//	get the price of this ingredient in the current Store,
		//	determine the minimum price between this Store's price 
		//			and the agent's current minimum price. 
		for (int i=0; i < size; i++){
			if (ingredients[i].equals(myIngredient)){
				if(myMinPrice.floatValue() > price[i].floatValue()){
					myMinPrice = price[i];
					myMinHost = hostNames[currentHostIndex];
				}
			}
		}
	}

}
