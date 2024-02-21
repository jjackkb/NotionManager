package org.scraper;

import org.manager.Clock;
import org.manager.Main;
import org.manager.Console;

import org.htmlunit.SilentCssErrorHandler;
import org.htmlunit.WebClient;
import org.htmlunit.html.HtmlPage;
import org.htmlunit.Page;

public class Scraper {

  private static final Console con = new Console(Scraper.class.getName());
  private WebClient webC;
  private long startTime;
  private static Clock clock = Main.getDaemon().getClock();

  public Scraper() {
    this.startTime = clock.getRefTime();
    this.webC = new WebClient();
    webC.setCssErrorHandler(new SilentCssErrorHandler()); //mute WebClient complaining
    webC.getOptions().setJavaScriptEnabled(false);

    //run subclass login method
    if (this.getClass().getName().equals("org.scraper.SchoologyScraper"))
      org.scraper.SchoologyScraper.login(webC);
  }

  //fetch page as Page given url
  public Page getPage(String url) {
    checkRefTime();
    try {
    return (Page) webC.getPage(url);
    } catch (Exception e) {
      con.err(e.toString());
      return null;
    } 
  }

  //fetch page as HtmlPage given url
  public HtmlPage getHtmlPage(String url) {
    checkRefTime();
    try {
      return (HtmlPage) webC.getPage(url);
    } catch (Exception e) {
      con.err(e.toString());
      return null;
    }
  }

  private void checkRefTime() {
    if ((startTime + 600) <= clock.getRefTime()) {
      webC = new WebClient();
      webC.setCssErrorHandler(new SilentCssErrorHandler()); //mute WebClient complaining
      webC.getOptions().setJavaScriptEnabled(false);
      if (this.getClass().getName().equals("org.scraper.SchoologyScraper"))
        org.scraper.SchoologyScraper.login(webC);
    }
    return;
  }
}
