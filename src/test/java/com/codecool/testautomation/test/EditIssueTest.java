package com.codecool.testautomation.test;

import com.codecool.testautomation.page.EditIssuePage;
import com.codecool.testautomation.page.LoginPage;
import org.junit.jupiter.api.*;

import static com.codecool.testautomation.utility.DriverSingleton.quit;


public class EditIssueTest {
    private EditIssuePage editIssuePage;


    @BeforeEach
    public void setUp(){
        editIssuePage = new EditIssuePage();
        LoginPage loginPage = new LoginPage();
        loginPage.getUrl("https://jira-auto.codecool.metastage.net/login.jsp");
        loginPage.fillUsernameAndPassword(System.getenv("USER"), System.getenv("PASSWORD"));
        loginPage.logIn();
        loginPage.getUrl("https://jira-auto.codecool.metastage.net/browse/MTP-2096");
    }

    @AfterEach
    public void tearDown(){
        editIssuePage.restoreChanges();
        quit();
    }
    @Test
    public void editExistingIssue(){
        editIssuePage.clickEditIssue();
        editIssuePage.renameSummary("Happy Path Edit");
        editIssuePage.updateIssue();
        Assertions.assertEquals("Happy Path Edit", editIssuePage.getSummary());
    }

    @Test
    public void editIssueCancel(){
        editIssuePage.clickEditIssue();
        editIssuePage.renameSummary("Happy Path Edit");
        editIssuePage.cancelUpdate();
        Assertions.assertEquals("Happy Path", editIssuePage.getSummary());
    }

    @Test
    public void addField(){
        editIssuePage.clickEditIssue();
        editIssuePage.addDescription("new description");
        editIssuePage.updateIssue();
        Assertions.assertEquals("new description",editIssuePage.getDescription());

    }
}
