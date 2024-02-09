package org.scraper;

import org.manager.Console;

import org.htmlunit.WebClient;
import org.htmlunit.html.DomElement;
import org.htmlunit.html.HtmlInput;
import org.htmlunit.html.HtmlPage;
import org.htmlunit.Page;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Map;
import java.util.HashMap;
import java.io.File;

public class SchoologyScraper extends Scraper {

  private static final Console con = new Console(SchoologyScraper.class.getName());
  
  public SchoologyScraper() {
    super();
  }

  //fetch calendar assignments as ArrayList from Schoology
  public ArrayList<String> getCalendar() {
    con.out("Fetching assignment list from Schoology");
  
    try {
      Page p = getPage("https://app.schoology.com/calendar/feed/export/user/118149844/download");
      String cal = p.getWebResponse().getContentAsString();

      ArrayList<String> a = new ArrayList<>(List.of(cal.split("\n")));
      return a;
    } catch (Exception e) {
      con.err("Error while fetching assignment list from Schoology!");
      return null;
    }
  }

  //Login Methods
  //log WebClient into Schoology site
  protected static void login(WebClient client) {
    con.out("Logging WebClient into Schoology");

    try {
      Map<String, String> creds = getCreds();

      HtmlPage curPage = client.getPage("https://app.schoology.com/login");
      HtmlInput account = curPage.getElementByName("mail");
      account.setValueAttribute(creds.get("account"));
      HtmlInput password = curPage.getElementByName("pass");
      password.setValueAttribute(creds.get("password"));
      DomElement submitBtn = curPage.getElementByName("op");
      submitBtn.click();
    } catch (Exception e) {
      con.err("Error while logging WebClient into Schoology!");
    }
  }

  //retreive Schoology login credentials from local secret file
  protected static Map<String, String> getCreds() {
    con.out("Retreiving credentials from local secret");
    Map<String, String> creds = new HashMap<>();
    String acc = null;
    String pass = null;

    try {
      File f = new File(System.getProperty("user.dir") + "/src/main/resources/secret.txt");
      Scanner sc = new Scanner(f);

      while (sc.hasNextLine()) {
        String s = sc.nextLine();
        
        if (s.contains("SCHOOLOGY_ACCOUNT:"))
          acc = s.replace("SCHOOLOGY_ACCOUNT:", "");
        if (s.contains("SCHOOLOGY_PASSWORD:"))
          pass = s.replace("SCHOOLOGY_PASSWORD:", "");
      }
      sc.close();

      creds.put("account", acc);
      creds.put("password", pass);
    
      if (acc == null || pass == null) {
        con.err("Error while retreiving credentials!");
        return null;
      }
      return creds;
    }
    catch (Exception e) {
      con.err("Error while retreiving credentials!");
      return null;    
    }
  }
}
