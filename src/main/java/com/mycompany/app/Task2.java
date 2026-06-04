package com.mycompany.app;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class Task2 {

    public static void execute(WebDriver browser) {
        try {
            browser.get("https://api.ipify.org/?format=json");
            String body = browser.findElement(By.tagName("pre")).getText();
            JSONObject data = (JSONObject) new JSONParser().parse(body);
            System.out.println(data.get("ip"));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
