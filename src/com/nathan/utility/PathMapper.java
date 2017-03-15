package com.nathan.utility;

import java.io.File;

public class PathMapper {
    private int sequence;
    private File source;
    private File destination;
    
    public PathMapper(File src,File dest) {
  
        this.source = src;
        this.destination = dest;
    }
    
    public PathMapper(){
      super();
    }


  
    public void setSequence(int sequence) {
        this.sequence = sequence;
    }

    public int getSequence() {
        return sequence;
    }

    public void setSource(File source) {
        this.source = source;
    }

    public File getSource() {
        return source;
    }

    public void setDestination(File destination) {
        this.destination = destination;
    }

    public File getDestination() {
        return destination;
    }
}
