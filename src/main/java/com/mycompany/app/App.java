package com.mycompany.app;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;

public class App {

    public static void main(String[] args) {

        System.setProperty("webdriver.chrome.driver", "/home/feynss/chromedriver/chromedriver");

        WebDriver webDriver = new ChromeDriver();

        try {
            System.out.println("-Task №1-");
            webDriver.get("https://www.calculator.net/password-generator.html");

            WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
            WebElement passwordBlock = wait.until(
                ExpectedConditions.presenceOfElementLocated(
                    By.cssSelector("div.verybigtext b"))
            );
            System.out.println("Сгенерированный пароль: " + passwordBlock.getText());
            Task2.run(webDriver);
            Task3.run(webDriver);

        } catch (Exception e) {
            System.out.println("Error");
            System.out.println(e.toString());
        } finally {
            webDriver.quit();
        }
    }
}
