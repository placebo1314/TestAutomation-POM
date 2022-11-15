package com.codecool.testautomation.captcha;

import com.codecool.testautomation.page.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import static com.codecool.testautomation.test.CreatePageTest.loginPage;
import static com.codecool.testautomation.utility.Utility.waitForWebElementToBePresent;

public class CaptchaPage extends BasePage {

    By captcha = By.xpath("//div[@id='captcha']/div/img");
    @FindBy(id = "login-form-submit")
    public static WebElement logInButton;
    @FindBy(id = "login-form-username")
    public static WebElement usernameField;
    @FindBy(id = "login-form-password")
    public static WebElement passwordField;
    @FindBy(id = "captcha")
    public static WebElement captchaPicture;

    public CaptchaPage() {
    }

    public void OpenLoginPage() {
        driver.get("https://jira-auto.codecool.metastage.net/login");
    }

    public void TryLoginThreeTimesWithWrongPassword(String password) {
        for (int i = 0; i<3; i++)
        {
            loginPage.fillUsernameAndPassword(System.getenv("USER"), System.getenv("PASSWORD"));
            loginPage.logIn();
        }
    }

    public boolean ValidateCaptcha() {
        waitForWebElementToBePresent(captchaPicture);
        return driver.findElements(captcha).isEmpty();
    }

    public void fillUsernameAndPassword(String password){
        usernameField.sendKeys("automation"+ 22);
        passwordField.sendKeys(password);
    }

    public void logIn(){
        logInButton.click();
    }

    public void CloseDriver()
    {
        driver.quit();
    }
}
