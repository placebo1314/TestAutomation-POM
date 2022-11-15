package com.codecool.testautomation.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PermissionsPage extends BasePage {
    LoginPage loginPage;
    @FindBy(id = "project-issuetypes-container") public static WebElement issueTypesContainer;
    @FindBy(id = "glass-workflow-nav") public static WebElement issueTypesDropDownButton;
    @FindBy(id = "dropdown-issuetypes") public static WebElement issueTypesDropDownContainer;
    @FindBy(id = "glass-permissions-nav") public static WebElement PermissionsMatrixButton;
    @FindBy(xpath = "//a[contains(text(),'View by Permissions')]") public static WebElement ViewByButton;


    public PermissionsPage() {
//        driver = new ChromeDriver();
//        PageFactory.initElements(driver, this);
//        driver.manage().window().maximize();
    }

    public  void  OpenPPProjectSettings()
    {
        driver.get("https://jira-auto.codecool.metastage.net/plugins/servlet/project-config/PP/summary");
    }

    public  void  OpenPPProjectGlassPage()
    {
        driver.get("https://jira-auto.codecool.metastage.net/projects/PP?selectedItem=com.codecanvas.glass:glass");
    }

    public void login()
    {
        loginPage = new LoginPage();
        loginPage.getUrl("https://jira-auto.codecool.metastage.net/login.jsp");
        loginPage.fillUsernameAndPassword(System.getenv("USER"), System.getenv("PASSWORD"));
        loginPage.logIn();
    }


    public List<String> GetAllIssueTypesFromSettings()
    {
        return new ArrayList<String>(Arrays.asList(issueTypesContainer.getText().split("\n")));
    }

    public boolean validateSettingIssues(List<String> requiredTypes) {

        return(GetAllIssueTypesFromSettings().containsAll(requiredTypes));
    }

    public boolean validateDropDown(List<String> issuesInSetting) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(6));
        wait.until(ExpectedConditions.visibilityOf(issueTypesDropDownButton));
        issueTypesDropDownButton.click();
        wait.until(ExpectedConditions.visibilityOf(issueTypesDropDownContainer));
        //Validate DropDown
        for(String s : issuesInSetting)
        {
            String dropDownPath = "//a[contains(.,' " + s + "')]";
            if(!driver.findElement(By.xpath(dropDownPath)).isDisplayed())
                return false;
        }
        return true;
    }

    public boolean validateIcons(List<String> issuesInSetting) {
        //ValidateIcons
        for(String s : issuesInSetting) {
            String spanPath = "//span[contains(@title, '" + s + "')]";
            if(!driver.findElement(By.xpath(spanPath)).isDisplayed())
                return false;
        }
        return true;
    }


    public int findTrInPermissionMatrix(String trName) {
        List<WebElement> matrix = driver.findElements(By.xpath("//td//p[@class = 'title']"));
        int result = 0;
        for (WebElement e : matrix) {
            result++;
            if (e.getText().contains(trName))
                return result;
        }
        return 0;
    }


    public int findThInPermissionMatrix(String thName) {
        List<WebElement> matrix = driver.findElements(By.xpath("//th//b"));
        int result = 1;
        for (WebElement e : matrix)
        {
            result++;
            if (e.getText().contains(thName))
                return result;
        }
        return 0;
    }

    public int findTrInViewByPermissions(String thName) {
        List<WebElement> matrix = (driver.findElements(By.xpath("//tr//td//b")));
        int result = 0;
        for(WebElement e : matrix)
        {
            if(e.getText() != "") {
                result++;
                if (e.getText().contains(thName))
                    return result;
            }
        }
        return 0;
    }

    public int findThInViewByPermissions(String thName) {
        List<WebElement> matrix = driver.findElements(By.xpath("//th"));
        int result = 0;
        for(WebElement e : matrix)
        {
            if(e.getText() != "") {
                result++;
                if (e.getText().contains(thName)) {
                    return result;
                }
            }
        }
        return 0;
    }

    public void goToPermissionsMatrix() {
        PermissionsMatrixButton.click();
    }

    public boolean validatePermissionsMatrix(String targetTr, String targetTh) {
        int tr = findTrInPermissionMatrix(targetTr);
        int th = findThInPermissionMatrix(targetTh);
        String selectedInMatrix = "//div[@id='glass-permissions-matrix-panel']/div/table/tbody/tr[" + tr + "]/td[" + th + "]/div";
        if(!driver.findElements(By.xpath(selectedInMatrix)).isEmpty())
            return true;
        return false;
    }

    public void goToViewBy() {
        ViewByButton.click();
    }

    public boolean validateViewBy(String targetTr, String targetTh) {
        int tr = findTrInViewByPermissions(targetTr);
        int th = findThInViewByPermissions(targetTh);
        String selectedInMatrix = "//*[@id=\"glass-permissions-permissionview-panel\"]/div/table/tbody/tr[" + tr + "]/td[" + th + "]";
        if(driver.findElement(By.xpath(selectedInMatrix)).getText().contains("Application Access: Any logged in user"))
            return true;
        return false;
    }
}
