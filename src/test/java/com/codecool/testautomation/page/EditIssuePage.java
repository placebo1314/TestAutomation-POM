package com.codecool.testautomation.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class EditIssuePage extends BasePage{
//    WebDriver driver;
//    WebDriverWait wait;

    @FindBy(id = "edit-issue")
    public WebElement editButton;

    @FindBy(id = "summary")
    public WebElement summaryField;

    @FindBy(id = "summary-val")
    public WebElement summaryValue;

    @FindBy(id = "description")
    public WebElement descriptionField;

    @FindBy(id = "description-val")
    public WebElement descriptionValue;

    @FindBy(id = "edit-issue-submit")
    public WebElement updateButton;

    @FindBy(xpath = "//*[@id=\"description-wiki-edit\"]/nav/div/div/ul/li[2]/button")
    public WebElement switchTextMode;

    @FindBy(xpath = "//*[@id=\"edit-issue-dialog\"]/footer/div/div/button")
    public WebElement cancelChangesButton;

    @FindBy(id = "fixVersions-textarea")
    public WebElement fixVersionsField;

    @FindBy(css = ".item-delete")
    public WebElement fixVersionDelete;

    @FindBy(id = "action_id_21")
    public WebElement inProgressButton;

    @FindBy(xpath = "//*[@id=\"aui-flag-container\"]/div/div")
    public WebElement updateSuccessMessage;

    public EditIssuePage() {
    }

    public void clickEditIssue(){
        wait.until(ExpectedConditions.elementToBeClickable(
                editButton)).click();
    }

    public void renameSummary(String summaryName){
        wait.until(ExpectedConditions.elementToBeClickable(
                summaryField)).clear();
        summaryField.sendKeys(summaryName);
    }

    public void updateIssue(){
       updateButton.click();
        wait.until(ExpectedConditions.elementToBeClickable(
                updateSuccessMessage));
    }

    public void cancelUpdate(){
        cancelChangesButton.click();
        driver.switchTo().alert().accept();
    }

    public String getSummary(){
        return summaryValue.getText();

    }

    public String getDescription(){
        return descriptionValue.getText();
    }

    public void restoreChanges(){
        editButton.click();
        wait.until(ExpectedConditions.elementToBeClickable(
                summaryField)).clear();
        summaryField.sendKeys("Happy Path");
        wait.until(ExpectedConditions.elementToBeClickable(
                switchTextMode)).click();
        descriptionField.clear();
        summaryField.submit();
    }


    public void addDescription(String description){
        wait.until(ExpectedConditions.elementToBeClickable(
                switchTextMode)).click();
        descriptionField.clear();
        wait.until(ExpectedConditions.elementToBeClickable(
                descriptionField)).sendKeys(description);
    }
}
