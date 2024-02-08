package org.manager;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/* The Daemon class runs background activities to maintain an accurate assignment listing.
Daemon.main utilizes org.manager.Clock to track elapsed time and periodically execute methods critical to program maintenance. */
public class Daemon {

   private static final Clock clock = new Clock(); //Clock object manages program-relative time
   private static final ScheduledExecutorService clockExecutor = Executors.newScheduledThreadPool(1); //delay executor
   private static final Runnable clockUpdate = clock::update; //delay executor method runner

   public static void main(String[] args) {
      clockExecutor.scheduleAtFixedRate(clockUpdate, 0, 1, TimeUnit.SECONDS); //call delay executor with delay period 1 second
   }
}