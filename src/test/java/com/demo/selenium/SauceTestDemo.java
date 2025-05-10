package com.demo.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.time.Duration;

public class SauceTestDemo {

    @Test
    public void validLoginTest() {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\krish\\IdeaProjects\\ecommerce-automation\\chromedriver.exe");

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        options.setHeadless(false);

        WebDriver driver = new ChromeDriver(options);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        try {
            // Navigate to the login page
            driver.get("https://www.saucedemo.com");

            // Enter valid credentials
            driver.findElement(By.id("user-name")).sendKeys("standard_user");
            driver.findElement(By.id("password")).sendKeys("secret_sauce");
            driver.findElement(By.id("login-button")).click();

            // Wait for the inventory page to load
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("inventory_item")));

            // Verify successful login by checking the URL
            String currentUrl = driver.getCurrentUrl();
            Assert.assertTrue(currentUrl.contains("inventory.html"), "Login failed: Inventory page not displayed.");
        }
        finally {
            driver.quit();
        }
    }

    @Test
    public void invalidLoginTest() {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\krish\\IdeaProjects\\ecommerce-automation\\chromedriver.exe");

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        options.setHeadless(false);

        WebDriver driver = new ChromeDriver(options);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        try {
            // Navigate to the login page
            driver.get("https://www.saucedemo.com");

            // Enter invalid credentials
            driver.findElement(By.id("user-name")).sendKeys("invalid_user");
            driver.findElement(By.id("password")).sendKeys("wrong_password");
            driver.findElement(By.id("login-button")).click();

            // Wait for the error message to be visible
            WebElement errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("h3[data-test='error']")));

            // Verify that the appropriate error message is displayed
            String expectedError = "Epic sadface: Username and password do not match any user in this service";
            Assert.assertEquals(errorMessage.getText(), expectedError, "Error message does not match expected.");
        }
        finally {
            driver.quit();
        }
    }

    @Test
    public void validateProductNames() {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\krish\\IdeaProjects\\ecommerce-automation\\chromedriver.exe");

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        options.setHeadless(false);

        WebDriver driver = new ChromeDriver(options);

        // Login steps
        driver.get("https://www.saucedemo.com");
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();

        // Validate product name
        String firstProduct = driver.findElement(By.className("inventory_item_name")).getText();
        Assert.assertEquals(firstProduct, "Sauce Labs Backpack");

        driver.quit();
    }

    @Test
    public void addToCartAndVerify() {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\krish\\IdeaProjects\\ecommerce-automation\\chromedriver.exe");

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        options.setHeadless(false);

        WebDriver driver = new ChromeDriver(options);

        // Login steps
        driver.get("https://www.saucedemo.com");
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();

        // Explicit wait for the page to load after login
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Wait until the inventory item is visible
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("inventory_item_name")));

        // Add product to the cart
        WebElement addToCartButton = driver.findElement(By.xpath("//button[contains(@class, 'btn_inventory')]"));
        addToCartButton.click();

        // Wait until the cart icon updates
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("shopping_cart_badge")));

        // Verify the cart count has increased
        String cartCount = driver.findElement(By.className("shopping_cart_badge")).getText();
        Assert.assertEquals(cartCount, "1");

        driver.quit();
    }
}