package com.codecool.testautomation.utility;

import com.codecool.testautomation.page.LoginPage;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.time.Instant;


public class Utility {
    public static void logout(WebDriver driver){
        driver.findElement(By.cssSelector(".aui-avatar-small img")).click();
        driver.findElement(By.cssSelector("#log_out")).click();
    }

    public static void clickButton(WebElement webElement){webElement.click();
    }

    public static void waitForWebElementToBePresent(WebElement webElement){
        new WebDriverWait(DriverSingleton.getDriver(), Duration.ofSeconds(4)).until(ExpectedConditions.visibilityOf(webElement));
    }

    public static void waitForElementToSendText(WebElement webElement, String text){
        new WebDriverWait(DriverSingleton.getDriver(), Duration.ofSeconds(4)).until(ExpectedConditions.elementToBeClickable(webElement)).sendKeys(text);

    }
    public static void waitForElementToClick(WebElement webElement){
        new WebDriverWait(DriverSingleton.getDriver(), Duration.ofSeconds(4)).until(ExpectedConditions.elementToBeClickable(webElement)).click();
    }
}
