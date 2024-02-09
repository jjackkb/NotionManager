package org.scraper;

import org.manager.Console;

import org.htmlunit.WebClient;

public class Scraper {

  private static final Console con = new Console(Scraper.class.getName());
  private WebClient webC;
  private final String site;

  public Scraper(String site) {
    this.webC =  new WebClient();
    this.site = site;
    
    login(webC);
  }

  public void login(WebClient client) {
    con.out(client.toString());
  }
}
