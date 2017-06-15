package com.nathan.mq;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQMapMessage;

import javax.jms.Session;
public class Publisher { 
	private ConnectionFactory factory;
	private Session session;
	private Connection  connection;
	private  MessageProducer producer;
	private Destination[]  destinations;
	public Publisher(String brokerURL)throws JMSException{
		 factory = new ActiveMQConnectionFactory(brokerURL);  
	     connection = factory.createConnection();  
	    try {  
	    connection.start();  
	    } catch (JMSException jmse) {  
	        connection.close();  
	        throw jmse;  
	    }  
	    session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);  
	    producer = session.createProducer(null);  
	}
	
	public static void main(String[] args)throws Exception {
		Publisher publisher = new Publisher("tcp://192.168.56.5:61616");  
	  
	      //  publisher.sendMessage("Test-Message");  
	      //  System.out.println("Published  job messages");  
		args = new String[]{"pig","lion","tiger","eagle","snake"};
		 publisher.setTopics(args);  
		  for(int i = 0; i < 5; i++) {  
		        publisher.sendMessage(args);  
		        System.out.println("Publisher-" + args[i]);  
		        try {  
		            Thread.sleep(10000);  
		        } catch(InterruptedException e) {  
		            e.printStackTrace();  
		        }  
		    }  
	       
	}
	
    protected void setTopics(String[] stocks) throws JMSException {  
       destinations = new Destination[stocks.length];  
        for(int i = 0; i < stocks.length; i++) {  
            destinations[i] = session.createTopic("STOCKS." + stocks[i]);  
        }  
    }  
    
    protected void sendMessage(String[] stocks) throws JMSException {  
        for(int i = 0; i < stocks.length; i++) {  
            Message message = createStockMessage(stocks[i], session);  
            System.out.println("Sending: " + ((ActiveMQMapMessage)message).getContentMap() + " on destination: " + destinations[i]);  
            producer.send(destinations[i], message);  
        }  
    }  
      
    protected Message createStockMessage(String stock, Session session) throws JMSException {  
        MapMessage message = session.createMapMessage();  
        message.setString("stock", stock);  
        message.setDouble("price", 1.00);  
        message.setDouble("offer", 0.01);  
        message.setBoolean("up", true);  
              
        return message;  
    }  
    
    
    public void sendMessage(String job) throws JMSException {  
    	 Destination destination = session.createQueue(job);  
    	 for(int i=1;i<100;i++){
    		 Country country = new Country();
	         country.setCountryId("k" + i);
	         country.setCountryName("china" + i);
         Message message = session.createObjectMessage(country);  
         System.out.println("Sending: id: " + ((ObjectMessage)message).getObject() + " on queue: " + destination);  
         producer.send(destination, message);  
    	 }
    	  
       /* for(int i = 0; i < jobs.length; i++)  
        {  
            String job = jobs[i];  
            Destination destination = session.createQueue("JOBS." + job);  
            Message message = session.createObjectMessage(i);  
            System.out.println("Sending: id: " + ((ObjectMessage)message).getObject() + " on queue: " + destination);  
            producer.send(destination, message);  
        }  */
    }  
	
}  