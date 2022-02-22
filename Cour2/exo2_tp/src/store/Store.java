package store;
                                                                             
/**
 * remote interface for Store services
 */
public interface Store extends java.rmi.Remote {
	
	/**
	 * get the price of the given ingredient
	 * note: this method can be called remotely 
	 * @param ingredient: the ingredient whose price should be returned
	 * @return: the price of the ingredient
	 * @throws java.rmi.RemoteException
	 */
	float getPrice(String ingredient) throws java.rmi.RemoteException;       
}                                                                               
             


