package com.codecool.testautomation.page;

import com.codecool.testautomation.utility.DriverSingleton;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public abstract class BasePage {

    public WebDriver driver;
    public WebDriverWait wait;

    public BasePage(){
        this.driver = DriverSingleton.getDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(4));
        PageFactory.initElements(driver, this);
        driver.manage().window().maximize();
    }
}