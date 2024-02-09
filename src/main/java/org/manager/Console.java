package org.manager;

public class Console {

   private static int level = 2; //priority limit for all outputs
   private final String header;

   public Console(String header) {
      this.header = "> " + header + ": ";
   }

   //out method prints formatted String output containing header and output with System.out
   public void out(String output, int l) {
      if (l<=level)
         System.out.println(header + output);
   }
   public void out(String output) {
      if (1<=level)
         System.out.println(header + output);
   }

   //err method prints formatted String error containing header and output with System.err
   public void err(String error, int i) {
      if (i<=level)
         System.err.println(header + error);
   }
   public void err(String error) {
      System.err.println(header + error);
   }

   //setters
   protected void setLevel(int l) {
      level = l;
   }
}
