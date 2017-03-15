package com.nathan.utility;

public class ProgressPeer {
   private static int progress = 0;
   private static Double distribute = 0.00;  //assingment value of single a path
   private static int upLayer = 0;
   private static int downLayer = 0;
   private static boolean isLowestLayer = false;
    public ProgressPeer() {
        super();
    }

    public static void setProgress(int progress) {
        ProgressPeer.progress = progress;
    }

    public static int getProgress() {
        return progress;
    }

    public static void setUpLayer(int upLayer) {
        ProgressPeer.upLayer = upLayer;
    }

    public static int getUpLayer() {
        return upLayer;
    }

    public static void setDownLayer(int downLayer) {
        ProgressPeer.downLayer = downLayer;
    }

    public static int getDownLayer() {
        return downLayer;
    }

    public static void setIsLowestLayer(boolean isLowestLayer) {
        ProgressPeer.isLowestLayer = isLowestLayer;
    }

    public static boolean isIsLowestLayer() {
        return isLowestLayer;
    }


    public static void setDistribute(Double distribute) {
        ProgressPeer.distribute = distribute;
    }

    public static Double getDistribute() {
        return distribute;
    }
}
