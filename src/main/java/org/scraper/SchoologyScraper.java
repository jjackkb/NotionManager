package org.scraper;

import org.manager.Console;

import org.htmlunit.WebClient;

public class SchoologyScraper extends Scraper {

  private final Console con = new Console(SchoologyScraper.class.getName());
  
  public SchoologyScraper() {
    super("schoology");
    con.out("Instantiating SchoologyScraper object");
  }
}
