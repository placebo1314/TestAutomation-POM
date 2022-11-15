package com.codecool.testautomation.test;

import com.codecool.testautomation.page.CreatePage;
import com.codecool.testautomation.page.LoginPage;
import org.junit.jupiter.api.*;

import static com.codecool.testautomation.utility.DriverSingleton.quit;
import static com.codecool.testautomation.utility.Utility.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CreatePageTest {
    private static CreatePage createPage;
    public static LoginPage loginPage;

    @BeforeAll
    public static void setUp() {
        createPage = new CreatePage();
        loginPage = new LoginPage();
        loginPage.getUrl("https://jira-auto.codecool.metastage.net/login.jsp");
        loginPage.fillUsernameAndPassword(System.getenv("USER"), System.getenv("PASSWORD"));
        loginPage.logIn();
    }

    @AfterAll
    public static void tearDown() {
        quit();
    }

//     I can't create sub-task for COALA
    @Test
    public void createCOALASubTask() {
        loginPage.getUrl("https://jira-auto.codecool.metastage.net/browse/COALA-130");
        Assertions.assertEquals("Create sub-task", createPage.issueHeader.getText());
        createPage.createSubTask();
        waitForWebElementToBePresent(createPage.popupMessage);
        Assertions.assertEquals("COALA-130 has been updated.", createPage.popupMessage.getText());
        Assertions.assertEquals("Sub-task test", createPage.subTaskName.getText());

        // Restore
        loginPage.getUrl("https://jira-auto.codecool.metastage.net/browse/COALA-130");
        createPage.restoreSubTask();
    }

//     I can't create sub-task for TOUCAN
    @Test
    public void createTOUCANSubTask() {
        loginPage.getUrl("https://jira-auto.codecool.metastage.net/browse/TOUCAN-132");
        Assertions.assertEquals("Create sub-task", createPage.issueHeader.getText());
        createPage.createSubTask();
        waitForWebElementToBePresent(createPage.popupMessage);
        Assertions.assertEquals("TOUCAN-121 has been updated.", createPage.popupMessage.getText());
        Assertions.assertEquals("Sub-task test", createPage.subTaskName.getText());

        // Restore
        loginPage.getUrl("https://jira-auto.codecool.metastage.net/browse/TOUCAN-121");
        createPage.restoreSubTask();
    }

    @Test
    public void createJETISubTask(){
        loginPage.getUrl("https://jira-auto.codecool.metastage.net/browse/JETI-103");
        Assertions.assertEquals("Create sub-task", createPage.issueHeader.getText());
        createPage.createSubTask();
        waitForWebElementToBePresent(createPage.popupMessage);
        Assertions.assertEquals("JETI-103 has been updated.", createPage.popupMessage.getText());
        Assertions.assertEquals("Sub-task test", createPage.subTaskName.getText());

        // Restore
        loginPage.getUrl("https://jira-auto.codecool.metastage.net/browse/JETI-103");
        createPage.restoreSubTask();
    }

    @Test
    public void createNewIssue() {
        loginPage.getUrl("https://jira-auto.codecool.metastage.net/secure/Dashboard.jspa");
        clickButton(createPage.mainCreateButton);
        createPage.createSpecificIssue("MTP", "Bug", "Happy Test");

        // Restore
        createPage.restoreIssue();
    }

    @Test
    public void createIssueWithEmptySummary(){
        loginPage.getUrl("https://jira-auto.codecool.metastage.net/secure/Dashboard.jspa");
        createPage.createIssueWithEmptySummary();

        Assertions.assertEquals("You must specify a summary of the issue.", createPage.createIssueErrorMessage.getText());
        createPage.cancelCreation();
    }

    @Test
    public void CreateIssueInCOALAProjectWithIssueTypes() {
        loginPage.getUrl("https://jira-auto.codecool.metastage.net/secure/Dashboard.jspa");
        createPage.setProjectTo("COALA");

        Assertions.assertEquals(createPage.issueTypesSupposedToBe, createPage.getIssueTypes());
    }

    @Test
    public void CreateIssueInJETIProjectWithIssueTypes() {
        loginPage.getUrl("https://jira-auto.codecool.metastage.net/secure/Dashboard.jspa");
        createPage.setProjectTo("JETI");

        Assertions.assertEquals(createPage.issueTypesSupposedToBe, createPage.getIssueTypes());
    }

    // I don't have permission to create TOUCAN project
    @Test
    public void CreateIssueInTOUCANProjectWithIssueTypes() {
        loginPage.getUrl("https://jira-auto.codecool.metastage.net/secure/Dashboard.jspa");
        createPage.setProjectTo("TOUCAN");

        Assertions.assertEquals(createPage.issueTypesSupposedToBe, createPage.getIssueTypes());
    }

    @Test
    public void CancelIssueAfterFill() {
        loginPage.getUrl("https://jira-auto.codecool.metastage.net/secure/Dashboard.jspa");
        clickButton(createPage.mainCreateButton);
        createPage.fillOutCreation("MTP", "Bug", "Issue Cancel Test");
        createPage.cancelCreation();
        createPage.validateIssueDoesntExist();

        Assertions.assertEquals("No issues were found to match your search", createPage.resultPageContent.getText());
    }
}
