package org.manager;

import org.dataman.DataManager;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class Daemon {

  private final ScheduledExecutorService clockExecutor; //delay executor
  private final Runnable clockUpdate; //delay executor method runner
  private static final Console con = new Console(Daemon.class.getName());
  private static final Clock clock = new Clock();
  public DataManager dataMan;

   public Daemon() {
    con.out("NotionManager daemon started at " + clock);
    
    this.clockExecutor = Executors.newScheduledThreadPool(1);
    this.clockUpdate = clock::update;
    clockExecutor.scheduleAtFixedRate(clockUpdate, 0, 1, TimeUnit.SECONDS);
    con.setLevel(3);
   }


   public void instantiate() {
     this.dataMan = new DataManager();

      dataMan.calendarAssignmentUpdate();
   }

   public Clock getClock() {
     return clock;
   }
  public DataManager getDataMan() {
    return dataMan;
  }
}
