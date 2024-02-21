package org.scraper;

import org.manager.Console;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class NotionAPI {

  private String token = "secret_lDMcBAmUBxa9lwMpTjxIp1l5j5Rca73S7GN8ukJCNPo";

  Console con = new Console(NotionAPI.class.getName());
  public NotionAPI() {
  }

  public boolean hasID(String id) {
    Runtime rt = Runtime.getRuntime();
    Process p;

    String[] command = {"curl", "-X", "POST", "https://api.notion.com/v1/databases/b900eb14d3814736b1979114f4a9c0eb/query", "-H", "Authorization: Bearer " + token, 
      "-H", "Content-Type: application/json",
      "-H", "Notion-Version: 2022-06-28",
      "--data", "{\"filter\":{\"property\":\"ID\",\"number\":{\"equals\":" + id + "}}}"};
    String response = "";

    try{
    ProcessBuilder ps = new ProcessBuilder(command);
    Process pr = ps.start();
    pr.waitFor();

    BufferedReader reader = new BufferedReader(new InputStreamReader(pr.getInputStream()));
    
    String line = "";

    while ((line = reader.readLine()) != null) {
      response += line;
      response += "\n";
    }
    
    if (response.contains("{\"object\":\"list\",\"results\":[],\"next_cursor\":null,\"has_more\":false,\"type\":\"page_or_database\",\"page_or_database\":{},\"request_id\":"))
      return false;
    else
      return true;
    } catch(Exception e) {
      con.err(e.toString());
      return false;
    }
  }

  public void createAssignment(String name, String date, String status, String course, String id) {
    Runtime rt = Runtime.getRuntime();
    Process p;

    String response = "";
    String command[]= {
      "curl", "-X", "POST", "https://api.notion.com/v1/pages", "-H", "Authorization: Bearer " + token, 
      "-H", "Content-Type: application/json",
      "-H", "Notion-Version: 2022-06-28",
      "--data", "{\"parent\": { \"type\": \"database_id\", \"database_id\": \"b900eb14d3814736b1979114f4a9c0eb\" },"+
        " \"properties\": { \"Name\": { \"type\": \"title\", \"title\": [{\"type\": \"text\", \"text\": { \"content\": \""+name+"\" }}]},"+
        " \"Due Date\": { \"type\": \"date\", \"date\": { \"start\": \""+date+"\" }}, "+
        " \"Status\": {\"status\": { \"name\": \""+status+"\"}},"+
        "\"Course\": {\"select\": {\"name\": \""+course+"\"}},"+
        "\"ID\": {\"number\": "+id+"}}}"
      };

    try{
      ProcessBuilder ps = new ProcessBuilder(command);
      Process pr = ps.start();
      pr.waitFor();
    } catch (Exception e) {
      con.err(e.toString());
    }
  }
}
