package com.nathan.utils;


import java.text.DecimalFormat;
import java.util.Random;

public class RandomUtil
{
  public static int getRand(int paramInt1, int paramInt2)
  {
    if (paramInt1 > paramInt2)
    {
      paramInt1 ^= paramInt2;
      paramInt2 = paramInt1 ^ paramInt2;
      paramInt1 ^= paramInt2;
    }
    Random localRandom = new Random();
    return localRandom.nextInt(paramInt2 - paramInt1 + 1) + paramInt1;
  }
  
  public static void main(String[] args) {
	  int a = RandomUtil.getRand(40, -40);
	  
	  String b = String.valueOf(a);
	  System.out.println(b);
	  if(b.indexOf("-") > -1){
		 
		  b = b.substring(1);
	  }
	  b = "0." + b;
	  System.out.println(b);
	  Double basePoint = 8888.88;
	  basePoint += Double.valueOf(b);
	  DecimalFormat    df   = new DecimalFormat("######0.00"); 
	  System.out.println(df.format(basePoint));
  }
}