package com.mycompany.app;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.FileWriter;
import java.io.IOException;

public class Task3 {

    public static void run(WebDriver webDriver) {
        try {
            String url = "https://api.open-meteo.com/v1/forecast"
                    + "?latitude=56&longitude=44"
                    + "&hourly=temperature_2m,rain"
                    + "&current=cloud_cover"
                    + "&timezone=Europe%2FMoscow"
                    + "&forecast_days=1"
                    + "&wind_speed_unit=ms";

            webDriver.get(url);
            WebElement elem = webDriver.findElement(By.tagName("pre"));
            String jsonStr = elem.getText();

            JSONParser parser = new JSONParser();
            JSONObject obj = (JSONObject) parser.parse(jsonStr);

            JSONObject hourly = (JSONObject) obj.get("hourly");
            JSONArray times = (JSONArray) hourly.get("time");
            JSONArray temps = (JSONArray) hourly.get("temperature_2m");
            JSONArray rains = (JSONArray) hourly.get("rain");

            StringBuilder sb = new StringBuilder();
            String header = String.format("%-5s %-22s %-15s %-15s%n",
                    "№", "Дата/время", "Температура", "Осадки (мм)");
            String separator = "-".repeat(60) + "\n";

            sb.append(header);
            sb.append(separator);

            System.out.println("-Task №3-");
            System.out.print(header);
            System.out.print(separator);

            for (int i = 0; i < times.size(); i++) {
                String time = (String) times.get(i);
                Object tempObj = temps.get(i);
                Object rainObj = rains.get(i);

                double temp = tempObj instanceof Long
                        ? ((Long) tempObj).doubleValue()
                        : (Double) tempObj;
                double rain = rainObj instanceof Long
                        ? ((Long) rainObj).doubleValue()
                        : (Double) rainObj;

                String row = String.format("%-5d %-22s %-15s %-15s%n",
                        (i + 1), time,
                        temp + " °C",
                        rain + " мм");

                sb.append(row);
                System.out.print(row);
            }

            try (FileWriter fw = new FileWriter("../result/forecast.txt")) {
                fw.write(sb.toString());
                System.out.println("\nТаблица сохранена в result/forecast.txt");
            } catch (IOException e) {
                System.out.println("Ошибка записи файла: " + e.toString());
            }

        } catch (Exception e) {
            System.out.println("Task3 Error: " + e.toString());
        }
    }
}
