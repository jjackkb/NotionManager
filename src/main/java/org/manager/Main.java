package org.manager;


public class Main {

  private static Daemon dam = new Daemon();
  private static Console con = new Console(Main.class.getName());

  public static void main(String[] args) {
    dam.instantiate();
  }

  public static Daemon getDaemon() {
    return dam;
  }
}
