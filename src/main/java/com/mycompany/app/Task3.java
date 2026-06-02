package com.mycompany.app;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Task3 {

    private static final String API_URL =
            "https://api.open-meteo.com/v1/forecast" +
            "?latitude=56&longitude=44" +
            "&hourly=temperature_2m,rain" +
            "&current=cloud_cover" +
            "&timezone=Europe%2FMoscow" +
            "&forecast_days=1" +
            "&wind_speed_unit=ms";

    public static void run(WebDriver driver) {
        System.out.println("\nЗадание №3");
        try {
            driver.get(API_URL);
            String rawJson = driver.findElement(By.tagName("pre")).getText();

            JSONObject root = (JSONObject) new JSONParser().parse(rawJson);
            JSONObject hourly = (JSONObject) root.get("hourly");

            JSONArray times = (JSONArray) hourly.get("time");
            JSONArray temps = (JSONArray) hourly.get("temperature_2m");
            JSONArray rains = (JSONArray) hourly.get("rain");

            printTable(times, temps, rains);
            saveToFile(times, temps, rains);

        } catch (Exception ex) {
            System.err.println("Ошибка в Task3: " + ex.getMessage());
        }
    }

    private static void printTable(JSONArray times, JSONArray temps, JSONArray rains) {
        String line = String.format("%-4s | %-20s | %-12s | %-12s", "№", "Дата/время", "Темп.(°C)", "Осадки(мм)");
        System.out.println(line);
        System.out.println("-".repeat(line.length()));
        for (int i = 0; i < times.size(); i++) {
            System.out.println(formatRow(i, times, temps, rains));
        }
    }

    private static void saveToFile(JSONArray times, JSONArray temps, JSONArray rains) {
        try (PrintWriter pw = new PrintWriter(new FileWriter("result/forecast.txt"))) {
            pw.printf("%-4s | %-20s | %-12s | %-12s%n", "№", "Дата/время", "Темп.(°C)", "Осадки(мм)");
            pw.println("-".repeat(56));
            for (int i = 0; i < times.size(); i++) {
                pw.println(formatRow(i, times, temps, rains));
            }
            System.out.println("Файл сохранён: result/forecast.txt");
        } catch (IOException ex) {
            System.err.println("Не удалось сохранить файл: " + ex.getMessage());
        }
    }

    private static String formatRow(int i, JSONArray times, JSONArray temps, JSONArray rains) {
        double temp = toDouble(temps.get(i));
        double rain = toDouble(rains.get(i));
        return String.format("%-4d | %-20s | %-12s | %-12s",
                i + 1, times.get(i), temp + " °C", rain + " мм");
    }

    private static double toDouble(Object val) {
        if (val instanceof Long) return ((Long) val).doubleValue();
        return (Double) val;
    }
}
