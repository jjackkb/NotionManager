package org.manager;

//The Console class is used across the project to format and print both output and error messages with class-specific headers
public class Console {

   private static int level = 2; //int level defines the priority limit for any output or error to be printed. priority is in descending order
   private final String header;

   public Console(String header) {
      this.header = "> " + header + ": "; //formal parameter header should be defined as <Class>.class.getName()
   }

   //out method prints formatted String output containing header and output with System.out
   public void out(String output, int l) {
      //int l refers to this output's priority level
      if (l<=level)
         System.out.println(header + output);
   }
   //out method w/o formal parameter for output priority level
   public void out(String output) {
      if (1<=level)
         System.out.println(header + output);
   }

   //err method prints formatted String error containing header and output with System.err
   public void err(String error, int i) {
      if (i<=level)
         System.err.println(header + error);
   }
   //err method w/o formal parameter for err priority level
   public void err(String error) {
      System.err.println(header + error);
   }

   //setters
   protected void setLevel(int l) {
      level = l;
   }
}