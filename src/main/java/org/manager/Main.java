package org.manager;

import org.scraper.NotionAPI;

public class Main {

  public static NotionAPI notion = new NotionAPI();
  private static Daemon dam = new Daemon();
  private static Console con = new Console(Main.class.getName());

  public static void main(String[] args) {
    dam.instantiate();
  }

  public static Daemon getDaemon() {
    return dam;
  }
}
