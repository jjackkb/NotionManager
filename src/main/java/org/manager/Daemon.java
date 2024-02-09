package org.manager;

import org.scraper.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class Daemon {

   private static final Clock clock = new Clock(); 
   private static final Console con = new Console(Daemon.class.getName());
   private static final ScheduledExecutorService clockExecutor = Executors.newScheduledThreadPool(1); //delay executor
   private static final Runnable clockUpdate = clock::update; //delay executor method runner

   public static void daemon() {
      con.out("NotionManager daemon started at " + clock);
      clockExecutor.scheduleAtFixedRate(clockUpdate, 0, 1, TimeUnit.SECONDS);
      con.setLevel(3);

      instantiate();
   }

   protected static void instantiate() {
      SchoologyScraper sc = new SchoologyScraper();
   }
}
