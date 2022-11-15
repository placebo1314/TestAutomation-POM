package com.codecool.testautomation.page;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.codecool.testautomation.utility.Utility.*;

public class CreatePage extends BasePage{

    @FindBy (xpath = "//*[@id=\"opsbar-operations_more\"]") public WebElement actionButton;
    @FindBy (xpath = "//button[text()='Cancel']") public WebElement cancelButton;
    @FindBy (xpath = "//div[@id='qf-field-summary']//div[@role='alert']") public WebElement createIssueErrorMessage;
    @FindBy (xpath = "//*[@id=\"create-issue-submit\"]") public WebElement createIssueButton;
    @FindBy (xpath = "//aui-item-link[@id='create-subtask']/a/span") public WebElement createSubClass;
    @FindBy (xpath = "//*[@id=\"delete-issue\"]/a") public WebElement deleteButton;
    @FindBy (xpath = "//*[@id=\"delete-issue-submit\"]") public WebElement finalDeleteButton;
    @FindBy (xpath="//*[@id=\"summary-val\"]") public WebElement issueHeader;
    @FindBy (xpath = "//*[@id=\"find_link\"]") public WebElement issuesButton;
    @FindBy (xpath = "//ul[@class='aui-last']") public WebElement issueScrollDown;
    @FindBy (id ="issuetype-field") public WebElement issueTypeSelector;
    @FindBy (xpath = "//*[@id=\"create_link\"]") public WebElement mainCreateButton;
    @FindBy (xpath = "//a[@id='opsbar-operations_more']") public WebElement moreButton;
    @FindBy (xpath = "//*[@id=\"aui-flag-container\"]/div/div") public WebElement popupMessage;
    @FindBy (xpath = "//input[@id='project-field']") public WebElement projectField;
    @FindBy (css = ".no-results > h2") public WebElement resultPageContent;
    @FindBy (xpath = "(//button[@type='button'])[3]") public WebElement searchButton;
    @FindBy (xpath = "/html//li[@id='issues_new_search_link']") public WebElement searchForIssuesButton;
    @FindBy (xpath = "//*[@id=\"searcher-query\"]") public WebElement searchForIssueField;
    @FindBy (xpath = "//*[@id=\"issuerow12961\"]/td[2]/a") public WebElement subTaskName;
    @FindBy (xpath= "//*[@id=\"summary\"]") public WebElement summaryField;
    @FindBy (xpath = "//*[@id=\"actions_12961\"]")

    public List<String> issueTypesSupposedToBe = Arrays.asList("Bug", "Story", "Task");

    public CreatePage() {
    }

    public void restoreIssue(){
        waitForElementToClick(actionButton);
        clickButton(deleteButton);
        waitForElementToClick(finalDeleteButton);
    }

    public void restoreSubTask(){
        waitForElementToClick(subTaskName);
        waitForElementToClick(actionButton);
        waitForElementToClick(deleteButton);
        clickButton(finalDeleteButton);
    }

    public void createSubTask(){
        clickButton(moreButton);
        clickButton(createSubClass);
        waitForElementToSendText(summaryField, "Sub-task test");
        clickButton(createIssueButton);
    }

    public void clearIssueType(WebDriverWait wait){
        String os = System.getProperty("os.name");
        wait.until(ExpectedConditions.elementToBeClickable(issueTypeSelector));
        if (os.equals("Mac OS X")){
            waitForElementToSendText(issueTypeSelector, Keys.COMMAND + "a");
        }else{
            waitForElementToSendText(issueTypeSelector, Keys.CONTROL + "a");
        }
        issueTypeSelector.sendKeys(Keys.DELETE);
    }

    public void clearProjectField(){
        String os = System.getProperty("os.name");
        if (os.equals("Mac OS X")){
            waitForElementToSendText(projectField, Keys.COMMAND + "a");
        }else{
            waitForElementToSendText(projectField, Keys.CONTROL + "a");
        }
        projectField.sendKeys(Keys.DELETE);
    }

    public void createSpecificIssue(String projectName, String issueType, String summary){
        clearProjectField();
        projectField.sendKeys(projectName);
        projectField.sendKeys(Keys.RETURN);
        clearIssueType(wait);
        issueTypeSelector.sendKeys(issueType);
        wait.until(ExpectedConditions.elementToBeClickable(
                issueTypeSelector)).sendKeys(Keys.RETURN);
        waitForElementToSendText(summaryField, summary);
        waitForElementToClick(createIssueButton);
        waitForWebElementToBePresent(popupMessage);
        waitForElementToClick(driver.findElement(By.partialLinkText("Happy Test")));
    }

    public void createIssueWithEmptySummary(){
        clickButton(mainCreateButton);
        waitForElementToClick(createIssueButton);
        waitForWebElementToBePresent(createIssueErrorMessage);
    }

    public List<String> getIssueTypes(){
        waitForElementToClick(issueTypeSelector);

        List<String> issueTypes = new ArrayList<>();
        issueTypes.add(issueTypeSelector.getAttribute("value"));

        WebElement ul_Element = issueScrollDown;
        List<WebElement> li_All = ul_Element.findElements(By.tagName("li"));


        for (WebElement webElement : li_All) {
            issueTypes.add(webElement.getText());
        }

        clickButton(cancelButton);
        Collections.sort(issueTypes);
        return issueTypes;
    }

    public void setProjectTo(String project){
        clickButton(mainCreateButton);
        clearProjectField();
        projectField.sendKeys(project);
        projectField.sendKeys(Keys.RETURN);
    }

    public void cancelCreation(){
        clickButton(cancelButton);
        wait.until(ExpectedConditions.alertIsPresent());
        driver.switchTo().alert().accept();
    }

    public void fillOutCreation(String projectName, String issueType, String summary){
        clearProjectField();
        projectField.sendKeys(projectName);
        projectField.sendKeys(Keys.RETURN);
        clearIssueType(wait);
        issueTypeSelector.sendKeys(issueType);
        wait.until(ExpectedConditions.elementToBeClickable(
                issueTypeSelector)).sendKeys(Keys.RETURN);
        waitForElementToSendText(summaryField, summary);
    }

    public void validateIssueDoesntExist(){
        waitForElementToClick(issuesButton);
        waitForElementToClick(searchForIssuesButton);
        waitForElementToSendText(searchForIssueField, "Issue Cancel Test");
        clickButton(searchButton);
        waitForWebElementToBePresent(resultPageContent);
    }

//    public void waitForWebElementToBePresent(WebElement webElement){
//        wait.until(ExpectedConditions.visibilityOf(webElement));
//    }
//
//    public void waitForElementToSendText(WebElement webElement, String text){
//        wait.until(ExpectedConditions.elementToBeClickable(webElement)).sendKeys(text);
//
//    }
//    public void waitForElementToClick(WebElement webElement){
//        wait.until(ExpectedConditions.elementToBeClickable(
//                webElement
//        )).click();
//    }
}
