package org.dataman;

import org.manager.Console;

import java.util.ArrayList;

public class Assignment {

  private static final ArrayList<Assignment> assignments = new ArrayList<>();
  private static final Console con = new Console(Assignment.class.getName());
  private String name;
  private String disc;
  private String course;
  private String dueDate;
  private String url = "https://app.schoology.com/assignment/";
  private String assignId;

  public Assignment(String name, String disc, String course, String dueDate, String assignId) {
    con.out(String.format("Instantiating Assignment object for %s", assignId));
    
    this.assignId = assignId;
    this.name = name;
    this.disc = disc;
    this.course = course;
    this.dueDate = dueDate;
    this.url += assignId;
    
    assignments.add(this);
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

  public String toString() {
    return String.format("Assignment %s {\n\tname: %s\n\tdiscription: %s\n\tcourse: %s\n\tdueDate: %s\n\turl: %s\n}\n", assignId, name, disc, course, dueDate, url);
  }
}
