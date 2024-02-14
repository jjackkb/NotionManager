package org.dataman;

import org.manager.Console;
import org.scraper.SchoologyScraper;

import org.json.simple.JSONObject;

import org.htmlunit.html.HtmlPage;

import java.util.ArrayList;

public class DataManager {

  private static final LocalDatabase db = new LocalDatabase("/src/main/resources/localDatabaseAssignments.json");
  private static final Console con = new Console(SchoologyData.class.getName());
  private SchoologyScraper sc;

  public DataManager() {
    loadDBAssignments();
    this.sc = new SchoologyScraper();
  }

  public void calendarAssignmentUpdate() {
    int intAssignments = 0;
    ArrayList<String> cal = sc.getCalendar();

    con.out("Parsing calendar assignment list");
    
    for (String s : cal) {
      if (s.contains("URL;VALUE=URI:")) {
        String url = s.replace("URL;VALUE=URI:", "");
        String assignId = s.replaceAll("\\D+", "");

        if (compareAssignId(assignId)) {
          if (url.contains("assignment")) {
            addAssignment(assignId); 
            intAssignments++;   
          }
        } else {
          continue;
        }
      }
    }
    con.out(String.format("Parsing complete! Instantiated Assignments: %s", intAssignments));
  }
  

  public void addAssignment(String assignId) {
    HtmlPage page = sc.getHtmlPage("https://app.schoology.com/assignment/"+assignId); 

    while (page == null) {
      con.err("Page empty, retrying");
      page = sc.getHtmlPage("https://app.schoology.com/assignment/"+assignId);
    }

    String name = sc.getName(page);
    String disc = sc.getDisc(page);
    String course = sc.getCourse(page);
    String dueDate = sc.getDueDate(page);
  
    db.writeAssignment(new Assignment(name, disc, course, dueDate, assignId));
  }

  public void loadDBAssignments() {
    con.out("Loading assignments from local database");

    for (Object o : db.getJSONArray()) {
      JSONObject obj = (JSONObject) o;
      
      String name = (String) obj.get("name");
      String disc = (String) obj.get("discription");
      String course = (String) obj.get("course");
      String dueDate = (String) obj.get("dueDate");
      String assignId = (String) obj.get("assignId");

      new Assignment(name, disc, course, dueDate, assignId);
    }
  }

  public boolean compareAssignId(String id) {
    for (String s : db.getAssignIds()) {
      if (s.equals(id))
        return false;
    }
    return true;
  }
}

