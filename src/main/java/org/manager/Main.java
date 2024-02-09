package org.manager;

public class Main {

  private static Console con = new Console(Main.class.getName());
   
  public static void main(String[] args) {
    con.out("Starting NotionManager at " + Main.class.getName()); 
    
    if (args.length == 0) { 
      Daemon.daemon();
    } else {

      //switch statemet for runtime parameters     
      switch(args[0]) {
        case "--daemon":
          Daemon.daemon();
          break;
        default:
          con.out("Using default launch configuration");
      }
    }
   }
}
