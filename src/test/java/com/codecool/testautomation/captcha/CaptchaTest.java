package com.codecool.testautomation.captcha;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertFalse;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CaptchaTest {
    CaptchaPage captchaPage;

    @BeforeAll
    public void setUp() {
        captchaPage = new CaptchaPage();
    }
    @BeforeEach
    public void OpenPage()
    {
        captchaPage.OpenLoginPage();
    }
    @AfterAll
    public void tearDown() {
        captchaPage.CloseDriver();
    }

    @Test
    public void CheckCaptcha() {
        captchaPage.TryLoginThreeTimesWithWrongPassword("CCAaa.");

        assertFalse(captchaPage.ValidateCaptcha());
    }

    @Test
    public void CaptchaLoginWrongCaptcha() {
        captchaPage.TryLoginThreeTimesWithWrongPassword("CCAaa.");

        captchaPage.fillUsernameAndPassword("CCAutoTest19.");
        assertFalse(captchaPage.ValidateCaptcha());
    }

}
