package org.manager;

import java.text.DecimalFormat;
import java.util.Date;

//The Clock class is used by org.manager.Daemon to track elapsed time and execute methods intended to run periodically.
public class Clock {

   private static final Console con = new Console(Clock.class.getName());
   private static long refTime = 0; //refTime is used to calculate elapsed time of program execution
   private int h;
   private int m;
   private int s;

   public Clock() {
      con.out("Instantiating Clock object", 2);
      Date d = new Date(); //Date is used to get current time

      this.h = d.getHours();
      this.m = d.getMinutes();
      this.s = d.getSeconds();
   }

   //update is called by Daemon.main every second, defines the current time, increments refTime and checks for time-elapsed milestones
   public void update() {
      //define new current time
      Date d = new Date();

      h = d.getHours();
      m = d.getMinutes();
      s = d.getSeconds();

      //each nested if statement checks if a span of time has passed and execute its respective method
      //(refTime != 0) checks program has not just started because all if statements result to true
      if (refTime != 0) {
         if (refTime % 5 == 0) //Every 5 seconds
            sec5();
         if (refTime % 900 == 0) //Every 15 minutes
            min15();
         if (refTime % 1800 == 0) //Every 30 minutes
            min30();
         if (refTime % 3600 == 0) //Every hour
            hour1();
      }
      refTime++;
   }

   //method execution lists for amount of elapsed time
   private void sec5() {
      con.out("5 seconds have passed!");
   }
   private void min15() {
      con.out("15 minutes have passed!");
   }
   private void min30() {
      con.out("30 minutes have passed!");
   }
   private void hour1() {
      con.out("1 hour has passed!");
   }

   @Override
   public String toString() {
      //DecimalFormat f is used to format numbers lower than 10 to display as 0x instead of x
      DecimalFormat f = new DecimalFormat("00");
      return String.format("%s:%s:%s", f.format(h), f.format(m), f.format(s));
   }
}
