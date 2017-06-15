package com.nathan.mq;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;

public class Consumer {
	private ActiveMQConnectionFactory factory;
	private Session session;
	private Connection  connection;
    public Consumer(String brokerURL) throws JMSException {  
        factory = new ActiveMQConnectionFactory(brokerURL);  
        factory.setTrustAllPackages(true);
        connection = factory.createConnection();  
        connection.start();  
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);  
    }  
	public static void main(String[] args)throws Exception {
		   System.out.println("start....");
		    Consumer consumer = new Consumer("tcp://192.168.56.5:61616"); 
		    String type = "P2P2";
		    if(type.equals("P2P")){
		    	 Destination destination = consumer.getSession().createQueue("Test-Message");  
				    MessageConsumer messageConsumer = consumer.getSession().createConsumer(destination);  
				    messageConsumer.setMessageListener(new ObjectListener());  
		    }
		    else{
		    	args = new String[]{"pig","lion","tiger","eagle","snake"};
			    for (String stock : args) {  
				    Destination destination = consumer.getSession().createTopic("STOCKS." + stock);  
				    MessageConsumer messageConsumer = consumer.getSession().createConsumer(destination);  
				    messageConsumer.setMessageListener(new MapListener());  
			    }  
		    }

	}
	
	public Session getSession() {  
	    return session;  
	}  

}
