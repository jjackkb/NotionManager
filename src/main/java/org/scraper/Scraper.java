package org.scraper;

import org.manager.Console;

import org.htmlunit.SilentCssErrorHandler;
import org.htmlunit.WebClient;
import org.htmlunit.Page;

public class Scraper {

  private static final Console con = new Console(Scraper.class.getName());
  private WebClient webC;

  public Scraper() {
    con.out("Instantiating new WebClient");

    this.webC = new WebClient();
    webC.setCssErrorHandler(new SilentCssErrorHandler()); //mute WebClient complaining
    webC.getOptions().setJavaScriptEnabled(false);

    //run subclass login method
    if (this.getClass().getName().equals("org.scraper.SchoologyScraper"))
      org.scraper.SchoologyScraper.login(webC);
  }

  //fetch page as Page given url
  protected Page getPage(String url) {
    try {
    return (Page) webC.getPage(url);
    } catch (Exception e) {
      con.err("Eror while fetching page");
      return null;
    } 
  }
}
