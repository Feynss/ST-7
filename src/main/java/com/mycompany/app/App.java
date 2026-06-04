package com.mycompany.app;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class App {

    static WebDriver browser;

    public static void main(String[] args) {
        initDriver();
        fetchPassword();
        Task2.execute(browser);
        Task3.execute(browser);
        browser.quit();
    }

    static void initDriver() {
        System.setProperty("webdriver.chrome.driver", "/home/feynss/chromedriver/chromedriver");
        browser = new ChromeDriver();
    }

    static void fetchPassword() {
        try {
            browser.get("https://www.calculator.net/password-generator.html");
            WebDriverWait w = new WebDriverWait(browser, Duration.ofSeconds(15));
            String pwd = w.until(
                ExpectedConditions.presenceOfElementLocated(By.cssSelector("div.verybigtext b"))
            ).getText();
            System.out.println("Результат: " + pwd);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
