package org.scraper;

import org.manager.Console;

import org.htmlunit.WebClient;

import java.util.Scanner;
import java.util.Map;
import java.util.HashMap;
import java.io.File;

public class SchoologyScraper extends Scraper {

  private static final Console con = new Console(SchoologyScraper.class.getName());
  
  public SchoologyScraper() {
    super();
  }

  public static Map<String, String> getCreds() {
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
