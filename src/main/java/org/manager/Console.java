package org.manager;

public class Console {

   private static int level = 2;
   private final String header;

   public Console(String header) {
      this.header = "> " + header + ": ";
   }

   public void out(String output, int l) {
      if (l<=level)
         System.out.println(header + output);
   }
   public void out(String output) {
      if (1<=level)
         System.out.println(header + output);
   }

   public void err(String error, int i) {
      if (i<=level)
         System.err.println(header + error);
   }
   public void err(String error) {
      System.err.println(header + error);
   }

   protected void setLevel(int l) {
      level = l;
   }
}