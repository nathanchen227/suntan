package com.nathan.utility;

import java.io.File;
import java.io.IOException;

import java.math.RoundingMode;

import java.util.Iterator;
import java.util.Set;
import org.apache.commons.io.FileUtils;
public class FilesProcess {
    private boolean isConsistent;
    public FilesProcess(boolean isConsistent) {
        super();
        this.isConsistent = isConsistent;
    }
  
    public void execute(Set paths){
      ProgressPeer.setDistribute( 80.00 / paths.size());
     // System.out.println("distribute:" + ProgressPeer.getDistribute());
        Iterator it = paths.iterator();
        while(it.hasNext()){
          PathMapper pm = (PathMapper)it.next();
          File srcPath = pm.getSource();
          File destPath = pm.getDestination();
          //System.out.println("src:" + srcPath.getName());
         // System.out.println("dest:" + destPath.getName());
          try {
                hierarchyCycle(srcPath, destPath, 1);
            } catch (IOException ioe) {
                
                ioe.printStackTrace();
            }
        }
      
    }
 /**
     * to compare
     * @param srcName
     * @param destName
     */
  public boolean compare(File srcFile,File destFile){
       
        try{
            if(!srcFile.getName().equals(destFile.getName())){
              return false;
            }
            else if(srcFile.length() != destFile.length()){
              return false;
            }
            else if(FileUtils.isFileNewer(srcFile, destFile)){
              return false;
            }
            else{
             
            }
      
          }
          catch(Exception e){
              e.printStackTrace();
          }
         return true;
      }
   
    private int hierarchyCycle(File srcPath,File destPath,int layer)throws IOException {
        Double ratio = ProgressPeer.getDistribute() / 5;
        // System.out.println("ratio:" + ratio);
        if(layer == 1){ProgressPeer.setProgress(ProgressPeer.getProgress() + DecimalFormat.getConvertDecimal(ratio));}
        if(srcPath == null || destPath == null){
          throw new NullPointerException("Source must not be null and destination must not be null");
        }
     String srcAbsolutePath = srcPath.getAbsolutePath();
     String destAbsolutePath = destPath.getAbsolutePath();
    // System.out.println("srcAbsolutePath:" + srcAbsolutePath);
     //System.out.println("layer:" + layer);
      String []fileList = srcPath.list();
      String []dest_fileList = destPath.list();

        if (isConsistent) {
            //delete redundant files
            for (int j = 0; j < dest_fileList.length; j++) {
                boolean isExist = false;
                for (int i = 0; i < fileList.length; i++) {
                    if (fileList[i].equals(dest_fileList[j])) {
                        isExist = true;
                        break;
                    }
                }
                if (!isExist) {
                    File file =
                        new File(destAbsolutePath + "\\" + dest_fileList[j]);
                    if (file.isDirectory()) {
                        FileUtils.deleteDirectory(file);
                    } else {
                        FileUtils.forceDelete(file);
                    }
                }
            }
        }
      
      for(int i=0;i<fileList.length;i++){
        if(layer == 1){
            if(i == fileList.length / 2){ProgressPeer.setProgress(ProgressPeer.getProgress() + DecimalFormat.getConvertDecimal(ratio * 2));  }
            if(i == fileList.length - 1){ProgressPeer.setProgress(ProgressPeer.getProgress() + DecimalFormat.getConvertDecimal(ratio));   }
        }
        File src_sub = new File(srcAbsolutePath,fileList[i]);
        File dest_sub = new File(destAbsolutePath + "\\" + src_sub.getName());
      
        if(src_sub.isDirectory()){
         // System.out.println("---------------directory--------------");
        
          if(!dest_sub.exists()){
              dest_sub.mkdir();
          }
          int l = hierarchyCycle(src_sub,dest_sub,layer + 1);
          //System.out.println("dir-layer:" + l);
        }
        else{
          //System.out.println("---------------file copy--------------");
            try {
                    if (!dest_sub.exists()) {
                        FileUtils.copyFile(src_sub, dest_sub);
                    } else {
                        if (!compare(src_sub, dest_sub)) {
                            FileUtils.copyFile(src_sub, dest_sub);
                        }
                    }
                } catch (IOException ioe) {
                    // TODO: Add catch code
                    ioe.printStackTrace();
                }
        }
      }
     if(layer == 1){ProgressPeer.setProgress(ProgressPeer.getProgress() + DecimalFormat.getConvertDecimal(ratio));}
     if(layer > 1){ --layer;   }
    
      return layer; 
   }
    
    private boolean isExistFileOrDirectory(String filePath){
        File file = new File(filePath);
        return file.exists();
    }
    
    private void createDirectory(String dirPath){
      File file = new File(dirPath);
      file.mkdir();
    }
}
