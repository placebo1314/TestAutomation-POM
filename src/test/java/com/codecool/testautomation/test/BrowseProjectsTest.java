package com.codecool.testautomation.test;

import com.codecool.testautomation.page.BrowsePage;
import com.codecool.testautomation.page.LoginPage;
import org.junit.jupiter.api.*;

import static com.codecool.testautomation.utility.DriverSingleton.quit;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BrowseProjectsTest {
    private static BrowsePage browsePage;
    static LoginPage loginPage;

    @BeforeAll
    public static void setUp() {
        browsePage = new BrowsePage();
        loginPage = new LoginPage();
        loginPage.getUrl("https://jira-auto.codecool.metastage.net/login.jsp");
        loginPage.fillUsernameAndPassword(System.getenv("USER"), System.getenv("PASSWORD"));
        loginPage.logIn();
    }

    @AfterAll
    public void tearDown() {
        quit();
    }

    @Test
    public void browseProjects() {
        loginPage.getUrl("https://jira-auto.codecool.metastage.net/secure/BrowseProjects.jspa");
        Assertions.assertEquals("Browse projects", browsePage.mainPageHeader.getText());
    }

    @Test
    public void openExistingProject(){
        loginPage.getUrl("https://jira-auto.codecool.metastage.net/projects/MTP/summary");
        Assertions.assertEquals("MTP", browsePage.projectMetaValue.getText());
    }

    @Test
    public void openCOALAProject(){
        loginPage.getUrl("https://jira-auto.codecool.metastage.net/projects/COALA/summary");
        Assertions.assertEquals("COALA", browsePage.projectMetaValue.getText());
    }

    @Test
    public void openJETIProject(){
        loginPage.getUrl("https://jira-auto.codecool.metastage.net/projects/JETI/summary");
        Assertions.assertEquals("JETI", browsePage.projectMetaValue.getText());
    }

    @Test
    public void openTOUCANProject(){
        loginPage.getUrl("https://jira-auto.codecool.metastage.net/projects/TOUCAN/summary");
        Assertions.assertEquals("TOUCAN", browsePage.projectMetaValue.getText());
    }

    @Test
    public void openNonExistingProject() {
        loginPage.getUrl("https://jira-auto.codecool.metastage.net/projects/SOMETHING/summary");
        Assertions.assertEquals("You can't view this project", browsePage.pageError.getText());
    }
}
