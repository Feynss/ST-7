package com.mycompany.app;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class Task2 {

    public static void run(WebDriver driver) {
        System.out.println("\nЗадание №2");
        try {
            driver.get("https://api.ipify.org/?format=json");
            String rawJson = driver.findElement(By.tagName("pre")).getText();
            JSONObject parsed = (JSONObject) new JSONParser().parse(rawJson);
            System.out.println("Внешний IP-адрес: " + parsed.get("ip"));
        } catch (Exception ex) {
            System.err.println("Ошибка в Task2: " + ex.getMessage());
        }
    }
}
