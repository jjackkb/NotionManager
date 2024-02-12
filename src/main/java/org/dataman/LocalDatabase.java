package org.dataman;

import org.manager.Console;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.util.ArrayList;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class LocalDatabase {

  private static Console con = new Console(LocalDatabase.class.getName());
  private String path = System.getProperty("user.dir");
  private JSONObject jsonObj;

  public LocalDatabase(String path) {
    con.out("Instantiating LocalDatabase");  
    this.path += path;
  }

  //write Assignment object to JSONObject in database file
  public void writeAssignment(Assignment a) {
    JSONObject obj = new JSONObject();

    obj.put("name", a.getName());
    obj.put("discription", a.getDisc());
    obj.put("course", a.getCourse());
    obj.put("dueDate", a.getDueDate());
    obj.put("assignId", a.getAssignId());

    try {
      FileWriter f = new FileWriter(path, true);
      f.append(obj.toString() + "\n");
      f.close();
    } catch(Exception e) {
      con.err(e.toString());
    }
  }

  //get string ArrayList with each objects assignmentId
  public ArrayList<String> getAssignIds() {
    ArrayList<String> a = new ArrayList<>();

    for (Object o : getJSONArray()) {
      JSONObject obj = (JSONObject) o;
      a.add((String) obj.get("assignId"));
    }
    return a;
  }

  //get ArrayList of all JSONObjects in database file
  public ArrayList getJSONArray() {
    ArrayList jsonObjArray = new ArrayList();
    try {
      JSONParser parser = new JSONParser();
      FileReader f = new FileReader(path);
      BufferedReader br = new BufferedReader(f);

      String curJSON = "";
      
      if (br.readLine() == null) {
        return jsonObjArray;
      }

      while ((curJSON = br.readLine()) != null) {
        JSONObject curJsonObj = (JSONObject) parser.parse(curJSON);
        jsonObjArray.add(curJsonObj);
      }

      return jsonObjArray;
    } catch (Exception e) {
      con.err(e.toString()); 
      return jsonObjArray;
    }
  }
}
