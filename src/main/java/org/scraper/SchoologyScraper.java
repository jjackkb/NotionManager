package org.scraper;

import org.manager.Console;

import org.htmlunit.WebClient;
import org.htmlunit.html.DomElement;
import org.htmlunit.html.DomNode;
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

  public String getName(HtmlPage page) {
    DomNode name = page.querySelector("#center-top > h2");

    return (name == null) ? "null" : name.asNormalizedText();
  }

  public String getDisc(HtmlPage page) {
    DomNode disc = page.querySelector("#main-inner > div.info-container > div > div > p");

    return (disc == null) ? "null" : disc.asNormalizedText();
  }

  //get course name
  public String getCourse(HtmlPage page) {
    DomNode course = page.querySelector("#center-top > div.content-top-upper > div > span > a");

    return (course == null) ? "" : switch(course.asNormalizedText()) {
      case "American Sign Language 3, 4: 2(A)" -> "ASL";
      case "AP Computer Science A: 7(A)" -> "APCSA";
      case "Chemistry 1,2: 1(A)" -> "Chemistry";
      case "Church History 1,2: 6(A)" -> "Church History";
      case "English 3,4: 3(A)" -> "English";
      case "Geometry 1,2: 5(A)" -> "Geometry";
      case "Music Appreciation: 4(A)" -> "Music Appreciation";
      case "Physical Education-10thGrade: 12(A)" -> "PE";
      default -> "Null";
    };
  }

  public String getDueDate(HtmlPage page) {
    DomNode dueDate = page.querySelector("#main-inner > div.assignment-details > p");

    return (dueDate == null) ? "null" : dueDate.asNormalizedText();
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
