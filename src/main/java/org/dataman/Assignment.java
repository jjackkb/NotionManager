package org.dataman;

import org.manager.Console;

import java.util.ArrayList;

import static org.manager.Main.notion;

public class Assignment {

  private static final ArrayList<Assignment> assignments = new ArrayList<>();
  private static final Console con = new Console(Assignment.class.getName());
  private String name;
  private String disc;
  private String course;
  private String status;
  private String dueDate;
  private String url = "https://app.schoology.com/assignment/";
  private String assignId;

  public Assignment(String name, String disc, String course, String dueDate, String assignId) {
    con.out(String.format("Instantiating Assignment object for %s", assignId));
    
    this.assignId = assignId;
    this.name = name;
    this.disc = disc;
    this.course = course;
    this.status = "Done";
    this.dueDate = dueDate;
    this.url += assignId;
    
    assignments.add(this);

    if (!notion.hasID(assignId))
      notion.createAssignment(name, dateConverter(dueDate), status, course, assignId);
  }
  
  public String getName() {
    return name;
  }
  public String getDisc() {
    return disc;
  }
  public String getCourse() {
    return course;
  }
  public String getDueDate() {
    return dueDate;
  }
  public String getAssignId() {
    return assignId;
  }

  private String dateConverter(String dueDate) {
    String month = "";

    if (dueDate.contains("January"))
      month = "01";
    if (dueDate.contains("February"))
      month = "02";
    if (dueDate.contains("March"))
      month = "03";
    if (dueDate.contains("April"))
      month = "04";
    if (dueDate.contains("May"))
      month = "05";
    if (dueDate.contains("June"))
      month = "06";
    if (dueDate.contains("July"))
      month = "07";
    if (dueDate.contains("August"))
      month = "08";
    if (dueDate.contains("September"))
      month = "09";
    if (dueDate.contains("October"))
      month = "10";
    if (dueDate.contains("November"))
      month = "11";
    if (dueDate.contains("December"))
      month = "12";

    String s = "";
    String day = "";
    String year = "";

    for (int x = 0; x < dueDate.length()-5; x++) {
      s = dueDate.substring(x, x + 4);
      if ((s.charAt(0) == ' ') && Character.isDigit(s.charAt(1)) && Character.isDigit(s.charAt(2)) && s.charAt(3) == ',') {
        day = s.substring(1, 3);
      } else if (Character.isDigit(s.charAt(2)) && s.charAt(3) == ',') {
        day = "0"+s.charAt(2);
      }

      if (Character.isDigit(s.charAt(0)) && Character.isDigit(s.charAt(1)) && Character.isDigit(s.charAt(2)) && Character.isDigit(s.charAt(3))) {
        year = s;
      }
    }

    if (month == "02" || month == "03" && year == "2024")
      this.status = "To-Do";

    if (year.isEmpty())
      year = "2000";
    if (month.isEmpty())
      month = "01";
    if (day.isEmpty())
      day = "01";

    return String.format("%s-%s-%s", year, month, day);
  }
  public String toString() {
    return String.format("Assignment %s {\n\tname: %s\n\tdiscription: %s\n\tcourse: %s\n\tdueDate: %s\n\turl: %s\n}\n", assignId, name, disc, course, dueDate, url);
  }
}
