package org.scraper;

import org.manager.Console;

import org.htmlunit.SilentCssErrorHandler;
import org.htmlunit.WebClient;
import org.htmlunit.html.DomElement;
import org.htmlunit.html.HtmlInput;
import org.htmlunit.html.HtmlPage;

import java.util.HashMap;
import java.util.Map;
import org.apache.commons.logging.LogFactory;
import java.util.logging.Logger;
import java.util.logging.Level;

public class Scraper {

  private static final Console con = new Console(Scraper.class.getName());
  private WebClient webC;

  public Scraper() {
    con.out("Instantiating new WebClient");

    this.webC = new WebClient();

    webC.setCssErrorHandler(new SilentCssErrorHandler());

    login(webC);
  }

  public void login(WebClient client) {
    con.out("Logging WebClient into Schoology");
    Map<String, String> creds = new HashMap();

    if (this.getClass().getName().equals("com.scraper.SchoologyScraper")) 
      creds = org.scraper.SchoologyScraper.getCreds();

    try {
        client.getOptions().setJavaScriptEnabled(false);
        HtmlPage currentPage = client.getPage("https://app.schoology.com/login");
        HtmlInput username = currentPage.getElementByName("mail");
        username.setValueAttribute(creds.get("account"));
        HtmlInput password = currentPage.getElementByName("pass");
        password.setValueAttribute(creds.get("password"));
        DomElement submitBtn = currentPage.getElementByName("op");
        submitBtn.click();
    } catch (Exception e) {
      con.err("Error logging WebClient into schoology");
    }
  }
}
