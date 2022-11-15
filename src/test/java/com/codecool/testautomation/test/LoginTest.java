package com.codecool.testautomation.test;

import com.codecool.testautomation.page.LoginPage;
import com.codecool.testautomation.utility.Utility;
import com.opencsv.CSVReader;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import java.io.FileReader;
import java.io.IOException;

import static com.codecool.testautomation.utility.DriverSingleton.quit;

public class LoginTest {
    LoginPage lp;
    String CSV_PATH= "src/test/java/com/codecool/testautomation/data/loginsData.csv";
    private CSVReader csvReader;
    String[] csvCell;

    @BeforeEach
    public void setUp() {
        lp = new LoginPage();
        lp.getUrl("https://jira-auto.codecool.metastage.net/login.jsp");

    }

    @AfterEach
    public void tearDown(){
        quit();
    }

    @Test
    public void logInSuccessful(){
        lp.fillUsernameAndPassword(System.getenv("USER"), System.getenv("PASSWORD"));
        lp.logIn();
        lp.validateLogin();
    }

    @Test
    public void logInUnregistered(){
        lp.fillWrongUsernameAndPassword();
        lp.logIn();
        lp.validateWrongLogin();
    }

    @Test
    public void csvTestLogin() throws IOException {
        csvReader = new CSVReader(new FileReader(CSV_PATH));
        while ((csvCell = csvReader.readNext()) != null) {
            String UserName = csvCell[0];
            String Password = csvCell[1];

            lp.csvTestLogin(UserName, Password);
            lp.logIn();
            lp.validateWrongLogin();
        }
        logInSuccessful();
    }
}