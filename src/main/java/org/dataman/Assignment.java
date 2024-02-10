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
  private String url;
  private String assignId;
  private int num;

  public Assignment(String name, String disc, String course, String dueDate, String url) {
    this.assignId = url.replaceAll("\\D+", "");

    con.out(String.format("Instantiating Assignment object for %s", assignId));
    
    this.name = name;
    this.disc = disc;
    this.course = course;
    this.dueDate = dueDate;
    this.url = url;
    this.num = assignments.size();
    
    assignments.add(this);
    }

  public String toString() {
    return String.format("Assignment %s : %s {\n\tname: %s\n\tdiscription: %s\n\tcourse: %s\n\tdueDate: %s\n\turl: %s\n}\n", num, assignId, name, disc, course, dueDate, url);
  }
}
