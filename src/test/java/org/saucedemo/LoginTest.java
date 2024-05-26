package org.saucedemo;

import org.example.Pages;
import org.example.User;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.time.Duration;

public class LoginTest {
    WebDriver driver;
    WebElement usernameField;
    WebElement passwordField;
    WebElement loginBtn;

    @BeforeMethod
    public void beforeEach(){
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        driver.get(Pages.BASE_URL);
        usernameField = driver.findElement(By.id("user-name"));
        passwordField = driver.findElement(By.id("password"));
        loginBtn = driver.findElement(By.id("login-button"));
    }

    @Test
    public void validLoginCredentials(){
        usernameField.sendKeys(User.VALID_USERNAME);
        passwordField.sendKeys(User.VALID_PASSWORD);
        loginBtn.click();
        Assert.assertEquals(driver.getCurrentUrl(), Pages.PRODUCTS_URL);
    }

    @Test
    public void invalidLoginCredentials(){
        usernameField.sendKeys(User.INVALID_USERNAME);
        passwordField.sendKeys(User.INVALID_PASSWORD);
        loginBtn.click();
        WebElement errorContainer = driver.findElement(By.cssSelector(".error-message-container"));
        Assert.assertFalse(errorContainer.getText().isEmpty());
        Assert.assertEquals(errorContainer.getText(), "Epic sadface: Username and password do not match any user in this service");
    }

    @Test
    public void withoutLoginCredentials(){
        loginBtn.click();
        WebElement errorContainer = driver.findElement(By.cssSelector(".error-message-container"));
        Assert.assertFalse(errorContainer.getText().isEmpty());
        Assert.assertEquals(errorContainer.getText(), "Epic sadface: Username is required");
    }

    @Test
    public void noUsernameLoginCredentials(){
        passwordField.sendKeys(User.VALID_PASSWORD);
        loginBtn.click();
        WebElement errorContainer = driver.findElement(By.cssSelector(".error-message-container"));
        Assert.assertFalse(errorContainer.getText().isEmpty());
        Assert.assertEquals(errorContainer.getText(), "Epic sadface: Username is required");
    }

    @Test
    public void noPasswordLoginCredentials(){
        usernameField.sendKeys(User.VALID_USERNAME);
        loginBtn.click();
        WebElement errorContainer = driver.findElement(By.cssSelector(".error-message-container"));
        Assert.assertFalse(errorContainer.getText().isEmpty());
        Assert.assertEquals(errorContainer.getText(), "Epic sadface: Password is required");
    }

    @AfterMethod
    public void afterEach(){
        driver.quit();
    }
}
