package tpt.jms;

import java.util.Enumeration;

import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
//import javax.jms.Message;
import javax.jms.Queue;
//import javax.jms.QueueBrowser;
//import javax.jms.QueueReceiver;
import javax.naming.Context;
import javax.naming.InitialContext;
//import javax.naming.NamingException;


//synchronous message reader from a destination; reads messages without consuming them.
public class Receiver {

	//connection identifiers
	public static final String USERNAME = "admin";
	public static final String PSW = "joram";

    public static void main(String[] args)
    {
    	//admin objects
        Context context = null; // a determiner
        ConnectionFactory factory = null; //a determiner
        //naming configs
        String factoryName = "ConnectionFactory";
        String destName = null;
        Destination dest = null;//a determiner
        //jms
        JMSContext jmsContext = null;
        JMSConsumer receiver = null;
        //
        int count = 1;

      //check arguments
        if (args.length < 1 || args.length > 2){
        	System.out.println("usage: Receiver <destination> [count]");
        	System.exit(1);
	    }

        //get the name of the Destination (queue or topic)
        destName = args[0];

        //get the number of messages to be received
        if (args.length == 2){
        	count = Integer.parseInt(args[1]);
	    }

        try{
        	// create the JNDI initial context
        	context = new InitialContext();

        	// look up the ConnectionFactory
        	factory = (ConnectionFactory) context.lookup(factoryName);

        	// look up the Destination
        	dest = (Destination) context.lookup(destName);

        	//make sure the destination is queue, then cast it
        	/*
        	if(!(dest instanceof Queue)){
        		System.out.println("Warning: can only browse from a destination of type queue. "
        				+ "Since destination " + destName + " is *not* a queue, the browser will return...");
        		return;
        	}
        	*/
        	//else
        	//Queue queue = (Queue)dest;

        	//close intitialContext
        	context.close();

        	//create the jms context (replacing connection & session in JMS1)
        	//the session will be non-transacted and messages received by this session will be acknowledged automatically
        		//--> JMSContext.AUTO_ACKNOWLEDGE is the default mode
			jmsContext = factory.createContext(USERNAME, PSW);

			// create the browser
			receiver = jmsContext.createConsumer(dest);

			System.out.println("Receiver Ready ...");

			//browse messages
			// Enumeration<Message> messages;
			// Message message;
			String textMessage;

			//messages = receiver.receiveBody();
			/*
			if((messages == null) || (!messages.hasMoreElements())){
				System.out.println("Receiver found no messages in queue " + destName);
			}
			else{
				while(messages.hasMoreElements()){
					message = messages.nextElement();
					if(message instanceof Message){
						textMessage = message.getBody(String.class);
						System.out.println("Received: " + textMessage);
					}
					else{
						System.out.println("Browsed message of unknown type " + message.getClass().getName());
					}
				}

			}
			*/
			for (int i = 0; i < count; ++i){
				//receive message synchronously -> block until msg or timeout
				//receiver params: message type, time-out in ms
				textMessage = receiver.receiveBody(String.class, 10000);
				System.out.println("Received: " + textMessage + " " + i);
				
			}
			//close JMSConext
			jmsContext.close();

	    }
        catch (Exception exception){
        	exception.printStackTrace();
	    }

    }

}
