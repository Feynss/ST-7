package com.mycompany.app;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.io.FileWriter;
import java.io.PrintWriter;

public class Task3 {

    static final String URL =
        "https://api.open-meteo.com/v1/forecast" +
        "?latitude=56&longitude=44" +
        "&hourly=temperature_2m,rain" +
        "&current=cloud_cover" +
        "&timezone=Europe%2FMoscow" +
        "&forecast_days=1" +
        "&wind_speed_unit=ms";

    public static void execute(WebDriver browser) {
        try {
            browser.get(URL);
            String raw = browser.findElement(By.tagName("pre")).getText();
            JSONObject root = (JSONObject) new JSONParser().parse(raw);
            JSONObject hourly = (JSONObject) root.get("hourly");

            JSONArray timeList = (JSONArray) hourly.get("time");
            JSONArray tempList = (JSONArray) hourly.get("temperature_2m");
            JSONArray rainList = (JSONArray) hourly.get("rain");

            display(timeList, tempList, rainList);
            persist(timeList, tempList, rainList);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    static void display(JSONArray t, JSONArray tmp, JSONArray r) {
        System.out.printf("%-5s %-22s %-14s %-14s%n", "№", "Время", "t°C", "Осадки мм");
        System.out.println("=".repeat(58));
        for (int i = 0; i < t.size(); i++) {
            System.out.printf("%-5d %-22s %-14s %-14s%n",
                i + 1, t.get(i), asDouble(tmp.get(i)), asDouble(r.get(i)));
        }
    }

    static void persist(JSONArray t, JSONArray tmp, JSONArray r) {
        try (PrintWriter out = new PrintWriter(new FileWriter("result/forecast.txt"))) {
            out.printf("%-5s %-22s %-14s %-14s%n", "№", "Время", "t°C", "Осадки мм");
            out.println("=".repeat(58));
            for (int i = 0; i < t.size(); i++) {
                out.printf("%-5d %-22s %-14s %-14s%n",
                    i + 1, t.get(i), asDouble(tmp.get(i)), asDouble(r.get(i)));
            }
            System.out.println("Готово");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    static double asDouble(Object v) {
        return v instanceof Long ? ((Long) v).doubleValue() : (Double) v;
    }
}
