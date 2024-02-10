package org.dataman;

import org.manager.Console;
import org.scraper.SchoologyScraper;

import org.htmlunit.html.HtmlPage;

import java.util.ArrayList;

public class SchoologyData {

  private static final Console con = new Console(SchoologyData.class.getName());
  private SchoologyScraper sc;

  public SchoologyData() {
    this.sc = new SchoologyScraper();
  }

  public void calendarAssignmentUpdate() {
    ArrayList<String> cal = sc.getCalendar();

    con.out("Parsing calendar assignment list");
    
    for (String s : cal) {
      if (s.contains("URL;VALUE=URI:")) {
        String slink = s.replace("URL;VALUE=URI:", "");

        if (slink.contains("assignment"))
          addAssignment(slink);
      }
    }
    con.out("Parsing calendar assignment list complete");
  }

  public void addAssignment(String slink) {
    HtmlPage page = sc.getHtmlPage(slink);

    String name = sc.getName(page);
    String disc = sc.getDisc(page);
    String course = sc.getCourse(page);
    String dueDate = sc.getDueDate(page);
  
    new Assignment(name, disc, course, dueDate, slink);
  }
}
