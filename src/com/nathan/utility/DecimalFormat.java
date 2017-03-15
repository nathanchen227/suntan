package com.nathan.utility;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class DecimalFormat {
  private static final RoundingMode rm = RoundingMode.valueOf("HALF_UP");
  private static final int scale = 0;
   
    public DecimalFormat() {
        super();
    }
    
  //to convert decimal
  public static int getConvertDecimal(Double decimal){
    BigDecimal convert = new BigDecimal(decimal);
     return  convert.setScale(scale, rm).intValue();
  }
}
