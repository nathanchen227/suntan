package com.nathan.utility;

import java.io.File;

import java.io.IOException;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.ConfigurationFactory;
import org.apache.commons.configuration.XMLConfiguration;

public class DirConfigLoader {
    private static  XMLConfiguration  config;
    private static Set paths;


    public DirConfigLoader() {
        super();
    }

    public static Set load(String file) {
        try {
           // ConfigurationFactory factory = new ConfigurationFactory(file);
            config = new XMLConfiguration(file);
           // config = factory.getConfiguration();
          //System.out.println(config.getBasePath());
          //System.out.println("directories:" + config.containsKey("path"));
            List pathList = config.getList("path");
         // System.out.println("directories:" + pathList.size());
         
            paths = new HashSet();
            for (int i = 0; i < pathList.size(); i++) {
              //System.out.println("path size:" + paths.size());
                String source = config.getString("path(" + i +")[@source]");
                String destination = config.getString("path(" + i +")[@destination]");
               // System.out.println("src:" + source);
                if(source == null || source.equals("")){continue;}
                if(destination == null || destination.equals("")){continue;}
                
                PathMapper pathMapper = new PathMapper();
                pathMapper.setSequence(i + 1);
                pathMapper.setSource(new File(source));
                pathMapper.setDestination(new File(destination));
                paths.add(pathMapper);
              //System.out.println("paths size:" + paths.size());
            }

        } 
        catch (ConfigurationException ce) {
            System.out.println("ConfigurationException:"  + ce.getMessage());
        }

        return paths;

    }
    
    public static Set load(String srcPath,String destPath){
      paths = new HashSet();
      PathMapper pathMapper = new PathMapper();
      pathMapper.setSequence(1);
      pathMapper.setSource(new File(srcPath));
      pathMapper.setDestination(new File(destPath));
      paths.add(pathMapper);
      return paths;
    }


}
