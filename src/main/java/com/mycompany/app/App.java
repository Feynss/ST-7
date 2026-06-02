package com.mycompany.app;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class App {

    public static void main(String[] args) {
        System.setProperty("webdriver.chrome.driver", "/home/feynss/chromedriver/chromedriver");
        WebDriver driver = new ChromeDriver();

        try {
            runTask1(driver);
            Task2.run(driver);
            Task3.run(driver);
        } finally {
            driver.quit();
        }
    }

    private static void runTask1(WebDriver driver) {
        System.out.println("Задание №1");
        try {
            driver.get("https://www.calculator.net/password-generator.html");
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement el = wait.until(
                ExpectedConditions.presenceOfElementLocated(By.cssSelector("div.verybigtext b"))
            );
            System.out.println("Пароль: " + el.getText());
        } catch (Exception ex) {
            System.err.println("Ошибка в Task1: " + ex.getMessage());
        }
    }
}
