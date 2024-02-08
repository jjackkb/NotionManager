package org.manager;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/* The Daemon class runs background activities to maintain an accurate assignment listing.
Daemon.main utilizes org.manager.Clock to track elapsed time and periodically execute methods critical to program maintenance. */
public class Daemon {

   private static final Clock clock = new Clock(); //Clock object manages program-relative time
   private static final Console con = new Console(Daemon.class.getName()); //Console object manages Daemon specific outputs and errors
   private static final ScheduledExecutorService clockExecutor = Executors.newScheduledThreadPool(1); //delay executor
   private static final Runnable clockUpdate = clock::update; //delay executor method runner

   public static void daemon() {
      con.out("NotionManager daemon started at " + clock);
      clockExecutor.scheduleAtFixedRate(clockUpdate, 0, 1, TimeUnit.SECONDS); //call delay executor with delay period 1 second
      con.setLevel(3);
   }
}
