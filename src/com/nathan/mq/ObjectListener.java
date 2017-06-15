package com.nathan.mq;

import java.text.DecimalFormat;

import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;


public class ObjectListener implements MessageListener {  
    
    public void onMessage(Message message) {  
        try {  
        	ObjectMessage obj = (ObjectMessage)message;  
          
            System.out.println("obj:" + obj.getObject()); 
           Country country =  (Country)obj.getObject();
            System.out.println("obj:" + country.getCountryId() + "--" + country.getCountryName());
           
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  
  
}  